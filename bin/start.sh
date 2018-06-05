#!/bin/bash

APP_NAME=demo-auth
APP_VERSION=1.0.0
APP_ENV=dev
LOG_MAX_SIZE=20MB
XMX_SIZE=512M


FWDIR="$(cd `dirname $0`/..; pwd)"
cd ${FWDIR}

if [ ! -d ${FWDIR}/java.pid ]; then
    touch ${FWDIR}/java.pid
fi

OSUSER=$(id -nu)
PSNUM=$(cat ${FWDIR}/java.pid)
if [[ "$PSNUM" -ne "" ]]; then
    echo ${APP_NAME}" has been started! stop first."
    exit;
fi

java -Xmx${XMX_SIZE} -Dapp.name=${APP_NAME} -Dapp.env=${APP_ENV} -Dmax.size=${LOG_MAX_SIZE} -Duser.dir=${FWDIR} -jar ${APP_NAME}-${APP_VERSION}.jar &
echo $! > ${FWDIR}/java.pid
exit
