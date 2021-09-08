package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.generator.ElectricityReadingsGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MeterReadingServiceImplTest {

    private MeterReadingService meterReadingService;

    @BeforeEach
    public void setUp() {
        meterReadingService = new MeterReadingServiceImpl(new HashMap<>());
    }

    @Test
    public void givenMeterIdThatDoesNotExistShouldReturnNull() {
        assertThat(meterReadingService.getReadings("unknown-id")).isEqualTo(Optional.empty());
    }

    @Test
    public void givenMeterReadingThatExistsShouldReturnMeterReadings() {
        meterReadingService.storeReadings("random-id", new ArrayList<>());
        assertThat(meterReadingService.getReadings("random-id")).isEqualTo(Optional.of(new ArrayList<>()));
    }
    @Test
    public void testGetPrevNaturalWeekReadingsWithEmptyList() {
        meterReadingService.storeReadings("random-id", new ArrayList<>());
        //assertThat(meterReadingService.getPrevNaturalWeekReadings("random-id")).isEmpty();
        assertThat(meterReadingService.getPrevNaturalWeekReadings("random-id")).isEqualTo(Optional.of(new ArrayList()));
    }
    @Test
    public void testGetPrevNaturalWeekReadingsWithPopulatedList() {
        List<ElectricityReading> electricityReadings= new ElectricityReadingsGenerator().generate(5);
        meterReadingService.storeReadings("random-id", electricityReadings);
        //assertThat(meterReadingService.getPrevNaturalWeekReadings("random-id")).isEmpty();
        assertThat(meterReadingService.getPrevNaturalWeekReadings("random-id")).isEqualTo(Optional.of(electricityReadings));
    }
}
