package uk.tw.energy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public class ElectricityReadings {

    @JsonValue
    List<ElectricityReading> electricityReadings;

    public ElectricityReadings(List<ElectricityReading> electricityReadings) {
        this.electricityReadings = electricityReadings;
    }

    public List<ElectricityReading> getElectricityReadings() {
        return electricityReadings;
    }

    public void setElectricityReadings(List<ElectricityReading> electricityReadings) {
        this.electricityReadings = electricityReadings;
    }
}
