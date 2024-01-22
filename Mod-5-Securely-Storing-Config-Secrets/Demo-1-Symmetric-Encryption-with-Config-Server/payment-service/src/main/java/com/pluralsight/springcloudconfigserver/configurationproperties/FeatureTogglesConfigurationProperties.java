package com.pluralsight.springcloudconfigserver.configurationproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "feature-toggles")
public class FeatureTogglesConfigurationProperties {
    private boolean creditCheck;
    private boolean fraudDetection;

    public boolean isCreditCheck() {
        return creditCheck;
    }

    public void setCreditCheck(boolean creditCheck) {
        this.creditCheck = creditCheck;
    }

    public boolean isFraudDetection() {
        return fraudDetection;
    }

    public void setFraudDetection(boolean fraudDetection) {
        this.fraudDetection = fraudDetection;
    }
}
