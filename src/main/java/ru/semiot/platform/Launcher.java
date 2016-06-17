package ru.semiot.platform;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.semiot.platform.coap.BuildingObserver;
import ru.semiot.platform.model.Building;
import ru.semiot.platform.model.Device;
import ru.semiot.platform.scheduler.ScheduledObserve;
import ru.semiot.platform.scheduler.ScheduledTemperature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Launcher {

  private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

  private static final ServiceConfig config = ConfigFactory.create(ServiceConfig.class);

  private static ScheduledExecutorService schedulerObserve;
  private static ScheduledObserve scheduledObserve;
  private static ScheduledFuture handleObserve = null;

  private static ScheduledExecutorService schedulerTemperature;
  private static ScheduledTemperature scheduledTemperature;
  private static ScheduledFuture handleTemperature = null;

  private static List<Building> buildings = new ArrayList<Building>();

  private static BuildingObserver server;

  public static void main(String[] args) {
    init();
    start();
  }

  private static void start() {
    server.start();
    startSheduledObserve();
    startSheduledTemperature();
  }

  private static void init() {
    StreetTemperature.setMinTemperature(config.temperatureMin());
    StreetTemperature.setMaxTemperature(config.temperatureMax());
    StreetTemperature
        .setCountObservationInTransitionExtremum(config.countObservationInTransitionExtremum());
    // StreetTemperature.setTemperature(t);
    Device.setOptimumTemperature(config.temperatureOptimum());

    for (int i = 0; i < config.countBuildings(); i++) {
      buildings
          .add(new Building(config.countFlats(), config.countDevices(), config.pressureValue()));
    }
    server = new BuildingObserver(5683, getDescription(), buildings);

    schedulerObserve = Executors.newScheduledThreadPool(1);
    scheduledObserve = new ScheduledObserve(server, buildings);

    schedulerTemperature = Executors.newScheduledThreadPool(1);
    scheduledTemperature = new ScheduledTemperature();
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

  public static void startSheduledObserve() {
    if (handleObserve != null)
      stopSheduledObserve();
    handleObserve = schedulerObserve.scheduleAtFixedRate(scheduledObserve, 5,
        config.scheduledDelayObserve(), SECONDS);
    logger.info("Scheduled observe started. Repeat will do every {} seconds",
        String.valueOf(config.scheduledDelayObserve()));
  }

  public static void stopSheduledObserve() {
    if (handleObserve == null)
      return;

    handleObserve.cancel(true);
    handleObserve = null;
    logger.info("Scheduled observe stoped");
  }

  public static void startSheduledTemperature() {
    if (handleTemperature != null)
      stopSheduledTemperature();
    handleTemperature = schedulerTemperature.scheduleAtFixedRate(scheduledTemperature, 0,
        config.scheduledDelayTemperature(), MINUTES);
    logger.info("Scheduled temperature started. Repeat will do every {} minutes",
        String.valueOf(config.scheduledDelayTemperature()));
  }

  public static void stopSheduledTemperature() {
    if (handleTemperature == null)
      return;

    handleTemperature.cancel(true);
    handleTemperature = null;
    logger.info("Scheduled temperature stoped");
  }

}
