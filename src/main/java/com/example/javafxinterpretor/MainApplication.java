package com.example.javafxinterpretor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Interpreter Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void startMainWindow(){
        MainWindow newWindow = new MainWindow();
        try {
            newWindow.start(new Stage());
        }
        catch(Exception e){
            displayAlert("Error opening the Main Window!");
        }
    }

    public static void displayAlert(String errorMessage){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}