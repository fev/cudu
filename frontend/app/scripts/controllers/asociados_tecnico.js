'use strict';

angular.module('cuduApp')
  .controller('AsociadosTecnicoController', ['$scope', '$location','AsociadoTecnico', 'Usuario',
  function($scope, $location, AsociadoTecnico, Usuario) {

   $scope.AsociadoFiltro = function() {
        return {
            asociacion: '',
            tipo: '',
            grupoId: '',
            genero: '',
            ramasSeparadasPorComas: '',
            nombreApellido: '',
            inactivo: false,
            orden: '',
            ordenAsc: false,
            certificadoDelitosSexuales: null,
            certificadoVoluntariado: null,
            cursoProteccionInfancia: null,
            compromisoCertificadoInfancia: null
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
        new $scope.AsociadoTipo('Comite', false),
        new $scope.AsociadoTipo('Voluntario', false)
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
        console.log(valores);
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
            genero: valores[indices.genero],
            certificadoDelitosSexuales: valores[indices.certificado_delitos_sexuales],
            certificadoVoluntariado: valores[indices.certificado_voluntariado],
            cursoProteccionInfancia: valores[indices.curso_proteccion_infancia],
            compromisoCertificadoInfancia: valores[indices.compromiso_certificado_infancia]
        };
    };

    $scope.obternerAsociados = function() {
        var me = this;
        if (me.scroll.isBusy || !me.scroll.isEnabled) {
            return;
        }

        me.scroll.isBusy = true;
        AsociadoTecnico.listado(me.scroll.pagina, me.filtro).success(function(data) {
            console.log(data);
            var asociados = _.map(data.datos, function(a) { return me.bindAsociado(a, data.campos); });
            console.log(asociados);
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
        Usuario.setCookie('FILTROS', btoa(JSON.stringify(me.filtro)));
    };

    $scope.filtraPorGenero = function(genero) {
        var me = this;
        if(me.filtro.genero === genero) {
            me.desactivarGenero();
        }
        else {
            me.filtro.genero = genero;
        }

        me.filtraAsociados();
    };

    $scope.filtraPorCertificadoDelitosSexuales = function(certificadoDelitosSexuales) {
        var me = this;
        var isTrueSet = null;
        if(certificadoDelitosSexuales === 'true') isTrueSet = true;
        if(certificadoDelitosSexuales === 'false') isTrueSet = false;


        if(me.filtro.certificadoDelitosSexuales === isTrueSet) {
            me.desactivarCertificadoDelitosSexuales();
        }
        else {
            me.filtro.certificadoDelitosSexuales = isTrueSet;
        }
        me.filtraAsociados();
    };

    $scope.filtraPorCertificadoVoluntariado = function(certificadoVoluntariado) {
        var me = this;
        var isTrueSet =null;
        if(certificadoVoluntariado === 'true') isTrueSet = true;
        if(certificadoVoluntariado === 'false') isTrueSet = false;


        if(me.filtro.certificadoVoluntariado === isTrueSet) {
            me.desactivarCertificadoVoluntariado();
        }
        else {
            me.filtro.certificadoVoluntariado = isTrueSet;
        }
        me.filtraAsociados();
    };

    $scope.filtraPorCursoProteccionInfancia = function(cursoProteccionInfancia) {
        var me = this;
        var isTrueSet =null;
        if(cursoProteccionInfancia === 'true') isTrueSet = true;
        if(cursoProteccionInfancia === 'false') isTrueSet = false;


        if(me.filtro.cursoProteccionInfancia === isTrueSet) {
            me.desactivarCursoProteccionInfancia();
        }
        else {
            me.filtro.cursoProteccionInfancia = isTrueSet;
        }
        me.filtraAsociados();
    };

      $scope.filtraPorCompromisoCertificadoInfancia = function(compromisoCertificadoInfancia) {
          var me = this;
          var isTrueSet =null;
          if(compromisoCertificadoInfancia === 'true') isTrueSet = true;
          if(compromisoCertificadoInfancia === 'false') isTrueSet = false;


          if(me.filtro.compromisoCertificadoInfancia === isTrueSet) {
              me.desactivarCompromisoCertificadoInfancia();
          }
          else {
              me.filtro.compromisoCertificadoInfancia = isTrueSet;
          }
          me.filtraAsociados();
      };

    $scope.desactivarGenero = function() {
        var me = this;
        me.filtro.genero = "";
    };

    $scope.desactivarCertificadoDelitosSexuales = function() {
        var me = this;
        me.filtro.certificadoDelitosSexuales = null;
    };

    $scope.desactivarCertificadoVoluntariado = function() {
        var me = this;
        me.filtro.certificadoVoluntariado = null;
    };

    $scope.desactivarCursoProteccionInfancia = function() {
        var me = this;
        me.filtro.cursoProteccionInfancia = null;
    };

    $scope.desactivarCompromisoCertificadoInfancia = function() {
        var me = this;
        me.filtro.compromisoCertificadoInfancia = null;
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
            me.filtro.tipo = '';
        }
        else {
            me.filtroAsociadoTipo.activar(tipo);
            me.filtro.tipo = tipo;
        }
        me.filtraAsociados();
    };

    $scope.limpiarFiltros = function() {
      var me = this;
      if (me.filtroAsociadoTipo.isActivo('Joven')) {
          me.filtroAsociadoTipo.desactivar('Joven');
      }
      if (me.filtroAsociadoTipo.isActivo('Kraal')) {
          me.filtroAsociadoTipo.desactivar('Kraal');
      }
      if (me.filtroAsociadoTipo.isActivo('Comite')) {
          me.filtroAsociadoTipo.desactivar('Comite');
      }
      if (me.filtroAsociadoTipo.isActivo('Voluntario')) {
          me.filtroAsociadoTipo.desactivar('Voluntario');
      }
      document.getElementById("inactivos").checked = false;

      $scope.filtro = new $scope.AsociadoFiltro();
      $scope.grupoSeleccionado = { id: -1, nombre: 'Todos' };
      $scope.grupos = [$scope.grupoSeleccionado];
      AsociadoTecnico.grupos().success(function(data) {
          $scope.grupos = $scope.grupos.concat(data);
      });
      $scope.limpiarFiltro();

    };

    $scope.filtraGrupo = function(id) {
        var me = this.$parent;
        me.grupoSeleccionado = _.find(me.grupos, { 'id': id});
        Usuario.setCookie('GRUPOSELECCIONADO', btoa(JSON.stringify(me.grupoSeleccionado)));
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
        { key: 'tipo', value: 'tipoAsc'},
        { key: 'rama', value: 'ramaAsc'},
        { key: 'id', value: 'idAsc'}
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

    var cookieFiltro= Usuario.getCookie('FILTROS');
    if( cookieFiltro!='' && cookieFiltro!=null){
      //Si existe la cookie FILTROS, aplica los filtros
      var objetoFiltros=JSON.parse(atob(cookieFiltro));
      $scope.filtro=objetoFiltros;
      $scope.filtraAsociados();

      // filtro tiene...:

      // .género
      if ( $scope.filtro.genero != '' ) document.getElementById($scope.filtro.genero).className+=" active";
      // .certificadoDelitosSexuales
      if ( $scope.filtro.certificadoDelitosSexuales != null ) {
        if($scope.filtro.certificadoDelitosSexuales)
          document.getElementById("siCertificadoDelitosSexuales").className+=" active";
        else
          document.getElementById("noCertificadoDelitosSexuales").className+=" active";
      }
      // .certificadoVoluntariado
      if ( $scope.filtro.certificadoVoluntariado != null ) {
        if($scope.filtro.certificadoVoluntariado)
          document.getElementById("siCertificadoVoluntariado").className+=" active";
        else
          document.getElementById("noCertificadoVoluntariado").className+=" active";
      }
       // .cursoProteccionInfancia
       if ( $scope.filtro.cursoProteccionInfancia != null ) {
        if($scope.filtro.cursoProteccionInfancia)
          document.getElementById("siCursoProteccionInfancia").className+=" active";
        else
          document.getElementById("noCursoProteccionInfancia").className+=" active";
      }

      // .nombreApellido ---> este filtro no se mantiene en la vista.
      // se muestra el texto cuando se recarga la página pero no cuando vienes de 'Volver'
      window.onload=function(){
          document.getElementById("filtroNombre").value = $scope.filtro.nombreApellido;
      };


      // .ramasSeparadasPorComas
      if ( $scope.filtro.ramasSeparadasPorComas != '' ) {
          if ($scope.esRama('colonia')){ document.getElementById("colonia").className+=" active";}
          if ($scope.esRama('manada')){ document.getElementById("manada").className+=" active";}
          if ($scope.esRama('exploradores')){ document.getElementById("exploradores").className+=" active";}
          if ($scope.esRama('expedicion')){ document.getElementById("expedicion").className+=" active";}
          if ($scope.esRama('ruta')){ document.getElementById("ruta").className+=" active";}
      }

      // .tipo
      if ( $scope.filtro.tipo != '' ) {
        if ($scope.filtroAsociadoTipo.isActivo($scope.filtro.tipo)) {
            $scope.filtroAsociadoTipo.desactivar($scope.filtro.tipo);
        }
        else {
            $scope.filtroAsociadoTipo.activar($scope.filtro.tipo);
        }
      }

      // .grupoId
      if ( $scope.filtro.grupoId != '' ) {
        var cookieGrupoSeleccionado= Usuario.getCookie('GRUPOSELECCIONADO');
        if( cookieGrupoSeleccionado!='' && cookieGrupoSeleccionado!=null){
          //Si existe la cookie GRUPOSELECCIONADO,
          var objetoGrupoSeleccionado=JSON.parse(atob(cookieGrupoSeleccionado));
          $scope.grupoSeleccionado=objetoGrupoSeleccionado;
          document.getElementById("verGrupo").className="btn-group ";
          var grupoTexto= document.getElementById("filtroGrupo").firstChild;
          document.getElementById("filtroGrupo").replaceChild(document.createTextNode($scope.grupoSeleccionado.nombre+' '), grupoTexto);
        }
      }

      // .inactivo
      if ($scope.filtro.inactivo){
        document.getElementById("inactivos").checked = true;
      }

      // .orden y .ordenAsc
      // TODO - cuando el filtro ordenAsc==true y viene de otra página, para cambiar a ordenAsc==false hay que hacer dos veces clic.
      if ( $scope.filtro.orden !='' &&  $scope.filtro.ordenAsc) {
        document.getElementById($scope.filtro.orden).className+="dropup";
      }

      // .asociacion
      if ( $scope.filtro.asociacion != '' ) {
        document.getElementById($scope.filtro.asociacion).className+=" active";
      }
    }
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
