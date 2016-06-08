package ru.semiot.platform.model;

import org.json.JSONObject;
import ru.semiot.platform.StreetTemperature;

public class Device {
  // TODO
  // t улицы = 24 - 50 * x
  // x - рычаг (0 - 100), остальное зависимость из графика
  private static double normalTemperature = 22;
  
  private static int countDevices = 0;
  private final int numberDevice;
  private double temperature = 22;

  // int Min = 16;
  // int Max = 28;

  public Device() {
    numberDevice = ++countDevices;
    temperature = Math.random() * 4 + 20;
  }

  public JSONObject getDescription() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("sensor_id", numberDevice);
    jsonObject.put("sensor_type", "temperature");

    return jsonObject;
  }

  public JSONObject getObservation(long timestamp, double presure) {
    double shiftStreet = temperature - normalTemperature - (50 * presure / 100 - 24 + StreetTemperature.getTemperature()) / 2;
    // берем от шифта половину (которая попадает в комнату) и в разных квартирах воздействие от 0.5 до 1
    temperature = temperature - shiftStreet - 2 + Math.random() * 4; 
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("sensor_id", numberDevice);
    // jsonObject.put("value", Min + (int) (Math.random() * (Max - Min)));
    jsonObject.put("value", temperature);
    jsonObject.put("timestamp", timestamp);

    return jsonObject;
  }
}
