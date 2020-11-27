package com.example.eranp.clientpage;

import java.io.Serializable;

/**
 * Created by Eran P on 23/01/2018.
 */

public class Problem {
    private String problemId;
    private String proDeviceDet;
    private String dataArrived;
    private int  urgency; /*דחיפות*/
    private String proDevDetShort;/*תיאור בעיה בקיצור*/

    public Problem()
    {

    }

    public Problem(String problemId, String dataArrived, String proDeviceDet, int urgency, String proDevDetShort)
    {
        this.problemId = problemId;
        this.dataArrived = dataArrived;
        this.proDeviceDet = proDeviceDet;
        this.urgency = urgency;
        this.proDevDetShort = proDevDetShort;
    }
    public static Problem create(String problemId, String dataArrived, String proDeviceDet, int urgency, String proDevDetShort)
    {
        Problem section = new Problem();
        section.setDataArrived(dataArrived);
        section.setProDevDetShort(proDevDetShort);
        section.setProDeviceDet(proDeviceDet);
        section.setUrgency(urgency);
        return section;
    }



    public int getUrgency() {
        return urgency;
    }

    public String getProblemId() {
        return problemId;
    }

    public String getDataArrived() {
        return dataArrived;
    }

    public String getProDeviceDet() {
        return proDeviceDet;
    }

    public void setDataArrived(String dataArrived) {
        this.dataArrived = dataArrived;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public void setProDeviceDet(String proDeviceDet) {
        this.proDeviceDet = proDeviceDet;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getProDevDetShort() {
        return proDevDetShort;
    }

    public void setProDevDetShort(String proDevDetShort) {
        this.proDevDetShort = proDevDetShort;
    }

}
