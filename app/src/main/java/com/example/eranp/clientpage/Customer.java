package com.example.eranp.clientpage;

import java.io.Serializable;

/**
 * Created by Eran P on 23/01/2018.
 */


public class Customer {
    private String customerId;
    private String fName;
    private String lName;
    private String eMail;
    private String phoneNum;

    public Customer(){

    }

    public Customer(String customerId,String fName, String lName, String eMail, String phoneNum)
    {
        this.customerId = customerId;
        this.fName = fName;
        this.lName = lName;
        this.eMail = eMail;
        this.phoneNum = phoneNum;
    }
    public static Customer create(String fName, String lName, String eMail, String phoneNum)
    {
        Customer section = new Customer();
        section.setfName(fName);
        section.setlName(lName);
        section.seteMail(eMail);
        section.setPhoneNum(phoneNum);
        return section;
    }




    public String getCustomerId() {
        return customerId;
    }

    public String geteMail() {
        return eMail;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
