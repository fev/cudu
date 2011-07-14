package org.scoutsfev.cudu.services;

public interface AuditoriaService {
	
	public enum Operacion {
		Acceder('R'),
		Almacenar('M'),
		Descartar('D'),
		Autenticar('A'),
                Eliminar('E');
                
		
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
		Usuario("usuario"),
                AsociadoTrimestre("asociadoTrimestre");
                
		
		private String entidad;
		
		Entidad(String entidad) {
			this.entidad = entidad;
		}
		
		public String getEntidad() {
			return this.entidad;
		}
	}
	
	public void registrar(Operacion operacion, Entidad entidad, String pk);
        
	public void registrar(Operacion operacion, Entidad entidad, String pk1,String pk2,String pk3);
}
