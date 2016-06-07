package ru.semiot.platform;

import org.eclipse.californium.core.CoapServer;

import java.util.Observable;
import java.util.Observer;

public class BuildingObserver extends CoapServer implements Observer {

  private ObservationsResource observations;
  private DescriptionResource description;
  
  public BuildingObserver(final int port, String descr) {
    super(port);

    observations = new ObservationsResource();
    description = new DescriptionResource(descr);

    add(observations);
    add(description);
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
