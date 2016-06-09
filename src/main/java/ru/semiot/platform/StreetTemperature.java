package ru.semiot.platform;

public class StreetTemperature {

  private static double temperature = 0;
  private static double min = 0;
  private static double max = 15;
  
  public static double getTemperature() {
    return temperature;
  }

  public static void setTemperature(double t) {
    temperature = t;
  }
  
  public static void setMinTemperature(double minT) {
    min = minT;
  }
  
  public static void setMaxTemperature(double maxT) {
    max = maxT;
  }
  
  public static void genTemperature() {
    temperature = min + Math.random() * (max - min);
    System.out.println("temperature = " + temperature);
  }
  
}
