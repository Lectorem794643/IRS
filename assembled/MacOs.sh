OS="$(uname -s)"
ARCH="$(uname -m)"
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
ABS_TARGET_DIR="$(cd "$TARGET_DIR" && pwd)"
if [ ! -d "$ABS_TARGET_DIR" ]; then
    echo "Ошибка: папка $ABS_TARGET_DIR не найдена!"
    exit 1
fi

echo "ОС: $OS, Архитектура: $ARCH"
echo "Целевая папка: $ABS_TARGET_DIR"
java --module-path "$ABS_TARGET_DIR" --add-modules javafx.controls,javafx.fxml -jar client.jar

