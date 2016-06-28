var Cudu;
(function (Cudu) {
    var Tecnicos;
    (function (Tecnicos) {
        var Asociados;
        (function (Asociados) {
            var AsociadoTipo = (function () {
                function AsociadoTipo(tipo, activo) {
                    this.tipo = tipo;
                    this.activo = activo;
                }
                AsociadoTipo.prototype.getTipo = function () { return this.tipo; };
                AsociadoTipo.prototype.activar = function () { this.activo = true; };
                AsociadoTipo.prototype.desactivar = function () { this.activo = false; };
                AsociadoTipo.prototype.isActivo = function () { return this.activo; };
                return AsociadoTipo;
            }());
            Asociados.AsociadoTipo = AsociadoTipo;
            var FiltroAsociadoTipo = (function () {
                function FiltroAsociadoTipo() {
                    this.tipoAsociados = [];
                    this.tipoAsociados.push(new AsociadoTipo("Kraal", false));
                    this.tipoAsociados.push(new AsociadoTipo("Joven", false));
                    this.tipoAsociados.push(new AsociadoTipo("Comite", false));
                }
                FiltroAsociadoTipo.prototype.getAsociadoTipo = function (tipo) {
                    return _.find(this.tipoAsociados, function (a) { return tipo === a.getTipo(); });
                };
                FiltroAsociadoTipo.prototype.activar = function (tipo) {
                    var asociadoTipo = this.getAsociadoTipo(tipo);
                    asociadoTipo.activar();
                    var restoTipos = _.without(this.tipoAsociados, asociadoTipo);
                    _.forEach(restoTipos, function (t) { return t.desactivar(); });
                };
                FiltroAsociadoTipo.prototype.desactivar = function (tipo) {
                    this.getAsociadoTipo(tipo).desactivar();
                };
                FiltroAsociadoTipo.prototype.isActivo = function (tipo) {
                    return this.getAsociadoTipo(tipo).isActivo();
                };
                return FiltroAsociadoTipo;
            }());
            Asociados.FiltroAsociadoTipo = FiltroAsociadoTipo;
        })(Asociados = Tecnicos.Asociados || (Tecnicos.Asociados = {}));
    })(Tecnicos = Cudu.Tecnicos || (Cudu.Tecnicos = {}));
})(Cudu || (Cudu = {}));
