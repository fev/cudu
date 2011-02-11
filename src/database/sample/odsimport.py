#!/usr/bin/env python
# encoding: utf-8
u"""
odsimport.py - Genera script para importación en PostgreSQL.
Copyright (C) 2010 Federació d'Escoltisme Valencià.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
"""

import re, sys

from itertools import chain, count, ifilter, imap, izip

from opendocument import Spreadsheet
from grupos import grupos

FIELDSEP = ','      # Separador de campos
FIELDSEPREPL = ''   # Reemplazo en el caso de encontrar uno

NIFDC = 'TRWAGMYFPDXBNJZSQVHLCKE'

rxNumeros = re.compile("\d+")

def flatten(l):
    out = []
    for item in l:
        if isinstance(item, (list, tuple)):
            out.extend(flatten(item))
        else:
            out.append(item)
    return out

def iflatten(iterable):
    it = iter(iterable)
    for e in it:
        if isinstance(e, (list, tuple)):
            for f in iter_flatten(e):
                yield f
        else:
            yield e

# lambda s: re.sub("[^A-Z0-9]", "", s.upper())
def filtroDNI(dni):
    num = re.sub("[^A-Z0-9]", "", dni.upper().strip(' ').lstrip('0'))
    if len(num) > 1 and num[0].isdigit() and num[-1].isdigit():
        return num + NIFDC[int(num)%23]
    return num

# Si existen más de dos apellidos, agrupa N-1 y deja el N
# DE SANTOS PEREZ -> [De Santos, Perez]
def filtroApellidos(txt):
    #print txt, "  ->  ",
    seq = legibilizar(txt).split(' ')
    if len(seq) < 2:
        seq.append('')
    elif len(seq) > 2:
        seq[0] = ' '.join(seq[0:-1])
        seq[1] = seq[-1]
        del(seq[2:])
    #print seq
    return seq

def filtroRama(txt):
    r = txt.lower()
    if   r == u'colonia':      return "C,t,f,f,f,f"
    elif r == u'lobatos':      return "M,f,t,f,f,f"
    elif r == u'exploradores': return "E,f,f,t,f,f"
    elif r == u'pioneros':     return "P,f,f,f,t,f"
    elif r == u'compañeros':   return "R,f,f,f,f,t"
    else:                      return ",f,f,f,f,f"

def filtroCalle(txt):
    txt = txt.title().strip(" ").replace(FIELDSEP, FIELDSEPREPL)
    if txt == '':
        return ['(desconocida)', '0']
    seq = rxNumeros.findall(txt)
    if len(seq) > 0: 
        return [txt, seq[0]]
    return [txt, '0']

def filtroGrupo(txt):
    txt = txt.upper().strip(" ")
    if txt in grupos:
        return [grupos[txt]]
    return ["(desconocido)"]
    
def evitarNulo(txt, reemplazo):
    txt = txt.strip(' ')
    if txt == '':
        return reemplazo
    return txt

# Filtros importación GRUPO ---------
def filtroWeb(txt):
    clean = txt.strip(' ')
    if clean == '':
        return ''
    elif clean[:7] != 'http://':
        return 'http://' + clean
    return clean

def filtroLocalizacion(txt):
    txt = txt.strip(' ')
    cp = txt[:5]
    sProv = txt.index('(')
    ciud = txt[6:sProv-1]
    prov = txt[sProv+1:-1]
    return [cp,prov,ciud]
# -----------------------------------   

def legibilizar(s): 
    return s.title().strip(' ')

