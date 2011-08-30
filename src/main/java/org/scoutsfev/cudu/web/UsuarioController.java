
package org.scoutsfev.cudu.web;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/permisos")
public class UsuarioController {
    
    protected final Log logger = LogFactory.getLog(getClass());
	
    @Autowired
    protected UsuarioService usuarioService;    

    @RequestMapping(value = "/permisos", method = RequestMethod.GET)
    public String setupForm(HttpServletRequest request, Model model) {
            String username = Usuario.obtenerLoginActual();

            Usuario usuario = usuarioService.obtenerUsuarioActual();
        logger.info("setupForm: " + usuario.getUsername());

        Collection<GrantedAuthority> authorities = usuario.getAuthorities();

        String permiso = null;
        Iterator<GrantedAuthority> it = authorities.iterator();
        while(it.hasNext() && permiso==null)
        {
            GrantedAuthority grantedAuthority = it.next();
            if(grantedAuthority.getAuthority().contains("PERMISO"))
            {
                //ROLE_PERMISO_B, ROLE_PERMISO_C1...
                String[] aux =grantedAuthority.getAuthority().split("_");
                permiso = aux[aux.length-1];
            }
        }
        List<Usuario> usuarios = usuarioService.obtenerUsuariosALosQuePuedeCambiarPermiso(usuario.getUsername(), permiso);
        List<String>  permisos = obtenerPermisosQuePuedeAsignar(permiso);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("permisos", permisos);

        return "permisos";	
    }
        
    public List<String> obtenerPermisosQuePuedeAsignar(String permiso)
    {
        List<String> permisos = new ArrayList<String>();
        permisos.add("A");
        permisos.add("B");
        permisos.add("C1");
        permisos.add("C2");
        permisos.add("C3");
        permisos.add("S");
        
        permisos.add("D");
        permisos.add("E");
        permisos.add("F");
        permisos.add("G");
        permisos.add("H");
        

        if(permiso.equals("B"))
        {    
            permisos = new ArrayList<String>();
            permisos.add("B");
        }
        else
        {
            if(permiso.compareTo("A")<0)
                permisos.remove("S");   
            
            if(permiso.compareTo("C1")<0 || permiso.compareTo("C2")<0 || permiso.compareTo("C3")<0)
            {
                permisos.remove("A");                
                permisos.remove("B");
                permisos.remove("C1");
                permisos.remove("C2");
                permisos.remove("C3");
            }
            if(permiso.compareTo("D")<0 )
            {
                permisos.remove("D");
            }
            if(permiso.compareTo("E")<0 )
            {
                permisos.remove("E");
            }
            if(permiso.compareTo("F")<0 )
            {
                permisos.remove("F");
            }
            //SOLO PUEDE DAR H, H será automático
//            if(permiso.compareTo("G")<0 )
//            {
//                permisos.remove("G");
//            }
        }
    
        return permisos;
        
    }
    
}
