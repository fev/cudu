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
            inactivo: false,
            orden: '',
            ordenAsc: false,
            certificadoDelitosSexuales: null
        };
    };

    $scope.AsociadoTipo = function(tipo, activo) {
        var me = this;
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
        new $scope.AsociadoTipo('Kraal', false),
        new $scope.AsociadoTipo('Joven', false),
        new $scope.AsociadoTipo('Comite', false)
    ];

    $scope.FiltroAsociadoTipo = function(asociadoTipos) {
        var me = this;
        me.asociadoTipos = asociadoTipos;

        me.getAsociadoTipo = function(tipo) {
            return _.find(me.asociadoTipos, function(a) { return tipo === a.getTipo(); });
        };

        me.activar = function(tipo) {
            var asociadoTipo = me.getAsociadoTipo(tipo);
            asociadoTipo.activar();
            var restoTipos = _.without(me.asociadoTipos, asociadoTipo);
            _.forEach(restoTipos, function (t) { t.desactivar(); });
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
                this.isEnabled = false;
            }
        };
    };

    $scope.bindAsociado = function(valores, indices) {
        return {
            grupoNombre: valores[indices.grupo_nombre],
            id: valores[indices.id],
            nombre: valores[indices.nombre],
            apellidos: valores[indices.apellidos],
            tipo: valores[indices.tipo],
            rama: valores[indices.rama],
            email: valores[indices.email],
            telefono: valores[indices.telefono],
            usuario: valores[indices.usuario_activo],
            activo: valores[indices.activo],
            creado: valores[indices.fecha_alta],
            actualizado: valores[indices.fecha_actualizacion],
            baja: valores[indices.fecha_baja],
            sexo: valores[indices.sexo],
            certificadoDelitosSexuales: valores[indices.certificado_delitos_sexuales]
        };
    };

    $scope.obternerAsociados = function() {
        var me = this;
        if (me.scroll.isBusy || !me.scroll.isEnabled) {
            return;
        }

        me.scroll.isBusy = true;
        AsociadoTecnico.listado(me.scroll.pagina, me.filtro).success(function(data) {
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
        var me = this;
        me.scroll = new me.Scroll();
        me.asociados = [];
        me.obternerAsociados();
    };

    $scope.filtraPorSexo = function(sexo) {
        var me = this;
        if(me.filtro.sexo === sexo) {
            me.desactivarSexo();
        }
        else {
            me.filtro.sexo = sexo;
        }

        me.filtraAsociados();
    };

    $scope.filtraPorCertificado = function(certificado) {
        var me = this;
        var isTrueSet =null;
        if(certificado === 'true') isTrueSet = true;
        if(certificado === 'false') isTrueSet = false;


        if(me.filtro.certificadoDelitosSexuales === isTrueSet) {
            me.desactivarCertificado();
        }
        else {
            me.filtro.certificadoDelitosSexuales = isTrueSet;
        }
        me.filtraAsociados();
    };

    $scope.desactivarSexo = function() {
        var me = this;
        me.filtro.sexo = null;
    };

    $scope.desactivarCertificado = function() {
        var me = this;
        me.filtro.certificadoDelitosSexuales = null;
    };

    $scope.filtraPorNombre = function() {
        var me = this;
        me.filtro.nombreApellido = me.busqueda;

        me.filtraAsociados();
    };

    $scope.limpiarFiltro = function() {
        var me = this;
        me.filtro.nombreApellido = '';
        document.getElementById("filtroNombre").value = "";

        me.filtraAsociados();
    };

    $scope.filtraRama = function(rama) {
        var me = this;
        var lista = _.words(me.filtro.ramasSeparadasPorComas);
        if (me.esRama(rama)) {
            _.remove(lista, function(r) { return r === rama; });
        }
        else {
        lista.push(rama);
        }
        me.filtro.ramasSeparadasPorComas = lista.join();
        me.filtraAsociados();
    };

    $scope.esRama = function(rama) {
        var me = this;
        return me.filtro.ramasSeparadasPorComas.indexOf(rama) > -1;
    };

    $scope.activar = function(tipo) {
        var me = this;
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
        var me = this.$parent;
        me.grupoSeleccionado = _.find(me.grupos, { 'id': id});
        me.filtro.grupoId = id === -1 ? '' : id;
        me.filtraAsociados();
    };

    $scope.filtraPropiedad = function(clave, valor) {
        var me = this;
        if(me.filtro[clave] === valor) {
            me.filtro[clave] = '';
            valor = '';
        } else {
            me.filtro[clave] = valor;
        }

        me.obtenerGruposAsociacion(clave, valor);
        me.filtraAsociados();
    };

    $scope.obtenerGruposAsociacion = function(clave, valor) {
        var me = this;
        if(clave === 'asociacion') {
            me.deseleccionaGrupo();
            me.grupos = [me.grupoSeleccionado];
            AsociadoTecnico.grupos(valor).success(function(data) {
                me.grupos = me.grupos.concat(data);
            });
        }
    };

    $scope.deseleccionaGrupo = function() {
        var me = this;
        me.filtro.grupoId = '';
        var todos = _.find(me.grupos, function(g) {
            return g.id === -1;
        });
        me.grupoSeleccionado = todos;
    };

    $scope.verGrupo = function() {
        $location.path('/grupo/' + $scope.grupoSeleccionado.id);
    };

    $scope.mostrarInactivos = function() {
        var me = this;
          me.filtro.inactivo = !me.filtro.inactivo;
          me.filtraAsociados();
    };

    $scope.verAsociado = function(id) {
        $location.path('/asociado/' + id);
    };

    $scope.ordenColumnas = [
        { key: 'grupo', value: 'grupoAsc'},
        { key: 'nombre', value: 'nombreAsc'},
        { key: 'apellidos', value: 'apellidosAsc'},
        { key: 'tipo', value: 'tipoAsc'}
    ];

    $scope.ordenar = function(columna) {
        var me = this;
        var ordenColumna = _.find(me.ordenColumnas, function(c) { return c.key === columna;});
        if(ordenColumna) {
            me[ordenColumna.value] = !me[ordenColumna.value];
            me.filtro.orden = columna;
            me.filtro.ordenAsc = me[ordenColumna.value];
        }

        me.filtraAsociados();
    };

    $scope.filtro = new $scope.AsociadoFiltro();
    $scope.scroll = new $scope.Scroll();
    $scope.filtroAsociadoTipo = new $scope.FiltroAsociadoTipo($scope.asociadoTipos);
    $scope.grupoSeleccionado = { id: -1, nombre: 'Todos' };
    $scope.grupos = [$scope.grupoSeleccionado];
    AsociadoTecnico.grupos().success(function(data) {
        $scope.grupos = $scope.grupos.concat(data);
    });
  }])
  .factory('AsociadoTecnico', function($http) {
      return {
          listado: function(pagina, filtro) {
            return $http.get('api/tecnico/asociado/?page=' + pagina, { params: filtro });
          },
          grupos: function(asociacion) {
              if(_.isUndefined(asociacion)) {
                asociacion = '';
              }

              var url = 'api/grupo/all?size=200&asociacion=' + asociacion;
              return $http.get(url);
          }
      };
  });
