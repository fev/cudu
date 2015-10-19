/// <reference path="../../typings/tsd.d.ts"/>

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

declare interface TraduccionesService {
  texto(clave: string, lenguaje?: string) : string;
  establecerLenguaje(codigo: string): string;
}
