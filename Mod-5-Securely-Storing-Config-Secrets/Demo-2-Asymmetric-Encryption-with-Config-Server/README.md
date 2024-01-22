# spring-cloud-config-3-fundamentals
Module 5: Securely Storing Config and Secrets

Demo: Asymmetric Encryption with the Config Server

Topics:
- Creating a self-signed symmetric key pair.
- Configuring the key-pair.
- Encrypting a secret.
- Decrypting a secret.
- Configuring only a public key.

STEP 1. Use the keytool as part of the JDK.
It's used to manage key certificates and key stores.

Jeffry@DESKTOP-DRQBSLJ MINGW64 /e/RMIT/Pluralsight/SpringCloudConfigServer (main)
$ keytool -genkeypair -alias mytestkey -keyalg RSA -dname "CN=config,OU=author,O=Pluralsight,L=Melbourne,S=VIC,C=Australia" -keystore config-server.jks -storepass password
Generating 2,048 bit RSA key pair and self-signed certificate (SHA256withRSA) with a validity of 90 days
        for: CN=config, OU=author, O=Pluralsight, L=Melbourne, ST=VIC, C=Australia

-genkeypair : tells keytool to generate a new key pair.
-alias : specifies the name of the key.
-keyalg : specifies the algorithm that we'll use to encrypt the key.
-dname : specifies the distinguished name of the entity the key represents. e.g a company
-keystore : specifies the name of the keystore file to be created.
-storepass : specifies the password to be used to protect the keystore.

Jeffry@DESKTOP-DRQBSLJ MINGW64 /e/RMIT/Pluralsight/SpringCloudConfigServer (main)
$ ls config-server.jks
config-server.jks

Jeffry@DESKTOP-DRQBSLJ MINGW64 /e/RMIT/Pluralsight/SpringCloudConfigServer (main)
$ keytool -list -v -keystore config-server.jks
Enter keystore password:  password
Keystore type: PKCS12
Keystore provider: SUN

Your keystore contains 1 entry

Alias name: mytestkey
Creation date: 22 Jan 2024
Entry type: PrivateKeyEntry
Certificate chain length: 1
Certificate[1]:
Owner: CN=config, OU=author, O=Pluralsight, L=Melbourne, ST=VIC, C=Australia
Issuer: CN=config, OU=author, O=Pluralsight, L=Melbourne, ST=VIC, C=Australia
Serial number: a74c9b02b5651633
Valid from: Mon Jan 22 14:47:24 AEDT 2024 until: Sun Apr 21 13:47:24 AEST 2024
Certificate fingerprints:
         SHA1: BB:20:88:F6:65:96:BC:19:16:7A:92:08:C7:38:94:96:60:B5:38:C2
         SHA256: 95:99:88:DF:16:68:AE:25:EA:14:72:9E:E1:B0:8C:DD:CC:4D:A0:58:28:18:27:0E:29:D1:CB:5D:77:FB:56:A3
Signature algorithm name: SHA256withRSA
Subject Public Key Algorithm: 2048-bit RSA key
Version: 3

Extensions:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: F5 E9 D3 B3 35 52 20 9C   10 CD 6A 7C 64 43 18 8E  ....5R ...j.dC..
0010: CB FB D4 4A                                        ...J
]
]



*******************************************
*******************************************

STEP 2. To store the keystore in the secure directory

In this demo, we create secure-folder/

STEP 3. Add the config in the resource/application.yml in the SpringCloudConfigServer application 

server.port: 8888
spring.cloud.config.server.git.uri: https://github.com/jsusanto/spring-cloud-config-server-repo.git

encrypt:
  key-store:
    location: file:/E:/RMIT/Pluralsight/SpringCloudConfigServer/secure-directory/config-server.jks
    alias: mytestkey

STEP 4. Add environment variable before running SpringCloudConfigServer application.

ENCRYPT_KEY-STORE_PASSWORD=password

