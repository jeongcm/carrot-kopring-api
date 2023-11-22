# kotlin-spring-boot-api

## kotlin-spring-boot-api with oauth2

- build
  - gradle, graalvm
- third-party:
  - postgresql
  - spring-security
  - jjwt
  - oauth2-client
  - lombok
  - jpa
  - amazon sns
 
- oauth2 target
  - google
  - kakao
  - naver   

- appication.properties
  ```
  # security oauth2 config
  spring.security.oauth2.client.registration.google.client-id=
  spring.security.oauth2.client.registration.google.client-secret=
  spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:8080
  spring.security.oauth2.client.registration.google.scope=profile,email
  
