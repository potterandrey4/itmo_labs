# 1. Создаём корневой CA (один раз)
```bash
openssl req -x509 -newkey rsa:4096 -keyout root-ca.key -out root-ca.crt \
  -days 3650 -nodes -subj "/CN=IFMO Lab Root CA 2025"
```

# 2. Универсальный SAN-конфиг (один на все сертификаты)
```bash
cat > san.cnf << 'EOF'
[req]
distinguished_name = dn
prompt = no
req_extensions = v3_req

[dn]
CN = helios.cs.ifmo.ru

[v3_req]
subjectAltName = DNS:helios.cs.ifmo.ru,DNS:localhost,IP:127.0.0.1,IP:77.234.196.4
EOF
```

# 3. LabworkService (Payara Micro) → сразу в JKS (Payara Micro 6 любит именно JKS!)
```bash
openssl req -new -newkey rsa:2048 -nodes \
  -keyout labwork.key -out labwork.csr \
  -subj "/CN=helios.cs.ifmo.ru" -config san.cnf

openssl x509 -req -in labwork.csr -CA root-ca.crt -CAkey root-ca.key \
  -CAcreateserial -out labwork.crt -days 3650 -sha256 \
  -extfile san.cnf -extensions v3_req

# Временный PKCS12
openssl pkcs12 -export -in labwork.crt -inkey labwork.key -certfile root-ca.crt \
  -out temp.p12 -name labwork -password pass:ufwiojq$hqoo~i1098*90w@yrf8qihc

# Конвертируем в JKS — это то, что нужно Payara Micro 6
keytool -importkeystore \
  -srckeystore temp.p12 -srcstoretype PKCS12 -srcstorepass ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -destkeystore labwork-keystore.jks -deststoretype JKS \
  -deststorepass ufwiojq$hqoo~i1098*90w@yrf8qihc -destkeypass ufwiojq$hqoo~i1098*90w@yrf8qihc -alias labwork -noprompt

rm temp.p12 labwork.key labwork.csr labwork.crt
```

# 4. BarsService (Spring Boot) → тоже сразу в JKS
```bash
openssl req -new -newkey rsa:2048 -nodes \
  -keyout bars.key -out bars.csr \
  -subj "/CN=helios.cs.ifmo.ru" -config san.cnf

openssl x509 -req -in bars.csr -CA root-ca.crt -CAkey root-ca.key \
  -CAcreateserial -out bars.crt -days 3650 -sha256 \
  -extfile san.cnf -extensions v3_req

openssl pkcs12 -export -in bars.crt -inkey bars.key -certfile root-ca.crt \
  -out temp.p12 -name bars -password pass:ufwiojq$hqoo~i1098*90w@yrf8qihc

keytool -importkeystore \
  -srckeystore temp.p12 -srcstoretype PKCS12 -srcstorepass ufwiojq$hqoo~i1098*90w@yrf8qihc \
  -destkeystore bars-keystore.jks -deststoretype JKS \
  -deststorepass ufwiojq$hqoo~i1098*90w@yrf8qihc -destkeypass ufwiojq$hqoo~i1098*90w@yrf8qihc -alias bars -noprompt

rm temp.p12 bars.key bars.csr bars.crt
```

# 5. Frontend (Angular) → PEM-файлы для ng serve --ssl
```bash
openssl req -new -newkey rsa:2048 -nodes \
  -keyout frontend.key -out frontend.csr \
  -subj "/CN=helios.cs.ifmo.ru" -config san.cnf

openssl x509 -req -in frontend.csr -CA root-ca.crt -CAkey root-ca.key \
  -CAcreateserial -out frontend.crt -days 3650 -sha256 \
  -extfile san.cnf -extensions v3_req

mv frontend.crt frontend.pem
mv frontend.key frontend-key.pem

rm frontend.csr
```

# 6. Truststore (общий для всех Java-приложений)
```bash
keytool -import -trustcacerts -alias ifmo-root-ca \
  -file root-ca.crt -keystore truststore.jks \
  -storepass ufwiojq$hqoo~i1098*90w@yrf8qihc -noprompt
```

# 7. Финальная уборка
```bash
rm san.cnf root-ca.key root-ca.srl  # .key больше НЕ нужен нигде
```
