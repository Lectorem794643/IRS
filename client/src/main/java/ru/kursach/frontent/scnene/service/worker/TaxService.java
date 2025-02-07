package ru.kursach.frontent.scnene.service.worker;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.kursach.frontent.dto.Tax;
import ru.kursach.frontent.scnene.service.BaseService;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class TaxService extends BaseService<Tax> {
    private TableView<Tax> tableViewTax;
    private TableColumn<Tax, String> columnTypeTax, columnSumTax, columnStatusTax, columnNameOrganizationsTax, columnFIOTax, columnDateTax;
    private DatePicker dateTax;
    private TextField sumTax;
    private ComboBox<String> nameOrganizationTax, fioTax, typeTax, statusTax;
    private final Tax duplicate = new Tax();

    public void init(){
        columnDateTax.setCellValueFactory(new PropertyValueFactory<>("payingDeadline"));
        columnNameOrganizationsTax.setCellValueFactory(new PropertyValueFactory<>("organizationName"));
        columnFIOTax.setCellValueFactory(new PropertyValueFactory<>("userName"));
        columnTypeTax.setCellValueFactory(new PropertyValueFactory<>("taxType"));
        columnStatusTax.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnSumTax.setCellValueFactory(new PropertyValueFactory<>("sum"));

    }

    private boolean isAnyFieldEmpty() {
            return sumTax.getText().isEmpty() || dateTax.getValue() == null
                    || nameOrganizationTax.getValue() == null || fioTax.getValue() == null
                    || typeTax.getValue() == null || statusTax.getValue() == null;
    }

    private void highlightEmptyFields() {
        highlightField(nameOrganizationTax, nameOrganizationTax.getValue() == null);
        highlightField(dateTax, dateTax.getValue() == null);
        highlightField(fioTax, fioTax.getValue() == null);
        highlightField(typeTax, typeTax.getValue() == null);
        highlightField(statusTax, statusTax.getValue() == null);
        highlightField(sumTax, sumTax.getText().isEmpty());
    }

    private void addListeners() {
        addFieldListener(nameOrganizationTax, () -> duplicate.getOrganizationName());
        addFieldListener(dateTax, () -> duplicate.getPayingDeadline());
        addFieldListener(fioTax, () -> duplicate.getUserName());
        addFieldListener(typeTax, () -> duplicate.getTaxType());
        addFieldListener(statusTax, () -> duplicate.getStatus());
        addFieldListener(sumTax, () -> duplicate.getSum());
    }

    @Override
    protected String getClientResponse() throws IOException {
        return "";
    }

    @Override
    protected TableView<Tax> getTableView() {
        return tableViewTax;
    }

    @Override
    protected Class<Tax> getTableViewDataClass() {
        return Tax.class;
    }
}
