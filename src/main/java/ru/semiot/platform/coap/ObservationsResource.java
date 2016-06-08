package ru.semiot.platform.coap;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
public class ObservationsResource extends CoapResource {

    private String observations;

    public ObservationsResource() {
        super("obs");
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

}
