package ru.kursach.frontent.scnene;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import ru.kursach.frontent.dto.User;
import ru.kursach.frontent.dto.enams.UserRole;
import ru.kursach.frontent.scnene.interfase.UUIDReceiver;
import ru.kursach.frontent.scnene.service.admin.AdminService;

import java.util.UUID;

@Slf4j
public class AdminView implements UUIDReceiver {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> columnFIO, columnLogin, columnRole, columnPhone, columnEmail;
    @FXML
    private TextField fioField, loginField, emailField, phoneField, findForNameField;
    @FXML
    private ChoiceBox<UserRole> roleBox;
    @FXML
    private Text errorText;
    private AdminService service;

    public void initialize() {
        service = new AdminService(tableView, fioField, loginField, emailField, phoneField, roleBox, columnFIO, columnLogin, columnRole, columnPhone, columnEmail, errorText, findForNameField);
        service.initialize();
    }

    public void selectUser() {
        service.selectUser();
    }

    public void addUser() {
        service.addUser();
    }

    public void update() {
        service.update();
    }

    public void deleteUser() {
        service.deleteUser();
    }

    public void updateUser() {
        service.updateUser();
    }

    public void passwordReset() {
        service.passwordReset();
    }

    public void canceled() {
        service.unselectUser();
    }

    public void unSelectUser() {
        service.unselectUser();
    }

    public void findUser(){
        service.findUser();
    }


    @Override
    public void setUUID(UUID uuid) {}
}
