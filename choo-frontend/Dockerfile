FROM node:13-slim

#RUN apk --no-cache add git

RUN mkdir /app
WORKDIR /app
COPY package.json /app/
COPY package-lock.json /app/
RUN npm install

COPY public /app/public
COPY src /app/src
COPY rollup.config.js /app/
RUN CI=true npm test && npm run build && npm install -g serve

ENV NODE_ENV=production
CMD serve -s public

EXPOSE 5000
