package ru.semiot.platform;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class BuildingRegulatorResource extends CoapResource {
 
  private final Building building;

  public BuildingRegulatorResource(Building building) {
    super(String.valueOf(building.getBuildingNumber()));
    this.building = building;
  }

  @Override
  public void handleGET(CoapExchange exchange) {
    exchange.respond(CoAP.ResponseCode.CONTENT, String.valueOf(building.getPresure()));
  }

  @Override
  public void handlePUT(CoapExchange exchange) {
    String pressureString = exchange.getRequestText();
    if (pressureString != null) {
      try {
        building.setPresure(Double.valueOf(pressureString));
      } catch (NumberFormatException nfe) {
        System.out.println(pressureString);
        System.out.println(nfe.getMessage());
      }
    }
    exchange.respond(CoAP.ResponseCode.CONTENT, String.valueOf(building.getPresure()));
  }
}
