package ru.semiot.platform;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScheduledDevice implements Runnable {

  private BuildingObserver  observer;
  private List<Building> buildings;
  double i=0;
  
  public ScheduledDevice (BuildingObserver observer, List<Building> buildings) {
    this.observer = observer;
    this.buildings = buildings;
  }
  
  public void run() {
    // JSONArray jsonArray = new JSONArray();
    List<JSONObject>list = new ArrayList<JSONObject>();
    for (Building building : buildings) {
      //jsonArray.put(building.getObservations(System.currentTimeMillis()));
      list.addAll(building.getObservations(System.currentTimeMillis()));
    }
    
    System.out.println(i);
    observer.update(null, list.toString());
    
  }

}
