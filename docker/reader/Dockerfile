FROM mysql:8.0.34

ADD my.cnf /etc/mysql/conf.d/
RUN chmod 644 /etc/mysql/conf.d/my.cnf

ADD docker-entrypoint.sh /docker-entrypoint-initdb.d

ADD script/restart-replica.sh /lib/restart-replica.sh
