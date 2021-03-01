package co.uk.golunch.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Restaurant extends AbstractBaseEntity {
    private Map<String, BigDecimal> menu;
    private Set<Integer> votes = new HashSet<>();
}
