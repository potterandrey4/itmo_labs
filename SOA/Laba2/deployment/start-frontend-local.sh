#!/bin/bash
echo "=== Запуск Angular фронтенда в dev mode (ng serve) ==="
cd ../frontend_angular
npm install --silent > /dev/null
echo "Фронтенд запущен: http://localhost:4200"
npm run start
