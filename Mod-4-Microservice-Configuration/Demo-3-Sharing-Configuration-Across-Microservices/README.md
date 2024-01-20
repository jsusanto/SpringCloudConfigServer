# spring-cloud-config-3-fundamentals
Module 4: Microservices Configuration

Demo: Sharing Configuration Across Microservices

- Introducing configuration to be shared across multiple services.
- Verifying our configuration with Spring Actuator.

STEP 1: Add the common configuration to enable Spring Actuator endpoint for 
all microservices and all environment by default.

In the spring-cloud repo - add application.yml

#Note: All SpringBoot Actuator will be enabled
management.endpoints.web.exposure.include: '*'

==> Means all springboot endpoint will be enabled.

STEP 2: Start the ConfigCloudServer manually and ensure it's working.

Output
2024-01-20 12:31:34.849  INFO 24996 --- [           main] c.p.s.SpringCloudConfigServerApplication : Starting SpringCloudConfigServerApplication using Java 17.0.4.1 on DESKTOP-DRQBSLJ with PID 24996 (E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-4-Microservice-Configuration\Demo-3-Sharing-Configuration-Across-Microservices\spring-cloud-config-server\build\classes\java\main started by Jeffry in E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-4-Microservice-Configuration\Demo-3-Sharing-Configuration-Across-Microservices)
2024-01-20 12:31:34.854  INFO 24996 --- [           main] c.p.s.SpringCloudConfigServerApplication : No active profile set, falling back to 1 default profile: "default"
2024-01-20 12:31:36.500  INFO 24996 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=796fdece-9afc-341e-942e-76178bcca9a8
2024-01-20 12:31:37.167  INFO 24996 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8888 (http)
2024-01-20 12:31:37.195  INFO 24996 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-01-20 12:31:37.196  INFO 24996 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.69]
2024-01-20 12:31:37.393  INFO 24996 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-01-20 12:31:37.393  INFO 24996 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2448 ms
2024-01-20 12:31:39.200  INFO 24996 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20 12:31:39.789  INFO 24996 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8888 (http) with context path ''
2024-01-20 12:31:41.051  INFO 24996 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20 12:31:41.073  INFO 24996 --- [           main] c.p.s.SpringCloudConfigServerApplication : Started SpringCloudConfigServerApplication in 8.048 seconds (JVM running for 8.618)


STEP 3: Run the PaymentServiceApplication by adding the VM Arguments: 

-Dspring.profiles.active=test

Noticed that the application.yml in the PaymentServiceApplication is specifically
set up (actuator) for env/

management:
    endpoints:
        web:
            exposure:
                include: env

Test using POSTMAN 
[GET] http://localhost:8080/actuator/env/

{
    "activeProfiles": [
        "staging",
        "uk"
    ],
    "propertySources": [
        {
            "name": "server.ports",
            "properties": {
                "local.server.port": {
                    "value": 8080
                }
            }
        },
        {
        
        },
        {
            "name": "configserver:https://github.com/jsusanto/spring-cloud-config-server-repo.git/payment-service-uk.yml",
            "properties": {
                "sales-tax-percentage": {
                    "value": 20,
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/payment-service-uk.yml:1:23"
                }
            }
        },
        {
            "name": "configserver:https://github.com/jsusanto/spring-cloud-config-server-repo.git/payment-service-staging.yml",
            "properties": {
                "feature-toggles.fraud-detection": {
                    "value": true,
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/payment-service-staging.yml:2:20"
                }
            }
        },
        {
            "name": "configserver:https://github.com/jsusanto/spring-cloud-config-server-repo.git/payment-service.yml",
            "properties": {
                "feature-toggles.credit-check": {
                    "value": false,
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/payment-service.yml:2:17"
                },
                "feature-toggles.fraud-detection": {
                    "value": false,
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/payment-service.yml:3:20"
                }
            }
        },
        {
            "name": "configserver:https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml",
            "properties": {
                "management.endpoints.web.exposure.include": {
                    "value": "*",
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml:2:44"
                }
            }
        },
        {
            "name": "configClient",
            "properties": {
                "config.client.version": {
                    "value": "0202566710dc7cdc15414f209b5a0d3a32736fee"
                }
            }
        },
        {
            "name": "Config resource 'class path resource [application.yml]' via location 'optional:classpath:/'",
    


Note:
See on this line

   {
            "name": "configserver:https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml",
            "properties": {
                "management.endpoints.web.exposure.include": {
                    "value": "*",
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml:2:44"
                }
            }
        },
        
Default configuration that's limiting to env/ has been now overriden by application.yml

