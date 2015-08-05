#!/bin/bash

readonly OUT=./dist
readonly JS=$OUT/scripts
readonly BIN=./node_modules/.bin

# TODO compass build, watchers en devenv
# TODO Quitar dependencias con Compass, usar libsass
# TODO Bump version number, .semver
# TODO bundle install

build() {
    mkdir $OUT
    cp -R app/* $OUT
    rm $OUT/styles/*scss

    compass compile

    $BIN/htmlprocessor $OUT/index.html -o $OUT/index.html --list $OUT/merge.list
    concat ':lib.*js$' $JS/vendor.js
    concat ':lib.*css$' $OUT/styles/vendor.css
    concat ':scripts.*js$' $JS/cudu.js

    $BIN/uglifyjs --compress --source-map-url=scripts/cudu.min.js.map --source-map $JS/cudu.min.js.map -o $JS/cudu.min.js $JS/cudu.js
    
    cat header.txt $JS/cudu.min.js > $JS/tmp.js && mv $JS/tmp.js $JS/cudu.min.js

    hash_replace "$JS/cudu.min.js" "dist/index.html"
    hash_replace "$JS/vendor.js" "dist/index.html"
    hash_replace "$OUT/styles/cudu.css" "dist/index.html"
    hash_replace "$OUT/styles/vendor.css" "dist/index.html"

    find dist/scripts/* ! -name cudu.min* ! -iname vendor.min* | xargs rm -rf
    rm $OUT/merge.list
    rm -rf $OUT/lib
}

concat() {
    local pattern=$1; shift
    local output=$1; shift
    for f in `cat $OUT/merge.list | grep $pattern | cut -d: -f2 | sed 's/^/dist\//'`; do 
        (cat "${f}"; echo) >> $output;
    done
}

hash_replace() {
    local fpath=$1; shift
    local fToReplace=$1; shift

    local hash=`shasum $fpath | cut -c1-8`
    local fname=$(basename "$fpath")
    local dname=$(dirname "$fpath")
    local nameWithoutExt="${fname%.*}"
    local ext="${fname##*.}"
    local newName=${nameWithoutExt}.${hash}.${ext}

    cp $fpath $dname/$newName
    sed -i .bak "s|$fname|${newName}|g" $fToReplace
    rm $fpath
}

function instalado() {
    local ejecutable=$1
    local instalado=1
    type $ejecutable >/dev/null 2>&1 || { local instalado=0; }
    echo "$instalado"
}

traducir() {
    node traducir.js
}

prepare() {
    if [ $(instalado npm) == 0 ]; then
        printf '\e[0;31m%s\e[0m\n' 'Necesitas instalar node.js para poder continuar.'
        exit 1
    fi
    if [ $(instalado bundler) == 0 ]; then
        printf '\e[0;31m%s\e[0m\n' 'Necesitas instalar bundler para poder continuar.'
        exit 1
    fi
    if [ $(instalado compass) == 0 ]; then
        bundler install
    fi
    if [ ! -d node_modules ]; then 
        npm install
    fi
    if [ ! -d app/lib ]; then 
        $BIN/bower install
    fi
}

clean() {
    rm -rf $OUT
}

main() {
    prepare
    clean
    traducir
    build
}
main

