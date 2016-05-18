FROM mhart/alpine-node

WORKDIR /app
ADD . .

# install dependencies
RUN npm install

EXPOSE 3000
CMD ["node", "dist/server.js"]
