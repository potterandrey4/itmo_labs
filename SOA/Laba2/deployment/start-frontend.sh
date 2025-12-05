#!/bin/bash
echo "=== Запуск Angular фронтенда с HTTPS на порту 4200 ==="
cd ../frontend_angular
npm install --silent > /dev/null
echo "Фронтенд запущен: https://localhost:4200"
npm run start
