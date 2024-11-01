#!/bin/bash
set -e

echo "Waiting for master to be ready..."
sleep 30

## get log file and position
master_log_file=$(mysql -uroot -p'root' -h 172.20.0.10 -S /var/run/mysqld/mysqld.sock -e "SHOW MASTER STATUS\G" | grep 'File:' | awk '{print $2}')
echo "master_log_file: ${master_log_file}"

master_log_pos=$(mysql -uroot -p'root' -h 172.20.0.10 -S /var/run/mysqld/mysqld.sock -e "SHOW MASTER STATUS\G" | grep 'Position:' | awk '{print $2}')
echo "master_log_pos: ${master_log_pos}"

## change master and start slave
query="change master to master_host='172.20.0.10', master_user='replUser', master_password='replPassword', master_log_file='${master_log_file}', master_log_pos=${master_log_pos}, master_port=3306, master_delay=1"
/usr/bin/mysql -uroot -p'root' -S /var/run/mysqld/mysqld.sock -e "${query}"
/usr/bin/mysql -uroot -p'root' -S /var/run/mysqld/mysqld.sock -e "start slave"

echo "Slave started"

/bin/bash
