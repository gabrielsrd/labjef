#!/bin/bash

sudo -i -u postgres <<EOF
psql -c "CREATE USER admin WITH ENCRYPTED PASSWORD 'admin';"
psql -c "CREATE DATABASE labjefdb WITH OWNER admin;"
psql -c "GRANT ALL PRIVILEGES ON DATABASE labjefdb TO admin;"
EOF