2024-01-22 15:15:44.018  INFO 5252 --- [           main] c.p.s.SpringCloudConfigServerApplication : Starting SpringCloudConfigServerApplication using Java 17.0.4.1 on DESKTOP-DRQBSLJ with PID 5252 (E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-5-Securely-Storing-Config-Secrets\Demo-2-Asymmetric-Encryption-with-Config-Server\spring-cloud-config-server\build\classes\java\main started by Jeffry in E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-5-Securely-Storing-Config-Secrets\Demo-2-Asymmetric-Encryption-with-Config-Server)
2024-01-22 15:15:44.025  INFO 5252 --- [           main] c.p.s.SpringCloudConfigServerApplication : No active profile set, falling back to 1 default profile: "default"
2024-01-22 15:15:45.465  INFO 5252 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=58184e1f-a449-3b78-9655-f903ef3a5fc1
2024-01-22 15:15:46.116  INFO 5252 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8888 (http)
2024-01-22 15:15:46.130  INFO 5252 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-01-22 15:15:46.130  INFO 5252 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.69]
2024-01-22 15:15:46.306  INFO 5252 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-01-22 15:15:46.306  INFO 5252 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2181 ms
2024-01-22 15:15:48.120  INFO 5252 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 15:15:48.726  INFO 5252 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8888 (http) with context path ''
2024-01-22 15:15:49.935  INFO 5252 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 15:15:49.973  INFO 5252 --- [           main] c.p.s.SpringCloudConfigServerApplication : Started SpringCloudConfigServerApplication in 8.192 seconds (JVM running for 8.851)


STEP 4. Use POSTMAN to test encryption/decryption password.

ENCRYPT
=======
http://localhost:8888/encrypt

Method: POST
[Body - Text] password 

AQCW++iTKQGtiuz50IQvOvS35rRwl8qyG2zgVmaferRxCwB7SiaOr0M3mT8sc24QF4+Mit+U85px+sCW+3YnCavIGp0HSmcTzLe/5gc7Xq2tL30jieBsySDo2H77oORElQgKhJXxqOhvOLbvYrqRkiFQNv3s6kmQQGv1CfTZI1tk4bjJlMNs5pbUYAKCUBZFGmMTSqYt9GgEXwaGekq6tQadpCp2OXHshy2zyGg6k+lxVCOTKXHXjqfEiUGwiOs+nuWGEokttyrvIMUWcjOgnEQA311qHv4I1hNYi9zb9Fzp/QTxe5goc43yhuOFVBMCfIXW9HtCF6hA5Y0epDhDPPI8TkMmTNcfOYvmv41dMvwUYQpYxTgiGOvbwnHQiwTGWFo=

DECRYPT
=======
http://localhost:8888/decrypt

Method: POST
[Body - Text] AQCW++iTKQGtiuz50IQvOvS35rRwl8qyG2zgVmaferRxCwB7SiaOr0M3mT8sc24QF4+Mit+U85px+sCW+3YnCavIGp0HSmcTzLe/5gc7Xq2tL30jieBsySDo2H77oORElQgKhJXxqOhvOLbvYrqRkiFQNv3s6kmQQGv1CfTZI1tk4bjJlMNs5pbUYAKCUBZFGmMTSqYt9GgEXwaGekq6tQadpCp2OXHshy2zyGg6k+lxVCOTKXHXjqfEiUGwiOs+nuWGEokttyrvIMUWcjOgnEQA311qHv4I1hNYi9zb9Fzp/QTxe5goc43yhuOFVBMCfIXW9HtCF6hA5Y0epDhDPPI8TkMmTNcfOYvmv41dMvwUYQpYxTgiGOvbwnHQiwTGWFo=

Output: password

STEP 5. Use the keytool to export the public key from our keystore.

Jeffry@DESKTOP-DRQBSLJ MINGW64 /e/RMIT/Pluralsight/SpringCloudConfigServer/secure-directory (main)
$ keytool -export -alias mytestkey -file mytestkey.cer -keystore config-server.jks
Enter keystore password:  password
Certificate stored in file <mytestkey.cer>


