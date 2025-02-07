package ru.kursach.frontent.scnene.service.worker;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kursach.frontent.dto.User;
import ru.kursach.frontent.scnene.service.BaseService;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class UserService extends BaseService<User> {
    private TableView<User> tableViewUsers;
    private TextField phoneUsers, fioUsers, emailUsers;
    private TableColumn<User, String> columnPhoneUsers, columnFIOUsers, columnEmailUsers;
    private final User duplicate = new User();

    public void init() {
        columnEmailUsers.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnFIOUsers.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPhoneUsers.setCellValueFactory(new PropertyValueFactory<>("tel"));

    }

    private boolean isAnyFieldEmpty() {
        return fioUsers.getText().isEmpty() || phoneUsers.getText().isEmpty() || emailUsers.getText().isEmpty();
    }

    private void highlightEmptyFields() {
        highlightField(fioUsers, fioUsers.getText().isEmpty());
        highlightField(phoneUsers, phoneUsers.getText().isEmpty());
        highlightField(emailUsers, emailUsers.getText().isEmpty());
    }


    private void addListeners() {
        addFieldListener(fioUsers, duplicate::getName);
        addFieldListener(phoneUsers, duplicate::getTel);
        addFieldListener(emailUsers, duplicate::getEmail);
    }

    @Override
    protected String getClientResponse() throws IOException {
        return "";
    }

    @Override
    protected TableView<User> getTableView() {
        return null;
    }

    @Override
    protected Class<User> getTableViewDataClass() {
        return User.class;
    }
}
