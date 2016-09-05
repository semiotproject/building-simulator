Данный симулятор генерирует показания температурных датчиков в квартирах. Для работы необходимо указать количество симулируемых домов, количество квартир в каждом доме и количество устройств (измеряющих температуру) в каждой комнате. Так же необходимо указать интервал времени между соседними показания температурных датчиков. Т.е. показания датчика будут приходить раз в заданный интервал времени. Данный интервал задается в секундах переменной services.scheduled.delay.observe. При расчете температуры в комнате учитываются следующие показатели: температура на улице, подаваемое для отопления давление в процентах, оптимальная температура. Подаваемое давление задается в процентах [0-100] переменной services.pressure_value. Оптимальная температура определяет к какой температуре, в идеальном случае, будет стремиться температура в квартире. Данная температура задается в градусах переменной services.temperature.optimum. Температура на улице изменяется раз в заданный интервал времени. Данный интервал времени задается в минутах переменной services.scheduled.delay.temperature. Температура на улице имеет точки экстремума. Т.е. между точками температура на улице изменяется плавно увеличиваясь или уменьшаясь.  Количество точек между экстремумами задается переменной services.count.observations.transition.extremum.  При расчете температуры на улице в точках экстремумов учитывается минимальная и максимальная допустимая температура на улице. Минимальная и максимальная температура на улице задается в градусах переменными services.temperature.min и services.temperature.max соответственно.
Пример для температуры на улице:

Переменные: 
```
services.count.observations.transition.extremum=2
services.temperature.min=0
services.temperature.max=15
```
Температура:
``` 2 2.7 3.4 4 10 12 13 9 4 0 ```

# Table with a description of config.properties file.

| Key        | Description       |
| :-------------: | :-------------: | 
| services.count.buildings | count of simulated buildings |
| services.count.flats | count of flats in each building |
| services.count.devices | count of devices in each flat |
| services.scheduled.delay.observe | delay for generating new observation of temperature devices | 
| services.pressure_value | initial value of the pressure |
| services.scheduled.delay.temperature | delay for generating new outdoor temperature |
| services.temperature.min | minimum outdoor temperature |
| services.temperature.max | maximum outdoor temperature |
| services.temperature.optimum | optimum outdoor temperature |
| services.count.observations.transition.extremum | count observations transition in extremum |
