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
                        $scope.typeaheadFormadorOpt = { highlight: true, editable: false };
                        $scope.typeaheadFormadorDts = TypeAhead.formador();
                        $scope.miembroPorIncluir = null;
                        $scope.$on('typeahead:selected', function (e, miembro) {
                            if (!_.isUndefined(_.findWhere($scope.curso.formadores, { 'id': miembro.id }))) {
                                return;
                            }
                            $scope.curso.formadores.unshift({ "id": miembro.id, "nombreCompleto": miembro.nombreCompleto, "nuevo": true });
                            $scope.$apply();
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
