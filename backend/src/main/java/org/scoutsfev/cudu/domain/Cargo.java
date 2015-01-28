package org.scoutsfev.cudu.domain;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = {@Index(name = "idx_cargo_ambito", columnList = "ambito", unique = false)})
public class Cargo extends CargoAbstracto {
}
