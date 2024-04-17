package com.example.project1_algo;

import java.util.ArrayList;
import java.util.List;

public class DPCell {

    private int value;

    private String currentCity;

    private List<DPPath> pathList = new ArrayList<>();

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public List<DPPath> getPathList() {
        return pathList;
    }

    public void setPathList(List<DPPath> pathList) {
        this.pathList = pathList;
    }
}
