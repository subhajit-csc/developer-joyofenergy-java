package uk.tw.energy.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PricePlans {

    @JsonValue
    private List<Map.Entry<String, BigDecimal>> pricePlanComparisons;

    public PricePlans(List<Map.Entry<String, BigDecimal>> pricePlanComparisons) {
        this.pricePlanComparisons = pricePlanComparisons;
    }

    public List<Map.Entry<String, BigDecimal>> getPricePlanComparisons() {
        return pricePlanComparisons;
    }

    public void setPricePlanComparisons(List<Map.Entry<String, BigDecimal>> pricePlanComparisons) {
        this.pricePlanComparisons = pricePlanComparisons;
    }
}
