package ru.kursach.frontent.scnene.service.worker;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kursach.frontent.dto.Organization;
import ru.kursach.frontent.http.api.WorkerClient;
import ru.kursach.frontent.scnene.service.BaseService;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class OrganizationService extends BaseService<Organization> {
    private final WorkerClient client = new WorkerClient();
    private final Organization duplicate = new Organization();
    private TableView<Organization> tableViewOrganization;
    private TextField nameOrganization, kppOrganization, innOrganization, addressOrganization;
    private TableColumn<Organization, String> columnNameOrganization, columnKppOrganization, columnInnOrganization, columnAddressOrganization;

    public void init() {
        columnNameOrganization.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnKppOrganization.setCellValueFactory(new PropertyValueFactory<>("kpp"));
        columnInnOrganization.setCellValueFactory(new PropertyValueFactory<>("inn"));
        columnAddressOrganization.setCellValueFactory(new PropertyValueFactory<>("address"));
        addFieldListener(nameOrganization, duplicate::getName);
        addFieldListener(kppOrganization, duplicate::getKpp);
        addFieldListener(innOrganization, duplicate::getInn);
        addFieldListener(addressOrganization, duplicate::getAddress);
    }

    public void unselectUser() {
        nameOrganization.clear();
        kppOrganization.clear();
        innOrganization.clear();
        addressOrganization.clear();
        resetFieldStyle(nameOrganization);
        resetFieldStyle(kppOrganization);
        resetFieldStyle(innOrganization);
        resetFieldStyle(addressOrganization);
    }

    public void update() {
        this.fetchData();
    }


    private boolean isAnyFieldEmpty() {
        return nameOrganization.getText().isEmpty() || kppOrganization.getText().isEmpty()
                || innOrganization.getText().isEmpty() || addressOrganization.getText().isEmpty();
    }

    private void highlightEmptyFields() {
        highlightField(nameOrganization, nameOrganization.getText().isEmpty());
        highlightField(kppOrganization, kppOrganization.getText().isEmpty());
        highlightField(innOrganization, innOrganization.getText().isEmpty());
        highlightField(addressOrganization, addressOrganization.getText().isEmpty());
    }

    @Override
    protected String getClientResponse() throws IOException {
        return client.getAllOrganizations();
    }

    @Override
    protected TableView<Organization> getTableView() {
        return tableViewOrganization;
    }

    @Override
    protected Class<Organization> getTableViewDataClass() {
        return Organization.class;
    }
}
