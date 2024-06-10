package com.coolweather.android.model;

public class CountyModel {

    private int id;
    private String weatherId;
    private String countyName;
    public CountyModel() {
    }

    public CountyModel(String weatherId, String countyName) {
        this.weatherId = weatherId;
        this.countyName = countyName;
    }

    public CountyModel(int id, String weatherId, String countyName) {
        this.id = id;
        this.weatherId = weatherId;
        this.countyName = countyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Override
    public String toString() {
        return "CountyModel{" +
                "weatherId='" + weatherId + '\'' +
                ", countyName='" + countyName + '\'' +
                '}';
    }
}
