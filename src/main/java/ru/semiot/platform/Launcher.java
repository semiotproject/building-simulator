package ru.semiot.platform;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Launcher {

  private static final ServiceConfig config = ConfigFactory.create(ServiceConfig.class);

  private static ScheduledExecutorService scheduler;
  private static ScheduledDevice scheduledDevice;
  private static ScheduledFuture handle = null;
  // private static int scheduledDelay = 20;
  private static List<Building> buildings = new ArrayList<Building>();

  private static BuildingObserver server;

  public static void main(String[] args) {

    // запустить с конфигом дом/сенсоры
    // посылать показания раз в какой-то промежуток в систему

    // рандомизировать данные показания таким образом, чтобы они отклонялись (например в зависимости
    // от температуры на улице)

    // изменять показания по рычаку (коэффициент от 0 до 1 например)
    init();
    start();
  }

  private static void start() {
    server.start();
    startSheduled();
  }

  private static void init() {
    for (int i = 0; i < config.countBuildings(); i++) {
      buildings.add(new Building(config.countFlats(), config.countDevices()));
    }
    server = new BuildingObserver(5683, getDescription());
    scheduler = Executors.newScheduledThreadPool(1);
    scheduledDevice = new ScheduledDevice(server, buildings);
  }

  private static String getDescription() {
    if (!buildings.isEmpty()) {
      JSONArray jsonArray = new JSONArray();
      for (Building building : buildings) {
        jsonArray.put(building.getDescription());
      }
      
      return jsonArray.toString();
    }
    return "";
  }

  public static void startSheduled() {
    if (handle != null)
      stopSheduled();

    handle =
        // scheduler.scheduleAtFixedRate(scheduledDevice, 1, scheduledDelay, MINUTES);
        scheduler.scheduleAtFixedRate(scheduledDevice, 5, config.scheduledDelay(), SECONDS);
    System.out.println(
        "UScheduled started. Repeat will do every " + String.valueOf(config.scheduledDelay()) + " minutes");
  }

  public static void stopSheduled() {
    if (handle == null)
      return;

    handle.cancel(true);
    handle = null;
    System.out.println("UScheduled stoped");
  }

}
