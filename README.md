Run these comands in order

sudo -i -u postgres
psql
CREATE USER admin WITH ENCRYPTED PASSWORD 'admin';
CREATE DATABASE labjefdb WITH OWNER admin;
GRANT ALL PRIVILEGES ON DATABASE labjefdb TO admin;
exit
exit

then, run this:
psql -U admin labjefdb -f createdatabase.sql

