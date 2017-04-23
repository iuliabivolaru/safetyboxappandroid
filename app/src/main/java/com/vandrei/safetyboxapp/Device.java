package com.vandrei.safetyboxapp;

import java.io.Serializable;

/**
 * Created by VAndrei on 4/23/2017.
 */

public class Device implements Serializable {

    private String name,uuid;
    private int distanceDb;
    private int notificationCounter;

    public Device(String name, int distanceDb, String uuid){
        this.name = name;
        this.distanceDb = distanceDb;
        this.uuid = uuid;
        notificationCounter = 0;

    }

    public int getNotificationCounter() {
        return notificationCounter;
    }

    public void setNotificationCounter(int notificationCounter) {
        this.notificationCounter = notificationCounter;
    }
    public void resetNotificationCounter() {
        this.notificationCounter = 0;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", distanceDb=" + distanceDb +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public int getDistanceDb() {
        return distanceDb;
    }

    public void setDistanceDb(int distanceDb) {
        this.distanceDb = distanceDb;
    }

    public String getName() {
        return name;
    }
}
