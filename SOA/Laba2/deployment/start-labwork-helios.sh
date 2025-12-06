#!/bin/bash
set -e

DIR="$(cd "$(dirname "$0")" && pwd)"

# Try to discover Payara Micro jar automatically if not provided via PAYARA_JAR env var
if [[ -z "$PAYARA_JAR" ]]; then
  candidates=("$DIR/payara-micro-*.jar" "$DIR/../deployment/payara-micro-*.jar")
  for c in "${candidates[@]}"; do
    files=( $c )
    if [[ -f "${files[0]}" ]]; then
      PAYARA_JAR="${files[0]}"
      break
    fi
  done
fi

# WAR candidates
WAR="$DIR/LabworkService-1.war"

if [[ -z "$WAR" ]]; then
  echo "❌ War-файл LabworkService не найден. Положите LabworkService-1.war в $DIR или в ../LabworkService/target/"
  exit 1
fi

if [[ -z "$PAYARA_JAR" || ! -f "$PAYARA_JAR" ]]; then
  echo "❌ Payara Micro jar ($PAYARA_JAR) не найден. Положите payara-micro jar в $DIR или укажите переменную окружения PAYARA_JAR"
  exit 1
fi

echo "=== Запуск LabworkService на Payara Micro ==="
echo "Payara: $PAYARA_JAR"
echo "War:    $WAR"

# Убиваем все Java процессы пользователя, связанные с Payara
pkill -9 -f "payara-micro" || true
sleep 1

# Проверяем, освободился ли порт 8445
if lsof -Pi :8445 -sTCP:LISTEN -t >/dev/null 2>&1 ; then
    echo "Порт 8445 всё ещё занят, принудительно убиваем процесс..."
    kill -9 $(lsof -t -i:8445) || true
    sleep 2
fi

echo "Порт 8445 свободен, запускаем сервис..."

# Создаём директорию для runtime в домашнем каталоге
RUNTIME_DIR="$DIR/payara-runtime"
mkdir -p "$RUNTIME_DIR"

java \
  -XX:MaxHeapSize=2G \
  -XX:MaxMetaspaceSize=1G \
  -Djavax.net.ssl.keyStore=$DIR/../files_https/labwork-keystore.jks \
  -Djavax.net.ssl.keyStorePassword=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -Djavax.net.ssl.keyStoreType=JKS \
  -Djavax.net.ssl.trustStore=$DIR/../files_https/truststore.jks \
  -Djavax.net.ssl.trustStorePassword=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -Djavax.net.ssl.trustStoreType=JKS \
  -Dhazelcast.local.localAddress=127.0.0.1 \
  -jar "$PAYARA_JAR" \
  --deploy "$WAR" \
  --rootdir "$RUNTIME_DIR" \
  --autoBindHttp \
  --sslPort 8445 \
  --sslCert labwork \
  --nohostaware \
  --nocluster \
  --lite \
  --disablephonehome \
  --logtofile "$DIR/payara-labwork.log" \
  --maxhttpthreads 10 \
  --minhttpthreads 2