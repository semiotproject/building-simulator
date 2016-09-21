package ru.semiot.platform;

import static java.util.concurrent.TimeUnit.MINUTES;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.semiot.platform.coap.BuildingsServer;
import ru.semiot.platform.model.Building;
import ru.semiot.platform.model.Device;
import ru.semiot.platform.scheduler.ScheduledTemperature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Launcher {

  private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

  private static final ServiceConfig config = ConfigFactory.create(ServiceConfig.class);

  private static ScheduledExecutorService schedulerTemperature;
  private static ScheduledTemperature scheduledTemperature;
  private static ScheduledFuture handleTemperature = null;

  private static ConcurrentLinkedQueue<Building> clQueue;

  private static BuildingsServer server;

  private static ExecutorService executor = new ThreadPoolExecutor(10, 10, 0L,
      TimeUnit.MILLISECONDS, new SynchronousQueue(), new ThreadPoolExecutor.CallerRunsPolicy());

  public static void main(String[] args) {
    init();
    start();
  }

  private static void init() {
    initTemperature();
    initBuildings();

    server = new BuildingsServer(5683, clQueue);

    schedulerTemperature = Executors.newScheduledThreadPool(1);
    scheduledTemperature = new ScheduledTemperature();
  }

  private static void initTemperature() {
    StreetTemperature.setMinTemperature(config.temperatureMin());
    StreetTemperature.setMaxTemperature(config.temperatureMax());
    StreetTemperature
        .setCountObservationInTransitionExtremum(config.countObservationInTransitionExtremum());
    StreetTemperature.setRandom(new Random(config.temperatureRandomSeed())); // seed
    Device.setOptimumTemperature(config.temperatureOptimum());
  }

  private static void initBuildings() {
    List<Building> buildings = new ArrayList<Building>();
    Random randomShiftForObserve = new Random(config.scheduledDelayObserve());
    long currTime = System.currentTimeMillis();
    for (int i = 0; i < config.countBuildings(); i++) {
      buildings.add(new Building(config.countFlats(), config.countDevices(), config.pressureValue(),
          currTime + randomShiftForObserve.nextInt(config.scheduledDelayObserve() * 1000)));
    }
    Collections.sort(buildings);

    clQueue = new ConcurrentLinkedQueue<Building>(buildings);
  }

  private static void start() {
    server.start();
    startSheduledTemperature();

    while (true) {
      if (!clQueue.isEmpty()) {
        Building building = clQueue.poll();
        if (building != null)
          updateBuildObsRes(building);
      }
    }
  }

  public static void updateBuildObsRes(Building b) {
    executor.execute(() -> {
      long diff = b.getTimeForObserve() - System.currentTimeMillis();
      if (diff > 0) {
        try {
          Thread.sleep(diff);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      b.updateBuildObsRes();
      b.setTimeForObserve(b.getTimeForObserve() + config.scheduledDelayObserve() * 1000);
      clQueue.add(b);
    });
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
