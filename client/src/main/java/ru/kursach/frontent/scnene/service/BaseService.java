package ru.kursach.frontent.scnene.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import ru.kursach.frontent.http.Client;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public abstract class BaseService<T> {
    protected Client client; // Класс client
    protected Text textError;
    protected abstract String getClientResponse() throws IOException; // Метод для получения данных (чтобы каждый сервис мог его переопределить)

    protected abstract TableView<T> getTableView(); // Метод для получения TableView, который используется в каждом сервисе


    protected void highlightField(Control field, boolean condition) {
        field.setStyle(condition ? "-fx-background-color: red;" : "-fx-background-color: white;");
    }

    protected <T> void addFieldListener(Control field, java.util.function.Supplier<T> expectedValueSupplier) {
        if (field instanceof TextField textField) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> updateFieldStyle(textField, newValue, expectedValueSupplier.get()));
        } else if (field instanceof ChoiceBox<?> choiceBox) {
            choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> updateFieldStyle(choiceBox, newValue, expectedValueSupplier.get()));
        }
    }

    protected void updateFieldStyle(Control field, Object newValue, Object expectedValue) {
        if (newValue == null || newValue.toString().isEmpty()) {
            field.setStyle("-fx-background-color: white;");
        } else {
            field.setStyle(!newValue.equals(expectedValue) ? "-fx-background-color: #d3bb10;" : "-fx-background-color: white;");
        }
    }

    protected void resetFieldStyle(Control field) {
        field.setStyle("-fx-background-color: white;");
    }

    protected void fetchData() {
        try {
            loadData();
        } catch (IOException e) {
            log.warn("Ошибка при получении данных {}", e.getMessage());
            textError.setText(String.format("Ошибка при получении данных: %s", e.getLocalizedMessage()));
        }
    }

    private void loadData() throws IOException {
        getTableView().getItems().clear();
        log.info("Запрос на получение данных");
        String clientResponse = getClientResponse();
        log.debug(clientResponse);
        ObservableList<T> data = FXCollections.observableArrayList(
                new ObjectMapper().readValue(clientResponse, getTableViewDataClass()));
        getTableView().setItems(data); // Передаем класс данных для загрузки в TableView
    }

    // Этот метод можно переопределить в каждом сервисе, чтобы указать нужный класс для TableView
    protected abstract Class<T> getTableViewDataClass();
}
