package com.pluralsight.springcloudconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PaymentServiceApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext run =
				SpringApplication.run(PaymentServiceApplication.class, args);

		final MutablePropertySources propertySources =
				run.getEnvironment().getPropertySources();

		int ham = 2
	}

}
