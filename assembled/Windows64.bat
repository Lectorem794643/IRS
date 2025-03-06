@echo off
setlocal

:: Получаем путь к директории, где находится .bat файл
set "CURRENT_DIR=%~dp0"

:: Добавляем ещё одну папку
set "TARGET_DIR=%CURRENT_DIR%openjfx-17.0.14_windows-x64_bin-sdk\javafx-sdk-17.0.14\lib"

echo Скрипт запущен из: %CURRENT_DIR%
echo Итоговый путь: %TARGET_DIR%

:: Пример использования (можно запустить команду в этой папке)
java --module-path %TARGET_DIR% --add-modules javafx.controls,javafx.fxml -jar client.jar

endlocal
pause