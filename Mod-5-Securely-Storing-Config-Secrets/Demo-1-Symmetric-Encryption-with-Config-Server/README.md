# spring-cloud-config-3-fundamentals
Module 5: Securely Storing Config and Secrets

Demo: Symmetric Encryption with the Config Server

Topics:
- Configuring a symmetric key
- Encrypting a secret
- Committing the encrypted secret with cipher notation.
- Use the secret with our payment service to ensure it can be decrypted.

STEP 1. Generating a secure symmetric key in the terminal

Run this in the terminal (bash)

$ openssl rand -base64 32
KMKsAh7SzwXOTG8CJSI942mrbzsZLC0u0cqspBrAYc8=

This is considered secure because it provides many possible keys.
We don't need to necessarily use the OpenSSL tool to generate key.

STEP 2. Set the environment variable in the config server

ENCRYPT_KEY=KMKsAh7SzwXOTG8CJSI942mrbzsZLC0u0cqspBrAYc8=

We set this in the local intelliJ for demo purposes, but in the production you need to store this securely.

STEP 3. Run the SpringCloudConfigConfigurationServer

2024-01-22 11:03:52.314  INFO 23488 --- [           main] c.p.s.SpringCloudConfigServerApplication : Starting SpringCloudConfigServerApplication using Java 17.0.4.1 on DESKTOP-DRQBSLJ with PID 23488 (E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-4-Microservice-Configuration\Demo-4-Using-Property-Dependencies\spring-cloud-config-server\build\classes\java\main started by Jeffry in E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-4-Microservice-Configuration\Demo-4-Using-Property-Dependencies)
2024-01-22 11:03:52.319  INFO 23488 --- [           main] c.p.s.SpringCloudConfigServerApplication : No active profile set, falling back to 1 default profile: "default"
2024-01-22 11:03:53.278  INFO 23488 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=796fdece-9afc-341e-942e-76178bcca9a8
2024-01-22 11:03:53.768  INFO 23488 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8888 (http)
2024-01-22 11:03:53.780  INFO 23488 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-01-22 11:03:53.780  INFO 23488 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.69]
2024-01-22 11:03:53.887  INFO 23488 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-01-22 11:03:53.888  INFO 23488 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1507 ms
2024-01-22 11:03:55.412  INFO 23488 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 11:03:55.859  INFO 23488 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8888 (http) with context path ''
2024-01-22 11:03:57.023  INFO 23488 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 11:03:57.033  INFO 23488 --- [           main] c.p.s.SpringCloudConfigServerApplication : Started SpringCloudConfigServerApplication in 6.628 seconds (JVM running for 7.045)


STEP 4. Test using POSTMAN (Encryption and Decryption)

[POST] http://localhost:8888/encrypt

[raw - text] password

Output: 37b051e4b5154a4371dd20d85b8d0b30f5eb429f6ca7415479dc407289c67547


[POST] http://localhost:8888/decrypt
[raw - text] 37b051e4b5154a4371dd20d85b8d0b30f5eb429f6ca7415479dc407289c67547

Output: password

STEP 5: In the application.yml in the repo, replace the 'password' plain text with the cipher one.

spring:
  datasource:
    url: jdbc:postgresql://${spring.datasource.ext.host}:5432/${spring.datasource.ext.database-name}
    username: postgres
    password: '{cipher}37b051e4b5154a4371dd20d85b8d0b30f5eb429f6ca7415479dc407289c67547'
    
STEP 6. Run the payment service and analyse using actuator

2024-01-22 11:42:51.504  INFO 16344 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect
2024-01-22 11:42:51.671  INFO 16344 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2024-01-22 11:42:51.688  INFO 16344 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-01-22 11:42:51.756  WARN 16344 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2024-01-22 11:42:53.613  INFO 16344 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 11:42:53.646  INFO 16344 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 15 endpoint(s) beneath base path '/actuator'
2024-01-22 11:42:53.711  INFO 16344 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2024-01-22 11:42:54.892  INFO 16344 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 11:42:54.906  INFO 16344 --- [           main] c.p.s.PaymentServiceApplication          : Started PaymentServiceApplication in 16.177 seconds (JVM running for 16.722)


POSTMAN | GET (http://localhost:8080/actuator/env/)

{
    "activeProfiles": [
        "development",
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
      
            "name": "configserver:https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml",
            "properties": {
                "management.endpoints.web.exposure.include": {
                    "value": "*",
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml:1:44"
                },
                "spring.datasource.url": {
                    "value": "jdbc:postgresql://development-host:5432/payments",
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml:5:10"
                },
                "spring.datasource.username": {
                    "value": "postgres",
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml:6:15"
                },
                "spring.jpa.database-platform": {
                    "value": "org.hibernate.dialect.PostgreSQLDialect",
                    "origin": "Config Server https://github.com/jsusanto/spring-cloud-config-server-repo.git/application.yml:9:24"
                },
                "spring.datasource.password": {
                    "value": "******"
                }
            }
        },]
        

Note: the password is now being hidden.

        "spring.datasource.password": {
            "value": "******"
        }