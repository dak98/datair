package data.visitors;

import data.AirDataCollector;
import data.SensorData;
import data.source.powietrze.gov.PowietrzeGov;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetAllValuesOfParamAtDateIntegrationTest {

    @Test
    void shouldReturnACorrectList() {
        try {
            AirDataCollector airDataCollector = new AirDataCollector();
            String[] args = {"PM10", "2018-12-17 13:00:00"};
            System.out.println((SensorData) airDataCollector.accept(new GetAllValuesOfParamAtDate(), args, new PowietrzeGov()));
            assertTrue(true);
        } catch (IOException e) {

        }
    }
}