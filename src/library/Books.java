package library;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

public class Books {

	private int id;
    private String name;
    private String surname;
    private String department ;
    private String mail;

    public Books(int Id, String Name, String Surname, String Department, String Mail){
        this.id = Id;
    	this.name=Name;
        this.surname = Surname;
        this.department=Department;
        this.mail=Mail;
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
}
