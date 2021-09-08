package uk.tw.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.generator.ElectricityReadingsGenerator;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PricePlanServiceImplTest {

    public static final String METER_ID_0 = "meter_0";
    public static final String METER_ID_1 = "meter_1";
    public static final String PLAN_ID_0 = "plan_id_0";
    public static final String PLAN_ID_1 = "plan_id_1";

    private PricePlanService pricePlanService;
    @BeforeEach
    public void setUp() {

        final List<PricePlan> pricePlans = Arrays.asList(
                new PricePlan(PLAN_ID_0, "Supplier 0", BigDecimal.ONE, Collections.emptyList()),
                new PricePlan(PLAN_ID_1, "Supplier 1", BigDecimal.TEN, Collections.emptyList())
        );

        ElectricityReadingsGenerator generator = new ElectricityReadingsGenerator();
        Map<String, List<ElectricityReading>> meterAssociatedReadings = new HashMap<>();
        // Time elapsed 360 seconds
        Arrays.asList(METER_ID_0, METER_ID_1).stream().forEach(meterId -> meterAssociatedReadings.put(meterId, generator.generate(37)));
        final MeterReadingService meterReadingService = new MeterReadingServiceImpl(meterAssociatedReadings);

        pricePlanService = new PricePlanServiceImpl(pricePlans, meterReadingService);
    }
    @Test
    public void testGetConsumptionCostOfElectricityReadingsForEachPricePlan(){

        Map<String, BigDecimal> costMap = pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan(METER_ID_0).get();

        assertEquals(BigDecimal.valueOf(0.1).stripTrailingZeros(), costMap.get(PLAN_ID_0).stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(1).stripTrailingZeros(), costMap.get(PLAN_ID_1).stripTrailingZeros());
        assertTrue(costMap.get(PLAN_ID_0).compareTo(BigDecimal.valueOf(0.1)) == 0);
        assertTrue(costMap.get(PLAN_ID_1).compareTo(BigDecimal.valueOf(1)) == 0);

        /*assertThat(pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan("smart-meter-0"))
                .isEqualTo(Optional.of(new HashMap<>()));*/
    }
    @Test
    public void givenMeterIdThatDoesNotHaveAnyElectricityReadingsShouldReturnNull() {

        assertThat(pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan("unknown-id")).isEqualTo(Optional.empty());
    }
}
