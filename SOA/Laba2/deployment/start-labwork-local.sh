#!/bin/bash
set -e

PAYARA_VERSION="6.2024.10"
PAYARA_JAR="payara-micro-$PAYARA_VERSION.jar"
PAYARA_URL="https://repo1.maven.org/maven2/fish/payara/extras/payara-micro/$PAYARA_VERSION/$PAYARA_JAR"

echo "=== Собираем LabworkService ==="
cd ../LabworkService
mvn clean package -DskipTests -q
echo "WAR собран: $(ls -lh target/LabworkService-1.war)"

echo ""
echo "=== Запускаем Payara Micro $PAYARA_VERSION только по HTTPS на 8443 ==="

cd ../deployment

if [ ! -f "$PAYARA_JAR" ]; then
  echo "Скачиваем Payara Micro $PAYARA_VERSION..."
  wget -q "$PAYARA_URL"

  if [ ! -f "$PAYARA_JAR" ]; then
    echo "Ошибка скачивания Payara Micro!"
    echo "URL: $PAYARA_URL"
    echo "Скачай вручную и положи в deployment/"
    exit 1
  fi
fi

echo "✓ Payara Micro найден: $PAYARA_JAR"

pkill -f payara-micro || true
sleep 1

echo "=== Запускаем Payara Micro ==="
java \
  -Djavax.net.ssl.keyStore=../files_https/labwork-keystore.jks \
  -Djavax.net.ssl.keyStorePassword=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -Djavax.net.ssl.keyStoreType=JKS \
  -Djavax.net.ssl.trustStore=../files_https/truststore.jks \
  -Djavax.net.ssl.trustStorePassword=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -Djavax.net.ssl.trustStoreType=JKS \
  -jar "$PAYARA_JAR" \
  --deploy ../LabworkService/target/LabworkService-1.war \
  --sslPort 8443 \
  --sslCert labwork \
  --nocluster \
  --nohostaware \
  --disablephonehome

