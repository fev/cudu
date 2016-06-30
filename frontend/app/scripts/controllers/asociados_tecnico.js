'use strict';

angular.module('cuduApp')
  .controller('AsociadosTecnicoController', ['$scope', '$location','AsociadoTecnico',
  function($scope, $location, AsociadoTecnico) {

   $scope.AsociadoFiltro = function() {
        return {
            asociacion: '',
            tipo: '',
            grupoId: '',
            sexo: '',
            ramasSeparadasPorComas: '',
            nombreApellido: '',
            activo: true,
            orden: '',
            ordenAsc: false
        };
    };
    
    $scope.AsociadoTipo = function(tipo, activo) {
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
    
    $scope.asociadoTipos = [
        new $scope.AsociadoTipo("Kraal", false),
        new $scope.AsociadoTipo("Joven", false),
        new $scope.AsociadoTipo("Comite", false)
    ];
    
    $scope.FiltroAsociadoTipo = function(asociadoTipos) {
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
    
    $scope.Scroll = function() {
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
      
    $scope.bindAsociado = function(valores, indices) {
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
      
    $scope.obternerAsociados = function() {
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
    
    $scope.filtraAsociados = function() {
        const me = this;
        me.scroll = new me.Scroll();
        me.asociados = [];
        me.obternerAsociados();  
    };
    
    $scope.filtraPorSexo = function(sexo) {
        const me = this;
        if(me.filtro.sexo === sexo) {
            me.desactivarSexo();
        }
        else {
            me.filtro.sexo = sexo;
        }
        
        me.filtraAsociados();
    };

    $scope.desactivarSexo = function() {
        const me = this;
        me.filtro.sexo = null;
    };
    
    $scope.filtraPorNombre = function() {
        const me = this;
        me.filtro.nombreApellido = me.busqueda;
        
        me.filtraAsociados();
    };
    
    $scope.limpiarFiltro = function() {
        const me = this;
        me.filtro.nombreApellido = '';
        
        me.filtraAsociados();
    };
    
    $scope.filtraRama = function(rama) {
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

    $scope.esRama = function(rama) {
        const me = this;
        return me.filtro.ramasSeparadasPorComas.indexOf(rama) > -1;
    };
    
    $scope.activar = function(tipo) {
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
    
    $scope.filtraGrupo = function(id) {
        const me = this.$parent;
        me.grupoSeleccionado = _.find(me.grupos, { 'id': id});
        me.filtro.grupoId = id === -1 ? '' : id;
        me.filtraAsociados();
    };
    
    $scope.filtraPropiedad = function(clave, valor) {
        const me = this;
        if(me.filtro[clave] === valor) {
            me.filtro[clave] = '';
        } else {
            me.filtro[clave] = valor;   
        }
        me.filtraAsociados();
    };
    
    $scope.verGrupo = function() {
        const me = this;
        $location.path('/grupo/' + $scope.grupoSeleccionado.id);
    };
    
    $scope.mostrarInactivos = function() {
        const me = this;
          me.filtro.activo = !me.filtro.activo;
          me.filtraAsociados(); 
    };
    
    $scope.verAsociado = function(id) {
        const me = this;
        $location.path('/asociado/' + id);
    };
    
    $scope.ordenColumnas = [
        { key: 'grupo', value: 'grupoAsc'},
        { key: 'nombre', value: 'nombreAsc'},
        { key: 'apellidos', value: 'apellidosAsc'},
        { key: 'tipo', value: 'tipoAsc'}
    ];
    
    $scope.ordenar = function(columna) {
        const me = this;
        let ordenColumna = _.find(me.ordenColumnas, function(c) { return c.key === columna}); 
        if(ordenColumna) {
            me[ordenColumna.value] = !me[ordenColumna.value];
            me.filtro.orden = columna;
            me.filtro.ordenAsc = me[ordenColumna.value];
        }
        
        me.filtraAsociados();
    } 

    $scope.filtro = new $scope.AsociadoFiltro();
    $scope.scroll = new $scope.Scroll();
    $scope.filtroAsociadoTipo = new $scope.FiltroAsociadoTipo($scope.asociadoTipos);
    $scope.grupoSeleccionado = { id: -1, nombre: 'Todos' };
    $scope.grupos = [$scope.grupoSeleccionado];
    AsociadoTecnico.grupos().success(data => {
        $scope.grupos = $scope.grupos.concat(data.content);
    });
  }])
  .factory('AsociadoTecnico', function($http) {
      return {
          listado: function(pagina, filtro) {
            return $http.get('api/tecnico/asociado/?page=' + pagina, { params: filtro });
          },
          grupos: function() {
              return $http.get('api/grupo/all?size=200');
          }
      }
  });