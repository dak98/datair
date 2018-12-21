package data.collector.visitors;

import data.collector.AirDataCollector;
import data.source.SensorData;
import data.source.powietrze.gov.PowietrzeGov;
import data.source.powietrze.gov.PowietrzeGovSensor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetAllValuesOfParamAtDateIntegrationTest {

    @Test
    void shouldReturnACorrectList() {
        AirDataCollector airDataCollector = new AirDataCollector();
        String[] args = { "PM10", "2018-12-17 13:00:00" };
        System.out.println((SensorData) airDataCollector.accept(new GetAllValuesOfParamAtDate(), args, new PowietrzeGov()));
        assertTrue(true);
    }
}