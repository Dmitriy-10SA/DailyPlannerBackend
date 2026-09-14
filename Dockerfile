# syntax=docker/dockerfile:1

# Этап сборки приложения
# Полный JDK необходим Gradle для компиляции исходного кода и создания JAR-файла
FROM eclipse-temurin:21-jdk-alpine AS build

# Рабочая директория этапа сборки
WORKDIR /workspace

# Копирование Gradle Wrapper и файлов конфигурации сборки
# Wrapper обеспечивает использование заданной проектом версии Gradle
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle

# Копирование исходного кода приложения
COPY src ./src

# Сборка исполняемого Spring Boot JAR-файла
# clean удаляет результаты предыдущей сборки
# bootJar создаёт JAR со всеми необходимыми зависимостями
# --no-daemon не оставляет фоновый процесс Gradle внутри временного контейнера
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew clean bootJar --no-daemon

# Этап запуска приложения
# JRE достаточно для запуска готового JAR и занимает меньше места, чем JDK
FROM eclipse-temurin:21-jre-alpine

# Создание системной группы и пользователя без привилегий root
# Это ограничивает возможный ущерб при компрометации приложения
RUN addgroup -S application \
    && adduser -S application -G application

# Рабочая директория приложения
WORKDIR /app

# Копирование собранного JAR из этапа build
# Владельцем файла сразу назначается непривилегированный пользователь application
COPY --from=build --chown=application:application /workspace/build/libs/*.jar app.jar

# Все последующие команды и само приложение выполняются от пользователя application
USER application

# Документирование внутреннего порта Spring Boot
# EXPOSE не публикует порт на хосте и не открывает к нему доступ из интернета
# Фактический доступ к порту определяется настройками docker-compose или docker run
EXPOSE 8080

# Проверка доступности приложения через служебный endpoint Spring Boot
# Render настраивает собственную проверку по тому же адресу /actuator/health
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget -q -O /dev/null http://127.0.0.1:${PORT:-8080}/actuator/health || exit 1

# Запуск приложения
# MaxRAMPercentage ограничивает используемую JVM память долей доступной контейнеру памяти
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
