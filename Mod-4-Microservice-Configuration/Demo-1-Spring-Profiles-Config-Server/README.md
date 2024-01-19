# spring-cloud-config-3-fundamentals
Microservice Configuration
Demo: Spring Profiles with the Config Server
- Implementing a feature toggle using the config server.
- Enable or disable the feature on different environments using Spring profiles.

STEP 1. check the payment-service project 
service/PaymentService.java

There are two methods:
private void detectFraud() {
    if (new Random().nextBoolean()) {
        throw new FraudException();
    }
}

private void creditCheck() {
    if (new Random().nextBoolean()) {
        throw new CreditCheckException();
    }
}

We are going to disable and enable these features.

STEP 2. To create a class configurationproperties/FeatureTogglesConfigurationProperties.java

How we bind properties: @ConfigurationProperties(prefix = "feature-toggles")

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

STEP 3: Go to PaymentServiceApplication class and add annotation @ConfigurationPropertiesScan

//This tells spring to scan classes annotated with configuration properties and binding

@SpringBootApplication
@ConfigurationPropertiesScan  //This to scan 
public class PaymentServiceApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(PaymentServiceApplication.class, args);
	}

}

STEP 4: In the PaymentService.java, we added dependendy injection to enable and disable.

public class PaymentService {
    private final BigDecimal salesTaxPercentage;
    private final FeatureTogglesConfigurationProperties features;

    public PaymentService(
            @Value("${sales-tax-percentage}") final BigDecimal salesTaxPercentage,
            FeatureTogglesConfigurationProperties features) {
        this.salesTaxPercentage = salesTaxPercentage;
        this.features = features;
    }
    
STEP 5. In the git repo - https://github.com/jsusanto/spring-cloud-config-server-repo.git
Branch: m4-demo-1 (payment-service.yml)

sales-tax-percentage: 20

feature-toggles:
  credit-check: false
  fraud-detection: false
  
STEP 6: Under resource/application.yml 

the label set to be the branch that you want to pull.

spring:
    application:
        name: payment-service
    config:
        import: configserver:http://localhost:8888
    cloud:
        config:
            label: m4-demo-1 #This tells to checkout branch: m4-demo-1
            failFast: true

STEP 7. Run the CloudConfigServerApplication and ensure it's running okay.

2024-01-20 09:30:25.386  INFO 27784 --- [           main] c.p.s.SpringCloudConfigServerApplication : No active profile set, falling back to 1 default profile: "default"
2024-01-20 09:30:26.541  INFO 27784 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=796fdece-9afc-341e-942e-76178bcca9a8
2024-01-20 09:30:27.334  INFO 27784 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8888 (http)
2024-01-20 09:30:27.360  INFO 27784 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-01-20 09:30:27.360  INFO 27784 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.69]
2024-01-20 09:30:27.537  INFO 27784 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-01-20 09:30:27.537  INFO 27784 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2086 ms
2024-01-20 09:30:29.118  INFO 27784 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20 09:30:29.600  INFO 27784 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8888 (http) with context path ''
2024-01-20 09:30:30.761  INFO 27784 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20 09:30:30.802  INFO 27784 --- [           main] c.p.s.SpringCloudConfigServerApplication : Started SpringCloudConfigServerApplication in 7.102 seconds (JVM running for 7.58)

STEP 8. Set the VM argument when running PaymentServiceApplication to enable profile: staging

-Dspring.profiles.active=staging

STEP 9. Run the PaymentServiceApplication

Ensure it's picking up: profiles=[staging], label=m4-demo-1

2024-01-20 09:32:29.255  INFO 14588 --- [           main] c.p.s.PaymentServiceApplication          : Starting PaymentServiceApplication using Java 17.0.4.1 on DESKTOP-DRQBSLJ with PID 14588 (E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-4-Microservice-Configuration\Demo-1-Spring-Profiles-Config-Server\payment-service\build\classes\java\main started by Jeffry in E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-4-Microservice-Configuration\Demo-1-Spring-Profiles-Config-Server)
2024-01-20 09:32:29.258  INFO 14588 --- [           main] c.p.s.PaymentServiceApplication          : The following 1 profile is active: "staging"
2024-01-20 09:32:29.299  INFO 14588 --- [           main] o.s.c.c.c.ConfigServerConfigDataLoader   : Fetching config from server at : http://localhost:8888
2024-01-20 09:32:29.299  INFO 14588 --- [           main] o.s.c.c.c.ConfigServerConfigDataLoader   : Located environment: name=payment-service, profiles=[staging], label=m4-demo-1, version=f8938f2032ec50e9208ff63a6537fa28a6d8eace, state=null
2024-01-20 09:32:29.980  INFO 14588 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=61de4969-c3b3-3dd7-82ec-c43b920a416b
2024-01-20 09:32:30.389  INFO 14588 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2024-01-20 09:32:30.397  INFO 14588 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-01-20 09:32:30.397  INFO 14588 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.68]
2024-01-20 09:32:30.536  INFO 14588 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-01-20 09:32:30.536  INFO 14588 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1235 ms
2024-01-20 09:32:32.078  INFO 14588 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20 09:32:32.144  INFO 14588 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2024-01-20 09:32:33.260  INFO 14588 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20 09:32:33.272  INFO 14588 --- [           main] c.p.s.PaymentServiceApplication          : Started PaymentServiceApplication in 8.954 seconds (JVM running for 9.418)

STEP 10. Test using POSTMAN

[GET] http://localhost:8080/payments

[Body - Raw]
{
    "paymentAmount": 100
}

Click couple times and you'll see either

{
    "total": 120
}

or 

{
    "message": "Fraud detected"
}

STEP 11. Set the VM argument when running PaymentServiceApplication to enable profile: staging

-Dspring.profiles.active=development
(Enable credit-check: true, fraud-detection: true)

STEP 12. Test using POSTMAN

[GET] http://localhost:8080/payments

[Body - Raw]
{
    "paymentAmount": 100
}

Click couple times and you'll see either

{
    "total": 120
}

or 

{
    "message": "Credit check failed"
}