package com.example.eranp.clientpage;

/**
 * Created by Eran P on 23/01/2018.
 */

public class Device {

    private String deviceId;
    private String deviceModel;
    private String deviceName;

    public Device()
    {

    }

    public Device(String deviceId, String deviceModel,String deviceName )
    {
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
    }



    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
