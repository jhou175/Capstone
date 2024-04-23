package Controllers;

import DBAccess.AppointmentsQuery;
import DBAccess.ContactsQuery;
import DBAccess.CustomersQuery;
import DBAccess.UsersQuery;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
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
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the AppointmentScreen class which is a controller and implements an Initializable method.
 * This class handles all functionality for the appointment screen of the application.
 */
public class AppointmentScreen implements Initializable {


    @FXML
    public TableView<Appointments> appointmentsTableView;
    public TableColumn<Object, Object> appointIdCol;
    public TableColumn<Object, Object> appointTitleCol;
    public TableColumn<Object, Object> appointDescriptionCol;
    public TableColumn<Object, Object> appointLocationCol;
    public TableColumn<Object, Object> appointContactCol;
    public TableColumn<Object, Object> appointTypeCol;
    public TableColumn<Object, Object> startDateCol;
    public TableColumn<Object, Object> endDateCol;
    public TableColumn<Object, Object> appointCustomerIdCol;
    public TableColumn<Object, Object> appointUserIdCol;
    public TableColumn<Object, Object> contactNameCol;

    @FXML
    public Button customersNavBtn;
    public Button modifyAptBtn;
    public Button deleteAptBtn;
    public Button addAppointBtn;
    public Button updateAppointBtn;
    public Button clearBtn;
    public Button toReports;


    @FXML
    public RadioButton allAppointRadio;
    public RadioButton monthAppointRadio;
    public RadioButton weekAppointRadio;

    @FXML
    public ComboBox<Contacts> contactCombo;
    public ComboBox<Customers> customerNameCombo;
    public ComboBox<Users> userIdCombo;

    @FXML
    public TextField appointIdTxt;
    public TextField appointTitleTxt;
    public TextField appointDescriptionTxt;
    public TextField appointLocationTxt;
    public TextField appointTypeTxt;

    @FXML
    public DatePicker appointStartDatePicker;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTimeCombo;
    public ToggleGroup appointmentsGroup;


    ObservableList<Appointments> appointmentList;
    ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    ObservableList<Customers> customerList = FXCollections.observableArrayList();
    ObservableList<Users> userList = FXCollections.observableArrayList();


    LocalDate estNow = LocalDate.now();
    LocalTime estLT = LocalTime.of(8, 0);
    LocalDateTime osLDT = LocalDateTime.of(estNow, estLT);
    static LocalDate estNow1 = LocalDate.now();
    static LocalTime estLT1 = LocalTime.of(8, 0);
    static LocalDateTime osLDT1 = LocalDateTime.of(estNow1, estLT1);
    static LocalDateTime appointCheck = osLDT1;

    ZoneId osZID = ZoneId.systemDefault();
    ZoneId estZID = ZoneId.of("America/New_York");

    ZonedDateTime estZDT = ZonedDateTime.of(estNow, estLT, estZID);
    ZonedDateTime localZDT = estZDT.withZoneSameInstant(osZID);

    int estHour = estZDT.getHour();
    int localHour = localZDT.getHour();
    int midnight = 0;

    // ZoneId pstZID = ZoneId.of("America/Los_Angeles");
    // ZoneId utcZID = ZoneId.of("UTC");
    // ZonedDateTime pstZDT = ZonedDateTime.ofInstant(estZDT.toInstant(),pstZID);
    //converts local time to UTC
    // ZonedDateTime utcZDT = ZonedDateTime.ofInstant(localZDT.toInstant(),utcZID);
    //converts utc to local time
    // ZonedDateTime utcToMyZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), osZID);
    //int pstHour = pstZDT.getHour();
    // int utcHour = utcZDT.getHour();
    //private static ZonedDateTime privateLocalZDT = localZDT ;


