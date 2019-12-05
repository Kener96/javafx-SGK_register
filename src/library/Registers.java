package library;

import java.sql.Date;

public class Registers {

	private int id;
    private String name;
    private String surname;
    private String department ;
    private String mail;
    private Date date;

    public Registers(int Id, String Name, String Surname, String Department, String Mail, Date Date){
        this.id = Id;
    	this.name=Name;
        this.surname = Surname;
        this.department=Department;
        this.mail=Mail;
        this.date=Date;
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
}
