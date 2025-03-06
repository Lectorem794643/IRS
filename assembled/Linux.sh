#!/bin/bash

# Определяем ОС и архитектуру
OS="$(uname -s)"
ARCH="$(uname -m)"

# Определяем целевую папку относительно текущей директории
case "$OS" in
    Linux)
        TARGET_DIR="./openjfx-17.0.14_linux-x64_bin-sdk/javafx-sdk-17.0.14/lib"
        ;;
    Darwin)
        case "$ARCH" in
            x86_64)
                TARGET_DIR="./openjfx-17.0.14_osx-x64_bin-sdk/javafx-sdk-17.0.14/lib"
                ;;
            arm64)
                TARGET_DIR="./openjfx-17.0.14_osx-aarch64_bin-sdk/javafx-sdk-17.0.14/lib"
                ;;
            *)
                echo "Неизвестная архитектура macOS: $ARCH"
                exit 1
                ;;
        esac
        ;;
    *)
        echo "Неизвестная ОС: $OS"
	sleep 15
        exit 1
        ;;
esac

# Получаем абсолютный путь к целевой папке
ABS_TARGET_DIR="$(cd "$TARGET_DIR" && pwd)"

# Проверяем, существует ли папка
if [ ! -d "$ABS_TARGET_DIR" ]; then
    echo "Ошибка: папка $ABS_TARGET_DIR не найдена!"
    exit 1
fi

echo "ОС: $OS, Архитектура: $ARCH"
echo "Целевая папка: $ABS_TARGET_DIR"

# Запуск Java-приложения
java --module-path %TARGET_DIR% --add-modules javafx.controls,javafx.fxml -jar client.jar 

