package com.example.eranp.clientpage;

/**
 * Created by Eran P on 20/04/2018.
 */

public class ProblemDeviceCustomer {
    private String problemDeviceCustomerId;
    private String proDevDetShortPDC;/*תיאור בעיה בקיצור*/
    private String proDevDetPDC;/*תיאור כל הבעיה*/
    private String deviceModelPDC;
    private String deviceNamePDC;
    private String fNamePDC;
    private String lNamePDC;
    private int  urgencyPDC; /*דחיפות*/
    private String phoneNumPDC;
    private String eMailPDC;
    private String dataArrivedPDC;
    public ProblemDeviceCustomer(){

    }



    public ProblemDeviceCustomer(String problemDeviceCustomerI, String fName, String lName, String phoneNum, String eMail, String deviceModelPDC, String deviceNamePDC, String proDevDetShortPDC ,String proDevDetPDC, String dataArrived, int urgencyPDC )
    {
        this.problemDeviceCustomerId = problemDeviceCustomerI;
        this.fNamePDC = fName;
        this.lNamePDC = lName;
        this.phoneNumPDC = phoneNum;
        this.deviceModelPDC = deviceModelPDC;
        this.deviceNamePDC = deviceNamePDC;
        this.urgencyPDC = urgencyPDC;
        this.proDevDetShortPDC = proDevDetShortPDC;
        this.proDevDetPDC = proDevDetPDC;
        this.eMailPDC = eMail;
        this.dataArrivedPDC = dataArrived;
    }


    public String getProDevDetPDC() {
        return proDevDetPDC;
    }

    public void setProDevDetPDC(String proDevDetPDC) {
        this.proDevDetPDC = proDevDetPDC;
    }

    public String getDataArrivedPDC() {
        return dataArrivedPDC;
    }

    public void setDataArrivedPDC(String dataArrivedPDC) {
        this.dataArrivedPDC = dataArrivedPDC;
    }

    public String geteMailPDC() {
        return eMailPDC;
    }

    public void seteMailPDC(String eMail) {
        this.eMailPDC = eMail;
    }

    public String getProblemDeviceCustomerId() {
        return problemDeviceCustomerId;
    }

    public void setProblemDeviceCustomerId(String problemDeviceCustomerId) {
        this.problemDeviceCustomerId = problemDeviceCustomerId;
    }

    public String getProDevDetShortPDC() {
        return proDevDetShortPDC;
    }

    public void setProDevDetShortPDC(String proDevDetShortPDC) {
        this.proDevDetShortPDC = proDevDetShortPDC;
    }

    public String getDeviceModelPDC() {
        return deviceModelPDC;
    }

    public void setDeviceModelPDC(String deviceModelPDC) {
        this.deviceModelPDC = deviceModelPDC;
    }

    public String getDeviceNamePDC() {
        return deviceNamePDC;
    }

    public void setDeviceNamePDC(String deviceNamePDC) {
        this.deviceNamePDC = deviceNamePDC;
    }

    public String getfNamePDC() {
        return fNamePDC;
    }

    public void setfNamePDC(String fNamePDC) {
        this.fNamePDC = fNamePDC;
    }

    public String getlNamePDC() {
        return lNamePDC;
    }

    public void setlNamePDC(String lNamePDC) {
        this.lNamePDC = lNamePDC;
    }

    public int getUrgencyPDC() {
        return urgencyPDC;
    }

    public void setUrgencyPDC(int urgencyPDC) {
        this.urgencyPDC = urgencyPDC;
    }

    public String getPhoneNumPDC() {
        return phoneNumPDC;
    }

    public void setPhoneNumPDC(String phoneNumPDC) {
        this.phoneNumPDC = phoneNumPDC;
    }
}
