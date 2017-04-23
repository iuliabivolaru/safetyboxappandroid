package com.vandrei.safetyboxapp;

import java.io.Serializable;

/**
 * Created by VAndrei on 4/23/2017.
 */

public class Device implements Serializable {

    private String name,uuid;
    private int distanceDb;

    public Device(String name, int distanceDb, String uuid){
        this.name = name;
        this.distanceDb = distanceDb;
        this.uuid = uuid;

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

    public String getName() {
        return name;
    }
}
