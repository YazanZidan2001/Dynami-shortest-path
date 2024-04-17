package com.example.project1_algo;

public class DPPath {

    private int value;

    private String path;

    private String currentCity;

    public DPPath() {}


    public DPPath(int value, String path, String currentCity) {
        this.value = value;
        this.path = path;
        this.currentCity = currentCity;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }


}