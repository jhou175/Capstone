package Controllers;

import DBAccess.*;
import Model.*;
import javafx.beans.value.ObservableValue;
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
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the CustomersScreen class which is a controller and implements an Initializable method.
 * This class handles all functionality for the customers screen of the application.
 */
public class CustomersScreen implements Initializable {
    @FXML
    public TableView<Customers> customerTableView;
    public TableColumn<Object, Object> customerIdCol;
    public TableColumn<Object, Object> customerNameCol;
    public TableColumn<Object, Object> customerAddressCol;
    public TableColumn<Object, Object> customerPostalCol;
    public TableColumn<Object, Object> customerPhoneCol;
    public TableColumn<Object, Object> customerDivisionCol;
    public TableColumn<Object, Object> divisionCol;
    public TableColumn<Object, Object> countryNameCol;
    public TableColumn<Object, Object> countryIdCol;
    public TableColumn<Object, Object> zoomEmailCol;

    @FXML
    public TextField customerIdTxt;
    public TextField addressTxt;
    public TextField postalTxt;
    public TextField phoneTxt;
    public TextField customerNameTxt;
    public TextField customerSearchTxt;
    public TextField zoomEmailTxt;
    public ComboBox<FirstLevelDivisions> divisionCombo;
    public ComboBox<Countries> countryCombo;

    @FXML
    public Button addCustomerBtn;
    public Button updateCustomerBtn;
    public Button manageAptBtn;
    public Button modifyCustomerBtn;
    public Button deleteCustomerBtn;
    public Button clearBtn;
    public Button reportsBtn;
    public Button searchBtn;
    public RadioButton virtualCustomerRadio;
    public Button customerResetBtn;

    ObservableList<Customers> customerList;
    ObservableList<FirstLevelDivisions> divisionList = FXCollections.observableArrayList();
    ObservableList<Countries> countriesList = FXCollections.observableArrayList();


