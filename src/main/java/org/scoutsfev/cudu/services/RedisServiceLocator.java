package org.scoutsfev.cudu.services;

import org.jredis.JRedis;
import org.jredis.ri.alphazero.JRedisService;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;

/**
 * Crea un conjunto de conexiones con el servidor de Redis
 * para compartir entre instancias. La clase se utiliza 
 * para configurar el servicio de auditoría mediante Spring.
 *
 * @see org.scoutsfev.cudu.services.AuditoriaServiceRedisImpl
 */
public class RedisServiceLocator {
	
	private final static int REDISCONPOOL = 10;

	private static JRedis redisPool = new JRedisService(DefaultConnectionSpec.newSpec(), REDISCONPOOL);
	
	private RedisServiceLocator() {}
	
	public JRedis createRedisServiceInstance() {
	    return redisPool;
	}
}
