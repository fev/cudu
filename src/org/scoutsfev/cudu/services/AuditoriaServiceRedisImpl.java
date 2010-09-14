package org.scoutsfev.cudu.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jredis.JRedis;
import org.jredis.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditoriaServiceRedisImpl implements AuditoriaService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	public final static String LISTNAME = "audit";
	public final static String FIELDSEPARATOR = ",";
	
	@Autowired
	protected JRedis redisService;
	
	class AsyncAuditWrite implements Runnable {
		private String entry;
		
		public AsyncAuditWrite(String entry) {
			this.entry = entry;
		}
		
		public void run() {		
			try {
				redisService.lpush(LISTNAME, entry);
			} catch (RedisException e) {
				logger.error(e);
			}
		}
	}
	
	public void registrar(Operacion operacion, Entidad entidad, String pk) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		String fecha = dateFormat.format(new Date());
		
		String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
		
		StringBuilder sb = new StringBuilder();
		sb.append(fecha);
		sb.append(FIELDSEPARATOR);
		sb.append(usuario);
		sb.append(FIELDSEPARATOR);
		sb.append(operacion.getOperacion());
		sb.append(FIELDSEPARATOR);
		sb.append(entidad.getEntidad());
		sb.append(FIELDSEPARATOR);
		sb.append(pk);
		
		new Thread(new AsyncAuditWrite(sb.toString())).start();
	}
}
