package ru.semiot.platform.model;

import org.json.JSONObject;
import ru.semiot.platform.StreetTemperature;

import java.util.Locale;

public class Device {
  // TODO
  // t street = 24 - 50 * x
  // x - presure (0 - 100)
  private static double optimumTemperature = 22;

  private static int countDevices = 0;
  private final int numberDevice;
  private double temperature;

  // int Min = 16;
  // int Max = 28;

  public Device() {
    numberDevice = ++countDevices;
    temperature = optimumTemperature - 2 + Math.random() * 4;
  }

  public JSONObject getDescription() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("sensor_id", numberDevice);
    jsonObject.put("sensor_type", "temperature");

    return jsonObject;
  }

  public JSONObject getObservation(long timestamp, double presure) {
    double shiftStreet = temperature - optimumTemperature
        - (50 * presure / 100 - 24 + StreetTemperature.getTemperature()) / 2;
    // take half of the shift (which falls into the room) and the impact of different apartments
    // from 0.5 to 1
    temperature = temperature - shiftStreet - 0.5 + Math.random() * 1;
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("sensor_id", numberDevice);
    // jsonObject.put("value", Min + (int) (Math.random() * (Max - Min)));
    jsonObject.put("value", String.format(Locale.ENGLISH, "%.1f", temperature)); 
    jsonObject.put("timestamp", timestamp);

    return jsonObject;
  }

  public static void setOptimumTemperature(double optimumT) {
    optimumTemperature = optimumT;
  }

}
