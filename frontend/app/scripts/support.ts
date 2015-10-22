
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
}
