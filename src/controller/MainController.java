package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.Books;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button giris_buton;

    @FXML
    private TableView<Books> TableView;
    
    @FXML
    private TableColumn<Books, Integer> idColumn;
    
    @FXML
    private TableColumn<Books, String> nameColumn;

    @FXML
    private TableColumn<Books, String> surnameColumn;

    @FXML
    private TableColumn<Books, String> departmentColumn;

    @FXML
    private TableColumn<Books, String> mailColumn;





    @FXML
    private void insertButton() {
    	String query = "insert into books values('"+idField.getText()+"','"+nameField.getText()+"','"+surnameField.getText()+"','"+departmentField.getText()+"','"+mailField.getText()+"')";
    	executeQuery(query);
    	showBooks();
    }


    @FXML 
    private void updateButton() {
    String query = "UPDATE books SET Name='"+nameField.getText()+"',Surname='"+surnameField.getText()+"',Department='"+departmentField.getText()+"',Mail='"+mailField.getText()+"' WHERE Number='"+idField.getText()+"'";
    executeQuery(query);
	showBooks();
    }
    
    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM books WHERE Number='"+idField.getText()+"'";
    	executeQuery(query);
    	showBooks();
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





    	showBooks();
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
    
    public ObservableList<Books> getBooksList(){
    	ObservableList<Books> booksList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM books ";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Books books;
			while(rs.next()) {
				books = new Books(rs.getInt("Number"),rs.getString("Name"),rs.getString("Surname"),rs.getString("Department"),rs.getString("Mail"));
				booksList.add(books);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return booksList;
    }
    
    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showBooks() {
    	ObservableList<Books> list = getBooksList();
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<Books,Integer>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("name"));
    	surnameColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("surname"));
    	departmentColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("department"));
    	mailColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("mail"));
    	
    	TableView.setItems(list);
    }

}
