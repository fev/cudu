package org.scoutsfev.cudu.services;

public interface AuditoriaService {
	
	public enum Operacion {
		Almacenar('M'),
		Descartar('D'),
		Autenticar('A');
		
		private char operacion;
		
		Operacion(char operacion) {
			this.operacion = operacion;
		}
		
		public char getOperacion() {
			return this.operacion;
		}
	}
	
	public enum Entidad {
		Asociado("asociado"),
		Grupo("grupo"),
		Usuario("usuario");
		
		private String entidad;
		
		Entidad(String entidad) {
			this.entidad = entidad;
		}
		
		public String getEntidad() {
			return this.entidad;
		}
	}
	
	public void registrar(Operacion operacion, Entidad entidad, String pk);
}
