package ru.semiot.platform.coap;

import org.eclipse.californium.core.CoapServer;
import ru.semiot.platform.model.Building;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BuildingObserver extends CoapServer implements Observer {

  private ObservationsResource observations;
  private DescriptionResource description;
  private RegulatorResource regulator;

  public BuildingObserver(final int port, String descr, List<Building> buildings) {
    super(port);

    observations = new ObservationsResource();
    description = new DescriptionResource(descr);
    regulator = new RegulatorResource();

    for (Building building : buildings) {
      regulator.add(new BuildingRegulatorResource(building));
    }
    add(observations);
    add(description);
    add(regulator);
  }

  @Override
  public void start() {
    super.start();
  }

  public void update(Observable arg0, Object arg1) {
    observations.setObservations(arg1.toString());
    observations.changed();
  }

}
