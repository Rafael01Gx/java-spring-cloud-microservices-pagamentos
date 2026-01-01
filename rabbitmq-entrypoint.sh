#!/bin/sh
set -e

echo ">> Criando Erlang cookie"
echo "novo_cookie" > /var/lib/rabbitmq/.erlang.cookie
chmod 400 /var/lib/rabbitmq/.erlang.cookie
chown rabbitmq:rabbitmq /var/lib/rabbitmq/.erlang.cookie

echo ">> Iniciando RabbitMQ"
docker-entrypoint.sh rabbitmq-server &
RABBIT_PID=$!

echo ">> Aguardando RabbitMQ ficar pronto"
rabbitmq-diagnostics -q ping

if [ "$JOIN_CLUSTER" = "true" ]; then
  echo ">> Entrando no cluster rabbit@rabbit1"
  rabbitmqctl stop_app
  rabbitmqctl reset
  rabbitmqctl join_cluster rabbit@rabbit1
  rabbitmqctl start_app
fi

echo ">> RabbitMQ pronto"
wait $RABBIT_PID
