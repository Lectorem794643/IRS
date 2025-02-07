package ru.kursach.frontent.scnene.service.admin;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import ru.kursach.frontent.dto.User;
import ru.kursach.frontent.dto.enams.UserRole;
import ru.kursach.frontent.http.api.AdminClient;
import ru.kursach.frontent.scnene.service.BaseService;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class AdminService extends BaseService<User> {
    private final AdminClient client = new AdminClient();
    private TableView<User> tableView;
    private TextField fioField, loginField, emailField, phoneField;
    private ChoiceBox<UserRole> roleBox;
    private User duplicate = new User();

    public void initialize(TableView<User> tableView, TableColumn<User, String> columnFIO, TableColumn<User, String> columnLogin, TableColumn<User, String> columnRole, TableColumn<User, String> columnPhone, TableColumn<User, String> columnEmail, TextField fioField, TextField loginField, TextField emailField, TextField phoneField, ChoiceBox<UserRole> roleBox) {
        this.tableView = tableView;
        this.fioField = fioField;
        this.loginField = loginField;
        this.emailField = emailField;
        this.phoneField = phoneField;
        this.roleBox = roleBox;

        columnFIO.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        columnRole.setCellValueFactory(new PropertyValueFactory<>("authority"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("tel"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleBox.getItems().addAll(UserRole.values());

        addListeners();
    }

    public void selectUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            duplicate = new User(selectedUser.getId(), selectedUser.getName(), selectedUser.getLogin(), selectedUser.getEmail(), selectedUser.getTel(), selectedUser.getAuthority());
            fioField.setText(selectedUser.getName());
            loginField.setText(selectedUser.getLogin());
            emailField.setText(selectedUser.getEmail());
            phoneField.setText(selectedUser.getTel());
            roleBox.setValue(selectedUser.getAuthority());
        }
    }

    public void addUser() {
        if (isAnyFieldEmpty()) {
            highlightEmptyFields();
        } else {
            User user = new User(fioField.getText(), loginField.getText(), emailField.getText(), phoneField.getText(), roleBox.getValue());
            try {
                client.addUser(user);
                update();
                unselectUser();
            } catch ( IOException e) {
                log.warn("Ошибка при добавлении пользователя: {}", e.getLocalizedMessage());
            }
        }
    }

    public void unselectUser() {
        fioField.clear();
        loginField.clear();
        emailField.clear();
        phoneField.clear();
        roleBox.setValue(null);

        resetFieldStyle(fioField);
        resetFieldStyle(loginField);
        resetFieldStyle(emailField);
        resetFieldStyle(phoneField);
        resetFieldStyle(roleBox);
    }


    public void deleteUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                client.deleteUser(selectedUser);
                update();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateUser() {
        if (isAnyFieldEmpty()) {
            highlightEmptyFields();
        } else {
            User selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                selectedUser.setName(fioField.getText());
                selectedUser.setLogin(loginField.getText());
                selectedUser.setEmail(emailField.getText());
                selectedUser.setTel(phoneField.getText());
                selectedUser.setAuthority(roleBox.getValue());

                log.debug("Обновление пользователя: {}", selectedUser);
                try {
                    client.updateUser(selectedUser);
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void update() {
        unselectUser();
        fetchData();
    }

    @Override
    protected String getClientResponse() throws IOException {
        return client.getAllUser();
    }

    @Override
    protected TableView<User> getTableView() {
        return tableView;
    }

    @Override
    protected Class<User> getTableViewDataClass() {
        return null;
    }


    private boolean isAnyFieldEmpty() {
        return fioField.getText().isEmpty() || loginField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty() || roleBox.getValue() == null;
    }

    private void highlightEmptyFields() {
        highlightField(fioField, fioField.getText().isEmpty());
        highlightField(loginField, loginField.getText().isEmpty());
        highlightField(emailField, emailField.getText().isEmpty());
        highlightField(phoneField, phoneField.getText().isEmpty());
        highlightField(roleBox, roleBox.getValue() == null);
    }

    private void addListeners() {
        addFieldListener(fioField, () -> duplicate.getName());
        addFieldListener(loginField, () -> duplicate.getLogin());
        addFieldListener(emailField, () -> duplicate.getEmail());
        addFieldListener(phoneField, () -> duplicate.getTel());
        addFieldListener(roleBox, () -> duplicate.getAuthority());
    }

    public void passwordReset() {
    }

    public void setUuid(UUID uuid) {

    }
}
