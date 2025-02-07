package ru.kursach.frontent.scnene;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.kursach.frontent.dto.Request;
import ru.kursach.frontent.dto.Tax;
import ru.kursach.frontent.http.api.UserClient;
import ru.kursach.frontent.scnene.interfase.UUIDReceiver;
import ru.kursach.frontent.scnene.service.user.RequestService;
import ru.kursach.frontent.scnene.service.user.TaxService;

import java.util.UUID;

public class UserView implements UUIDReceiver {
    @FXML
    private TextField requestSubjectRequest;
    @FXML
    private TextArea bodySubjectRequest;
    @FXML
    private TableView<Request> tableViewRequest;
    @FXML
    private TableView<Tax> tableViewTax;
    @FXML
    private Text textErrorRequest, textErrorTax;
    @FXML
    private TableColumn<Request, String> columnThemeRequest, columnDateRequest, columnStateRequest, columnBodyRequest;
    @FXML
    private TableColumn<Tax, String> columnNameTax, columnSumTax, columnDataTax, columnStatusTax;

    private final UserClient client = new UserClient();
    private RequestService serviceRequest;
    private TaxService serviceTax;
    @FXML
    public void initialize() {
        serviceRequest = new RequestService(client, tableViewRequest,requestSubjectRequest, bodySubjectRequest, textErrorRequest, columnThemeRequest, columnDateRequest, columnStateRequest, columnBodyRequest);
        serviceTax = new TaxService(client, tableViewTax, columnNameTax, columnSumTax, columnDataTax, columnStatusTax, textErrorTax);
        serviceRequest.init();
        serviceTax.init();
    }

    public void updateRequest() {
        serviceRequest.update();
    }

    public void sendRequest() {
        serviceRequest.sendRequest();
    }
    public void updateTax(){
        serviceTax.update();
    }

    public void canceledRequest(){
    }



    public void selectionRequest(){
        serviceRequest.update();
    }

    public void selectionTax(){
        serviceTax.update();
    }

    @Override
    public void setUUID(UUID uuid) {
        client.setUUID(uuid);
    }
}
