package org.scoutsfev.cudu.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jredis.JRedis;
import org.jredis.RedisException;
import org.jredis.ri.alphazero.support.DefaultCodec;
import org.scoutsfev.cudu.domain.Parcial;
import org.scoutsfev.cudu.domain.Sugerencia;
import org.springframework.beans.factory.annotation.Autowired;

public class SugerenciasServiceRedisImpl implements SugerenciasService {
	
	@Autowired
	protected JRedis redisService;

	@Override
	public Collection<Sugerencia> obtenerLista() {
		try {
			List<byte[]> rawList = redisService.sort("suggest").GET("suggest:*").exec();
			Collection<Sugerencia> lista = new ArrayList<Sugerencia>();
			int index = 1;
			for(byte[] e : rawList) {
				String pk = Integer.toString(index);
				List<String> votantes = DefaultCodec.toStr(redisService.smembers("suggest:" + pk + ":votes"));
				String rawString = new String(e, "UTF8");
				lista.add(new Sugerencia(pk, rawString.substring(14), rawString.substring(0, 14),
						StringUtils.join(votantes, ", "), Integer.toString(votantes.size())));
				index++;
			}
			return lista;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		} catch (RedisException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long crear(String sugerencia, String usuario) {
		try {
			long pk = redisService.incr("suggest:index");
			redisService.set("suggest:" + pk, sugerencia);
			redisService.rpush("suggest", pk); 
			redisService.sadd("suggest:" + pk + ":votes", usuario);
			return pk;
		} catch (RedisException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Parcial votar(String pk, String usuario) {
		try {
			redisService.sadd("suggest:" + pk + ":votes", usuario);
			List<String> votantes = DefaultCodec.toStr(redisService.smembers("suggest:" + pk +  ":votes"));
			int votos = votantes.size(); // redisService.smembers("suggest:" + pk + ":votes").size(); ???
			return new Parcial(votantes, votos);
		} catch (RedisException e) {
			e.printStackTrace();
			return null;
		}
	}
}
