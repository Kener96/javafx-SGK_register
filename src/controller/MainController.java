package controller;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import library.AlertBox;
import library.Registers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.time.LocalDate;
//import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;
import org.controlsfx.control.textfield.TextFields;
// # onaction ın çözümü bu
import javax.management.Notification;
import java.sql.*;

import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {

    @FXML
    public Button buttonnotification;
    @FXML
    public Circle mycircle;
    @FXML
    public ComboBox combobox1;
   @FXML
   public ImageView image1;
    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField filteredField;

    @FXML
    private DatePicker datePicker;
    @FXML
    private DatePicker datePicker2;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button button;
    @FXML
    private TableView<Registers> TableView;
    @FXML
    private TableColumn<Registers, Integer> idColumn;
    @FXML
    private TableColumn<Registers, String> nameColumn;
    @FXML
    private TableColumn<Registers, String> surnameColumn;
    @FXML
    private TableColumn<Registers, String> departmentColumn;
    @FXML
    private TableColumn<Registers, String> mailColumn;
    @FXML
    private TableColumn<Registers, Date> dateColumn;
    @FXML
    private TableColumn<Registers, Date> dateColumn2;


    @FXML
    private void insertButton() {
        String query = "insert into registers values('" + idField.getText() + "','" + nameField.getText() + "','" + surnameField.getText() + "','" + departmentField.getText() + "','" + mailField.getText() + "','" + datePicker.getValue() + "','" + datePicker2.getValue() + "')";
        executeQuery(query);
        showRegisters();
        alert2();

    }

    @FXML
    private void updateButton() {
        String query = "UPDATE registers SET Name='" + nameField.getText() + "',Surname='" + surnameField.getText() + "',Department='" + departmentField.getText() + "',Mail='" + mailField.getText() + "' WHERE Number='" + idField.getText() + "'";
        executeQuery(query);
        showRegisters();
        alert1();
    }

    @FXML
    private void deleteButton() {
        String query = "DELETE FROM registers WHERE Number='" + idField.getText() + "'";
        executeQuery(query);
        showRegisters();
        alert3();
    }

    @FXML
    private void alert1() {
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Bilgi Mesajı");
        a1.setContentText("Güncellendi");
        a1.setHeaderText(null);
        a1.showAndWait();
        //WARNING yerine INFORMATION da yazabilirsin
    }

    @FXML
    private void alert2() {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setTitle("Bilgi Mesajı");
        a1.setContentText("Kaydedildi");
        a1.setHeaderText(null);
        a1.showAndWait();
    }

    @FXML
    private void alert3() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("Bilgi Mesajı");
        a1.setContentText("Silindi");
        a1.setHeaderText(null);
        a1.showAndWait();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Image img = new Image("/tik.jpg");
        Notifications notificationBuilder = Notifications.create()
                .text("Saved").hideAfter(Duration.seconds(5)).position(Pos.TOP_RIGHT)
                .graphic(new ImageView(img))
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Cliked on Notification");

                    }
                });
        notificationBuilder.showInformation();

    }



    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showRegisters();

        mycircle.setStroke(Color.SEAGREEN);
        Image im=new Image("src/indir (1).png",false);
        mycircle.setFill(new ImagePattern(im));
        mycircle.setEffect((new DropShadow(+25d, +0d ,+2d,Color.DARKCYAN)));
        
    }

    public  void comboBox(){

        combobox1.getItems().removeAll(combobox1.getItems());
        combobox1.getItems().addAll("Bilgisayar Mühendisliği", "Elektirik-Elektronik Mühendisliği", "Gıda Mühendisliği","Makine Mühendisliği");
        combobox1.getSelectionModel().select("Bilgisayar Mühendisliği");
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "864297531");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Registers> getRegistersList() {
        ObservableList<Registers> registerList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM registers ";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Registers registers;
            while (rs.next()) {
                registers = new Registers(rs.getInt("Number"), rs.getString("Name"), rs.getString("Surname"), rs.getString("Department"), rs.getString("Mail"), rs.getDate("Date"), rs.getDate("Date2"));
                registerList.add(registers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registerList;
    }

    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showRegisters() {

        ObservableList<Registers> list = getRegistersList();

        idColumn.setCellValueFactory(new PropertyValueFactory<Registers, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Registers, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Registers, String>("surname"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Registers, String>("department"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<Registers, String>("mail"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Registers, Date>("date"));
        dateColumn2.setCellValueFactory(new PropertyValueFactory<Registers, Date>("date2"));
        TableView.setItems(list);

        TableView.setEditable(true);
        TableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    @FXML
    public void clickItem(MouseEvent event) {
        if (event.getClickCount() == 2) //Checking double click
        {
            idField.setText(String.valueOf(TableView.getSelectionModel().getSelectedItem().getId()));
            nameField.setText(TableView.getSelectionModel().getSelectedItem().getName());
            surnameField.setText(TableView.getSelectionModel().getSelectedItem().getSurname());
           departmentField.setText(TableView.getSelectionModel().getSelectedItem().getDepartment());
            mailField.setText(TableView.getSelectionModel().getSelectedItem().getMail());
            datePicker.setValue(TableView.getSelectionModel().getSelectedItem().getDate().toLocalDate());
            datePicker2.setValue(TableView.getSelectionModel().getSelectedItem().getDate2().toLocalDate());


        }
    }

    public void ClearButton() {
        idField.clear();
        nameField.clear();
        surnameField.clear();
        departmentField.clear();
        mailField.clear();
        datePicker.getEditor().clear();
        datePicker2.getEditor().clear();
    }

    public void Filtering(){
        ObservableList<Registers> list = getRegistersList();
        FilteredList<Registers> filteredList=new FilteredList<>(list,p->true);
        filteredField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(p -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (p.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (p.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Registers> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(TableView.comparatorProperty());
        TableView.setItems(sortedData);

    }












    }













