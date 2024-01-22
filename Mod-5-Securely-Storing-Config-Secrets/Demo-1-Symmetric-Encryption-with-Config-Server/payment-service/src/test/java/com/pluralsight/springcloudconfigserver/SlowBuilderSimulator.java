package com.pluralsight.springcloudconfigserver;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SlowBuilderSimulator {
    @Test
    @Disabled
    public void slowBuild() throws InterruptedException {
        System.out.println("Simulating a slow test suite...");
        Thread.sleep(10000);
    }
}
