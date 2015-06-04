package org.scoutsfev.cudu.storage;


import org.scoutsfev.cudu.domain.Ficha;
import org.scoutsfev.cudu.domain.FichaId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface FichaRepository extends Repository<Ficha, FichaId> {

    @Query("SELECT F FROM Ficha F WHERE F.id = :idFicha AND F.lenguaje = :lenguaje")
    public Ficha obtenerFicha(@Param("idFicha") int idFicha, @Param("lenguaje") String lenguaje);

}
