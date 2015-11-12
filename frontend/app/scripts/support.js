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
        var TypeaheadImpl = (function () {
            function TypeaheadImpl(elementId, dataset, options) {
                if (options === void 0) { options = { highlight: true }; }
                this.elementId = elementId;
                this.dataset = dataset;
                this.options = options;
            }
            TypeaheadImpl.prototype.attach = function (scope) {
                var _this = this;
                var element = $(this.elementId);
                element.typeahead(this.options, this.dataset);
                element.bind('typeahead:selected', function (e, seleccion) {
                    console.log(seleccion);
                });
                scope.$on("$destroy", function () { return _this.dispose(); });
            };
            TypeaheadImpl.prototype.dispose = function () {
                $(this.elementId).typeahead('destroy');
            };
            return TypeaheadImpl;
        })();
        Ux.TypeaheadImpl = TypeaheadImpl;
        function TypeaheadFactory(elementId, entidad, displayKeyFnc) {
            return function () {
                displayKeyFnc = displayKeyFnc || (function (r) { return r.nombre + " " + r.apellidos; });
                var dataset = CuduTypeaheadDataSetFactory(entidad, displayKeyFnc);
                return new TypeaheadImpl(elementId, dataset);
            };
        }
        Ux.TypeaheadFactory = TypeaheadFactory;
    })(Ux = Cudu.Ux || (Cudu.Ux = {}));
})(Cudu || (Cudu = {}));
