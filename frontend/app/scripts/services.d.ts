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

declare interface CallbackNotificaciones {
  cerrar();
}

declare interface NotificacionesService {
  progreso(mensaje: string, timeOut: number): CallbackNotificaciones;
  errorServidor(mensaje: string, progreso?: CallbackNotificaciones): CallbackNotificaciones;
  completado(mensaje: string, progreso?: CallbackNotificaciones): CallbackNotificaciones;
}
