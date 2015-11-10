var Cudu;
(function (Cudu) {
    var Lluerna;
    (function (Lluerna) {
        var Cursos;
        (function (Cursos) {
            var Detalle;
            (function (Detalle) {
                var CursoLluernaController = (function () {
                    function CursoLluernaController($scope, $routeParams, cursoService, TypeAhead) {
                        var _this = this;
                        this.$scope = $scope;
                        $scope.typeaheadFormadorOpt = $scope.typeaheadParticipanteOpt = { highlight: true, editable: false };
                        $scope.typeaheadFormadorDts = TypeAhead.formador();
                        $scope.typeaheadParticipanteDts = TypeAhead.participante(+$routeParams.id);
                        $scope.miembroPorIncluir = $scope.participantePorIncluir = null;
                        this.cursoService = cursoService;
                        this.scope = $scope;
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
                            lista = this.scope.curso.formadores;
                        }
                        else {
                            nuevoAsociado = { "id": asociado.id, "nombreCompleto": asociado.nombre + " " + asociado.apellidos };
                            fn = function (cursoId, asociadoId) { return _this.cursoService.añadirParticipante(cursoId, asociadoId); };
                            lista = this.scope.curso.participantes;
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
                    CursoLluernaController.$inject = ['$scope', '$routeParams', 'CursosService', 'Typeahead'];
                    return CursoLluernaController;
                })();
                Detalle.CursoLluernaController = CursoLluernaController;
            })(Detalle = Cursos.Detalle || (Cursos.Detalle = {}));
        })(Cursos = Lluerna.Cursos || (Lluerna.Cursos = {}));
    })(Lluerna = Cudu.Lluerna || (Cudu.Lluerna = {}));
})(Cudu || (Cudu = {}));
angular.module('cuduApp')
    .controller('CursoLluernaController', Cudu.Lluerna.Cursos.Detalle.CursoLluernaController);
