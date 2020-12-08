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

    public static MenuTemperature fromString(String text) {
        for (MenuTemperature t : MenuTemperature.values()) {
            if (t.temperature.equalsIgnoreCase(text)) {
                return t;
            }
        }
        return null;
    }
}
