var Cudu;
(function (Cudu) {
    var Ux;
    (function (Ux) {
        var BootstrapModal = (function () {
            function BootstrapModal(elementId, focusOnElementId, select) {
                this.elementId = elementId;
                this.focusOnElementId = focusOnElementId;
                this.command({ show: false });
                if (focusOnElementId) {
                    if (select === true) {
                        $(elementId).on('shown.bs.modal', function () { $(focusOnElementId).select(); });
                    }
                    else {
                        $(elementId).on('shown.bs.modal', function () { $(focusOnElementId).focus(); });
                    }
                }
            }
            BootstrapModal.prototype.show = function () { this.command('show'); };
            BootstrapModal.prototype.hide = function () { this.command('hide'); };
            BootstrapModal.prototype.command = function (param) {
                return $(this.elementId).modal(param);
            };
            return BootstrapModal;
        })();
        function ModalFactory(elementId, focusOnElementId, select) {
            return function () { return new BootstrapModal(elementId, focusOnElementId, select); };
        }
        Ux.ModalFactory = ModalFactory;
    })(Ux = Cudu.Ux || (Cudu.Ux = {}));
})(Cudu || (Cudu = {}));
