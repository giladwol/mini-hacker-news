#!/bin/bash

mongosh -- "$MONGO_INITDB_DATABASE" <<EOF
  use minihacker
  db.createUser({
    user: "root",
    pwd: "secret",
    roles: [
      {
        role: "readWrite",
        db: "minihacker"
      }
    ]
  })

  db.createCollection("posts")
  db.createCollection("votes")
EOF

mongoimport --host localhost --db $MONGO_INITDB_DATABASE --collection posts --type json --file /init-data/posts.json --jsonArray