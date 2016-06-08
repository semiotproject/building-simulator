package ru.semiot.platform.coap;

import org.eclipse.californium.core.CoapResource;

public class RegulatorResource extends CoapResource {
  private static final String name = "regulator";

  public RegulatorResource() {
    super(name);
  }
}
