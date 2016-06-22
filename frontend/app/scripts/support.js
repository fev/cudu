var Cudu;
(function (Cudu) {
    var Ux;
    (function (Ux) {
        (function (ModalEvent) {
            ModalEvent[ModalEvent["BeforeShow"] = 1] = "BeforeShow";
            ModalEvent[ModalEvent["AfterShow"] = 2] = "AfterShow";
            ModalEvent[ModalEvent["BeforeHide"] = 3] = "BeforeHide";
            ModalEvent[ModalEvent["AfterHide"] = 4] = "AfterHide";
        })(Ux.ModalEvent || (Ux.ModalEvent = {}));
        var ModalEvent = Ux.ModalEvent;
        var modalEvents = {
            1: "show.bs.modal",
            2: "shown.bs.modal",
            3: "hide.bs.modal",
            4: "hidden.bs.modal"
        };
        var BootstrapModal = (function () {
            function BootstrapModal(elementId, focusOnElementId, select) {
                this.elementId = elementId;
                this.focusOnElementId = focusOnElementId;
                this.events = {};
                this.command({ show: false });
                if (focusOnElementId) {
                    if (select === true) {
                        $(elementId).on('shown.bs.modal', function () { $(focusOnElementId).select(); });
                    }
                    else {
                        $(elementId).on('shown.bs.modal', function () { $(focusOnElementId).focus(); });
                    }
                }
                this.events = this.initialEventTrackingHash();
            }
            BootstrapModal.prototype.show = function () { this.command('show'); };
            BootstrapModal.prototype.hide = function () { this.command('hide'); };
            BootstrapModal.prototype.subscribe = function (event, handler) {
                var eventStr = modalEvents[event];
                if (this.events[eventStr] === false) {
                    this.events[eventStr] = true;
                    $(this.elementId).on(eventStr, handler);
                }
            };
            BootstrapModal.prototype.unsubscribe = function () {
                var _this = this;
                _.forIn(this.events, function (v, k) { $(_this.elementId).off(k); });
                this.events = this.initialEventTrackingHash();
            };
            BootstrapModal.prototype.initialEventTrackingHash = function () {
                return _.reduce(_.values(modalEvents), function (a, n) { a[n] = false; return a; }, {});
            };
            BootstrapModal.prototype.command = function (param) {
                return $(this.elementId).modal(param);
            };
            return BootstrapModal;
        }());
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
                this.onSelected = function () { };
            }
            TypeaheadImpl.prototype.attach = function (scope) {
                var _this = this;
                var element = $(this.elementId);
                element.typeahead(this.options, this.dataset);
                scope.$on("$destroy", function () { _this.dispose(); });
                element.bind('typeahead:selected', function (e, seleccion) {
                    $(_this.elementId).addClass("tt-selected");
                    if (_this.onSelected != null) {
                        _this.onSelected(seleccion);
                    }
                });
                element.focus(function () {
                    $(_this.elementId).removeClass("tt-selected");
                });
                return this;
            };
            TypeaheadImpl.prototype.observe = function (onSelected) {
                this.onSelected = onSelected;
            };
            TypeaheadImpl.prototype.dispose = function () {
                $(this.elementId).typeahead('destroy');
            };
            return TypeaheadImpl;
        }());
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
