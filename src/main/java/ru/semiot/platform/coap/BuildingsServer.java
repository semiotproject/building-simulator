package ru.semiot.platform.coap;

import org.eclipse.californium.core.CoapServer;
import ru.semiot.platform.model.Building;

import java.util.List;

public class BuildingsServer extends CoapServer {

  public BuildingsServer(final int port, List<Building> buildings) {
    super(port);

    for (Building building : buildings) {
      BuildingResource buildingResource = new BuildingResource(String.valueOf(building.getBuildingNumber()));
      BuildingDescriptionResource bdr = new BuildingDescriptionResource(building.getDescription().toString());
      BuildingObservationsResource bor = new BuildingObservationsResource();
      BuildingRegulatorResource brr = new BuildingRegulatorResource(building);
      
      building.setBuildingObservationsResource(bor);
      
      buildingResource.add(bdr); // descr
      buildingResource.add(bor); // obs
      buildingResource.add(brr); // reg
      
      add(buildingResource);
    }
  }

 @Override
  public void start() {
    super.start();
  }

}
