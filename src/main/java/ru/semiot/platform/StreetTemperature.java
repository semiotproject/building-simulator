package ru.semiot.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreetTemperature {

  private static final Logger logger = LoggerFactory.getLogger(StreetTemperature.class);

  private static int currentCountObservation;
  private static int countObservationInTransitionExtremum = 3; // default
  private static double prevTemperature = -999;
  private static double nextTemperature;
  private static double temperature = 0;
  private static double min = 0; // default
  private static double max = 15; // default

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

  public static void setCountObservationInTransitionExtremum(int count) {
    countObservationInTransitionExtremum = count;
  }

  public static void genTemperature() {
    if (currentCountObservation > countObservationInTransitionExtremum
        || prevTemperature == -999) {
      if (prevTemperature == -999) {
        prevTemperature = min + Math.random() * (max - min);
      } else {
        prevTemperature = nextTemperature;
      }
      nextTemperature = min + Math.random() * (max - min);
      currentCountObservation = 0;
      logger.info("Previous temperature = {}, next temperature = {}", prevTemperature,
          nextTemperature);
    }
    if(currentCountObservation == countObservationInTransitionExtremum) {
      temperature = nextTemperature;
    } else {
      double shift = (prevTemperature - nextTemperature) / (countObservationInTransitionExtremum + 1);
      temperature = prevTemperature - shift * (Math.random() + currentCountObservation);
    }
    currentCountObservation++;
    logger.info("temperature{} = {};", currentCountObservation, temperature);
  }

}
