package ru.semiot.platform.scheduler;

import org.json.JSONObject;
import ru.semiot.platform.coap.BuildingObserver;
import ru.semiot.platform.model.Building;

import java.util.ArrayList;
import java.util.List;

public class ScheduledObserve implements Runnable {

  private BuildingObserver observer;
  private List<Building> buildings;
  double i = 0;

  public ScheduledObserve(BuildingObserver observer, List<Building> buildings) {
    this.observer = observer;
    this.buildings = buildings;
  }

  public void run() {
    List<JSONObject> list = new ArrayList<JSONObject>();
    for (Building building : buildings) {
      list.addAll(building.getObservations(System.currentTimeMillis()));
    }

    System.out.println("Observer update.");
    observer.update(null, list.toString());

  }

}
