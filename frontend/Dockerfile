# build stage
FROM node:14.18.1-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm cache clean --force
RUN npm ci
COPY . .
RUN npm run build
FROM nginx:stable-alpine as production-stage

COPY --from=build-stage /app/build /usr/share/nginx/html
#COPY --from=build-stage /app/deploy_conf/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

CMD ["nginx", "-g","daemon off;"]
