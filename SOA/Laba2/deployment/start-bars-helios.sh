#!/bin/bash
set -e

DIR="$(cd "$(dirname "$0")" && pwd)"

# Try to discover BarsService JAR automatically if not provided via JAR env var
$JAR="$DIR/BarsService.jar" 

if [[ -z "$JAR" ]]; then
  echo "❌ JAR BarsService не найден. Положите jar в $DIR"
  exit 1
fi

echo "=== Запуск BarsService ==="

pkill -f "$JAR" || true
sleep 1

java -jar \
  -Dserver.port=8444 \
  -Dserver.ssl.enabled=true \
  -Dserver.ssl.key-store=$DIR/../files_https/bars-keystore.jks \
  -Dserver.ssl.key-store-password=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -Dserver.ssl.key-store-type=JKS \
  -Dserver.ssl.trust-store=$DIR/../files_https/truststore.jks \
  -Dserver.ssl.trust-store-password=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  "$JAR" --spring.profiles.active=prod