# Nombre de columna, función a aplicar, columnas al expandir (si procede)
filtros = [("nombre", legibilizar),
           ("apellidos", filtroApellidos, ["primerApellido", "segundoApellido"]),
           #("calle", filtroCalle, ["calle", "numero"]),
           ('calle', lambda e: legibilizar(e.replace(FIELDSEP,FIELDSEPREPL))),
           ("provincia", lambda e: evitarNulo(legibilizar(e), '(desconocida)')),
		   ("seguridadsocial", lambda s: re.sub("[^0-9]", "", s)),
		   ("telefonomovil", lambda s: re.sub("[^0-9]", "", s)),
		   ("telefonocasa", lambda s: re.sub("[^0-9]", "", s)),
  		   ("padre_telefono", lambda s: re.sub("[^0-9]", "", s)),
		   ("madre_telefono", lambda s: re.sub("[^0-9]", "", s)),
           ("primerApellido", legibilizar),
           ("segundoApellido", legibilizar),
		   ("numero", lambda e: evitarNulo(legibilizar(e), '0')),
           ("grupo", filtroGrupo, ["idGrupo"]),
           ("municipio", lambda e: evitarNulo(legibilizar(e), '(desconocido)')),
           ("fechanacimiento", lambda e: evitarNulo(e, "01/01/1900")),
           ("email", lambda e: e.lower()),
           ("codigopostal", lambda e: evitarNulo(e, "00000")),
           ("rama", filtroRama, ["ramas", "rama_colonia", "rama_manada", "rama_exploradores", "rama_pioneros", "rama_rutas"]),
           ("dni", filtroDNI)]

# FILTROS IMPORTACIÓN GRUPOS
# filtros = [("email", lambda e: e.lower()),
#            ("direccion", lambda e: e.replace(',', '')),
#            ("web", filtroWeb),
#            ("localizacion", filtroLocalizacion, ['codigopostal', 'provincia', 'municipio'])]
    
def componerCabecera(cabecera):
    for h in cabecera:
        filtro = None
        for f in filtros:
            if f[0] == h:
                filtro = f
                break
        if filtro is not None and len(filtro) == 3:
            yield FIELDSEP.join(filtro[2])
        else:
            yield h

def prueba0():
    spreadsheet = Spreadsheet('sample.ods')
    raw = spreadsheet.hojas.items()[0][1]
    cabecera = raw[0]
    filas = raw[1:]
    
    # columna = lambda c: [primerNumero(f[cabecera.index(c)]) + " :  " + f[cabecera.index(c)] for f in filas if len(f) > 1]
    columna = lambda c: [' '.join([str(len(f)), f[cabecera.index(c)]]) for f in filas if len(f) > 1]
    print '\n'.join(columna("calle"))

    return

def generarSql(filename):
    # TODO Iterar sobre todas las hojas del archivo
    # TODO Sacar nombre de la hoja como nombre de la tabla

    spreadsheet = Spreadsheet(filename)
    raw = spreadsheet.hojas.items()[0][1]
    
    cabecera = raw[0]
    filas = raw[1:]

    print "COPY asociado (", ','.join(componerCabecera(cabecera)), ") FROM stdin WITH DELIMITER ',' CSV;"
    sys.stdout.flush() 

    noVacias = ifilter(lambda e: len(e) > 1, filas)
    sinSeparador = imap(lambda e: e.replace(FIELDSEP, FIELDSEPREPL), noVacias)
    for fila in noVacias:
        for filtro in filtros:
            tituloColumna = filtro[0]
            if tituloColumna not in cabecera:
                continue
            ncol = cabecera.index(tituloColumna)
            fnc = filtro[1]
            fila[ncol] = fnc(fila[ncol])

        print FIELDSEP.join(flatten(fila))
    
    print "\."
    
    # Función para extraer una columna determinada
    # columna = lambda c: [f[cabecera.index(c)] for f in filas if len(f) > 1]
    # print '\n'.join(columna("localizacion"))
    
    # REVISIÓN NÚMEROS DE DNI
    # Números que la primera letra no es un número y la última lo es, para comprobarla
    # recalculables = ifilter(lambda x: len(x) > 1 and x[0].isdigit() and x[-1].isalpha(), columna("dni"))
    # for c in recalculables:
    #     letra = NIFDC[int(c[0:-1])%23]
    #     print c, letra, letra != c[-1]

def main():
    # prueba0()
    # return
    
    generarSql('/Users/luis/Desktop/20100120-02.ods')
    return
    
    if len(sys.argv) < 2:
        print "Debe especificar el nombre del archivo.\nUso: odsimport.py sample.ods\n"
    else:
        generarSql(sys.argv[1])

if __name__ == "__main__":
    sys.exit(main())
