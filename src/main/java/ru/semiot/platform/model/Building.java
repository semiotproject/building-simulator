package ru.semiot.platform.model;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.semiot.platform.coap.BuildingObservationsResource;

import java.util.ArrayList;
import java.util.List;

public class Building implements Comparable<Building> {

  private static int countBuildings = 0;

  private final int numberBuilding;
  private List<Flat> flats = new ArrayList<Flat>();
  private double presure;
  private BuildingObservationsResource bor = null;
  private long timeForObserve = 0;

  public Building(int countFlats, int countDevices, double presure, long timeForObserve) {
    for (int i = 0; i < countFlats; i++) {
      flats.add(new Flat(countDevices));
    }
    numberBuilding = ++countBuildings;
    this.presure = presure;
    this.timeForObserve = timeForObserve;
  }

  public double getPresure() {
    return presure;
  }

  public void setPresure(double presure) {
    this.presure = presure;
  }

  public BuildingObservationsResource getBuildingObservationsResource() {
    return bor;
  }

  public void setBuildingObservationsResource(BuildingObservationsResource bor) {
    this.bor = bor;
  }

  public int getBuildingNumber() {
    return numberBuilding;
  }

  public void setTimeForObserve(long timeForObserve) {
    this.timeForObserve = timeForObserve;
  }

  public long getTimeForObserve() {
    return timeForObserve;
  }

  public void updateBuildObsRes() {
    if (bor != null) {
      bor.update(null, getObservations(System.currentTimeMillis()));
    }
  }

  public JSONObject getDescription() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("building_number", numberBuilding); // building_number

    // TODO
    JSONObject jsonGeo = new JSONObject();
    jsonGeo.put("lattitude", 63.8383);
    jsonGeo.put("longitude", 35.923913);
    jsonObject.put("building_location", jsonGeo); // building_location

    if (!flats.isEmpty()) {
      JSONArray jsonFlats = new JSONArray();
      for (Flat flat : flats) {
        jsonFlats.put(flat.getDescription());
      }
      jsonObject.put("building_flats", jsonFlats);
    }
    return jsonObject;
  }

  public List<JSONObject> getObservations(long timestamp) {
    if (!flats.isEmpty()) {
      List<JSONObject> list = new ArrayList<JSONObject>();
      for (Flat flat : flats) {
        list.addAll(flat.getObservations(timestamp, presure));
      }
      return list;
    }
    return null;
  }

  @Override
  public int compareTo(Building building2) {
    return timeForObserve == building2.getTimeForObserve() ? 0
        : timeForObserve < building2.getTimeForObserve() ? -1 : 1;
  }

}
