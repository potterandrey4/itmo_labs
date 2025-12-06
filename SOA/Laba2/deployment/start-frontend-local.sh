#!/bin/bash
echo "=== Запуск Angular фронтенда в dev mode (ng serve) с HTTPS ==="
cd ../frontend_angular
npm install --silent > /dev/null
echo "Фронтенд запущен: https://localhost:4200"
npm run start:https
