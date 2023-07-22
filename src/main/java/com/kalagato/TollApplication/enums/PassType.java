package com.kalagato.TollApplication.enums;

import java.time.Duration;

public enum PassType {
    SINGLE(Duration.ZERO),
    RETURN(Duration.ofDays(1)),
    SEVEN_DAY(Duration.ofDays(7));

    private final Duration durationAddOn;

    PassType(Duration durationAddOn) {
        this.durationAddOn = durationAddOn;
    }

    public Duration getDurationAddOn(){
        return durationAddOn;
    }
}
