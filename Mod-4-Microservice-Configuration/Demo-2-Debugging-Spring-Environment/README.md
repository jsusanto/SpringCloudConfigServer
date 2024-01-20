# spring-cloud-config-3-fundamentals
Module 2: Microservices Configuration

Demo: Debugging the Spring Environment

STEP 1: Go to payment-service project and add dependency

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.cloud:spring-cloud-starter-config'
	implementation 'org.springframework.retry:spring-retry'
	implementation 'org.springframework.boot:spring-boot-starter-actuator'

	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

implementation 'org.springframework.boot:spring-boot-starter-actuator'
To enable bunch of different end points to monitor the application and debug.

STEP 2: Enable the end point in the configuration.
payment-service/resources/application.yml

management:
    endpoints:
        web:
            exposure:
                include: env
                
STEP 3: Run the SpringCloudConfigServerApplication

Output
 :: Spring Boot ::                (v2.7.6)

2024-01-20 10:46:38.756  INFO 33344 --- [           main] c.p.s.SpringCloudConfigServerApplication : Starting SpringCloudConfigServerApplication using Java 17.0.4.1 on DESKTOP-DRQBSLJ with PID 33344 (E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-4-Microservice-Configuration\Demo-2-Debugging-Spring-Environment\spring-cloud-config-server\build\classes\java\main started by Jeffry in E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-4-Microservice-Configuration\Demo-2-Debugging-Spring-Environment)
2024-01-20 10:46:38.760  INFO 33344 --- [           main] c.p.s.SpringCloudConfigServerApplication : No active profile set, falling back to 1 default profile: "default"
2024-01-20 10:46:40.050  INFO 33344 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=796fdece-9afc-341e-942e-76178bcca9a8
2024-01-20 10:46:40.605  INFO 33344 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8888 (http)
2024-01-20 10:46:40.619  INFO 33344 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-01-20 10:46:40.619  INFO 33344 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.69]
2024-01-20 10:46:40.778  INFO 33344 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-01-20 10:46:40.778  INFO 33344 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1938 ms
2024-01-20 10:46:42.424  INFO 33344 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20 10:46:42.902  INFO 33344 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8888 (http) with context path ''
2024-01-20 10:46:44.053  INFO 33344 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-20 10:46:44.073  INFO 33344 --- [           main] c.p.s.SpringCloudConfigServerApplication : Started SpringCloudConfigServerApplication in 7.024 seconds (JVM running for 7.557)


STEP 4: Go to payment-service + run and change the profile to staging,uk

-Dspring.profiles.active=staging,uk

STEP 5: Test using POSTMAN 

[GET] http://localhost:8080/actuator/env

[GET] http://localhost:8080/actuator/env/feature-toggles.fraud-detection

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
            "name": "servletContextInitParams",
            "properties": {}
        },
        {
            "name": "systemProperties",
            "properties": {
                "java.specification.version": {
                    "value": "17"
                },
                "sun.cpu.isalist": {
                    "value": "amd64"
                },
                "sun.jnu.encoding": {
                    "value": "Cp1252"
                },
                "java.class.path": {
                    "value": "E:\\RMIT\\Pluralsight\\SpringCloudConfigServer\\Mod-4-Microservice-Configuration\\Demo-2-Debugging-Spring-Environmen
                    
