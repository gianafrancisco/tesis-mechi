FROM ubuntu

COPY target/restaurant-0.1.0.jar /opt/restaurant-0.1.0.jar
COPY mysqlSchema.sh /opt/mysqlSchema.sh
COPY init.sh /opt/init.sh

MAINTAINER Francisco Giana <gianafrancisco@gmail.com>

RUN apt-get update && \
	apt-get -y install default-jre && \
	chmo a+x /opt/*.sh

RUN     echo "mysql-server mysql-server/root_password password toor" | debconf-set-selections && \
	echo "mysql-server mysql-server/root_password_again password toor" | debconf-set-selections && \
	apt-get -y install mysql-server && sh /mysqlSchema.sh

EXPOSE 8080

ENTRYPOINT /opt/init.sh