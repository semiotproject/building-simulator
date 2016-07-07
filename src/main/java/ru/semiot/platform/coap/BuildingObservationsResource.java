package ru.semiot.platform.coap;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Observable;
import java.util.Observer;

public class BuildingObservationsResource extends CoapResource implements Observer {

  private String observations;
  private static final String name = "obs";

  public BuildingObservationsResource() {
    super(name);
    setObservable(true);
    getAttributes().setObservable();
  }

  public void setObservations(final String observations) {
    this.observations = observations;
  }


  @Override
  public void handleGET(CoapExchange exchange) {
    exchange.respond(CONTENT, observations, TEXT_PLAIN);
  }

  public void update(Observable arg0, Object arg1) {
    this.setObservations(arg1.toString());
    this.changed();
  }

}
