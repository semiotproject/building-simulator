package ru.semiot.platform;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Building {

  private static int countBuildings = 0;

  private final int numberBuilding;
  private List<Flat> flats = new ArrayList<Flat>();

  public Building(int countFlats, int countDevices) {
    for (int i = 0; i < countFlats; i++) {
      flats.add(new Flat(countDevices));
    }
    numberBuilding = ++countBuildings;
  }

  public int getBuildingNumber() {
    return numberBuilding;
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
        list.addAll(flat.getObservations(timestamp));
      }
      return list;
    }
    return null;
  }

}
