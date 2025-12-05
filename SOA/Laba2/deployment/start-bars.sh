#!/bin/bash
echo "=== Собираем BarsService ==="
cd ../BarsService
mvn clean package -DskipTests -q

echo "=== Запуск BarsService — ТОЛЬКО HTTPS на порту 8444 ==="
java -jar \
  -Dserver.port=8444 \
  -Dserver.ssl.enabled=true \
  -Dserver.ssl.key-store=../files_https/bars-keystore.jks \
  -Dserver.ssl.key-store-password=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -Dserver.ssl.key-store-type=JKS \
  -Dserver.ssl.trust-store=../files_https/truststore.jks \
  -Dserver.ssl.trust-store-password=ufwiojq$hqoo~i1098*90w@yrf8qihc \
  target/*.jar --spring.profiles.active=prod
