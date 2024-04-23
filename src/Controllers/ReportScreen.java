package Controllers;

import DBAccess.AppointmentsQuery;
import DBAccess.ContactsQuery;
import DBAccess.CountriesQuery;
import DBAccess.CustomersQuery;
import Model.Appointments;
import Model.Contacts;
import Model.Countries;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This is the ReportScreen controller class and it implements an Initializable override method. This class handles all the
 * functionality for the Reports Screen of the application.
 */
public class ReportScreen implements Initializable {

    @FXML
    public TableView<Appointments> appointmentsTableView;
    public TableColumn<Object, Object> appointIdCol;
    public TableColumn<Object, Object> appointTitleCol;
    public TableColumn<Object, Object> appointDescriptionCol;
    public TableColumn<Object, Object> appointLocationCol;
    public TableColumn<Object, Object> appointTypeCol;
    public TableColumn<Object, Object> startDateCol;
    public TableColumn<Object, Object> endDateCol;
    public TableColumn<Object, Object> appointCustomerIdCol;
    public TableColumn<Object, Object> appointUserIdCol;
    public TableColumn<Object, Object> appointContactCol;
    public TableColumn<Object, Object> contactNameCol;

    @FXML
    public RadioButton allAppointRadio;
    public ToggleGroup appointmentsGroup;

    @FXML
    public Button customersNavBtn;
    public Button appointmentsNavBtn;
    public Button clearBtn;

    @FXML
    public ComboBox<Countries> countriesCombo;
    public ComboBox<Month> monthCombo;
    public ComboBox<String> typeCombo;
    public ComboBox<Contacts> contactCombo;


    @FXML
    public TextField numAppointTxt;
    public TextField numCustomersTxt;

    ObservableList<Appointments> appointmentList;
    ObservableList<Appointments> contact;
    ObservableList<Customers> customerList;

    ObservableList<String> types = FXCollections.observableArrayList();
    ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    ObservableList<Countries> countriesList = FXCollections.observableArrayList();
    ObservableList<Month> monthList = FXCollections.observableArrayList();
    ObservableList<String> monthListString = FXCollections.observableArrayList();


