package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.ElectricityReadings;
import uk.tw.energy.domain.MeterReadings;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeterReadingServiceImpl implements MeterReadingService{

    private final Map<String, List<ElectricityReading>> meterAssociatedReadings;

    public MeterReadingServiceImpl(Map<String, List<ElectricityReading>> meterAssociatedReadings) {
        this.meterAssociatedReadings = meterAssociatedReadings;
    }

    @Override
    public Optional<ElectricityReadings> getReadings(String smartMeterId) {
        return Optional.ofNullable(new ElectricityReadings(meterAssociatedReadings.get(smartMeterId)));
    }

    @Override
    public MeterReadings storeReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
        if (!meterAssociatedReadings.containsKey(smartMeterId)) {
            meterAssociatedReadings.put(smartMeterId, new ArrayList<>());
        }
        meterAssociatedReadings.get(smartMeterId).addAll(electricityReadings);

        return new MeterReadings(smartMeterId,meterAssociatedReadings.get(smartMeterId));
    }

    @Override
    public Optional<List<ElectricityReading>> getPrevNaturalWeekReadings(String smartMeterId) {
        Instant lastWeekFrom = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() + 6).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant lastWeekTo = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1).atStartOfDay().toInstant(ZoneOffset.UTC);
        return Optional.of(meterAssociatedReadings.get(smartMeterId).stream()
                .filter(reading -> reading.getTime().isAfter(lastWeekFrom) && reading.getTime().isBefore(lastWeekTo)).collect(Collectors.toList()));
    }
}