    /**
     * Initialization method retrieves and sets all the customer data for the customers tableview and the country and division data
     * for the combo boxes.
     *
     * @param url            The uniform Resource Locator that is a pointer to a location.
     * @param resourceBundle Contains locale specific objects for the class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        countryNameCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        zoomEmailCol.setCellValueFactory(new PropertyValueFactory<>("zoomEmail"));


        divisionList = FirstLevelDivisionQuery.selectAllDivisionId();
        countriesList = CountriesQuery.selectAllCountries();

        customerList = CustomersQuery.selectAllCustomers();
        customerTableView.setItems(customerList);


        divisionCombo.setItems(divisionList);
        divisionCombo.setVisibleRowCount(5);
        divisionCombo.setPromptText("Please Select a Division");
        countryCombo.setItems(countriesList);
        countryCombo.setPromptText("Please Select a Country");

        updateCustomerBtn.setDisable(true);
        addCustomerBtn.setDisable(false);
        zoomEmailTxt.setDisable(true);
        zoomEmailCol.setVisible(false);

    }


    /**
     * Method retrieves all input from text fields and combo boxes and adds the customer to the customers table.
     * Uses the checkInput() method to validate if all text fields and combo boxes have been populated.
     * If the virtual customer radio button is selected it will enter that customer into the virtual customer table.
     *
     * @param actionEvent When the addCustomerBtn is pressed.
     */
    public void addCustomer(ActionEvent actionEvent) {
        int divisionID = 0;
        String division = "";
        //String country = countryCombo.getValue().toString();
        String name = customerNameTxt.getText();
        String address = addressTxt.getText();
        String zip = postalTxt.getText();
        String phone = phoneTxt.getText();
        String zoomEmail = zoomEmailTxt.getText();


        if (checkInput()) {
            Alert blackAlert = new Alert(Alert.AlertType.ERROR, "All fields must be filled out to add a customer. Please check and try again.");
            blackAlert.setHeaderText("Blank input field(s).");
            blackAlert.showAndWait();
        }
        if (virtualCustomerRadio.isSelected() && (zoomEmail.isBlank())) {
            Alert blackAlert = new Alert(Alert.AlertType.ERROR, "Zoom email field is blank, please enter in a zoom email or deselect virtual customer.");
            blackAlert.setHeaderText("Blank input field(s).");
            blackAlert.showAndWait();

        } else {


//        else if (!addressRegex(address, countryCombo.getSelectionModel().getSelectedItem().toString())) {
//            if(countryCombo.getSelectionModel().getSelectedItem().toString().equals("U.S")) {
//                Alert addressFormatAlert = new Alert(Alert.AlertType.ERROR, "Please enter in an United States address in the format of ex:\n 123 ABC Street, White Plains");
//                addressFormatAlert.setHeaderText("Incorrect United States address format.");
//                addressFormatAlert.showAndWait();
//            }
//            else if(countryCombo.getSelectionModel().getSelectedItem().toString().equals("UK")){
//                Alert addressFormatAlert = new Alert(Alert.AlertType.ERROR, "Please enter in an United Kingdom address in the format of ex:\n 123 ABC Street, Greenwich, London");
//                addressFormatAlert.setHeaderText("Incorrect United Kingdom address format.");
//                addressFormatAlert.showAndWait();
//            }
//            else if(countryCombo.getSelectionModel().getSelectedItem().toString().equals("Canada")){
//                Alert addressFormatAlert = new Alert(Alert.AlertType.ERROR, "Please enter in a Canadian address in the format of ex:\n 123 ABC Street, Newmarket");
//                addressFormatAlert.setHeaderText("Incorrect Canadian address format.");
//                addressFormatAlert.showAndWait();
//            }

            try {
                division = divisionCombo.getValue().toString();

                for (FirstLevelDivisions f : divisionList) {
                    if (f.getDivisionName().equals(division)) {
                        divisionID = f.getDivisionId();
                    }
                }

                //The following lines will detect if the virtual radio button is selected or not. If it is it will enter in the customer into the customers table and will retrieve the last
                //customerID created and use that ID to enter in that customer into the virtual customer table with the CustomerID and Zoom Email and will create a auto-incremental virtualCustomerID.
                if (virtualCustomerRadio.isSelected()) {
                    int customerId = CustomersQuery.insertCustomers(name, address, zip, phone, divisionID);
                    VirtualCustomerQuery.insertVirtualCustomer(customerId, zoomEmail);
                } else {
                    CustomersQuery.insertCustomers(name, address, zip, phone, divisionID);
                }


                customerTableView.setItems(CustomersQuery.selectAllCustomers());
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/customersScreen.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1231, 681);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * This method clears all text fields and combo box selections by reloading the customers screen.
     *
     * @param actionEvent When the clearBtn is pressed.
     */
    public void clearAllFields(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CustomersScreen.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that will filter the divisions in the divisions combo box to match that countries' countryId once the user selects a country in the country combo box.
     *
     * @param actionEvent When a selection is made in the countryCombo combo box is made.
     */
    public void countrySelected(ActionEvent actionEvent) {

        if (countryCombo.getSelectionModel().getSelectedIndex() == 0) {
            ObservableList<FirstLevelDivisions> divisionList = FirstLevelDivisionQuery.selectDivisionCountryId(1);
            divisionCombo.setItems(divisionList);
        }
        if (countryCombo.getSelectionModel().getSelectedIndex() == 1) {
            ObservableList<FirstLevelDivisions> divisionList = FirstLevelDivisionQuery.selectDivisionCountryId(2);
            divisionCombo.setItems(divisionList);
        }
        if (countryCombo.getSelectionModel().getSelectedIndex() == 2) {
            ObservableList<FirstLevelDivisions> divisionList = FirstLevelDivisionQuery.selectDivisionCountryId(3);
            divisionCombo.setItems(divisionList);
        }
    }


    /**
     * This method checks to make sure a customer is selected, and if one is, it populates the text fields and combo boxes with the
     * data associated with that customer.If a customer is not selected first, than an error message is displayed.
     * This method will also check to see if the selected customer is a virtual customer and if so it will populate the textfield with the
     * zoom email and enable the radio button and text field for virtual customers.
     *
     * @param actionEvent When the modifyCustomerBtn is pressed.
     */
    public void modifyCustomer(ActionEvent actionEvent) {
        String countryMatch = "";
        String divisionMatch = "";

        addCustomerBtn.setDisable(true);
        updateCustomerBtn.setDisable(false);


        if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            int selectedId = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();
            Customers c = CustomersQuery.selectCustomerById(selectedId);
            VirtualCustomers v = VirtualCustomerQuery.selectCustomerById(selectedId);

            customerIdTxt.setText(String.valueOf((selectedId)));
            customerNameTxt.setText(c.getCustomerName());
            addressTxt.setText(c.getAddress());
            postalTxt.setText(c.getPostalCode());
            phoneTxt.setText(c.getPhone());

            ObservableList<Countries> countriesList = CountriesQuery.selectAllCountries();
            ObservableList<FirstLevelDivisions> divisionList = FirstLevelDivisionQuery.selectAllDivisionId();

            countryCombo.setItems(countriesList);
            divisionCombo.setItems(divisionList);


            //Sets the country combo box with the correct country value for the selected customer
            for (Countries cCountry : countryCombo.getItems()) {
                try {
                    countryMatch = c.getCountry();
                    if (countryMatch.equals(cCountry.toString())) {
                        countryCombo.setValue(cCountry);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            //Sets the division combo box with the correct division value for the selected customer
            for (FirstLevelDivisions div : divisionCombo.getItems()) {
                try {
                    divisionMatch = c.getDivisionName();
                    if (divisionMatch.equals(div.toString())) {
                        divisionCombo.setValue(div);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            //Checks to see if v object is null, if not, it will set the zoom email into the text field and select the radio button
            //and enable the text field.
            //If v is null it will set the radio button to false, clear and disable the text field.
            if (v != null) {
                zoomEmailTxt.setText(v.getZoomEmail());
                virtualCustomerRadio.setSelected(true);
                zoomEmailTxt.setDisable(false);
            } else {
                virtualCustomerRadio.setSelected(false);
                zoomEmailTxt.setDisable(true);
                zoomEmailTxt.clear();
            }
        } else {
            Alert modifyAlert = new Alert(Alert.AlertType.ERROR, "Please select a customer in the table to be modified.");
            modifyAlert.setHeaderText("No customer selected to modify");
            modifyAlert.showAndWait();
            addCustomerBtn.setDisable(false);
            updateCustomerBtn.setDisable(true);
        }
    }

    /**
     * This method retrieves all the input from the text fields, and combo boxes and updates that customers object in the database with the new data.
     * It also has input validation for blank fields.
     *
     * @param actionEvent When the updateCustomerBtn is pressed.
     */
    public void updateCustomer(ActionEvent actionEvent) {
        int divisionID = 0;
        String zoomEmail = zoomEmailTxt.getText();

        if (checkInput()) {
            Alert blackAlert = new Alert(Alert.AlertType.ERROR, "All fields must be filled out to add a customer. Please check and try again.");
            blackAlert.setHeaderText("Blank input field(s).");
            blackAlert.showAndWait();
        }
        if (virtualCustomerRadio.isSelected()) {
            if (zoomEmail.isBlank()) {
                Alert blackAlert = new Alert(Alert.AlertType.ERROR, "Zoom email field is blank, please enter in a zoom email or deselect virtual customer.");
                blackAlert.setHeaderText("Blank input field(s).");
                blackAlert.showAndWait();
            }
        }
        //        else if (!addressRegex(address, countryCombo.getSelectionModel().getSelectedItem().toString())) {
//            if(countryCombo.getSelectionModel().getSelectedItem().toString().equals("U.S")) {
//                Alert addressFormatAlert = new Alert(Alert.AlertType.ERROR, "Please enter in an United States address in the format of ex:\n 123 ABC Street, White Plains");
//                addressFormatAlert.setHeaderText("Incorrect United States address format.");
//                addressFormatAlert.showAndWait();
//            }
//            else if(countryCombo.getSelectionModel().getSelectedItem().toString().equals("UK")){
//                Alert addressFormatAlert = new Alert(Alert.AlertType.ERROR, "Please enter in an United Kingdom address in the format of ex:\n 123 ABC Street, Greenwich, London");
//                addressFormatAlert.setHeaderText("Incorrect United Kingdom address format.");
//                addressFormatAlert.showAndWait();
//            }
//            else if(countryCombo.getSelectionModel().getSelectedItem().toString().equals("Canada")){
//                Alert addressFormatAlert = new Alert(Alert.AlertType.ERROR, "Please enter in a Canadian address in the format of ex:\n 123 ABC Street, Newmarket");
//                addressFormatAlert.setHeaderText("Incorrect Canadian address format.");
//                addressFormatAlert.showAndWait();
//            }

        try {
            String customerIdString = customerIdTxt.getText();
            int customerID = Integer.parseInt(customerIdString);
            String name = customerNameTxt.getText();
            String address = addressTxt.getText();
            String zip = postalTxt.getText();
            String phone = phoneTxt.getText();
            String division = divisionCombo.getValue().toString();
            String zoom = zoomEmailTxt.getText();

            for (FirstLevelDivisions f : divisionList) {
                if (f.getDivisionName().equals(division)) {
                    divisionID = f.getDivisionId();
                }
            }
            if ((virtualCustomerRadio.isSelected()) && (zoom != null) && (VirtualCustomerQuery.selectCustomerById(customerID) == null)) {
                VirtualCustomerQuery.insertVirtualCustomer(customerID, zoomEmail);
            } else if ((virtualCustomerRadio.isSelected()) && (zoom != null) && (VirtualCustomerQuery.selectCustomerById(customerID) != null)) {
                VirtualCustomerQuery.updateVirtualCustomer(customerID, zoom);
            } else {
                VirtualCustomerQuery.deleteVirtualCustomer(customerID);
            }

            CustomersQuery.updateCustomer(customerID, name, address, zip, phone, divisionID);


            customerList = CustomersQuery.selectAllCustomers();
            customerTableView.setItems(customerList);
            customerTableView.setItems(CustomersQuery.selectAllCustomers());

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/customersScreen.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1231, 681);
            stage.setScene(scene);
            stage.show();

        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method checks to make sure the user has selected a customer and if so, that customer is deleted from the customers table.
     * Checks for associated appointments and deletes those first before deleting the customer.
     *
     * @param actionEvent When the deleteCustomerBtn is pressed.
     */
    public void deleteCustomer(ActionEvent actionEvent) {
        ObservableList<Appointments> associatedAppointments = FXCollections.observableArrayList();

        if (customerTableView.getSelectionModel().getSelectedItem() == null) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select a customer to be deleted");
            noSelection.setHeaderText("No customer selected.");
            noSelection.showAndWait();
        } else {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
            deleteAlert.setHeaderText("Deletion Confirmation");
            Optional<ButtonType> cancelResult = deleteAlert.showAndWait();

            if (cancelResult.isPresent() && cancelResult.get() == ButtonType.OK) {
                int selectedId;
                String customerName = customerTableView.getSelectionModel().getSelectedItem().getCustomerName();
                selectedId = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();
                associatedAppointments = AppointmentsQuery.selectByCustomer(selectedId);
                associatedAppointments.removeAll();
                CustomersQuery.deleteCustomer(selectedId);
                customerList = CustomersQuery.selectAllCustomers();
                customerTableView.setItems(customerList);
                Alert deleteC = new Alert(Alert.AlertType.INFORMATION, "Customer and associated appointments successfully deleted.");
                deleteC.setHeaderText("Customer " + selectedId + ": " + customerName + " Removed");
                deleteC.setTitle("");
                deleteC.showAndWait();
            }
        }
    }

    /**
     * This method validates if the text fields and combo boxes on the customers form are populated and returns a boolean
     *
     * @return Boolean true or false.
     */
    public boolean checkInput() {

        String name = customerNameTxt.getText();
        String address = addressTxt.getText();
        String zip = postalTxt.getText();
        String phone = phoneTxt.getText();

        return (name.isBlank()) || (address.isBlank()) || (zip.isBlank()) || (phone.isBlank()) || (divisionCombo.getSelectionModel().getSelectedItem() == null) || (countryCombo.getSelectionModel().getSelectedItem() == null);
    }

    /**
     * Navigates the user to the appointment screen.
     *
     * @param actionEvent When the manageAptBtn is pressed.
     */
    public void toAppointmentScreen(ActionEvent actionEvent) {
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
     * Navigates the user to the ReportsScreen.
     *
     * @param actionEvent When the reportsBtn is pressed.
     */
    public void toReportsScreen(ActionEvent actionEvent) {
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

    /**
     * This method retrieves the input from the customerSeach textfield and searches the customers table for both an customerId integer and a customerName string value.
     * After searching, either any matches will be displayed or an error messaging letting the user know there were no mathces found.
     *
     * @param actionEvent - when the search button is pressed.
     */
    public void onSearch(ActionEvent actionEvent) {
        String searchInput = customerSearchTxt.getText();

        if (customerSearchTxt.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter in a search criteria to search the customer table.");
            alert.setTitle("Customer Search");
            alert.setHeaderText("Blank search field");
            alert.showAndWait();
        }
        ObservableList<Customers> customerList = customerSearch(searchInput);

        try {
            int customerId = Integer.parseInt(searchInput);
            Customers searchId = customerSearch(customerId);
            if (searchId != null) {
                customerList.add(searchId);
            } else {
                customerList = CustomersQuery.selectAllCustomers();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer Id not found.");
                alert.setTitle("Customer Search");
                alert.setHeaderText("Search Error");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            customerList = customerSearch(searchInput);
            if (customerList.size() == 0) {
                customerList = CustomersQuery.selectAllCustomers();
                Alert alert = new Alert(Alert.AlertType.ERROR, "No customers found from this search criteria.");
                alert.setTitle("Customer Search");
                alert.setHeaderText("Search Error");
                alert.showAndWait();
            }
        }
        customerTableView.setItems(customerList);

    }

    /**
     * This method has a string variable for customerName passed into it and runs a search on the customer table and returns any matches.
     *
     * @param name - the inputted paramater from the search textfield
     * @return customerMatch - an observablelist that contains any matching customer entities.
     */
    public static ObservableList<Customers> customerSearch(String name) {
        ObservableList<Customers> customerMatch = FXCollections.observableArrayList();

        ObservableList<Customers> allCustomers = CustomersQuery.selectAllCustomers();

        for (Customers C : allCustomers) {
            if (C.getCustomerName().contains(name.toUpperCase()) || (C.getCustomerName().contains(name.toLowerCase()))) {
                customerMatch.add(C);
            }
        }
        return customerMatch;
    }

    /**
     * This method has an integer variable for customerId passed into it and runs a search on the customer table and returns any mathces.
     *
     * @param customerId - the inputted paramater from the search textfield.
     * @return Id - an observablelist that contains any matching customer entities.
     */
    public static Customers customerSearch(int customerId) {
        ObservableList<Customers> allCustomers = CustomersQuery.selectAllCustomers();

        for (Customers Id : allCustomers) {
            if (Id.getCustomerId() == customerId) {
                return Id;
            }
        }
        return null;
    }

    /**
     * This method will reset the customer tableview and display all customers and then clear the customerSearch text field. It also hides the zoom_email table column.
     *
     * @param actionEvent - when the customerReset button is pressed.
     */
    public void onResetCustomerTable(ActionEvent actionEvent) {
        zoomEmailCol.setVisible(false);
        ObservableList<Customers> allCustomers = CustomersQuery.selectAllCustomers();
        customerTableView.setItems(allCustomers);
        customerTableView.scrollToColumnIndex(0);
        customerSearchTxt.clear();
    }

    /**
     * This method controls the virtual appointment radio button and will either allow the zoom email textfield to be editable or disable and clear that textfield.
     *
     * @param actionEvent - when the virtualAppointment radio button is selected or unselected.
     */
    public void onVirtualSelected(ActionEvent actionEvent) {
        if (virtualCustomerRadio.isSelected()) {
            zoomEmailTxt.setDisable(false);
        }
        if (!virtualCustomerRadio.isSelected()) {
            zoomEmailTxt.clear();
            zoomEmailTxt.setDisable(true);
        }
    }

    /**
     * This method will display all virtual customers in the customers tableview as well as make the zoom_email table column visible.
     *
     * @param actionEvent When the display virtual customer button is clicked.
     */
    public void onDisplayVirtualCustomers(ActionEvent actionEvent) {
        customerList = CustomersQuery.selectAllVirtualCustomers();
        zoomEmailCol.setVisible(true);
        customerTableView.setItems(customerList);
        customerTableView.scrollToColumnIndex(0);
    }
}

// This method uses a regex to check if the address user input is in the correct format for the country they selected
//        public static boolean addressRegex ( final String address, String country){
//
//            final Pattern usaPattern = Pattern.compile("^[0-9]+\\s+[A-Za-z]+\\s[A-Za-z]+,\\s[A-Za-z]+\\s[A-Za-z]+$");
//            final Pattern ukPattern = Pattern.compile("^[0-9]+\\s+[A-Za-z]+\\s[A-Za-z]+,\\s[A-Za-z]+,\\s[A-Za-z]+$");
//            final Pattern caPattern = Pattern.compile("^[0-9]+\\s+[A-Za-z]+\\s[A-Za-z]+,\\s[A-Za-z]+$");
//            final Matcher usaMatcher = usaPattern.matcher(address);
//            final Matcher ukMatcher = ukPattern.matcher(address);
//            final Matcher caMatcher = caPattern.matcher(address);
//
//            if (country.equals("U.S")) {
//                return usaMatcher.matches();
//            } else if (country.equals("UK")) {
//                return ukMatcher.matches();
//            } else if (country.equals("Canada")) {
//                return caMatcher.matches();
//            } else {
//                return false;
//            }
//        }






