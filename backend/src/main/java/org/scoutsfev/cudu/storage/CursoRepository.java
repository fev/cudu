package org.scoutsfev.cudu.storage;

import org.scoutsfev.cudu.domain.Curso;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Integer> {

}
