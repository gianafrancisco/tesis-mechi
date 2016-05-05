service mysql start
echo "create database restaurant" >/tmp/database
mysql -u root --password=toor < /tmp/database

