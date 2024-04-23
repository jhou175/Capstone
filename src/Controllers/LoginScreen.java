package Controllers;

import DBAccess.UsersQuery;
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
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * This is the LoginScreen controller class and it handles the login functionality for the application.
 * It includes the code to change the language of the login
 * form based on the location of the user.
 * If a correct username and password are entered then the user will be logged into the application.
 */
public class LoginScreen implements Initializable {
    @FXML
    public TextField usernameTxt;
    public TextField passwordTxt;
    public Label loginLbl;
    public Label usernameLbl;
    public Label passwordLbl;
    public Button loginButton;
    public Label zoneIdLbl;

    private Alert loginAlert;
    Locale userLocale = Locale.getDefault();

    /**
     * This is the override Initialize method that retrieves the users zoneId and displays the longin screen and alerts in either English or French based on the users location.
     *
     * @param url            The uniform Resource Locator that is a pointer to a location.
     * @param resourceBundle Contains locale specific objects for the class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
        Locale userLocale = Locale.getDefault();
        String zoneId = ZoneId.systemDefault().getId();
        ButtonType ok = new ButtonType(rb.getString("Ok"));


        if (userLocale.getLanguage().equals("fr") || userLocale.getLanguage().equals("en")) {
            usernameLbl.setText(rb.getString("Username"));
            passwordLbl.setText(rb.getString("Password"));
            loginLbl.setText(rb.getString("Login_Form"));
            loginButton.setText(rb.getString("Login"));
            zoneIdLbl.setText(rb.getString("Time_zone") + ": " + zoneId);
            loginAlert = new Alert(Alert.AlertType.ERROR, rb.getString("Alert_message"), ok);
            loginAlert.setHeaderText(rb.getString("Alert_header"));

        }
    }


    /**
     * This method uses the user's input for username and password and checks the database for a match.
     * If a match is found, the user is logged in and if not, an error message is displayed.
     *
     * @param actionEvent When the loginButton is pressed.
     * @throws IOException If there is already a file of that name existing in the project.
     */
    public void login(ActionEvent actionEvent) throws IOException {
        ObservableList<Users> users = FXCollections.observableArrayList();
        String name = usernameTxt.getText();
        String password = passwordTxt.getText();
        boolean result;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


        //File name and location for th file
        String fileName = "src/login_activity.txt";

        //Create file writer object to be able to append the file instead of overwriting
        FileWriter fWriter = new FileWriter(fileName, true);

        //Create and open file
        PrintWriter outputFile = new PrintWriter(fWriter);

        try {
            result = UsersQuery.checkUserPassword(name, password);
            users = UsersQuery.selectAllUsers();

            if (result) {
                //Write to file if login was successful
                outputFile.println("User: " + name + " successfully logged in at " + LocalDate.now() + " " + LocalTime.now().format(formatter));

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/customersScreen.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                AppointmentScreen.checkForUpcomingAppointments();
            } else {
                //Write to file if login was unsuccessful
                outputFile.println("User: " + name + " gave invalid log-in at " + LocalDate.now() + " " + LocalTime.now().format(formatter));
                loginAlert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //CLoses the login results file
        outputFile.close();
    }
}

