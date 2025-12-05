#!/bin/bash
set -e

echo "=== Собираем LabworkService ==="
cd ../LabworkService
mvn clean package -DskipTests -q
echo "WAR собран: $(ls -lh target/LabworkService-1.war)"

echo ""
echo "=== Запускаем Payara Micro 6.2025.11 только по HTTPS на 8443 ==="

cd ../deployment

# Скачиваем Payara Micro, если нет (прямая рабочая ссылка на 03.12.2025)
if [ ! -f payara-micro-6.2025.11.jar ]; then
  echo "Скачиваем Payara Micro 6.2025.11..."
  wget -q https://s3-eu-west-1.amazonaws.com/payara-fish-downloads/6.2025.11/payara-micro-6.2025.11.jar
  if [ ! -f payara-micro-6.2025.11.jar ]; then
    echo "Ошибка скачивания. Скачай вручную с https://payara.fish/downloads/ и положи в deployment/"
    exit 1
  fi
fi

# Убиваем старый процесс
pkill -f payara-micro || true
sleep 2

# Запуск: только HTTPS с JVM props для keystore/truststore
java \
  -Djavax.net.ssl.keyStore=../files_https/labwork-keystore.jks \
  -Djavax.net.ssl.keyStorePassword=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -Djavax.net.ssl.keyStoreType=JKS \
  -Djavax.net.ssl.trustStore=../files_https/truststore.jks \
  -Djavax.net.ssl.trustStorePassword=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -Djavax.net.ssl.trustStoreType=JKS \
  -jar payara-micro-6.2025.11.jar \
  --deploy ../LabworkService/target/LabworkService-1.war \
  --sslPort 8443 \
  --sslCert labwork \
  --nocluster \
  --nohostaware \
  --disablephonehome

