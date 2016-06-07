package ru.semiot.platform;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.FIRST)
@Sources({"file:/semiot-platform/building-simulator/config.properties"})
public interface ServiceConfig extends Config {

  @DefaultValue("2")
  @Key("services.count.buildings")
  Integer countBuildings();

  @DefaultValue("4")
  @Key("services.count.flats")
  Integer countFlats();
  
  @DefaultValue("1")
  @Key("services.count.devices")
  Integer countDevices();
  
  @DefaultValue("30")
  @Key("services.scheduled.delay")
  Integer scheduledDelay();
  
  @DefaultValue("3")
  @Key("services.pressure_value")
  Integer pressure_value();
  
}
