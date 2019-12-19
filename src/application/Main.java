package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.AlertBox;
import library.Registers;

import java.io.FileInputStream;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = (Parent) FXMLLoader.load(getClass().getResource(
                "MainPane.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Register Window");
        stage.show();




















    }

    public static void main(String[] args) {
        launch(args);
    }
}
