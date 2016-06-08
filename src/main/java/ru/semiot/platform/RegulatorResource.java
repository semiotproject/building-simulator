package ru.semiot.platform;

import org.eclipse.californium.core.CoapResource;

public class RegulatorResource extends CoapResource {
  private static final String name = "regulator";

  public RegulatorResource() {
    super(name);
  }

  /* @Override
  public void handleGET(CoapExchange exchange) {
    exchange.respond(CoAP.ResponseCode.CONTENT, String.valueOf(pressure));
  }

  @Override
  public void handlePUT(CoapExchange exchange) {
    String pressureString = exchange.getRequestText();
    if (pressureString != null) {
      try {
        pressure = Double.valueOf(pressureString);
      } catch (NumberFormatException nfe) {
        System.out.println(pressureString);
        System.out.println(nfe.getMessage());
      }
    }
    exchange.respond(CoAP.ResponseCode.CONTENT, String.valueOf(pressure));
  } */
}
