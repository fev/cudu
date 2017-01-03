#!/bin/bash

set -eo pipefail

generar() {
    node traducir.js
    yarn run sass
    yarn run webpack
}

empaquetar() {
    local pkgname=cudu-ui-$(git describe --tags --dirty=-dev --always).tgz
    tar -C app -X package_exclude.txt -czvf $pkgname .
    shasum -a 256 $pkgname > ${pkgname}.sha
    echo "Package name: ${pkgname}"
}

function instalado() {
    local ejecutable=$1
    local instalado=1
    type $ejecutable >/dev/null 2>&1 || { local instalado=0; }
    echo "$instalado"
}

preparar() {
    if [ $f_skipclean == 1 ]; then
        printf '\e[0;33mWARN\e[0m: %s\n' 'Se omite yarn y bower.'
        return
    fi
    if [ $(instalado npm) == 0 ]; then
        printf '\e[0;31m%s\e[0m\n' 'ERROR: Necesitas instalar node.js para poder continuar.'
        exit 1
    fi
    local pkgman=yarn
    if [ $(instalado yarn) == 0 ]; then
        printf '\e[0;33mWARN\e[0m: %s\n' 'Yarn no instalado, se usará npm.'
        pkgman=npm
    fi
    if [ ! -d node_modules ]; then 
        $pkgman install
    fi
    if [ ! -d app/lib ]; then 
        ./node_modules/.bin/bower install
    fi
}

clean() {
    if [ $f_skipclean == 1 ]; then
        printf '\e[0;33mWARN\e[0m: %s\n' 'No se limpiará el workspace.'
        return
    fi
    rm -rf \
        node_modules \
        app/lib \
        *.tgz *.sha \
        app/bundle* \
        app/index.html* \
        app/styles/*.css
}

main() {
    clean
    preparar
    generar
    empaquetar
}

f_skipclean=0
while getopts 'd' flag; do
  case "${flag}" in
    d) f_skipclean=1 ;;
    *) error "Opción desconocida: ${flag}" ;;
  esac
done

main