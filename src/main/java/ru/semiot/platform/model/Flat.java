package ru.semiot.platform.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Flat {

  private static int countFlats = 0;
  private final int numberFlat;
  private List<Device> devices = new ArrayList<Device>();
  
  public Flat(int countDevices) {
    for(int i =0; i< countDevices; i++) {
      devices.add(new Device());
    }
    numberFlat = ++countFlats;
  }
  
  public JSONObject getDescription() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("flat_number", numberFlat);
    if(!devices.isEmpty()) {
      JSONArray jsonArray = new JSONArray();
      for(Device device : devices) {
        jsonArray.put(device.getDescription());
      }
      jsonObject.put("flat_sensors", jsonArray);
    }
    return jsonObject;
  }
  
  public List<JSONObject> getObservations(long timestamp) {
    if (!devices.isEmpty()) {
      List<JSONObject> list = new ArrayList<JSONObject>();
      for (Device device : devices) {
        list.add(device.getObservation(timestamp));
      }
      return list;
    }
    return null;
  }
}
