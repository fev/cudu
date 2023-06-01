FROM node:12-alpine3.11 as build-deps
MAINTAINER "Martin Espinach <martin.espinach@jamgo.coop>"

WORKDIR /usr/src/app

RUN apk update && \
	apk add ruby-bundler ruby-dev build-base libffi-dev perl-utils
COPY ./frontend/ ./
COPY ./backend/src/main/resources/*.properties ./i18n/
RUN yarn install && \
	bundler install && \
	node traducir.js && \
	compass compile && \
	sh build.sh

FROM nginx:alpine
COPY --from=build-deps /usr/src/app/dist /usr/share/nginx/html
COPY ./frontend/docker/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
