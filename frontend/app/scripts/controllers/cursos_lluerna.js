var Cudu;
(function (Cudu) {
    var Lluerna;
    (function (Lluerna) {
        var Cursos;
        (function (Cursos) {
            var Curso = (function () {
                function Curso() {
                }
                return Curso;
            })();
            Cursos.Curso = Curso;
            var CursosService = (function () {
                function CursosService($http) {
                    this.http = $http;
                }
                CursosService.prototype.listarCursos = function () {
                    var result = this.http.get("/api/lluerna/curso/completo");
                    return result;
                };
                CursosService.prototype.getCurso = function (id) {
                    var result = this.http.get("/api/lluerna/curso/" + id);
                    return result;
                };
                CursosService.prototype.añadirFormador = function (cursoId, formadorId) {
                    return this.http.post('/api/lluerna/curso/' + cursoId + '/formadores', formadorId);
                };
                CursosService.prototype.eliminarFormador = function (cursoId, formadorId) {
                    return this.http.delete('/api/lluerna/curso/' + cursoId + '/formadores/' + formadorId);
                };
                CursosService.prototype.añadirParticipante = function (cursoId, participanteId) {
                    return this.http.post('/api/lluerna/curso/' + cursoId + '/participantes', participanteId);
                };
                CursosService.prototype.eliminarParticipante = function (cursoId, participanteId) {
                    return this.http.delete('/api/lluerna/curso/' + cursoId + '/participantes/' + participanteId);
                };
                CursosService.prototype.guardarCurso = function (curso) {
                    return this.http.put('/api/lluerna/curso/' + curso.id, curso);
                };
                CursosService.prototype.crearCurso = function (curso) {
                    return this.http.post('/api/lluerna/curso', curso);
                };
                CursosService.$inject = ['$http'];
                return CursosService;
            })();
            Cursos.CursosService = CursosService;
            var CursosController = (function () {
                function CursosController($scope, $location, service) {
                    var _this = this;
                    this.service = service;
                    this.cursos = [];
                    this.location = $location;
                    service.listarCursos().success(function (r) { return _this.cursos = r.content; });
                    $scope.cursos = this.cursos;
                }
                CursosController.prototype.detalle = function (id) {
                    this.location.path('/lluerna/curso/' + id);
                };
                CursosController.$inject = ['$scope', '$location', 'CursosService'];
                return CursosController;
            })();
            Cursos.CursosController = CursosController;
        })(Cursos = Lluerna.Cursos || (Lluerna.Cursos = {}));
    })(Lluerna = Cudu.Lluerna || (Cudu.Lluerna = {}));
})(Cudu || (Cudu = {}));
angular.module('cuduApp')
    .controller('CursosController', Cudu.Lluerna.Cursos.CursosController)
    .service('CursosService', Cudu.Lluerna.Cursos.CursosService);
