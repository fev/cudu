package org.scoutsfev.cudu.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SparseTable {

    public Map<String, Integer> campos;
    public Object[][] datos;
    public int total;

    public SparseTable(List<String> campos, Object[][] datos, int total) {
        this.campos = IntStream
                .range(0, campos.size()).boxed()
                .collect(Collectors.toMap(campos::get, z -> z));
        this.datos = datos;
        this.total = total;
    }
}
