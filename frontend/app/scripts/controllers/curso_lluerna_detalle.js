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
                        this.$scope = $scope;
                        this.cursoService = cursoService;
                        $scope.typeaheadFormadorOpt = $scope.typeaheadParticipanteOpt = { highlight: true, editable: false };
                        $scope.typeaheadFormadorDts = TypeAhead.formador();
                        $scope.typeaheadParticipanteDts = TypeAhead.participante(+$routeParams.id);
                        $scope.miembroPorIncluir = $scope.participantePorIncluir = null;
                        $scope.$on('typeahead:selected', function (e, miembro) {
                            if (!_.isUndefined(_.findWhere($scope.curso.formadores, { 'id': miembro.id }))) {
                                return;
                            }
                            cursoService.añadirFormador($scope.curso.id, miembro.id).success(function () {
                                $scope.curso.formadores.unshift({ "id": miembro.id, "nombreCompleto": miembro.nombreCompleto, "nuevo": true });
                            }).error(function (e) { alert(e); });
                        });
                        cursoService.getCurso(+$routeParams.id).success(function (c) {
                            var formadores = _.map(c.formadores, function (f) {
                                f.nuevo = false;
                                return f;
                            });
                            c.formadores = formadores;
                            $scope.curso = c;
                        });
                    }
                    CursoLluernaController.prototype.eliminarFormador = function (formadorId) {
                        var _this = this;
                        this.cursoService.eliminarFormador(this.$scope.curso.id, formadorId).success(function () {
                            _.remove(_this.$scope.curso.formadores, function (f) { return f.id == formadorId; });
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
