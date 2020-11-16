package com.example.uorders.domain;

public enum MenuTemperature {
    HOT("HOT"),
    ICED("ICED"),
    NONE("");

    private String temperature;

    MenuTemperature(String temperature) {
        this.temperature = temperature;
    }
    String getTemperature(){
        return this.temperature;
    }
}