STEP 6. To import public key into a new keystore

Jeffry@DESKTOP-DRQBSLJ MINGW64 /e/RMIT/Pluralsight/SpringCloudConfigServer/secure-directory (main)
$ keytool -importcert -file mytestkey.cer -keystore public-config-server.jks
Enter keystore password:  password
Re-enter new password: password
Owner: CN=config, OU=author, O=Pluralsight, L=Melbourne, ST=VIC, C=Australia
Issuer: CN=config, OU=author, O=Pluralsight, L=Melbourne, ST=VIC, C=Australia
Serial number: a74c9b02b5651633
Valid from: Mon Jan 22 14:47:24 AEDT 2024 until: Sun Apr 21 13:47:24 AEST 2024
Certificate fingerprints:
         SHA1: BB:20:88:F6:65:96:BC:19:16:7A:92:08:C7:38:94:96:60:B5:38:C2
         SHA256: 95:99:88:DF:16:68:AE:25:EA:14:72:9E:E1:B0:8C:DD:CC:4D:A0:58:28:18:27:0E:29:D1:CB:5D:77:FB:56:A3
Signature algorithm name: SHA256withRSA
Subject Public Key Algorithm: 2048-bit RSA key
Version: 3

Extensions:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: F5 E9 D3 B3 35 52 20 9C   10 CD 6A 7C 64 43 18 8E  ....5R ...j.dC..
0010: CB FB D4 4A                                        ...J
]
]

Trust this certificate? [no]:  yes
Certificate was added to keystore

Jeffry@DESKTOP-DRQBSLJ MINGW64 /e/RMIT/Pluralsight/SpringCloudConfigServer/secure-directory (main)
$ ls
config-server.jks  mytestkey.cer  public-config-server.jks

Why have we done this? because public key can be safety shared in the network.

Only the production server would have the secret key to decrypt the encryption made by the public key.

STEP 7. Move this public key (public-config-server.jks) into the spring-cloud-config-server resources/ 

Under the spring-cloud-config-server
            src/main/resources/application.yml
            src/main/resources/public-config-server.jks

STEP 8. Update the config in the spring-cloud-config-server - application.yml

server.port: 8888
spring.cloud.config.server.git.uri: https://github.com/jsusanto/spring-cloud-config-server-repo.git

encrypt:
  key-store:
    location: classpath:/public-config-server.jks
    alias: mykey
    
======================================================

To check the alias name of  public-config-server.jks

Jeffry@DESKTOP-DRQBSLJ MINGW64 /e/RMIT/Pluralsight/SpringCloudConfigServer/Mod-5-Securely-Storing-Config-Secrets/Demo-2-Asymmetric-Encryption-with-Config-Server/spring-cloud-config-server/src/main/resources (main)
$ keytool -list -v -keystore public-config-server.jks
Enter keystore password:  password
Keystore type: PKCS12
Keystore provider: SUN

Your keystore contains 1 entry

Alias name: mykey
Creation date: 22 Jan 2024
Entry type: trustedCertEntry

Owner: CN=config, OU=author, O=Pluralsight, L=Melbourne, ST=VIC, C=Australia
Issuer: CN=config, OU=author, O=Pluralsight, L=Melbourne, ST=VIC, C=Australia
Serial number: a74c9b02b5651633
Valid from: Mon Jan 22 14:47:24 AEDT 2024 until: Sun Apr 21 13:47:24 AEST 2024
Certificate fingerprints:
         SHA1: BB:20:88:F6:65:96:BC:19:16:7A:92:08:C7:38:94:96:60:B5:38:C2
         SHA256: 95:99:88:DF:16:68:AE:25:EA:14:72:9E:E1:B0:8C:DD:CC:4D:A0:58:28:18:27:0E:29:D1:CB:5D:77:FB:56:A3
