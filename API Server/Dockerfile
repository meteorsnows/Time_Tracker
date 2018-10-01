FROM node:10.11-alpine
ENV NODE_ENV production
WORKDIR /usr/src/app
COPY ["package.json", "package-lock.json*", "npm-shrinkwrap.json*", "./"]
RUN ["npm", "install"]
RUN npm install typescript -g
COPY . .
RUN tsc
EXPOSE 8080
CMD ["node", "./Build/index.js"]