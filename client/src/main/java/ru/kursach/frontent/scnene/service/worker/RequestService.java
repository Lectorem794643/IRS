package ru.kursach.frontent.scnene.service.worker;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kursach.frontent.dto.Request;
import ru.kursach.frontent.dto.enams.Status;
import ru.kursach.frontent.scnene.service.BaseService;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class RequestService extends BaseService<Request> {
    private TableView<Request> tableViewRequest;
    private TextField requestSubjectRequest;
    private TextArea bodySubjectRequest;
    private TableColumn<Request, String> columnThemeRequest, columnStateRequest, columnDateRequest, columnBodyRequest;
    private ComboBox<Status> statusRequest;
    private final Request duplicate= new Request();

    public void init() {
        columnBodyRequest.setCellValueFactory(new PropertyValueFactory<>("body"));
        columnDateRequest.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnStateRequest.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnThemeRequest.setCellValueFactory(new PropertyValueFactory<>("theme"));
    }


    private boolean isAnyFieldEmpty() {
        return statusRequest.getValue() == null
                || requestSubjectRequest.getText().isEmpty() || bodySubjectRequest.getText().isEmpty();
    }

    private void highlightEmptyFields() {
        highlightField(requestSubjectRequest, requestSubjectRequest.getText().isEmpty());
        highlightField(bodySubjectRequest, bodySubjectRequest.getText().isEmpty());
        highlightField(statusRequest, statusRequest.getValue() == null);
    }

    private void addListeners() {
        addFieldListener(requestSubjectRequest, () -> duplicate.getTheme());
        addFieldListener(bodySubjectRequest, () -> duplicate.getBody());
        addFieldListener(statusRequest, () -> duplicate.getStatus());
    }

    @Override
    protected String getClientResponse() throws IOException {
        return "";
    }

    @Override
    protected TableView<Request> getTableView() {
        return null;
    }

    @Override
    protected Class<Request> getTableViewDataClass() {
        return Request.class;
    }
}