Signature algorithm name: SHA256withRSA
Subject Public Key Algorithm: 2048-bit RSA key
Version: 3

Extensions:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: F5 E9 D3 B3 35 52 20 9C   10 CD 6A 7C 64 43 18 8E  ....5R ...j.dC..
0010: CB FB D4 4A                                        ...J
]
]



*******************************************
*******************************************

STEP 9: Delete the keystore environment password because we're no longer encypting using private key
update application.yml 

server.port: 8888
spring.cloud.config.server.git.uri: https://github.com/jsusanto/spring-cloud-config-server-repo.git

encrypt:
  key-store:
    location: classpath:/public-config-server.jks
    alias: mykey
    password: password

STEP 10. Run the SpringCloudConfigServer

2024-01-22 15:31:49.163  INFO 8796 --- [           main] c.p.s.SpringCloudConfigServerApplication : Starting SpringCloudConfigServerApplication using Java 17.0.4.1 on DESKTOP-DRQBSLJ with PID 8796 (E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-5-Securely-Storing-Config-Secrets\Demo-2-Asymmetric-Encryption-with-Config-Server\spring-cloud-config-server\build\classes\java\main started by Jeffry in E:\RMIT\Pluralsight\SpringCloudConfigServer\Mod-5-Securely-Storing-Config-Secrets\Demo-2-Asymmetric-Encryption-with-Config-Server)
2024-01-22 15:31:49.168  INFO 8796 --- [           main] c.p.s.SpringCloudConfigServerApplication : No active profile set, falling back to 1 default profile: "default"
2024-01-22 15:31:50.856  INFO 8796 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=58184e1f-a449-3b78-9655-f903ef3a5fc1
2024-01-22 15:31:51.631  INFO 8796 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8888 (http)
2024-01-22 15:31:51.650  INFO 8796 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-01-22 15:31:51.651  INFO 8796 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.69]
2024-01-22 15:31:51.893  INFO 8796 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-01-22 15:31:51.894  INFO 8796 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2611 ms
2024-01-22 15:31:53.771  INFO 8796 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 15:31:54.433  INFO 8796 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8888 (http) with context path ''
2024-01-22 15:31:55.627  INFO 8796 --- [           main] o.s.cloud.commons.util.InetUtils         : Cannot determine local hostname
2024-01-22 15:31:55.645  INFO 8796 --- [           main] c.p.s.SpringCloudConfigServerApplication : Started SpringCloudConfigServerApplication in 8.862 seconds (JVM running for 9.473)

STEP 11. Use POSTMAN to test encryption/decryption

ENCRYPT
=======
http://localhost:8888/encrypt
[POST - BODY] password 

Expected output:
AQCwECrET5sKCmUeaPCb0nZLAne2G4GT764Qx2wCrfd6CHxfqjDXq99ryhkVD6SH6ERG5N2F+0RR/Z/kaJvs5iTVs6paGb3YWlOvwQQ4BXUsy6bZcdJLW/M7kyVSnTKGpi7ZKckCbnTjDgeiXd4C9S/WiB8LqmVdgrmJqBh+lNmYdFknAKpo5kjvPfUWyvrmTGyAMO4QnnFrR6J+kYOw8HqE7+SqIoM/p0DlVsnp9db641GaGHbx/GkZco1n4Wl1hFFDvLtTTet3eMdO6HjTc10fRQKaMQbu5aML36eBJoWbmTz+W+yzCUw2u+2buFu2EULASnK6ETxk8XyiZVwOz4pBNxdeCvcrqnUmPaPsMhecu5C+gpfdulcg+1Q/XWDn/Nk=

DECRYPT
=======
http://localhost:8888/decrypt
[POST - BODY] password 


Output:
{
    "description": "Server-side decryption is not supported",
    "status": "BAD_REQUEST"
}

Summary: Now we know that developer can encrypt the text but can't decrypt due to using public key only
to decrypt the text and developer doesn't have any private key.