# Задание
На основе разработанной в рамках лабораторной работы #1 спецификации реализовать два веб-сервиса и использующее их API клиентское приложение.

### Требования к реализации и развёртыванию сервисов:

- Первый ("вызываемый") веб-сервис должен быть реализован на фреймворке Spring MVC REST и развёрнут в окружении под управлением сервера приложений Payara.
- Второй веб-сервис должен быть реализован на фреймворке Spring MVC REST, развёрнут в окружении под управлением сервера приложений Tomcat и вызывать REST API первого сервиса.
- Для обоих сервисов необходимо реализовать все функции, задокументированные в API, в строгом соответствии со спецификацией!
- Доступ к обоим сервисам должен быть реализован по протоколу HTTPS с самоподписанным сертификатом сервера. Доступ к сервисам посредством HTTP без шифрования должен быть запрещён.


### Требования к клиентскому приложению:


# Структура решения

- `LabworkService/` – первый ("вызываемый") сервис (Spring MVC, WAR) развёрнут на **Payara**, CRUD API для лабораторных работ.
- `BarsService/` – второй сервис (Spring Boot + embedded Tomcat, JAR), вызывает REST API LabworkService для выполнения операций BARS.
- `frontend_angular/` – Angular клиент, предоставляющий единый интерфейс для обоих сервисов (фильтры, сортировки, пагинация, CRUD и операции BARS).

# LabworkService (Payara)

## Зависимости

Сервис использует Spring MVC 6, Hibernate, PostgreSQL и Jackson.

## Сборка

```bash
cd LabworkService
mvn clean package
# Полученный war (LabworkService-1.war) разворачивается в Payara
```

## Развёртывание на Payara с HTTPS

1. Импортировать сертификат в keystore Payara:
```bash
# Скопировать keystore в домен Payara
cp keystore.p12 $PAYARA_HOME/glassfish/domains/domain1/config/

# Или импортировать в существующий keystore
keytool -importkeystore -srckeystore keystore.p12 -srcstoretype PKCS12 \
  -destkeystore $PAYARA_HOME/glassfish/domains/domain1/config/keystore.jks \
  -deststoretype JKS -srcstorepass password -deststorepass changeit
```

2. Настроить HTTPS listener в Payara Admin Console или через asadmin:
```bash
asadmin create-ssl --type http-listener --certname s1as http-listener-2
```

3. Деплой приложения:
```bash
asadmin deploy target/LabworkService-1.war
```

Сервис будет доступен по: `https://localhost:8181/LabworkService-1`

# BarsService (Tomcat / Spring Boot)

## Зависимости

Сервис использует Spring Boot 3 с embedded Tomcat, RestTemplate с поддержкой SSL (Apache HttpClient 5).

## Конфигурация

Настройки в `src/main/resources/application.properties`:
- Порт: 8444 (HTTPS)
- SSL: keystore.p12 с самоподписанным сертификатом
- URL LabworkService: `https://localhost:8181/LabworkService-1`

```properties
server.port=8444
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password
labwork.service.base-url=https://localhost:8181/LabworkService-1
```

## Сборка и запуск

```bash
cd BarsService
mvn clean package
java -jar target/BarsService.jar
```

Сервис запускается на: `https://localhost:8444`

# Frontend

## Установка

```
cd frontend
npm install
```

## Сборка

```
npm run build
```

## Переменные окружения

Скопируйте `.env.example` в `.env` и укажите адреса обоих сервисов:

```
VITE_LABWORK_API_BASE_URL=http://localhost:8085/LabworkService_war
VITE_BARS_API_BASE_URL=http://localhost:8081/BarsService
```

## Режим разработки

```
npm run dev
```

Vite поднимет dev-server на `http://localhost:5173` с горячей перезагрузкой.


# Шпаргалка
### Задание переменной
*(важно: в пути не должно быть кириллицы)*

``
export PAYARA_HOME=/home/andrey/payara6
``

### Запустить сервер:
``
$PAYARA_HOME/bin/asadmin start-domain
``

### Остановить сервер:
``
$PAYARA_HOME/bin/asadmin stop-domain
``

### Задеплоить приложение:
``
$PAYARA_HOME/bin/asadmin deploy target/LabworkService-1.war
``

### Перезадеплоить приложение:
``
$PAYARA_HOME/bin/asadmin redeploy LabworkService-1
``

### Просмотреть задеплоенные приложения:
``
$PAYARA_HOME/bin/asadmin list-applications
``
### Удалить задеплоенное приложение:
``
$PAYARA_HOME/bin/asadmin undeploy LabworkService-1
``

### Просмотреть логи:
``
tail -f $PAYARA_HOME/glassfish/domains/domain1/logs/server.log
```

# HTTPS Настройка

## Самоподписанный сертификат

Keystore `keystore.p12` создан в корне проекта командой:
```bash
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 \
  -storetype PKCS12 -keystore keystore.p12 -validity 3650 \
  -dname "CN=localhost, OU=ITMO, O=SOA, L=SPb, ST=SPb, C=RU" \
  -storepass password -keypass password
```

## Для LabworkService (Payara):

1. Скопируйте keystore в конфигурацию Payara:
```bash
cp keystore.p12 $PAYARA_HOME/glassfish/domains/domain1/config/
```

2. Импортируйте в keystore Payara:
```bash
keytool -importkeystore -srckeystore keystore.p12 -srcstoretype PKCS12 \
  -destkeystore $PAYARA_HOME/glassfish/domains/domain1/config/keystore.jks \
  -srcstorepass password -deststorepass changeit -noprompt
```

3. Включите HTTPS на порту 8181 (http-listener-2) через Admin Console или:
```bash
asadmin set server-config.network-config.protocols.protocol.http-listener-2.security-enabled=true
```

## Для BarsService (Spring Boot + Tomcat):

Keystore уже включен в `src/main/resources/keystore.p12`, настройки в `application.properties`:
```properties
server.port=8444
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password
server.ssl.keyStoreType=PKCS12
server.ssl.keyAlias=tomcat
```

## Запуск

1. Запустите Payara и задеплойте LabworkService:
```bash
$PAYARA_HOME/bin/asadmin start-domain
$PAYARA_HOME/bin/asadmin deploy LabworkService/target/LabworkService-1.war
```

2. Запустите BarsService:
```bash
java -jar BarsService/target/BarsService.jar
```

3. Проверка:
- LabworkService: https://localhost:8181/LabworkService-1/labworks
- BarsService: https://localhost:8444/bars/discipline/{disciplineId}/add-minimal-point/{pointsToAdd}