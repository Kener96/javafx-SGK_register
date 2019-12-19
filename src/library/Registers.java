package library;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class Registers {


	private int id;
    private String name;
    private String surname;
    private String department ;
    private String mail;
    private Date date;
    private Date date2;


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Registers(int Id, String Name, String Surname, String Department, String Mail, Date Date, Date Date2){
        this.id = Id;
    	this.name=Name;
        this.surname = Surname;
        this.department=Department;
        this.mail=Mail;
        this.date=Date;
       this.date2=Date2;

    }




    public int getId() {
    	return id;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDepartment() {
        return department;
    }


    public String getMail() {
        return mail;
    }
    public Date getDate() {
        return date;
    }
    public Date getDate2() { return date2; }
}
