package ru.semiot.platform;

import org.eclipse.californium.core.CoapServer;

import java.util.Observable;
import java.util.Observer;

public class BuildingObserver extends CoapServer implements Observer {

  private ObservationsResource observations;
  private DescriptionResource description;
  private RegulatorResource regulator;
  
  public BuildingObserver(final int port, String descr, double pressure) {
    super(port);

    observations = new ObservationsResource();
    description = new DescriptionResource(descr);
    regulator = new RegulatorResource(pressure);
    
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