    /**
     * LAMBDA:This method has one lambda which uses the parameter e and is used to call the getSource() method to handle the action even to navigate the user to the customers screen.
     * Having this lambda handle the ActionEvent enables not needing a separate method for this process cleaning up code and making it more readable.
     * This code will run at the beginning on the load of this page and execute the code inside first.
     * This method initializes the appointmentsTableView and populates the combo boxes on this screen with data.
     *
     * @param url            The uniform Resource Locator that is a pointer to a location.
     * @param resourceBundle Contains locale specific objects for the class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appointCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));


        //LAMBDA: handles setting up the action event for the customerNavBtn that returns the user to the customers screen upon pressing the button.
        customersNavBtn.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/customersScreen.fxml")));
                Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        contactList = ContactsQuery.selectAllContacts();
        contactCombo.setItems(contactList);

        countriesList = CountriesQuery.selectAllCountries();
        countriesCombo.setItems(countriesList);

        monthList.add(Month.JANUARY);
        monthList.add(Month.FEBRUARY);
        monthList.add(Month.MARCH);
        monthList.add(Month.APRIL);
        monthList.add(Month.MAY);
        monthList.add(Month.JUNE);
        monthList.add(Month.JULY);
        monthList.add(Month.AUGUST);
        monthList.add(Month.SEPTEMBER);
        monthList.add(Month.OCTOBER);
        monthList.add(Month.NOVEMBER);
        monthList.add(Month.DECEMBER);

        monthCombo.setItems(monthList);

        typeCombo.setDisable(true);
        typeCombo.setPromptText("Choose a Month ");

        appointmentList = AppointmentsQuery.selectAllAppointments();
        for (Appointments a : appointmentList) {
            String type = a.getType();
            types.add(type);
        }
        typeCombo.setItems(types);
    }


    /**
     * This method will clear all text fields, tableview data, and combo boxes by reloading the screen.
     *
     * @param actionEvent When the clearBtn is pressed.
     */
    public void clearAllFields(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/reportsScreen.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method will return the user back to the customers screen
    //Replaced by the lambda in the Initializable method.
//    public void backToCustomers(ActionEvent actionEvent) {
//        try {
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/customersScreen.fxml")));
//            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root, 1231, 681);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * This method will navigate the user to the appointments screen.
     *
     * @param actionEvent When the appointmentsNavBtn is pressed.
     */
    public void backToAppointments(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/appointmentScreen.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method will set the appointment tableview with all the appointments in the database if the radio button is selected and will clear the tableview if it is unselected.
     *
     * @param actionEvent When the radio button is selected or a value in the contactCombo is selected.
     */
    public void filterAppointments(ActionEvent actionEvent) {
        if (allAppointRadio.isSelected()) {
            appointmentList = AppointmentsQuery.selectAllAppointments();
            appointmentsTableView.setItems(appointmentList);
            contactCombo.getSelectionModel().clearSelection();

        } else if (!allAppointRadio.isSelected()) {
            appointmentsTableView.getItems().clear();
        }
    }

    /**
     * This method  will filter the appointment tableview to show all appointments that are under the selected contact.
     *
     * @param actionEvent When the user selects a value from the contactCombo combo box.
     */
    public void filterByContact(ActionEvent actionEvent) {
        if (contactCombo.getSelectionModel().getSelectedItem() != null) {
            int contactId = contactCombo.getValue().getContactId();
            contact = AppointmentsQuery.selectByContact(contactId);
            appointmentsTableView.setItems(contact);
            allAppointRadio.setSelected(false);
        }

    }

    /**
     * This method gets the values entered in the month combo and type combo and passes them into the appointmentsQuery which selects the appointments in that range.
     *
     * @param actionEvent When a value is selected in the monthComo or typeCombo boxes.
     */
    public void filterMonthType(ActionEvent actionEvent) {
        ObservableList<Appointments> a = FXCollections.observableArrayList();
        if (monthCombo.getValue() != null) {
            typeCombo.setDisable(false);
            typeCombo.setPromptText("Choose a Type");
        }

        if ((monthCombo != null) && (typeCombo != null)) {
            String monthName = monthCombo.getSelectionModel().getSelectedItem().toString();
            a = AppointmentsQuery.getMonthTypes(monthName, typeCombo.getValue());
        }
        numAppointTxt.setText(String.valueOf(a.size()));
    }

    //This method retrieves the amount of customers based on the country that is selected and sets the text field with the numerical amount of customers.
    //This method implements an anonymous lambda to filter through the customerList that contains all customers in the database and returns the filteredCustomers list with just the customers from the selected country.
    //This lambda simplifies the expressions needed to perform the search through the customer list, which also improves the readability of the method.
    //Using this lambda also omits the need to have any for loops to iterate through the customerList which will improve performance.

    /**
     * LAMBDA:This method implements an anonymous lambda to filter through the customerList that contains all customers in the database and returns the filteredCustomers list with just the customers from the selected country.
     * This lambda simplifies the expressions needed to perform the search through the customer list, which also improves the readability of the method.
     * Using this lambda also omits the need to have any for loops to iterate through the customerList which will improve performance.
     * This method retrieves the amount of customers based on the country that is selected and sets the text field with the numerical amount of customers.
     *
     * @param actionEvent When a selection is made in the countriesCombo combo box is delivered.
     */
    public void filterByCountry(ActionEvent actionEvent) {
        customerList = CustomersQuery.selectAllCustomers();

        //Filtered anonymous lambda
        ObservableList<Customers> filteredCustomers = customerList.filtered(t -> {
            if (t.getCountry().equals(countriesCombo.getValue().toString()))
                return true;
            return false;
        });
        numCustomersTxt.setText(String.valueOf(filteredCustomers.size()));
    }
}
