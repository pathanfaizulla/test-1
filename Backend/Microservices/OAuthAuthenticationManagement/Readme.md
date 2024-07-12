# External OAuth flow

Registering any external OAuth2 provider. Following is the example of creating a flow for Google. This can be applied for other providers such as Github, LinkedIn, Facebook, and many more.
The pattern which we follow is `http://oauth.insigniaconsultancy.com/login/oauth2/code/{provider}`

Eg. for Google is `http://localhost:8082/login/oauth2/code/google`

## Dependencies
Use start.spring.io to find the dependency declarations
1. Sprign security
    ```xml
    <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    ```
2. Oauth 2 client
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    ```
## Configure the external provider in application.properties/yml
_Pre-requisite: Create Google OAuth credentials inorder to get the client-id & client-secret_
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: GEENRATED_GOOGLE_API_CLIENT_ID
            client-secret: GEENRATED_GOOGLE_API_CLIENT_SECRET
            redirect-uri : https://oauth.insigniaconsultancy.com:8082/login/oauth2/code/google
```

## Wire in the OAuth client into spring security

1. Create or update the class annotated with `@EnableWebSecurity`
2. add a request matcher as ``"/login"`` as permit all to allow spring to allow this request mapping
3. add a request match as ``"/oauth2/**""`` as this is what we have configured in the `redirect-uri`
4. The oauth code should look like
```java
class WebSecurityConfig {
   private CustomOAuthUserService customOAuthService;
   
   // 
   private CustomerBasicDetailsService customerBasicDetailsService;
   
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
      // request handlers to be placed above
      
      // oauth config
      httpSecurity.oauth2Login(
              oauth2Customizer -> oauth2Customizer
                      .loginPage("/login")
                      .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuthService))
                      .successHandler(new AuthenticationSuccessHandler() {
                          @Override
                          public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
                                  throws IOException, ServletException {
                              CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                              // store this log-in the db
                             // if this exits then skip otherwise update if required
                              customerBasicDetailsService.processOAuthPostLogin(oauthUser.getEmail());
                              // this redirection goes to oauth.insigniaconsultancy.com/jwt/authenticate to create an custom enty
                              response.sendRedirect("/authenticate");
                          }
                      })
      ); 
   }
   
   // other methods
}
```

## Storing the user

> Once the user logs in to the OAuth provider, we would be getting the auth token in the request
> Store this information in the `customer_basic_details` or `tokens_table` table.

##  Create OAuth User & Service (Optional)
1. Create `CustomOAuthUser.java` to wrap the provider given user
2. Create `CustomOAuthUserService.java` to load the OAuth user with required details
3. Create or update `CustomerBasicDetailsService.java` to create or update the external login

## Points to remember;
1. OAuth2 providers will share only the basic information such as email ands scopes
2. The token which gets shared can be used for expiry, but we use our own expiry mechanism.
3. Make sure to update the expiry in the external provider
4. Make sure to logout of our application after the expiry only.
5. **DONOT** logout of the external provider. They will issue a refresh token.

***

## Test with Single account, Multiple Login.

## contact: neyas@insigniaconsultancy.com for clarifications.