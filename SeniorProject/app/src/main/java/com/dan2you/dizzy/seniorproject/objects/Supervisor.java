package com.dan2you.dizzy.seniorproject.objects;

/**
 * Created by Dizzy on 4/11/2017.
 */

public class Supervisor {

    // Class Attributes
    private String firstname = "";
    private String lastname = "";
    private String job_id = "";

    public Supervisor(String firstname, String lastname, String job_id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.job_id = job_id;
    }

    // Class Behaviors
    public String getFullname(){
        String fullname = firstname + " " + lastname;
        return fullname;
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

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    @Override
    public String toString() {
        return firstname + ' ' + lastname ;
    }
}
