package uk.tw.energy.domain;

import java.math.BigDecimal;
import java.util.Map;

public class PricePlanDetails {

    private String pricePlanId;
    private Map<String, BigDecimal> pricePlanComparisons;

    public PricePlanDetails(String pricePlanId, Map<String, BigDecimal> pricePlanComparisons) {
        this.pricePlanId = pricePlanId;
        this.pricePlanComparisons = pricePlanComparisons;
    }

    public String getPricePlanId() {
        return pricePlanId;
    }

    public void setPricePlanId(String pricePlanId) {
        this.pricePlanId = pricePlanId;
    }

    public Map<String, BigDecimal> getPricePlanComparisons() {
        return pricePlanComparisons;
    }

    public void setPricePlanComparisons(Map<String, BigDecimal> pricePlanComparisons) {
        this.pricePlanComparisons = pricePlanComparisons;
    }
}
