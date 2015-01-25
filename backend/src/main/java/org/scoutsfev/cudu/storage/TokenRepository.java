package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.security.Timestamp;

public interface TokenRepository extends CrudRepository<Token, String> {

    Token findByEmail(String email);

    @Modifying
    @Transactional
    @Query("delete Token t where t.creado < :marca")
    void eliminarCaducados(@Param("marca") Timestamp marca);

    @Modifying
    @Transactional
    @Query("delete Token t where t.email in (select a.email from Asociado a where a.id = :asociadoId)")
    void eliminarTodos(@Param("asociadoId") int asociadoId);
}
