package uk.tw.energy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.tw.energy.domain.PricePlanDetails;
import uk.tw.energy.domain.PricePlans;
import uk.tw.energy.service.AccountService;
import uk.tw.energy.service.PricePlanService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/price-plans")
public class PricePlanComparatorController {

    private final PricePlanService pricePlanService;
    private final AccountService accountService;

    public PricePlanComparatorController(PricePlanService pricePlanService, AccountService accountService) {
        this.pricePlanService = pricePlanService;
        this.accountService = accountService;
    }

    @GetMapping("/compare-all/{smartMeterId}")
    public ResponseEntity<PricePlanDetails> calculatedCostForEachPricePlan(@PathVariable String smartMeterId) {
        String pricePlanId = accountService.getPricePlanIdForSmartMeterId(smartMeterId);
        Optional<Map<String, BigDecimal>> consumptionsForPricePlans =
                pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan(smartMeterId);

        if (!consumptionsForPricePlans.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        PricePlanDetails pricePlanDetails=new PricePlanDetails(pricePlanId,consumptionsForPricePlans.get());


        return pricePlanDetails !=null
                ? ResponseEntity.ok(pricePlanDetails)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/recommend/{smartMeterId}")
    public ResponseEntity<PricePlans> recommendCheapestPricePlans(@PathVariable String smartMeterId,
                                                                  @RequestParam(value = "limit", required = false) Integer limit) {
        Optional<Map<String, BigDecimal>> consumptionsForPricePlans =
                pricePlanService.getConsumptionCostOfElectricityReadingsForEachPricePlan(smartMeterId);

        if (!consumptionsForPricePlans.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        /*Map<String, BigDecimal> recommendPricePlans =  new HashMap<>();

        consumptionsForPricePlans.get()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .limit(limit !=null ? limit : consumptionsForPricePlans.get().size())
                //.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new));
                .forEach(entry -> recommendPricePlans.put(entry.getKey(), entry.getValue()));*/

        List<Map.Entry<String, BigDecimal>> recommendations = new ArrayList<>(consumptionsForPricePlans.get().entrySet());
        recommendations.sort(Comparator.comparing(Map.Entry::getValue));

        if (limit != null && limit < recommendations.size()) {
            recommendations = recommendations.subList(0, limit);
        }

        return ResponseEntity.ok(new PricePlans(recommendations));
    }
    @GetMapping("/prev-week-cost/{smartMeterId}")
    public ResponseEntity<PricePlanDetails> calculatedPrevNaturalWeekCostForEachPricePlan(String smartMeterId) {

        return ResponseEntity.notFound().build();
    }
}
