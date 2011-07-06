/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scoutsfev.cudu.services;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.AsociadosTrimestre;

/**
 *
 * @author gaxp
 */
public class EstadisticaServiceImpl
extends StorageServiceImpl<Asociado>
implements EstadisticaService
{
    
    @Override
    public Collection getInfoEstadisticasRamasJoven()
    {
        Collection c;
        //ramas de jovenes
        String select = "select count(*) as cantidad, ramas as etiqueta from Asociado where tipo = 'J'  group by ramas order by ramas";
        c = this.entityManager.createQuery(select).getResultList();

        return c;
    }
    @Override
    public Collection getInfoEstadisticasVoluntarios()
    {
        Collection c;
        //kraal + comite
         String select2 = 
        "select count(*) as cantidad, tipo  as etiqueta from Asociado where tipo <> 'J' group by tipo  order by tipo";
        c =this.entityManager.createQuery(select2).getResultList();
        return c;

    }
    @Override
    public Collection getNumGruposYGrupoMenosNumeroso()
    {

        Collection c;
         //grupo menos numeroso
         String select5 = 
        " select count(*) as cantidad, g.nombre as etiqueta from Asociado a, Grupo g where "
        + "a.grupo = g.id group by g.nombre having count(*)<=count(*) "; 

         //total de grupos
         String select6 = 
        " select count(*) as cantidad, 'Grupos' as etiqueta  from Grupo"; 

        c =this.entityManager.createQuery(select5).getResultList();
        c.addAll(this.entityManager.createQuery(select6).getResultList()); 
         return c;
    }
    
    
      
    @Override
    public AsociadosTrimestre getInfoEstadisticasAsociados()
    {
        List l4;
        //ramas de jovenes
        String select = 
                "select count(*) as cantidad, ramas as etiqueta, tipo from Asociado where tipo = 'J'  AND ramas<> '' group by ramas,tipo";
        String select2 = 
                "select count(*) as cantidad, tipo  as etiqueta, tipo  from Asociado where tipo <> 'J' AND ramas<> ''   group by tipo  ORDER By tipo, etiqueta";
        String select3 =  select + " Union "+ select2;
        l4 = this.entityManager.createNativeQuery(select3).getResultList();
        
        AsociadosTrimestre at = new AsociadosTrimestre();
        
          Object []o = (Object []) l4.get(0);
        at.setComite(((BigInteger)o[0]).intValue());
        
        o = (Object []) l4.get(1);
        at.setCastores(((BigInteger)o[0]).intValue());
        
        o = (Object []) l4.get(2);
        at.setExploradores(((BigInteger)o[0]).intValue());
        
        o = (Object []) l4.get(3);
        at.setLobatos(((BigInteger)o[0]).intValue());
        
        o = (Object []) l4.get(4);
        at.setPioneros(((BigInteger)o[0]).intValue());
        
        o = (Object []) l4.get(5);
        at.setCompanys(((BigInteger)o[0]).intValue());
        
        o = (Object []) l4.get(6);
        at.setScouters(((BigInteger)o[0]).intValue());
        
        return at;
    }

    @Override
    public Object getGrupoMenosActualizado()
    {
        String sel1 = "select  last_updated LAS_UPDATE,nombre from grupo ";
        String union = " UNION ";
        String sel2 = "select  a.last_updated LAS_UPDATE, g.nombre from grupo g, asociado a where g.id = a.idgrupo order by LAS_UPDATE asc ";
        String select = sel1+union+sel2;
        List l = this.entityManager.createNativeQuery(select).getResultList();
        return  l.get(0);
    }
    
    @Override
    public List<AsociadosTrimestre> getInfoEstadisticasTrimestresGrupo(String idGrupo) 
    {
        List c;
        
        //ramas de jovenes
        String select = "select anyo,mes, castores,lobatos,exploradores,pioneros,companys,scouters,comite from AsociadosTrimestre WHERE idgrupo = '" +idGrupo+ "' order by anyo, mes";
        c = this.entityManager.createNativeQuery(select).getResultList();

         return c;
    }
    
      
    @Override
    public List getAsociadoPorTrimestreYGrupo(String idGrupo)
    {
        String select = "select  anyo, mes, castores + lobatos +exploradores + pioneros + companys + scouters +comite from asociadosTrimestre WHERE idGrupo= '" + idGrupo+"'";
        List l =this.entityManager.createNativeQuery(select).getResultList();       
         return l; 
    }
    
    @Override
    public AsociadosTrimestre getInfoEstadisticasAsociadosPorGrupo(String idGrupo)
    {
        List l4;
        //ramas de jovenes
        String select = 
                "select count(*) as cantidad, ramas as etiqueta, tipo from Asociado where tipo = 'J'  AND ramas<> '' AND idGrupo= '" + idGrupo+ "' group by ramas,tipo";
        String select2 = 
                "select count(*) as cantidad, tipo  as etiqueta, tipo  from Asociado where tipo <> 'J' AND ramas<> '' AND idGrupo= '" + idGrupo+ "' group by tipo  ORDER By tipo, etiqueta";
        String select3 =  select + " Union "+ select2;
        l4 = this.entityManager.createNativeQuery(select3).getResultList();
        
        if(l4.size()>0)
        {
            AsociadosTrimestre at = new AsociadosTrimestre();

              Object []o = (Object []) l4.get(0);
            at.setComite(((BigInteger)o[0]).intValue());

            o = (Object []) l4.get(1);
            at.setCastores(((BigInteger)o[0]).intValue());

            o = (Object []) l4.get(2);
            at.setExploradores(((BigInteger)o[0]).intValue());

            o = (Object []) l4.get(3);
            at.setLobatos(((BigInteger)o[0]).intValue());

            o = (Object []) l4.get(4);
            at.setPioneros(((BigInteger)o[0]).intValue());

            o = (Object []) l4.get(5);
            at.setCompanys(((BigInteger)o[0]).intValue());

            o = (Object []) l4.get(6);
            at.setScouters(((BigInteger)o[0]).intValue());
            
            return at;
        }
        else
            return null;
        
        
    }
    
}
