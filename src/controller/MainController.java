package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import library.Registers;
import org.omg.Messaging.SyncScopeHelper;
//import org.controlsfx.control.Notifications;

import javax.management.Notification;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.io.Console;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

//import javafx.time.LocalDate;
//import java.awt.event.ActionEvent;
// # onaction ın çözümü bu

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
    private ScrollPane scrollpane;
    @FXML
    private TextArea textarea;
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
    private TableColumn<Registers, Calendar> dateColumn;
    @FXML
    private TableColumn<Registers, Date> dateColumn2;
    private Object TimeSpan;

    private Service<Void> backgroundThread;

    @FXML
    private void showAlert() {

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return null;
            }
        };
        for (int i = 0; i <= 10; i++) {

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {

                    //alertDate();
                    //  textarea.setText("kayıt bulundu");

                    //alert2();
                }

            }, 5000 * i); // 5 second intervals


            //alertDate();
            // alert2();

        }

    }


    @FXML
    private void insertButton() {
        String query = "insert into registers values('" + idField.getText() + "','" + nameField.getText() + "','" + surnameField.getText() + "','" + departmentField.getText() + "','" + mailField.getText() + "','" + datePicker.getValue() + "','" + datePicker2.getValue() + "')";
        executeQuery(query);
        showRegisters();
        // alert2();
        //  isInputValid();

    }

    @FXML
    private void updateButton() {
        String query = "UPDATE registers SET Name='" + nameField.getText() + "',Surname='" + surnameField.getText() + "',Department='" + departmentField.getText() + "',Mail='" + mailField.getText() + "',Date='" + datePicker.getValue() + "',Date2='" + datePicker2.getValue() + "' WHERE Number='" + idField.getText() + "'";
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

        idField.clear();
        nameField.clear();
        surnameField.clear();
        departmentField.clear();
        mailField.clear();
        datePicker.getEditor().clear();
        datePicker2.getEditor().clear();

    }

    @FXML
    private void alert1() {
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Bilgi Mesajı");
        a1.setContentText("Güncellendi");
        a1.setHeaderText(null);
        //a1.hide();


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
    private void alertValidation() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("Bilgi Mesajı");
        a1.setContentText("Yanlış veri tipi");
        a1.setHeaderText(null);
        a1.showAndWait();
    }

 /*   @FXML
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

    }*/


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


        mycircle.setStroke(Color.SEAGREEN);
        Image im = new Image("src/indir (1).png", false);
        mycircle.setFill(new ImagePattern(im));
        mycircle.setEffect((new DropShadow(+25d, +0d, +2d, Color.DARKCYAN)));
        alertDate();
        //dongu();
        //deneme();
        // deneme2();
        showRegisters();
        showAlert();

        idField.addEventFilter(KeyEvent.KEY_TYPED, numFilter());
        nameField.addEventFilter(KeyEvent.KEY_TYPED, stringFilter());
        surnameField.addEventFilter(KeyEvent.KEY_TYPED, stringFilter());
        departmentField.addEventFilter(KeyEvent.KEY_TYPED, stringFilter());
     //  mailField.addEventFilter(KeyEvent.KEY_TYPED, handle(e));


        // idField.addEventFilter(KeyEvent.KEY_TYPED, new FiltroValorMaximo(
        //  99));

    }



   /* public  void comboBox(){

        combobox1.getItems().removeAll(combobox1.getItems());
        combobox1.getItems().addAll("Bilgisayar Mühendisliği", "Elektirik-Elektronik Mühendisliği", "Gıda Mühendisliği","Makine Mühendisliği");
        combobox1.getSelectionModel().select("Bilgisayar Mühendisliği");
    }
*/

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
        dateColumn.setCellValueFactory(new PropertyValueFactory<Registers, Calendar>("date"));
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

    public void Filtering() {
        ObservableList<Registers> list = getRegistersList();
        FilteredList<Registers> filteredList = new FilteredList<>(list, p -> true);
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

    public void alertDate() {
        ObservableList<Registers> list = getRegistersList();
        LocalDate currentDate = LocalDate.now();

        SortedList<Registers> alertList = new SortedList<Registers>(list,
                (Registers date, Registers date2) -> {

                    // textarea.setText(currentDate.plusDays(1).toString());
                    if (date.getDate().toLocalDate().isEqual(currentDate.now().plusDays(1))) {
                        // textarea.appendText(date.getDate().toString());
                        textarea.setText("Yarın başlayacak olan staj var");
                        textarea.setStyle("-fx-textcolor :yellow;");
                        TableView.setStyle("-fx-background-color: #38ee00; -fx-text-fill: green;");

                        //alert1();

                    } else {
                        return 0;
                    }
                    return 0;
                });

             /*  for (int i = 1; i <= 10; i++) // start i at 1 for initial delay
               {
                   Timer timer = new Timer();
                   timer.schedule(new TimerTask() {
                       public void run()
                       {
                           //System.out.println("30 Seconds Later");

                           textarea.setText("kayıt bulundu");


                       }

                   }, 10000 * i); // 5 second intervals
               }*/
        // alert1();
    }

    public void deneme() {
        for (int i = 0; i <= 10; i++) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                               public void run() {
                                   alert1();
                                   //System.out.println("30 Seconds Later");
                               }
                           }, 30000
            );
        }

        //*********************
           /*    final Timer timer = new Timer();
// Note that timer has been declared final, to allow use in anon. class below
               timer.schedule( new TimerTask()
                               {
                                   private int i = 10;
                                   public void run()
                                   {
                                       //textarea.setText("kayıt bulundu");
                                       alert1();

                                       //System.out.println("30 Seconds Later");
                                       if (--i < 1) timer.cancel(); // Count down ten times, then cancel
                                   }
                               }, 10000, 10000 //Note the second argument for repetition
               );*/

    }

    public void deneme2() {

        for (int i = 1; i <= 10; i++) // start i at 1 for initial delay
        {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    //System.out.println("30 Seconds Later");

                    textarea.setText("kayıt bulundu");

                    //alert2();
                }

            }, 5000 * i); // 5 second intervals
        }

    }

    public void deneme3() {


    }


    //  @FXML private void


    public void dongu() {

        ObservableList<Registers> list = getRegistersList();
        LocalDate currentDate = LocalDate.now();

        SortedList<Registers> alertList = new SortedList<Registers>(list,
                (Registers date, Registers date2) -> {

                    if (date.getDate().toLocalDate().isEqual(currentDate.now().plusDays(1))) {

                        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
                        a1.setTitle("Bilgi Mesajı");
                        a1.setContentText("Yarın stajı başlayacak kayıtlar var.");
                        a1.setHeaderText(null);
                        //a1.showAndWait();

                        Thread thread = new Thread(() -> {
                            try {
                                // Wait for 3 secs
                                Thread.sleep(3000);
                                if (a1.isShowing()) {
                                    Platform.runLater(() -> a1.close());
                                }
                            } catch (Exception exp) {
                                exp.printStackTrace();
                            }
                        });
                        thread.setDaemon(true);
                        thread.start();
                        Optional<ButtonType> result = a1.showAndWait();
                    } else {
                        return 0;
                    }

                    return 0;
                });


    }


    public static EventHandler<KeyEvent> numFilter() {

        EventHandler<KeyEvent> aux = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (!"0123456789".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();

                }
            }

        };
        return aux;
    }

    public static EventHandler<KeyEvent> stringFilter() {

        EventHandler<KeyEvent> aux = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (!"[A-Za-z]".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();

                }
            }

        };
        return aux;
    }

/*
    public void handle(KeyEvent e) {

        String type=e.getEventType().getName();
        KeyCode keyCode=e.getCode();
       // System.out.println(type+ ": Key Code=" + keyCode.getName()+", Text=" +e.getText());
        if(e.getEventType()== KeyEvent.KEY_PRESSED && e.getCode() == KeyCode.Q){
            keyCode.isFunctionKey();

        }

    }
*/

}

















