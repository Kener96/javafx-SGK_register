package controller;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
// # onaction ın çözümü bu
import javax.management.Notification;
import java.sql.*;

import java.net.URL;

import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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
    private DatePicker datePicker;


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
    private void insertButton() {
    	String query = "insert into registers values('"+idField.getText()+"','"+nameField.getText()+"','"+surnameField.getText()+"','"+departmentField.getText()+"','"+mailField.getText()+"','"+datePicker.getValue()+"')";
    	executeQuery(query);
    	showRegisters();
    }


    @FXML 
    private void updateButton() {
    String query = "UPDATE registers SET Name='"+nameField.getText()+"',Surname='"+surnameField.getText()+"',Department='"+departmentField.getText()+"',Mail='"+mailField.getText()+"' WHERE Number='"+idField.getText()+"'";
    executeQuery(query);
	showRegisters();
    }
    
    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM registers WHERE Number='"+idField.getText()+"'";
    	executeQuery(query);
    	showRegisters();
    }

    @FXML
    private  void alert1(ActionEvent event){
        Alert a1=new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Basic Alert");
        a1.setContentText("You have a very simple message");
        a1.setHeaderText(null);
        a1.showAndWait();
        //WARNING yerine INFORMATION da yazabilirsin
    }


    @FXML
    private void handleButtonAction(ActionEvent event){
        Image img=new Image("/tik.jpg");
        Notifications notificationBuilder=Notifications.create()
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
    }


    public Connection getConnection() {
    	Connection conn;
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","864297531");
    		return conn;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public ObservableList<Registers> getRegistersList(){
    	ObservableList<Registers> registerList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM registers ";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Registers registers;
			while(rs.next()) {
				registers = new Registers(rs.getInt("Number"),rs.getString("Name"),rs.getString("Surname"),rs.getString("Department"),rs.getString("Mail"),rs.getDate("Date"));
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
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<Registers,Integer>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Registers,String>("name"));
    	surnameColumn.setCellValueFactory(new PropertyValueFactory<Registers,String>("surname"));
    	departmentColumn.setCellValueFactory(new PropertyValueFactory<Registers,String>("department"));
    	mailColumn.setCellValueFactory(new PropertyValueFactory<Registers,String>("mail"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Registers, Date>("date"));
        TableView.setItems(list);
//*************
        TableView.setEditable(true);

        TableColumn idColumn =new TableColumn("Number"); // Kolon Başlığı
        idColumn.setCellValueFactory(new PropertyValueFactory("idColumn")); // Kolona yüklenecek veri alanı adı
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // Edit Yapacaksak alanı bir textfield olarak tanımladık



        TableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//******************

/*
        idColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {

                TableCell<Registers,String> cell=new TableCell<Registers, String>(){

                    Tooltip tooltip=new Tooltip();

                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(!empty){

                            Registers per=getTableView().getItems().get(getIndex());
                            tooltip.setText(per.getName());
                            setTooltip(tooltip);
                            setText(per.getName()+" "+per.getSurname());

                        }
                    }
                };
                return cell;
            }
        });


*/




    }

    public  void showRegisters2() {


    }










}
