#!/bin/bash
set -e

echo "=== Собираем LabworkService ==="
cd ../LabworkService
mvn clean package -DskipTests -q

echo "=== Запускаем Payara Micro 6 — ТОЛЬКО HTTPS на 8443 ==="
cd ../deployment

# Убиваем старый процесс
pkill -f payara-micro || true
sleep 2

java \
  -Djavax.net.ssl.keyStore=../files_https/labwork-keystore.jks \
  -Djavax.net.ssl.keyStorePassword=ufwiojq\$hqoo~i1098*90w@yrf8qihc \
  -Djavax.net.ssl.keyStoreType=JKS \
  -Djavax.net.ssl.trustStore=../files_https/truststore.jks \
  -Djavax.net.ssl.trustStorePassword=ufwiojq\$hqoo~i1098*90w@yrf8qihc \
  -jar payara-micro-6.2025.11.jar \
  --deploy ../LabworkService/target/LabworkService-1.war \
  --sslPort 8443 \
  --addLibs postgresql-42.7.8.jar \
  --nocluster \
  --nohostaware
