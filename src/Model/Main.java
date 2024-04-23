//Author: Justin House
package Model;

import Database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;

/**
 * This is the main class and where the program starts. The login screen fxml is launched from here.
 * The JDBC open and close connection methods are called from the main class.
 */
public class Main extends Application {

    /**
     * This is the start method and it will be called from the main method when launch(args) is called.
     * The first fxml screen is initialized from here.
     * The start method is a method from the base parent Application.
     *
     * @param primaryStage This is the primary stage that the FX passes.
     * @throws Exception This exception is thrown if the fxml file is not found or is null.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/View/loginScreen.fxml"))));
        primaryStage.setTitle("Customer Appointment Management System");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.setMinHeight(500);
        primaryStage.setMaxHeight(700);
        primaryStage.setMinWidth(900);
        primaryStage.setMaxWidth(1240);
        primaryStage.show();
    }

    /**
     * This is the main line of the program. This is the code that is called when the program starts.
     * This method calls the open and close connection methods to connect and disconnect to the SQL database from the JDBC class.
     *
     * @param args Command line argument that are used when launch() is called.
     */
    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }


}