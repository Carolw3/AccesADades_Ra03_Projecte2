package com.botiga.com_botiga.model;

public enum ProductCondition {
    NOU("nou"),
    BON_ESTAT("bon estat"),
    ACCEPTABLE("acceptable"),
    MAL_ESTAT("mal estat");

    private final String displayName;

    ProductCondition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}