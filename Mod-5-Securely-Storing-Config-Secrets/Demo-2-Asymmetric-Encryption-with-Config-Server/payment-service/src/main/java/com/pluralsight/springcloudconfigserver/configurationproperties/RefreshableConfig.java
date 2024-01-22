package com.pluralsight.springcloudconfigserver.configurationproperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RefreshScope
public class RefreshableConfig {
    @Value("${sales-tax-percentage}")
    private BigDecimal salesTaxPercentage;

    public BigDecimal getSalesTaxPercentage() {
        return salesTaxPercentage;
    }

    public void setSalesTaxPercentage(BigDecimal salesTaxPercentage) {
        this.salesTaxPercentage = salesTaxPercentage;
    }
}
