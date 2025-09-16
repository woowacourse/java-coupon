#!/bin/bash
set -e

while ! mysqladmin ping -uroot -p'root' --silent; do
    sleep 1
done

## create user
/usr/bin/mysql -uroot -p'root' -S /var/run/mysqld/mysqld.sock -e "create user 'replUser'@'%' identified by 'replPassword'"
/usr/bin/mysql -uroot -p'root' -S /var/run/mysqld/mysqld.sock -e "grant replication slave on *.* to 'replUser'@'%'"

/usr/bin/mysql -uroot -p'root' -S /var/run/mysqld/mysqld.sock -e "flush privileges"

/bin/bash
