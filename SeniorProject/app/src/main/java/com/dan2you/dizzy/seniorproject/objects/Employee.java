package com.dan2you.dizzy.seniorproject.objects;

/**
 * Created by Dizzy on 4/1/2017.
 */

public class Employee {

    // Class Attributes
    private String employeeID = "";
    private String firstname = "";
    private String lastname = "";

    public Employee(String employeeID, String firstname, String lastname) {
        this.employeeID = employeeID;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Class Behaviors
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public  String getFullname(){
        String fullname = firstname + " " + lastname;
        return fullname;
    }
}
