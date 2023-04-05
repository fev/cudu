#!/bin/bash

PUSH_IMAGE=""
VERSION=""

function usage() {
    printf "\n"
    printf "Build docker image\n"
    printf "\n"
    printf "Usage:\n"
    printf "\tbulid.sh [-p|--push]\n"
    printf "\tbulid.sh -h|--help\n"
    printf "\n"
    printf "Options:\n"
    printf "\t-p --push\t\t\tPush image to private repository.\n"
    printf "\n"
}

while [ "$1" != "" ]; do
    PARAM=`echo $1 | awk -F= '{print $1}'`
    VALUE=`echo $1 | awk -F= '{print $2}'`
    case $PARAM in
        -h | --help)
            usage
            exit
            ;;
        -p | --push)
            PUSH_IMAGE=yes
            ;;
        *)
            printf "ERROR: unknown parameter \"$PARAM\"\n"
            usage
            exit 1
            ;;
    esac
    shift
done

IMAGE_NAME=nexus.jamgo.org:5000/jamgocoop/cudu-backend
DOCKER_BUILDKIT=1 docker build --rm --progress=plain --file Dockerfile --tag $IMAGE_NAME:latest ..

if [ -n "$PUSH_IMAGE" ]; then
	docker push $IMAGE_NAME:latest
fi