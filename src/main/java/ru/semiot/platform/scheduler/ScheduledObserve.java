package ru.semiot.platform.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.semiot.platform.model.Building;

import java.util.List;

public class ScheduledObserve implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledObserve.class);

  private List<Building> buildings;
  double i = 0;

  public ScheduledObserve(List<Building> buildings) {
    this.buildings = buildings;
  }

  public void run() {
    for (Building building : buildings) {
      building.updateBuildObsRes();
    }

    logger.debug("All observer update.");

  }

}
