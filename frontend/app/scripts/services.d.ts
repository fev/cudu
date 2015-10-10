/// <reference path="../../../typings/tsd.d.ts"/>

declare interface Usuario {
  id: number;
  grupo: Grupo;
}

declare interface Grupo {
  id: string
}

declare interface UsuarioService {
  obtenerActual(): Q.Promise<Usuario>;
}
