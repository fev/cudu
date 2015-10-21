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
                        $scope.typeaheadFormadorDts = TypeAhead.miembro();
                        $scope.miembroPorIncluir = null;
                        var id = +$routeParams.id;
                        var curso = cursoService.getCurso(id).success(function (c) { return $scope.curso = c; });
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
