FROM mongo:latest

COPY init-mongo.sh /docker-entrypoint-initdb.d/
COPY init-data /init-data

VOLUME /data/db

ENV MONGO_INITDB_DATABASE=minihacker
ENV MONGO_INITDB_PASSWORD=secret
ENV MONGO_INITDB_USERNAME=root

RUN chmod +x /docker-entrypoint-initdb.d/init-mongo.sh
# RUN /docker-entrypoint-initdb.d/init-mongo.sh

EXPOSE 27017