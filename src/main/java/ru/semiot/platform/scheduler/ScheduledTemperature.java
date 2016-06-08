package ru.semiot.platform.scheduler;

import ru.semiot.platform.StreetTemperature;

public class ScheduledTemperature implements Runnable {
  
  public void run() {
    StreetTemperature.genTemperature();
  }

}
