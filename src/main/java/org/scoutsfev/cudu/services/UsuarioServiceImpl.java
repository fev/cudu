package org.scoutsfev.cudu.services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Query;

@Repository
public class UsuarioServiceImpl 
extends StorageServiceImpl<Usuario> 
implements UsuarioService, UserDetailsService {

    @PersistenceContext
    protected EntityManager entityManager;
  
    public Usuario find(String username) {
            return entityManager.find(Usuario.class, username);
    }

    /**
     * Obtiene un usuario de la base de datos. El método se implementa
     * para poder obtener el usuario actual desde el contexto de seguridad,
     * mediante SecurityContextHolder.getContext().getAuthentication().getPrincipal()
     * 
     * @param username Nombre (login) del usuario a obtener
     * @return Objeto Usuario cargado desde BBDD.
     * @see http://bit.ly/9AxtUh
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException, DataAccessException {

            Usuario usuario = find(username);
            if (usuario == null)
                    throw new UsernameNotFoundException("No se encontró el usuario con el nombre " + username);

            /* SuperHack!
             * Se marca el método como transaccional y se llama a size()
             * para que la colección se rellene sin producir una excepción
             * posteriormente, ya que el contexto se pierde al saltar a
             * j_spring_security_check.
             */
            usuario.getAuthorities().size();

            return usuario;
    }

    @Override
    public Usuario obtenerUsuarioActual() {
            return (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    @Override
    @Transactional
    public Usuario merge(Usuario entity) {
            Usuario persistedEntity = super.merge(entity);
            auditoria.registrar(AuditoriaService.Operacion.Almacenar, AuditoriaService.Entidad.Usuario, persistedEntity.getUsername());
            return persistedEntity;
    }
    
    @Override
    public List<Usuario> obtenerUsuariosALosQuePuedeCambiarPermiso(String userName, String permiso)
    {
        String filtroRelacion = " id:=idUsu AND permiso < :=mipermiso  AND a.usuario = u.username " ;
        String filtroPermiso="";
        if(!permiso.equals("ROLE_PERMISO_A"))
            filtroPermiso += " AND permiso <> ROLE_PERMISO_S ";
        
        if(!permiso.equals("ROLE_PERMISO_C1"))
            filtroPermiso += " AND a.asociacion = 1 ";
        
        if(!permiso.equals("ROLE_PERMISO_C2"))
            filtroPermiso += " AND a.asociacion = 2 ";
        
        if(!permiso.equals("ROLE_PERMISO_C3"))
            filtroPermiso += " AND a.asociacion = 3 ";
        
        
        
        Query query = this.entityManager
            .createQuery("SELECT u FROM  Usuario u, Asociado a WHERE "
                            + filtroRelacion + filtroPermiso 
                            + " AND fechaBaja IS NULL ");

        query.setParameter("idUsu", userName);
		
        query.setParameter("mipermiso", permiso);
		
        return query.getResultList();        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    }
    
    

}
