package com.botiga.com_botiga.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductCondition {
    NOU("nou"),
    BON_ESTAT("bon estat"),
    ACCEPTABLE("acceptable"),
    MAL_ESTAT("mal estat");

    private final String displayName;

    ProductCondition(String displayName) {
        this.displayName = displayName;
    }




    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static ProductCondition fromValue(String value) {
        for (ProductCondition pc : ProductCondition.values()) {
            if (pc.displayName.equalsIgnoreCase(value)) {
                return pc;
            }
        }
        throw new IllegalArgumentException("Invalid ProductCondition: " + value);
    }
}