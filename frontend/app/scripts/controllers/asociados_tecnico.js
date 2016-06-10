'use strict';

angular.module('cuduApp')
  .controller('AsociadosTecnicoController', ['$scope', 'AsociadoTecnico',
  function($scope, AsociadoTecnico) {
    const me = this;
    me.$scope = $scope;
    me.service = AsociadoTecnico;
    me.$scope.grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3', 'Grupo 4']; // TODO
    me.$scope.grupoPorDefecto = $scope.grupos[0]; // TODO

    me.AsociadoFiltro = function() {
        return {
            asociacion: '',
            tipo: '',
            grupoId: '',
            sexo: '',
            ramasSeparadasPorComas: '',
            nombreApellido: ''
        };
    };
    
    me.AsociadoTipo = function(tipo, activo) {
        const me = this;
        me.tipo = tipo;
        me.activo = activo;
        
        return {
            getTipo: function() { return me.tipo; },
            activar: function() { me.activo = true; },
            desactivar:  function() { me.activo = false; },
            isActivo: function() { return me.activo; }  
        };
    };
    me.asociadoTipos = [
        new me.AsociadoTipo("Kraal", false),
        new me.AsociadoTipo("Joven", false),
        new me.AsociadoTipo("Comite", false)
    ];
    
    me.$scope.FiltroAsociadoTipo = function(asociadoTipos) {
        const me = this;
        me.asociadoTipos = asociadoTipos;
        
        me.getAsociadoTipo = function(tipo) {
            return _.find(me.asociadoTipos, (a) => { return tipo === a.getTipo() });
        };
        
        me.activar = function(tipo) {
            var asociadoTipo = me.getAsociadoTipo(tipo);
            asociadoTipo.activar();
            var restoTipos = _.without(me.asociadoTipos, asociadoTipo);
            _.forEach(restoTipos, (t) => t.desactivar());
        };
        
        me.desactivar = function(tipo) {
            me.getAsociadoTipo(tipo).desactivar();
        };
        
        me.isActivo = function(tipo) {
            return me.getAsociadoTipo(tipo).isActivo();
        };
                
        return me;
    };
    
    me.$scope.Scroll = function() {
        return {
            isBusy: false,
            isEnabled: true,
            pagina: 0,
            limite: 0,    
            disable: function() {
                const me = this;
                me.isEnabled = false;
            }    
        };
    };
      
    me.$scope.bindAsociado = function(valores, indices) {
        return {
            grupoId: valores[indices.grupo_id],
            id: valores[indices.id],
            nombre: valores[indices.nombre],
            apellidos: valores[indices.apellidos],
            tipo: valores[indices.tipo],
            rama: valores[indices.rama],
            email: valores[indices.email],
            telefono: valores[indices.telefono],
            usuario: valores[indices.usuario_activo],
            creado: valores[indices.fecha_alta],
            actualizado: valores[indices.fecha_actualizacion],
            baja: valores[indices.fecha_baja],
            sexo: valores[indices.sexo]
        };
    };
      
    me.$scope.obternerAsociados = function() {
        const me = this;
        if (me.scroll.isBusy || !me.scroll.isEnabled) {
            return;
        }
        
        me.scroll.isBusy = true;
        AsociadoTecnico.listado(me.scroll.pagina, me.filtro).success(data => {
            const me = this;
            var asociados = _.map(data.datos, function(a) { return me.bindAsociado(a, data.campos); });
            if(me.scroll.pagina === 0) {
                me.scroll.limite = data.total;
            }
            if(_.isUndefined(me.asociados)) {
                me.asociados = asociados;
            } else {
                me.asociados.push.apply(me.asociados, asociados);   
            }
            
            if(me.asociados.length >= me.scroll.limite) {
                me.scroll.disable();
            }
            
            me.scroll.isBusy = false;
            me.scroll.pagina = me.scroll.pagina + 1;
        });
    };
    
    me.$scope.filtraAsociados = function() {
        const me = this;
        me.scroll = new me.Scroll();
        me.asociados = [];
        me.obternerAsociados();  
    };
    
    me.$scope.filtraPorSexo = function(sexo) {
        const me = this;
        if(me.filtro.sexo === sexo) {
            me.desactivarSexo();
        }
        else {
            me.filtro.sexo = sexo;
        }
        
        me.filtraAsociados();
    };

    me.$scope.desactivarSexo = function() {
        const me = this;
        me.filtro.sexo = null;
    };
    
    me.$scope.filtraPorNombre = function() {
        const me = this;
        me.filtro.nombreApellido = me.busqueda;
        
        me.filtraAsociados();
    };
    
    me.$scope.limpiarFiltro = function() {
        const me = this;
        me.filtro.nombreApellido = '';
        
        me.filtraAsociados();
    };
    
    me.$scope.filtraRama = function(rama) {
        const me = this;
        let lista = _.words(me.filtro.ramasSeparadasPorComas);
        if (me.esRama(rama)) {
        _.remove(lista, r => r === rama);
        }
        else {
        lista.push(rama);
        }
        me.filtro.ramasSeparadasPorComas = lista.join();
        me.filtraAsociados();
    };

    me.$scope.esRama = function(rama) {
        const me = this;
        return me.filtro.ramasSeparadasPorComas.indexOf(rama) > -1;
    };
    
    me.$scope.activar = function(tipo) {
        const me = this;
        if (me.filtroAsociadoTipo.isActivo(tipo)) {
            me.filtroAsociadoTipo.desactivar(tipo);
        }
        else {
            me.filtroAsociadoTipo.activar(tipo);
            me.filtro.tipo = tipo;
        }
        me.filtraAsociados();
    };

    me.$scope.filtro = new me.AsociadoFiltro();
    me.$scope.scroll = new me.$scope.Scroll();
    me.$scope.filtroAsociadoTipo = new me.$scope.FiltroAsociadoTipo(me.asociadoTipos);
  }])
  .factory('AsociadoTecnico', function($http) {
      return {
          listado: function(pagina, filtro) {
            return $http.get('api/tecnico/asociado/?page=' + pagina, { params: filtro });
          }
      }
  });