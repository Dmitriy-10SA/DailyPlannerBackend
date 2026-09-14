# syntax=docker/dockerfile:1

# Этап сборки production-версии приложения
# JDK 21 необходим Gradle и Kotlin для компиляции JS и Wasm
# Alpine здесь не используется: Kotlin загружает Node.js-бинарники под glibc
FROM eclipse-temurin:21-jdk AS build

# Библиотека libatomic необходима Node.js, который Gradle использует
# при подготовке и оптимизации WebAssembly-дистрибутива
RUN apt-get update \
    && apt-get install -y --no-install-recommends libatomic1

# Рабочая директория изолирует файлы проекта внутри этапа сборки
WORKDIR /workspace

# Сначала копируются Gradle Wrapper и конфигурация проекта
# Такой порядок позволяет Docker повторно использовать слой с зависимостями,
# когда изменился только исходный код приложения
COPY gradlew gradle.properties settings.gradle.kts build.gradle.kts ./
COPY gradle/wrapper ./gradle/wrapper
COPY gradle/libs.versions.toml ./gradle/libs.versions.toml
COPY shared/build.gradle.kts ./shared/build.gradle.kts
COPY webApp/build.gradle.kts ./webApp/build.gradle.kts
COPY kotlin-js-store ./kotlin-js-store

# Исходный код копируется после файлов сборки
COPY shared/src ./shared/src
COPY webApp/src ./webApp/src

# Ограничение памяти Node.js не позволяет Webpack занять всю память сборочного контейнера
ENV NODE_OPTIONS="--max-old-space-size=4096"

# Создаётся production JS-дистрибутив, совместимый с основными браузерами
# Отдельная Wasm-сборка не выполняется, чтобы не удваивать потребление памяти на Render
# Один Gradle worker предотвращает параллельный запуск ресурсоёмких задач
# Кэш Gradle ускоряет повторные сборки и не попадает в итоговый образ
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew :webApp:jsBrowserDistribution --no-daemon --max-workers=1

# Этап запуска содержит только Nginx и готовые статические файлы
# Образ nginx-unprivileged запускает сервер без прав root
FROM nginxinc/nginx-unprivileged:stable-alpine

# Адрес локального backend используется по умолчанию и переопределяется на Render
ENV BACKEND_URL=http://backend:8080

# Подстановка ограничена одной переменной, чтобы сохранить системные переменные Nginx
ENV NGINX_ENVSUBST_FILTER=BACKEND_URL

# При запуске контейнера адрес публичного backend подставляется в конфигурацию Nginx
COPY nginx.conf /etc/nginx/templates/default.conf.template

# Копирование production-дистрибутива из этапа сборки
COPY --from=build --chown=nginx:nginx \
    /workspace/webApp/build/dist/js/productionExecutable/ \
    /usr/share/nginx/html/

# Внутренний порт Nginx
# EXPOSE документирует порт, но сам по себе не публикует его на хосте
EXPOSE 8080

# Проверка подтверждает, что Nginx принимает HTTP-запросы внутри контейнера
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD wget -q -O /dev/null http://127.0.0.1:8080/health || exit 1

# SIGQUIT позволяет Nginx корректно завершить активные соединения
STOPSIGNAL SIGQUIT
