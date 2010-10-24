#!/usr/bin/env python
# encoding: utf-8
"""
openoffice.py - Permite leer y escribir archivos de OpenOffice.
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

from zipfile import ZipFile
from itertools import repeat
import xml.dom.minidom

OD_TABLE_NS = 'urn:oasis:names:tc:opendocument:xmlns:table:1.0'

def get_text(node):
    text = ''
    for child in node.childNodes:
        if child.nodeType == child.ELEMENT_NODE:
            text = text + get_text(child)
        elif child.nodeType == child.TEXT_NODE:
            text = text + child.nodeValue
    return text

def obtenerContenido(filename):
    zfile = ZipFile(filename, 'r')
    rawContent = zfile.read('content.xml')
    zfile.close()
    return xml.dom.minidom.parseString(rawContent)
    
def obtenerCeldas(fila):
    celdas = fila.getElementsByTagNameNS(OD_TABLE_NS, 'table-cell')
    # print len([celda.tagName for celda in celdas]), "celdas"
    
    for celda in celdas:
        # print celda.hasAttributeNS(OD_TABLE_NS, 'number-columns-repeated')
        if celda.hasAttributeNS(OD_TABLE_NS, 'number-columns-repeated'):
            rep = celda.getAttributeNS(OD_TABLE_NS, 'number-columns-repeated')
            for i in range(0, int(rep)):
                yield ''
        else:
            yield get_text(celda)

class Spreadsheet():
    def _contenido(self, filename):
        zfile = ZipFile(filename, 'r')
        rawContent = zfile.read('content.xml')
        zfile.close()
        return xml.dom.minidom.parseString(rawContent)
        
    def _hojas(self, xml):
        hojas = {}
        for hoja in xml.getElementsByTagNameNS(OD_TABLE_NS, 'table'):
            nombre = hoja.getAttributeNS(OD_TABLE_NS, 'name')
            hojas[nombre] = self._filas(hoja)
        return hojas
        
    def _filas(self, hoja):
        filas = []
        for fila in hoja.getElementsByTagNameNS(OD_TABLE_NS, 'table-row'):
            filas.append(self._celdas(fila))
        return filas
    
    def _celdas(self, fila):
        celdas = []
        for celda in fila.getElementsByTagNameNS(OD_TABLE_NS, 'table-cell'):
            txt = get_text(celda)
            if celda.hasAttributeNS(OD_TABLE_NS, 'number-columns-repeated'):
                rep = celda.getAttributeNS(OD_TABLE_NS, 'number-columns-repeated')
                celdas.extend([txt for i in range(0,int(rep))])
                # for i in range(0, int(rep)):
                #     celdas.append('')
            else:
                celdas.append(txt)
        return celdas

    def __init__(self, filename):
        self.filename = filename
        self.xml = self._contenido(filename)
        self.hojas = self._hojas(self.xml)

def main():
    filename = 'testdata.ods'
    spreadsheet = Spreadsheet(filename)
    hoja = spreadsheet.hojas['Sheet1']
    for fila in hoja[:6]:
        print len(fila), ' - ', ','.join(fila)
    return
     
    contenido = obtenerContenido(filename)
    hojas = contenido.getElementsByTagNameNS(OD_TABLE_NS, 'table')

    for hoja in hojas:
        print "Hoja:" , hoja.getAttributeNS(OD_TABLE_NS, 'name')
        
        filas = hoja.getElementsByTagNameNS(OD_TABLE_NS, 'table-row')
        for fila in filas:
            print fila
            celdas = list(obtenerCeldas(fila))
            print len(celdas)
            
        # cell = getChildElement(filas[1], "table:table-cell" )
        # print [len(f.childNodes) for f in filas]
        
        # cells = sheet.getElementsByTagNameNS(OD_TABLE_NS, 'table-cell')
        # 
        # if len(cells) > 0:
        #     print "%s: %s" (sheet_name, get_text(cells[0]))


if __name__ == '__main__':
    main()

