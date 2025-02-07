package ru.kursach.frontent.scnene;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.kursach.frontent.dto.*;
import ru.kursach.frontent.dto.enams.Status;
import ru.kursach.frontent.scnene.service.worker.OrganizationService;
import ru.kursach.frontent.scnene.service.worker.RequestService;
import ru.kursach.frontent.scnene.service.worker.TaxService;
import ru.kursach.frontent.scnene.service.worker.UserService;

public class WorkerView {
    @FXML
    private TableView<User> tableViewUsers;
    @FXML
    private TextField phoneUsers, fioUsers, emailUsers;
    @FXML
    private TableColumn<User, String> columnPhoneUsers, columnFIOUsers, columnEmailUsers;

    @FXML
    private TableView<Request> tableViewRequest;
    @FXML
    private TextField requestSubjectRequest;
    @FXML
    private TextArea bodySubjectRequest;
    @FXML
    private TableColumn<Request, String> columnThemeRequest, columnStateRequest, columnDateRequest, columnBodyRequest;
    @FXML
    private ComboBox<Status> statusRequest;

    @FXML
    private TableView<Tax> tableViewTax;
    @FXML
    private TableColumn<Tax, String> columnTypeTax, columnSumTax, columnStatusTax, columnNameOrganizationsTax, columnFIOTax, columnDateTax;
    @FXML
    private DatePicker dateTax;
    @FXML
    private TextField sumTax;
    @FXML
    private ComboBox<String> nameOrganizationTax, fioTax, typeTax, statusTax;

    @FXML
    private TableView<Organization> tableViewOrganization;
    @FXML
    private TextField nameOrganization, kppOrganization, innOrganization, addressOrganization;
    @FXML
    private TableColumn<Organization, String> columnNameOrganization, columnKppOrganization, columnInnOrganization, columnAddressOrganization;

    private final UserService userService = new UserService(tableViewUsers, phoneUsers, fioUsers, emailUsers, columnPhoneUsers, columnFIOUsers, columnEmailUsers);
    private final RequestService requestService = new RequestService(tableViewRequest, requestSubjectRequest, bodySubjectRequest, columnThemeRequest, columnStateRequest, columnDateRequest, columnBodyRequest, statusRequest);
    private final TaxService taxService = new TaxService(tableViewTax, columnTypeTax, columnSumTax, columnStatusTax, columnNameOrganizationsTax, columnFIOTax, columnDateTax, dateTax, sumTax, nameOrganizationTax, fioTax, typeTax, statusTax);
    private final OrganizationService organizationService = new OrganizationService(tableViewOrganization, nameOrganization, kppOrganization, innOrganization, addressOrganization, columnNameOrganization, columnKppOrganization, columnInnOrganization, columnAddressOrganization);

    @FXML
    private void initialize() {
        userService.init();
        requestService.init();
        taxService.init();
        organizationService.init();
    }
}
