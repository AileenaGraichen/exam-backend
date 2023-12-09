package dat3.security.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.SecurityContext;
import dat3.security.error.CustomOAuth2AccessDeniedHandler;
import dat3.security.error.CustomOAuth2AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

@Configuration
public class SecurityConfig {

  //Remove default value below BEFORE deployment
  @Value("${app.secret-key}")
  private String tokenSecret;

  @Autowired
  CorsConfigurationSource corsConfigurationSource;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
    http
            .cors(Customizer.withDefaults()) //Will use the CorsConfigurationSource bean declared in CorsConfig.java
            .csrf(csrf -> csrf.disable())  //We can disable csrf, since we are using token based authentication, not cookie based
            .httpBasic(Customizer.withDefaults())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer((oauth2ResourceServer) ->
                    oauth2ResourceServer
                            .jwt((jwt) -> jwt.decoder(jwtDecoder())
                                    .jwtAuthenticationConverter(authenticationConverter())
                            )
                            .authenticationEntryPoint(new CustomOAuth2AuthenticationEntryPoint())
                            .accessDeniedHandler(new CustomOAuth2AccessDeniedHandler()));
    
    http.authorizeHttpRequests((authorize) -> authorize
            //SecurityControllers
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/auth/login")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/user-with-role")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PATCH, "/api/user-with-role")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/user-with-role")).hasAuthority("ADMIN")//Only Admin can access the accounts to the site

            //LocationControllers
            //Get endpoints usable by all authorized users
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/location")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PATCH, "/api/location/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/location/{id}")).hasAuthority("ADMIN")

            //UnitController
            //Get endpoints usable by all authorized users
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/unit")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PATCH, "/api/unit/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/unit/{id}")).hasAuthority("ADMIN")

            //OwnerController
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/owner")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/owner/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/owner/search/{search}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/owner/byMobile")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/owner")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PATCH, "/api/owner/{id}")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/owner/{id}")).hasAuthority("ADMIN")

            //MaintenanceTaskController
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/maintenance-task")).hasAnyAuthority("ADMIN", "TECH")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/maintenance-task/location/{locationId}")).hasAnyAuthority("ADMIN", "TECH")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/maintenance-task")).hasAnyAuthority("ADMIN", "TECH")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PATCH, "/api/maintenance-task")).hasAnyAuthority("ADMIN", "TECH")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/maintenance-task")).hasAuthority("ADMIN")

            //CleaningPlanController
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/cleaning")).hasAnyAuthority("ADMIN", "CLEAN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/cleaning/pageable")).hasAnyAuthority("ADMIN", "CLEAN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/cleaning/user/{username}")).hasAnyAuthority("ADMIN", "CLEAN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/cleaning/unit/{unitId}")).hasAnyAuthority("ADMIN", "CLEAN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/cleaning/{username}/{date}")).hasAnyAuthority("ADMIN", "CLEAN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/cleaning")).hasAuthority("ADMIN")
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/api/cleaning")).hasAuthority("ADMIN")


            //Allow index.html and everything else on root level. So make sure to put ALL your endpoints under /api
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET,"/*")).permitAll()

            .requestMatchers(mvcMatcherBuilder.pattern("/error")).permitAll()


            //Use this to completely disable security (Will not work if endpoints has been marked with @PreAuthorize)
            //.requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll());
            .anyRequest().authenticated());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationConverter authenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

  @Bean
  public SecretKey secretKey() {
    return new SecretKeySpec(tokenSecret.getBytes(), "HmacSHA256");
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withSecretKey(secretKey()).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(
            new ImmutableSecret<SecurityContext>(secretKey())
    );
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
          throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
