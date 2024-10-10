FROM mysql:8.0.34

ADD my.cnf /etc/mysql/conf.d/my.cnf
RUN chmod 644 /etc/mysql/conf.d/my.cnf

ADD docker-entrypoint.sh /docker-entrypoint-initdb.d