    /**
     * This is the override initialize method for the AppointmentScreen class and is processed as soon as this screen is opened.
     * This method initializes the appointmentsTableview and populates the combo boxes with data.
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
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        appointmentList = AppointmentsQuery.selectAllAppointments();
        appointmentsTableView.setItems(appointmentList);
        appointStartDatePicker.setValue(LocalDate.now());

        //Queries to select all contacts,customers,and users to be used to populate the combo boxes.
        contactList = ContactsQuery.selectAllContacts();
        customerList = CustomersQuery.selectAllCustomers();
        userList = UsersQuery.selectAllUsers();

        //Populates the contacts,customers,and users combo boxes.
        contactCombo.setItems(contactList);
        customerNameCombo.setItems(customerList);
        userIdCombo.setItems(userList);

        updateAppointBtn.setDisable(true);

        startTimeCombo.setVisibleRowCount(5);
        endTimeCombo.setVisibleRowCount(5);
        endTimeCombo.setDisable(true);

        //Populates the appointment time combo boxes with the correct time based on user's time zone.
        ZonedDateTime localEndZDT = localZDT.plusHours(14);
        while (localZDT.isBefore(localEndZDT)) {
            startTimeCombo.getItems().add(localZDT.toLocalTime());
            localZDT = localZDT.plusMinutes(30);
            endTimeCombo.getItems().add(localZDT.toLocalTime());
        }
    }

    //Old code for populating the appointment time combo boxes.
//        if (osZID == estZID) {
//            for (int i = estHour; i <= estHour + 14; i++) {
//                startTimeCombo.getItems().add(LocalTime.of(i, 0));
//            }
//        }
//        if (osZID == pstZID) {
//            for (int i = pstHour; i <= pstHour + 14; i++) {
//                startTimeCombo.getItems().add(LocalTime.of(i, 0));
//            }
//        }
//        if (osZID == utcZID) {
//            for (int i = utcHour; i <= utcHour + 14; i++) {
//                if (i <= 23) {
//                    startTimeCombo.getItems().add(LocalTime.of(i, 0));
//                } else {
//                    startTimeCombo.getItems().add(LocalTime.of(midnight, 0));
//                    midnight += 1;
//                }}}


    /**
     * This method handles the action event to filter all appointments by all,month, and current week.
     *
     * @param actionEvent When the radio buttons are selected.
     */
    public void filterAppointments(ActionEvent actionEvent) {
        try {
            appointmentList = AppointmentsQuery.selectAllAppointments();
            LocalDateTime currentDate = LocalDateTime.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());

            ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();

            if (allAppointRadio.isSelected()) {
                appointmentList = AppointmentsQuery.selectAllAppointments();
                appointmentsTableView.setItems(appointmentList);
            } else if (monthAppointRadio.isSelected()) {
                for (Appointments appointments : appointmentList) {
                    if (appointments.getStartTime().getMonth() == currentDate.getMonth() && (currentDate.getYear() == appointments.getStartTime().getYear())) {
                        filteredAppointments.add(appointments);
                    }
                }
                appointmentsTableView.setItems(filteredAppointments);
            } else if (weekAppointRadio.isSelected()) {
                int weekNumber = currentDate.get(weekFields.weekOfWeekBasedYear());
                for (Appointments appointmentCW : appointmentList) {
                    if (currentDate.getYear() == appointmentCW.getStartTime().getYear() && (appointmentCW.getStartTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == weekNumber)) {
                        filteredAppointments.add(appointmentCW);
                    }
                }
                appointmentsTableView.setItems(filteredAppointments);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method gets the selected appointments data to be modified/updated and populates the text field,combo boxes, and date picker with the correct saved data.
     *
     * @param actionEvent When the modifyAptBtn is selected.
     */
    public void modifyAppointment(ActionEvent actionEvent) {

        addAppointBtn.setDisable(true);
        updateAppointBtn.setDisable(false);


        if (appointmentsTableView.getSelectionModel().getSelectedItem() != null) {
            int selectedId = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentId();
            Appointments a = AppointmentsQuery.selectAllAppointmentsByID(selectedId);
            String contactMatch = "";
            String userMatch = "";
            String customerMatch = "";
            LocalDateTime start = null;
            LocalDateTime end = null;

            //Gets the values for the selected appointment and sets the text fields and date picker with them.
            appointIdTxt.setText(String.valueOf(selectedId));
            appointTitleTxt.setText(a.getTitle());
            appointDescriptionTxt.setText(a.getDescription());
            appointLocationTxt.setText(a.getLocation());
            appointTypeTxt.setText(a.getType());
            appointStartDatePicker.setValue(a.getStartTime().toLocalDate());

            //Retrieves the contacts,customers, and users and sets the values to the combo boxes.
            contactList = ContactsQuery.selectAllContacts();
            customerList = CustomersQuery.selectAllCustomers();
            userList = UsersQuery.selectAllUsers();
            contactCombo.setItems(contactList);
            customerNameCombo.setItems(customerList);
            userIdCombo.setItems(userList);

            //Sets the contactCombo combo box with the correct contact name/id for the selected appointment.
            for (Contacts contacts : contactCombo.getItems()) {
                try {
                    contactMatch = a.getContactId() + " " + a.getContactName();
                    if (contactMatch.equals(contacts.toString())) {
                        contactCombo.setValue(contacts);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            //Sets the userIdCombo combo box with the correct user name/id for the selected appointment.
            for (Users user : userIdCombo.getItems()) {
                try {
                    userMatch = a.getUserId() + " " + user.getUserName();
                    if (userMatch.equals(user.toString())) {
                        userIdCombo.setValue(user);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            //Sets the customerNameCombo combo box with the correct customer name/id for the selected appointment.
            for (Customers customer : customerNameCombo.getItems()) {
                try {
                    customerMatch = a.getCustomerId() + " " + customer.getCustomerName();
                    if (customerMatch.equals(customer.toString())) {
                        customerNameCombo.setValue(customer);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

            //Sets the start time combo box with the correct starting time for the selected appointment object.
            for (LocalTime startTime : startTimeCombo.getItems()) {
                try {
                    start = a.getStartTime();
                    if (start.toLocalTime().equals(startTime)) {
                        startTimeCombo.setValue(startTime);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Sets the end time combo box with the correct ending time from the selected appointment object.
            for (LocalTime endTime : endTimeCombo.getItems()) {
                try {
                    end = a.getEndTime();
                    if (end.toLocalTime().equals(endTime)) {
                        endTimeCombo.setValue(endTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } else {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select an appointment to be modified.");
            noSelection.setHeaderText("No Appointment Selected.");
            noSelection.showAndWait();
            addAppointBtn.setDisable(false);
            updateAppointBtn.setDisable(true);
        }
    }


    /**
     * This method will check for a selected appointment and if one is selected it will delete the appointment from the appointments table.
     *
     * @param actionEvent When the deleteAptBtn is clicked.
     */
    public void deleteAppointment(ActionEvent actionEvent) {
        if (appointmentsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select an appointment to be canceled.");
            noSelection.setHeaderText("No Appointment Selected.");
            noSelection.showAndWait();
        } else {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this appointment?");
            deleteAlert.setHeaderText("Cancellation Confirmation");
            Optional<ButtonType> cancelResult = deleteAlert.showAndWait();

            try {
                if (cancelResult.isPresent() && cancelResult.get() == ButtonType.OK) {
                    int selectedId;
                    String type;
                    selectedId = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentId();
                    type = appointmentsTableView.getSelectionModel().getSelectedItem().getType();
                    System.out.println("Appointment : " + selectedId + " (" + type + ")" + "successfully canceled.");
                    AppointmentsQuery.deleteAppointment(selectedId);
                    appointmentList = AppointmentsQuery.selectAllAppointments();
                    appointmentsTableView.setItems(appointmentList);
                    Alert deleteA = new Alert(Alert.AlertType.INFORMATION, "Appointment Id: " + selectedId + " (" + type + ") " + "successfully canceled.");
                    deleteA.setHeaderText("Appointment Canceled");
                    deleteA.setTitle("");
                    deleteA.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method handles adding a new appointment to the appointments table in the data base. It also handles input validation
     * for blank fields, appointment time overlaps, and wrong time slots.
     *
     * @param actionEvent When the addAppointBtn is pressed.
     */
    public void addAppointment(ActionEvent actionEvent) {
        if (checkInput()) {
            Alert blackAlert = new Alert(Alert.AlertType.ERROR, "All fields must be filled out to add an appointment. Please check and try again.");
            blackAlert.setHeaderText("Blank input field(s).");
            blackAlert.showAndWait();
        } else {
            String title = appointTitleTxt.getText();
            String description = appointDescriptionTxt.getText();
            String location = appointLocationTxt.getText();
            String type = appointTypeTxt.getText();
            LocalDate startDay = appointStartDatePicker.getValue();
            LocalTime startTime = startTimeCombo.getValue();
            LocalTime endTime = endTimeCombo.getValue();
            LocalDateTime start = LocalDateTime.of(startDay, startTime);
            LocalDateTime end = LocalDateTime.of(startDay, endTime);

            int customer = customerNameCombo.getValue().getCustomerId();
            int contact = contactCombo.getValue().getContactId();
            int user = userIdCombo.getValue().getUserId();


            if (start.isAfter(end) || (end.equals(start))) {
                Alert times = new Alert(Alert.AlertType.ERROR, "Appointment start time must be before appointment end time");
                times.setHeaderText("Time Selection Error");
                times.showAndWait();
            } else if (end.isBefore(start) || (start.equals(end))) {
                Alert time = new Alert(Alert.AlertType.ERROR, "Appointment end time must be after appointment start time");
                time.setHeaderText("Time Selection Error");
                time.showAndWait();
            } else if (timeOverlapCheck(start, end, customer, -1)) {
                Alert overlap = new Alert(Alert.AlertType.ERROR, " Selected appointment times overlap with an existing appointment.");
                overlap.setHeaderText("Appointment Time Conflict");
                overlap.showAndWait();
            } else {
                try {
                    AppointmentsQuery.insertAppointment(title, description, location, type, start, end, customer, user, contact);
                    appointmentsTableView.setItems(AppointmentsQuery.selectAllAppointments());

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentScreen.fxml")));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1249, 746);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //Code that checks to make sure the user is not selecting a weekend appointment.
//        else if(checkWeekendAppointment(startDay)){
//            Alert weekendAlert = new Alert(Alert.AlertType.ERROR,"Appointments must be during business hours: M-F (8:00 am - 10 pm EST)");
//            weekendAlert.setHeaderText("Weekend date selected.");
//            weekendAlert.showAndWait();
//        }


    }

    //

    /**
     * This method retrieves all the input from the text fields, date picker, and combo boxes and updates that appointment object in the database with the new data.
     * It also has input validation for blank fields, time overlaps, time errors, and no change warnings.
     *
     * @param actionEvent When the updateAppointBtn is pressed.
     */
    public void updateAppointment(ActionEvent actionEvent) {
        int selectedId = Integer.parseInt(appointIdTxt.getText());
        Appointments a = AppointmentsQuery.selectAllAppointmentsByID(selectedId);

        String idString = appointIdTxt.getText();
        int id = Integer.parseInt(idString);
        String title = appointTitleTxt.getText();
        String description = appointDescriptionTxt.getText();
        String location = appointLocationTxt.getText();
        String type = appointTypeTxt.getText();
        LocalDate startDay = appointStartDatePicker.getValue();
        LocalTime startTime = startTimeCombo.getValue();
        LocalDateTime start = LocalDateTime.of(startDay, startTime);
        LocalTime endTime = endTimeCombo.getValue();
        LocalDateTime end = LocalDateTime.of(startDay, endTime);
        int customer = customerNameCombo.getValue().getCustomerId();
        int contact = contactCombo.getValue().getContactId();
        int user = userIdCombo.getValue().getUserId();

        //Method checks to make sure there aren't any blank text fields or null combo boxes
        if (checkInput()) {
            Alert blackAlert = new Alert(Alert.AlertType.ERROR, "All fields must be filled out to add an appointment. Please check and try again.");
            blackAlert.setHeaderText("Blank input field(s).");
            blackAlert.showAndWait();

            //Checks if there are any changes to be updated and displays that the changes were updated.
        } else if (start.isAfter(end) || (end.equals(start))) {
            Alert times = new Alert(Alert.AlertType.ERROR, "Appointment start time must be before appointment end time");
            times.setHeaderText("Time Selection Error");
            times.showAndWait();
        } else if (end.isBefore(start) || (start.equals(end))) {
            Alert time = new Alert(Alert.AlertType.ERROR, "Appointment end time must be after appointment start time");
            time.setHeaderText("Time Selection Error");
            time.showAndWait();
        } else if (!title.equals(a.getTitle()) || (!description.equals(a.getDescription()) || (!location.equals(a.getLocation()) || (!type.equals(a.getType())
                || (customerNameCombo.getSelectionModel().getSelectedItem().getCustomerId() != a.getCustomerId()) || (contactCombo.getSelectionModel().getSelectedItem().getContactId() != a.getContactId()) ||
                (userIdCombo.getSelectionModel().getSelectedItem().getUserId() != a.getUserId()))))) {


            AppointmentsQuery.updateAppointment(id, title, description, location, type, start, end, customer, user, contact);
            appointmentsTableView.setItems(AppointmentsQuery.selectAllAppointments());
            Alert updateAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + selectedId + " was successfully updated.");
            updateAlert.setHeaderText("Appointment Updated");
            updateAlert.showAndWait();
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentScreen.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1249, 746);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Displays an alert notifying the user that there were not any changes to the appointment to be updated.
        } else if ((!start.equals(a.getStartTime()) || (!end.equals(a.getEndTime())))) {


            if (timeOverlapCheck(start, end, customer, selectedId)) {
                Alert overlap = new Alert(Alert.AlertType.ERROR, " Selected appointment times overlap with an existing appointment");
                overlap.setHeaderText("Appointment Time Conflict");
                overlap.showAndWait();
            } else {
                AppointmentsQuery.updateAppointment(id, title, description, location, type, start, end, customer, user, contact);
                appointmentsTableView.setItems(AppointmentsQuery.selectAllAppointments());
                Alert updateAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + selectedId + " was successfully updated.");
                updateAlert.setHeaderText("Appointment Updated");
                updateAlert.showAndWait();
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentScreen.fxml")));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1249, 746);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert noChange = new Alert(Alert.AlertType.INFORMATION, "No changes were made to Appointment ID: " + selectedId);
            noChange.setHeaderText("No Update Necessary");
            noChange.showAndWait();
        }
    }


    //This method retrieves all appointments under a selected customer id and checks to make sure there are no appointment time overlaps and excludes the appointment being updated.
    //This method now uses allAppts obsv. list to check for any appointment overlaps for all customers.

    /**
     * This method uses allAppts observable list to check for any appointment time overlaps for all customers and returns the boolean result.
     *
     * @param start         LocalDateTime value for appointment start time.
     * @param end           LocalDateTime value for appointment end time.
     * @param customerId    integer value for customerId.
     * @param appointmentId integer value for appointmentId.
     * @return boolean value for result
     */
    public boolean timeOverlapCheck(LocalDateTime start, LocalDateTime end, int customerId, int appointmentId) {
        boolean result = false;
        //Use Ap list if you want the method to check for overlaps only for the selected customer
        //ObservableList<Appointments> ap = AppointmentsQuery.selectByCustomer(customerId);
        ObservableList<Appointments> allAppts = AppointmentsQuery.selectAllAppointments();

        for (Appointments appointment : allAppts) {
            LocalDateTime s = appointment.getStartTime();
            LocalDateTime e = appointment.getEndTime();
            //if statement is for updating appointments to exclude the appointment being updated.
            if (appointment.getAppointmentId() != appointmentId) {
                //When start time is in the window of another appointment
                if (((s.isAfter(start) || (s.isEqual(start)))) && ((s.isBefore(end)))) {
                    result = true;
                }
                //When end time is in the window of another appointment
                else if (((e.isBefore(end) || (e.isEqual(end)))) && ((e.isAfter(start)))) {
                    result = true;
                    //When start and end times are outside the window
                } else if (((s.isBefore(start) || (s.isEqual(start)))) && ((e.isAfter(end) || (e.isEqual(end))))) {
                    result = true;
                }
            }
        }
        return result;
    }

    //Checks and makes sure user doesn't try to schedule an appointment during the weekend.
//    public boolean checkWeekendAppointment(LocalDate day){
//      DayOfWeek d = DayOfWeek.of(day.get(ChronoField.DAY_OF_WEEK));
//
//      if((d == DayOfWeek.SATURDAY) || (d ==DayOfWeek.SUNDAY)){
//          return true;
//      }else{
//          return false;
//      }
//    }

    /**
     * This method checks the database for any appointments starting in the next 15 minutes for the current day and alerts the user if there is one or not.
     */
    public static void checkForUpcomingAppointments() {
        //Appointments a = AppointmentsQuery.selectNextAppointment();
        ObservableList<Appointments> b = AppointmentsQuery.selectAllAppointments();
        boolean appointment = false;

        if (b != null) {
            for (Appointments c : b) {
                LocalTime start = c.getStartTime().toLocalTime();
                boolean today = c.getStartTime().toLocalDate().isEqual(LocalDate.now());
                LocalDateTime currentTime = LocalDateTime.now();
                long timeDifference = ChronoUnit.MINUTES.between(start, currentTime);
                long interval = (timeDifference * -1) + 1;

                if ((today) && (interval > 0) && (interval <= 15)) {
                    appointment = true;
                    Alert apptAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + c.getAppointmentId() +
                            " " + c.getStartTime() + " is starting in " + interval + " minute(s).");
                    apptAlert.setHeaderText("Appointment Beginning Soon.");
                    apptAlert.showAndWait();
                }
            }
        }
        if (!appointment) {
            Alert noApptAlert = new Alert(Alert.AlertType.INFORMATION, "There are no appointments starting within the next 15 minutes.");
            noApptAlert.setHeaderText("No Upcoming Appointments.");
            noApptAlert.showAndWait();
        }
    }


    //This is old code that used an sql query to get the time of the very next appointment. Changed to use local code instead of databsae code.
//         for(Appointments c : b) {
//        if (c.getStartTime().toLocalTime().isBefore(LocalTime.now().plusMinutes(15))) {
//            Alert apptAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + a.getAppointmentId() +
//                    " " + a.getStartTime() + " is starting in " + interval + " minute(s).");
//            apptAlert.setHeaderText("Appointment Beginning Soon.");
//            apptAlert.showAndWait();
//        }
//    }

//        if (a != null) {
//            LocalDateTime start = a.getStartTime();
//            LocalDateTime currentTime = LocalDateTime.now();
//            long timeDifference = ChronoUnit.MINUTES.between(start, currentTime);
//            long interval = timeDifference * -1;
//            Alert apptAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + a.getAppointmentId() +
//                    " " + a.getStartTime() + " is starting in " + interval + " minute(s).");
//            apptAlert.setHeaderText("Appointment Beginning Soon.");
//            apptAlert.showAndWait();
//
//        } else {
//            Alert noApptAlert = new Alert(Alert.AlertType.INFORMATION, "There are no appointments starting within the next 15 minutes.");
//            noApptAlert.setHeaderText("No Upcoming Appointments.");
//            noApptAlert.showAndWait();
//        }


    /**
     * Enables the appointment end time combo box once a starting time is selected in the startTimeCombo combo box.
     *
     * @param actionEvent When the user selects an item in the startTimeCombo combo box
     */
    public void startTimeSelectionMade(ActionEvent actionEvent) {
        if (startTimeCombo.getSelectionModel().getSelectedItem() != null) {
            endTimeCombo.setDisable(false);
        }
    }


    /**
     * Clears all text fields and combo boxes by reloading the page.
     *
     * @param actionEvent When the clearBtn is pressed.
     */
    public void clearAllFields(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentScreen.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Checks that there are no empty text fields or null combo boxes or date picker.
     *
     * @return Boolean result-true or false
     */
    public boolean checkInput() {
        String title = appointTitleTxt.getText();
        String description = appointDescriptionTxt.getText();
        String location = appointLocationTxt.getText();
        String type = appointTypeTxt.getText();

        return (title.isBlank()) || (description.isBlank()) || (location.isBlank()) || (type.isBlank()) ||
                (startTimeCombo.getSelectionModel().getSelectedItem() == null) || (endTimeCombo.getSelectionModel().getSelectedItem() == null)
                || (customerNameCombo.getSelectionModel().getSelectedItem() == null) || (userIdCombo.getSelectionModel().getSelectedItem() == null)
                || (contactCombo.getSelectionModel().getSelectedItem() == null) || (appointStartDatePicker == null) || customerNameCombo.getValue() == null;
    }

    //This method will return the user back to the customers add update screen.

    /**
     * This method will navigate the user to the customers screen.
     *
     * @param actionEvent When the customersNavBtn is pressed.
     */
    public void backToCustomers(ActionEvent actionEvent) {
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
     * This method will navigate the user to the ReportScreen.
     *
     * @param actionEvent When the toReports button is pressed.
     */
    public void toReports(ActionEvent actionEvent) {
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
}


