package ru.semiot.platform.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class DescriptionResource extends CoapResource {

  private static final String name = "desc";
  private String description;
  
  public DescriptionResource(String description) {
    super(name);
    this.description = description;
  }
  
  @Override
  public void handleGET(CoapExchange exchange) {
      exchange.respond(CoAP.ResponseCode.CONTENT, description);
  }
  
}
