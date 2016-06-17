package ru.semiot.platform.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.semiot.platform.model.Building;

public class BuildingRegulatorResource extends CoapResource {

  private static final Logger logger = LoggerFactory.getLogger(BuildingRegulatorResource.class);

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
        logger.error(nfe.getMessage(), nfe);
      }
    }
    exchange.respond(CoAP.ResponseCode.CONTENT, String.valueOf(building.getPresure()));
  }
}
