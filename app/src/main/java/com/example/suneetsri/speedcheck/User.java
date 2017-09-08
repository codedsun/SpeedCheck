package com.example.suneetsri.speedcheck;

/**
 * Created by suneetsri on 8/9/17.
 */

public class User {
    private String Name;
    private String number1,number2,number3;
    private String notificationEnabled;
    private float currentSpeed;

    public String getNotificationEnabled() {
        return notificationEnabled;
    }

    public void setNotificationEnabled(String notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    private String speedLimit;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getNumber3() {
        return number3;
    }

    public void setNumber3(String number3) {
        this.number3 = number3;
    }



    public String getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(String speedLimit) {
        this.speedLimit = speedLimit;
    }
}
