#!/usr/bin/env python
# encoding: utf-8
"""
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

# TODO List
# - Nombre ods como parámetro línea comandos

import getopt, re, sys

from itertools import chain, count, ifilter, imap, izip

from odf import text
from odf.opendocument import load, Spreadsheet
from odf.table import TableRow, TableCell
from odf.text import P

FIELDSEP = ','      # Separador de campos
FIELDSEPREPL = ''   # Reemplazo en el caso de encontrar uno

NIFDC = 'TRWAGMYFPDXBNJZSQVHLCKE'

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
    num = re.sub("[^A-Z0-9]", "", dni.upper())
    if len(num) > 1 and num[0].isdigit() and num[-1].isdigit():
        return num + NIFDC[int(num)%23]
    return num

# Si existen más de dos apellidos, agrupa N-1 y deja el N
# DE SANTOS PEREZ -> [De Santos, Perez]
def filtroApellidos(txt):
    seq = legibilizar(txt).split(' ')
    if len(seq) > 2:
        seq[0] = ' '.join(seq[0:-1])
        seq[1] = seq[-1]
        del(seq[2:])
    return seq
    
def filtroRama(txt):
    r = txt.lower()
    if   r == u'colonia':      return "t,f,f,f,f"
    elif r == u'lobatos':      return "f,t,f,f,f"
    elif r == u'exploradores': return "f,f,t,f,f"
    elif r == u'pioneros':     return "f,f,f,t,f"
    elif r == u'compañeros':   return "f,f,f,f,t"
    else:                      return "f,f,f,f,f"
    
def legibilizar(s): 
    return s.title().strip(' ')

# Nombre de columna, función a aplicar, columnas al expandir (si procede)
filtros = [("nombre", legibilizar),
           ("apellidos", filtroApellidos, ["primerApellido", "segundoApellido"]),
           ("calle", legibilizar),
           ("provincia", legibilizar),
           ("municipio", legibilizar),
           ("email", lambda e: e.lower()),
           ("rama", filtroRama, ["rama_colonia", "rama_manada", "rama_exploradores", "rama_pioneros", "rama_rutas"]),
           ("dni", filtroDNI)]
        
def pruebaCero():
    doc = load("./ainkaren.ods")
    rows = doc.spreadsheet.getElementsByType(TableRow)
    row = rows[3]
    print dir(row)
    print row.childNodes
    for c in row.getElementsByType(TableCell):
        print c
    print [extraerCelda(c) for c in row.getElementsByType(TableCell)]


def extraerCelda(cell):
    innerData = cell.getElementsByType(text.P)
    if len(innerData) == 0: 
        return ''
    return ''.join([unicode(f.firstChild).replace(FIELDSEP, FIELDSEPREPL) for f in innerData])

def extraerFilas(doc):
    rows = doc.getElementsByType(TableRow)
    filas = []
    for row in rows:
        filas.append([extraerCelda(c) for c in row.getElementsByType(TableCell)])
    return filas[0], filas[1:]
    
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

def importar():
    doc = load("./ainkaren.ods")
    cabecera, filas = extraerFilas(doc.spreadsheet)

    print "COPY asociado (", FIELDSEP.join(componerCabecera(cabecera)), ") FROM stdin WITH DELIMITER ',' CSV;"  

    noVacias = ifilter(lambda e: len(e) > 1, filas)
    sinSeparador = imap(lambda e: e.replace(FIELDSEP, FIELDSEPREPL), noVacias)
    for fila in noVacias:
        for filtro in filtros:
            ncol = cabecera.index(filtro[0])
            fnc = filtro[1]
            fila[ncol] = fnc(fila[ncol])
        
        # print FIELDSEP.join(imap(lambda e: e.replace(FIELDSEP, FIELDSEPREPL), flatten(fila)))
        print FIELDSEP.join(flatten(fila))
    
    print "\."
    
    # Función para extraer una columna determinada
    # columna = lambda c: [f[cabecera.index(c)] for f in filas if len(f) > 1]
    # print '\n'.join(columna("dni"))
    
    # REVISIÓN NÚMEROS DE DNI
    # Números que la primera letra no es un número y la última lo es, para comprobarla
    # recalculables = ifilter(lambda x: len(x) > 1 and x[0].isdigit() and x[-1].isalpha(), columna("dni"))
    # for c in recalculables:
    #     letra = NIFDC[int(c[0:-1])%23]
    #     print c, letra, letra != c[-1]

class Usage(Exception):
	def __init__(self, msg):
		self.msg = msg

def main(argv=None):
    if argv is None:
        argv = sys.argv
    try:
        try:
            opts, args = getopt.getopt(argv[1:], "ho:v", ["help", "output="])
        except getopt.error, msg:
            raise Usage(msg)

        for option, value in opts:
            if option == "-v":
                verbose = True
            if option in ("-h", "--help"):
                raise Usage(help_message)
            if option in ("-o", "--output"):
                output = value
                
    importar()

    except Usage, err:
        print >> sys.stderr, sys.argv[0].split("/")[-1] + ": " + str(err.msg)
        print >> sys.stderr, "\t for help use --help"
        return 2

if __name__ == "__main__":
    sys.exit(main())