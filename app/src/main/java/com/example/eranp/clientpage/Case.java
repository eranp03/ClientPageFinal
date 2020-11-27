package com.example.eranp.clientpage;

import android.widget.TextView;

/**
 * Created by Eran P on 15/05/2018.
 */

public class Case {

    private  String fullName;
    private  String phone;
    private  String proDetDev;
    private  String nameDev;
    private  String modelDev;
    private  String price;
    private  String worker;
    private String date;
    private boolean fixable;
    private String cashNum; //מספר קופה

    public Case(String fullName, String phone, String proDetDev, String nameDev, String modelDev, String price, String worker, boolean fixable, String date,String cashNum) {
        this.fullName = fullName;
        this.phone = phone;
        this.proDetDev = proDetDev;
        this.nameDev = nameDev;
        this.modelDev = modelDev;
        this.price = price;
        this.worker = worker;
        this.fixable = fixable;
        this.date = date;
        this.cashNum = cashNum;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCashNum() {
        return cashNum;
    }

    public void setCashNum(String cashNum) {
        this.cashNum = cashNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProDetDev() {
        return proDetDev;
    }

    public void setProDetDev(String proDetDev) {
        this.proDetDev = proDetDev;
    }

    public String getNameDev() {
        return nameDev;
    }

    public void setNameDev(String nameDev) {
        this.nameDev = nameDev;
    }

    public String getModelDev() {
        return modelDev;
    }

    public void setModelDev(String modelDev) {
        this.modelDev = modelDev;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public boolean isFixable() {
        return fixable;
    }

    public void setFixable(boolean fixable) {
        this.fixable = fixable;
    }
}
