package com.linone.warning;

import com.google.gson.annotations.SerializedName;

public class JsonObject {
    @SerializedName("created_at")
    private String created_at;

    @SerializedName("entry_id")
    private String entry_id;

    @SerializedName("field1")
    private String WaterLevel;

    @SerializedName("field2")
    private String Rainfall_1hour;

    @SerializedName("field3")
    private String Rainfall_24hour;

    @SerializedName("field4")
    private String Temperature;

    @SerializedName("field5")
    private String Voltage;

    @SerializedName("field6")
    private String Alarm;

    public JsonObject(String created_at, String entry_id, String waterLevel, String rainfall_24hour, String rainfall_1hour, String temperature, String voltage, String alarm) {
        this.created_at = created_at;
        this.entry_id = entry_id;
        WaterLevel = waterLevel;
        Rainfall_24hour = rainfall_24hour;
        Rainfall_1hour = rainfall_1hour;
        Temperature = temperature;
        Voltage = voltage;
        Alarm = alarm;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public String getWaterLevel() {
        return WaterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        WaterLevel = waterLevel;
    }

    public String getRainfall_24hour() {
        return Rainfall_24hour;
    }

    public void setRainfall_10min(String rainfall_10min) {
        Rainfall_24hour = rainfall_10min;
    }

    public String getRainfall_1hour() {
        return Rainfall_1hour;
    }

    public void setRainfall_1hour(String rainfall_1hour) {
        Rainfall_1hour = rainfall_1hour;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getVoltage() {
        return Voltage;
    }

    public void setVoltage(String voltage) {
        Voltage = voltage;
    }

    public String getAlarm() {
        return Alarm;
    }

    public void setAlarm(String alarm) {
        Alarm = alarm;
    }
}
