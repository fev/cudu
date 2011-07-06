select count(*) as total, 'Grupos' as tipo from grupo
UNION
select count(*) as total, 'Asociados' as tipo from asociado
UNION
select count(*) as total, 'Voluntarios' as tipo from asociado where tipo<>'J'
UNION
select count(*) as total, 'Jovenes' as tipo from asociado where tipo='J'
UNION
select count(*) as total, 'Kraal' as tipo from asociado where tipo='K'
UNION
select count(*) as total, 'Comite' as tipo from asociado where tipo='C' 
UNION
select count(*) as total, ramas as tipo from asociado where tipo='J' AND ramaS <>''
group by ramas
order by tipo;

---TODO ESTO SERÍA PARA EL FEDERATIVO.
/*
  EN EL CASO DE LS PERMISOS C1 C2 C3 (ALICANTE, CASTELLÓN Y VALENCIA) solo verían los de su asociación.

  la opción de machacar -> solo machacarían su información provincial.

  faltaría por incluir grupo meos numeroso y grupo menos actualizado.
*/
