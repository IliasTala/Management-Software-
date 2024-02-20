package com.example.helbelectro;

public enum Optimization {
    PRICE("Prix"),
    DURATION("Durée de fabrication"),
    ECO_SCORE("Éco-score"),
    DIVERSITY("Diversité");

    private String displayName;

    Optimization(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

