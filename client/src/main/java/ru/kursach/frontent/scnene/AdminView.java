package ru.kursach.frontent.scnene;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import ru.kursach.frontent.dto.User;
import ru.kursach.frontent.dto.enams.UserRole;
import ru.kursach.frontent.scnene.interfase.UUIDReceiver;
import ru.kursach.frontent.scnene.service.admin.AdminService;

import java.util.UUID;

@Slf4j
public class AdminView implements UUIDReceiver {
    private final AdminService service = new AdminService();
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> columnFIO, columnLogin, columnRole, columnPhone, columnEmail;
    @FXML
    private TextField fioField, loginField, emailField, phoneField;
    @FXML
    private ChoiceBox<UserRole> roleBox;

    public void initialize() {
        service.initialize(tableView, columnFIO, columnLogin, columnRole, columnPhone,
                columnEmail, fioField, loginField, emailField, phoneField, roleBox);
    }

    public void selectUser() {
        service.selectUser();
    }

    public void addUser() {
        service.addUser();
    }

    public void update(){
        service.update();
    }

    public void deleteUser() {
        service.deleteUser();
    }
    public void updateUser() {
        service.updateUser();
    }
    public void passwordReset(){
        service.passwordReset();
    }
    public void canceled(){
        service.unselectUser();
    }

    public void unSelectUser() {
        service.unselectUser();
    }


    @Override
    public void setUUID(UUID uuid) {
        service.setUuid(uuid);
    }
}
