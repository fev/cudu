


module Cudu {
  export interface Page<T> {
    content: T[];
    first: boolean;
    last: boolean;
    number: number;
    numberOfElements: number;
    size: number;
    sort: string;
    totalElements: number;
    totalPages: number;
  }

  export interface Disposable {
    dispose();
  }
}

module Cudu.Ux {

  export interface Modal {
    show();
    hide();
  }

  class BootstrapModal implements Modal {
    constructor(private elementId: string, private focusOnElementId?: string, select?: boolean) {
      this.command({ show: false });
      if (focusOnElementId) {
        if (select === true) {
          $(elementId).on('shown.bs.modal', () => { $(focusOnElementId).select() });
        } else {
          $(elementId).on('shown.bs.modal', () => { $(focusOnElementId).focus() });
        }
      }
    }

    show() { this.command('show'); }
    hide() { this.command('hide'); }

    private command(param: any) {
      return (<any> $(this.elementId)).modal(param);
    }
  }

  export function ModalFactory(elementId: string, focusOnElementId?: string, select?: boolean): () => Modal {
    return () => new BootstrapModal(elementId, focusOnElementId, select);
  }

  export function CuduTypeaheadDataSetFactory(entidad: string, displayKeyFnc: (e: any) => string): Twitter.Typeahead.Dataset {
    var bloodhound = new Bloodhound({
      datumTokenizer: function(d) { return Bloodhound.tokenizers.whitespace(d.nombre); },
      queryTokenizer: Bloodhound.tokenizers.whitespace,
      remote: {
        url: '/api/typeahead/' + entidad + '/%QUERY',
        wildcard: '%QUERY',
        filter: function(response) { return response.content; }
      }
    });
    bloodhound.initialize()
    return {
      displayKey: displayKeyFnc,
      source: bloodhound.ttAdapter()
    };
  }

  export interface Typeahead extends Disposable {
    attach(scope: ng.IScope);
    dispose();
  }

  export class TypeaheadImpl implements Typeahead {
    constructor(private elementId: string, private dataset: Twitter.Typeahead.Dataset,
      private options: Twitter.Typeahead.Options = { highlight: true }) { }

    attach(scope) {
      var element = $(this.elementId);
      element.typeahead(this.options, this.dataset);
      (<any> element).bind('typeahead:selected', (e, seleccion) => {
        // TODO Callback here
        console.log(seleccion);
      });
      scope.$on("$destroy", () => this.dispose());
    }

    dispose() {
      $(this.elementId).typeahead('destroy');
    }
  }

  export function TypeaheadFactory(elementId: string, entidad: string, displayKeyFnc?: (e: any) => string) : () => Typeahead {
    return () => {
      displayKeyFnc = displayKeyFnc || ((r: any) => r.nombre + " " + r.apellidos);
      var dataset = CuduTypeaheadDataSetFactory(entidad, displayKeyFnc);
      return new TypeaheadImpl(elementId, dataset);
    };
  }
}
