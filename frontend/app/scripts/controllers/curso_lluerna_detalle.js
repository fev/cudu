var Cudu;
(function (Cudu) {
    var Lluerna;
    (function (Lluerna) {
        var Cursos;
        (function (Cursos) {
            var Detalle;
            (function (Detalle) {
                var CursoLluernaController = (function () {
                    function CursoLluernaController($scope, traducciones, $routeParams, cursoService, typeAhead, estados) {
                        var _this = this;
                        this.$scope = $scope;
                        this.traducciones = traducciones;
                        this.$routeParams = $routeParams;
                        this.cursoService = cursoService;
                        this.estados = estados;
                        $scope.typeaheadFormadorOpt = $scope.typeaheadParticipanteOpt = { highlight: true, editable: false };
                        $scope.typeaheadFormadorDts = typeAhead.formador();
                        $scope.typeaheadParticipanteDts = typeAhead.participante(+$routeParams.id);
                        $scope.miembroPorIncluir = $scope.participantePorIncluir = null;
                        $scope.estado = estados.LIMPIO;
                        $scope.erroresValidacion = [];
                        $scope.$on('typeahead:selected', function (e, asociado) { return _this.añadirAsociado(e, asociado); });
                        cursoService.getCurso(+$routeParams.id).success(function (c) {
                            var formadores = _.map(c.formadores, function (f) {
                                f.nuevo = false;
                                return f;
                            });
                            c.formadores = formadores;
                            var participates = _.map(c.formadores, function (f) {
                                f.nuevo = false;
                                return f;
                            });
                            c.participates = participates;
                            $scope.curso = c;
                        });
                    }
                    CursoLluernaController.prototype.añadirAsociado = function (e, asociado) {
                        var _this = this;
                        var fn;
                        var lista;
                        var nuevoAsociado;
                        if (!_.isUndefined(asociado.nombreCompleto)) {
                            nuevoAsociado = { "id": asociado.id, "nombreCompleto": asociado.nombreCompleto };
                            fn = function (cursoId, asociadoId) { return _this.cursoService.añadirFormador(cursoId, asociadoId); };
                            lista = this.$scope.curso.formadores;
                        }
                        else {
                            nuevoAsociado = { "id": asociado.id, "nombreCompleto": asociado.nombre + " " + asociado.apellidos };
                            fn = function (cursoId, asociadoId) { return _this.cursoService.añadirParticipante(cursoId, asociadoId); };
                            lista = this.$scope.curso.participantes;
                            if (lista.length == this.$scope.curso.plazas) {
                                this.$scope.estado = this.estados.CUSTOM;
                                this.$scope.customError = this.traducciones.texto('cursos.maxPlazas');
                                this.$scope.$apply();
                                return;
                            }
                        }
                        if (!_.isUndefined(_.findWhere(lista, { 'id': asociado.id }))) {
                            return;
                        }
                        fn(this.$scope.curso.id, asociado.id).success(function () {
                            nuevoAsociado.nuevo = true;
                            lista.unshift(nuevoAsociado);
                        }).error(function (e) { alert(e); });
                    };
                    CursoLluernaController.prototype.eliminarFormador = function (formadorId) {
                        var _this = this;
                        this.cursoService.eliminarFormador(this.$scope.curso.id, formadorId).success(function () {
                            _.remove(_this.$scope.curso.formadores, function (f) { return f.id == formadorId; });
                        }).error(function (e) { alert(e); });
                    };
                    CursoLluernaController.prototype.eliminarParticipante = function (participanteId) {
                        var _this = this;
                        this.cursoService.eliminarParticipante(this.$scope.curso.id, participanteId).success(function () {
                            _.remove(_this.$scope.curso.participantes, function (p) { return p.id == participanteId; });
                        }).error(function (e) { alert(e); });
                    };
                    CursoLluernaController.prototype.guardar = function () {
                        var _this = this;
                        this.cursoService.guardarCurso(this.$scope.curso)
                            .success(function () { return _this.$scope.estado = _this.estados.OK; })
                            .error(function (data, e) {
                            if (e == 400) {
                                _this.$scope.estado = _this.estados.VALIDACION;
                                _this.$scope.erroresValidacion = data || [];
                            }
                            else {
                                _this.$scope.estado = _this.estados.ERROR;
                            }
                        });
                    };
                    CursoLluernaController.$inject = ['$scope', 'Traducciones', '$routeParams', 'CursosService', 'Typeahead', 'EstadosFormulario'];
                    return CursoLluernaController;
                })();
                Detalle.CursoLluernaController = CursoLluernaController;
            })(Detalle = Cursos.Detalle || (Cursos.Detalle = {}));
        })(Cursos = Lluerna.Cursos || (Lluerna.Cursos = {}));
    })(Lluerna = Cudu.Lluerna || (Cudu.Lluerna = {}));
})(Cudu || (Cudu = {}));
angular.module('cuduApp')
    .controller('CursoLluernaController', Cudu.Lluerna.Cursos.Detalle.CursoLluernaController);
