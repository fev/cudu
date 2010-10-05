package org.scoutsfev.cudu.web;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jredis.JRedis;
import org.jredis.RedisException;
import org.jredis.ri.alphazero.support.DefaultCodec;
import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// TODO Tirar a la basura y volver a empezar

@Controller
@RequestMapping("/sugerencias")
public class SugerenciasController {

	@Autowired
	protected JRedis redisService;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public class Sugerencia implements Serializable  {
		private static final long serialVersionUID = -4890971528838542983L;
		private String pk;
		private String texto;
		private String fecha;
		private String votantes;
		private String votos;
		
		public Sugerencia(String pk, String texto, String fecha, String votantes, String votos) {
			this.setPk(pk);
			this.setTexto(texto);
			this.setVotantes(votantes);
			this.setVotos(votos);
			
			this.setFecha(fecha.substring(6,8) + '/' + fecha.substring(4,6)+ '/' + fecha.substring(0, 4)
					+ ' ' + fecha.substring(8,10) + ':' + fecha.substring(10,12) + ':' + fecha.substring(12,14));
		}

		public void setTexto(String texto) {
			this.texto = texto;
		}

		public String getTexto() {
			return texto;
		}

		public void setPk(String pk) {
			this.pk = pk;
		}

		public String getPk() {
			return pk;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		public String getFecha() {
			return fecha;
		}
		
		public void setVotantes(String votantes) {
			this.votantes = votantes;
		}

		public String getVotantes() {
			return votantes;
		}

		public void setVotos(String votos) {
			this.votos = votos;
		}

		public String getVotos() {
			return votos;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) throws RedisException, UnsupportedEncodingException {
		//List<byte[]> rawList = redisService.lrange("suggest", 0, -1);
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

		model.addAttribute("username", Usuario.obtenerLoginActual());
		model.addAttribute("sugerencias", lista);
		return "sugerencias";
	}

	@RequestMapping(value = "/nueva", method = RequestMethod.GET)
	public @ResponseBody String processSubmit(@RequestParam("s") String sugerencia, HttpServletRequest request) throws RedisException {
//	@RequestMapping(method = RequestMethod.POST)
//	public @ResponseBody String processSubmit(@RequestBody String sugerencia) throws RedisException {
		long pk = redisService.incr("suggest:index");
		redisService.set("suggest:" + pk, sugerencia);
		redisService.rpush("suggest", pk); 
		redisService.sadd("suggest:" + pk + ":votes", Usuario.obtenerLoginActual());
		return Long.toString(pk);
	}
	
	@RequestMapping(value = "/{pk}", method = RequestMethod.POST)
	public @ResponseBody String processVote(@PathVariable String pk) throws RedisException {
		redisService.sadd("suggest:" + pk + ":votes", Usuario.obtenerLoginActual());
		List<String> votantes = DefaultCodec.toStr(redisService.smembers("suggest:" + pk +  ":votes"));
		int votos = votantes.size(); // redisService.smembers("suggest:" + pk + ":votes").size();
		return "{\"v\": \"" + votos + "\", \"u\": \"" + StringUtils.join(votantes, ", ") + "\"}";
	}
}
