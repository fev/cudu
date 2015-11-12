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
        function CuduTypeaheadDataSetFactory(entidad, displayKeyFnc) {
            var bloodhound = new Bloodhound({
                datumTokenizer: function (d) { return Bloodhound.tokenizers.whitespace(d.nombre); },
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                remote: {
                    url: '/api/typeahead/' + entidad + '/%QUERY',
                    wildcard: '%QUERY',
                    filter: function (response) { return response.content; }
                }
            });
            bloodhound.initialize();
            return {
                displayKey: displayKeyFnc,
                source: bloodhound.ttAdapter()
            };
        }
        Ux.CuduTypeaheadDataSetFactory = CuduTypeaheadDataSetFactory;
        var CuduTypeaheadImpl = (function () {
            function CuduTypeaheadImpl(elementId, dataset, options) {
                this.elementId = elementId;
                options = options || { highlight: true };
                $(elementId).typeahead(options, dataset);
            }
            return CuduTypeaheadImpl;
        })();
        Ux.CuduTypeaheadImpl = CuduTypeaheadImpl;
        function CuduTypeaheadFactory(elementId, entidad, displayKeyFnc) {
            return function () {
                displayKeyFnc = displayKeyFnc || (function (r) { return r.nombre + " " + r.apellidos; });
                var dataset = CuduTypeaheadDataSetFactory(entidad, displayKeyFnc);
                return new CuduTypeaheadImpl(elementId, dataset);
            };
        }
        Ux.CuduTypeaheadFactory = CuduTypeaheadFactory;
    })(Ux = Cudu.Ux || (Cudu.Ux = {}));
})(Cudu || (Cudu = {}));
