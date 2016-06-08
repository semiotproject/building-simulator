package ru.semiot.platform.model;

import org.json.JSONObject;

public class Device {

  private static int countDevices = 0;
  private final int numberDevice;
  int value = 1;
  
  int Min = 16;
  int Max = 28;
  
  public Device() {
    numberDevice = ++countDevices;
  }
  
  public JSONObject getDescription() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("sensor_id", numberDevice);
    jsonObject.put("sensor_type", "temperature");
    
    return jsonObject;
  }
  
  public JSONObject getObservation(long timestamp) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("sensor_id", numberDevice);
    jsonObject.put("value", Min + (int)(Math.random() * (Max - Min)));
    jsonObject.put("timestamp", timestamp);
    
    return jsonObject;
  }
}
