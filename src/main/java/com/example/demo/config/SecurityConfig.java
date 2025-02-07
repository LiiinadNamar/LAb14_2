package com.example.demo.config;

//import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;


// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// public class SecurityConfig {

//   private final UserRepository userRepository;

//   public SecurityConfig(UserRepository userRepository) {
//       this.userRepository = userRepository;
//   }

//   @Bean
//   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//     // http
//     //   .csrf(csrf -> csrf.disable())
//     http
//     .csrf(csrf -> csrf.ignoringRequestMatchers( "/url1/","/url2/"))
//       .authorizeHttpRequests(auth -> auth
//           .requestMatchers("/auth/**").permitAll() 
//           .anyRequest().authenticated())
      
//       // .formLogin(Customizer.withDefaults())
//       .logout(logout -> logout.invalidateHttpSession(true).deleteCookies("JSESSIONID"));
//     return http.build();
//   }
 
//   @Bean
//   public UserDetailsService userDetailsService() {
//     return username -> userRepository.findByUsername(username)
//         .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//   }
// }




@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final UserRepository userRepository;

  public SecurityConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/auth/**").permitAll()
          .anyRequest().authenticated())
         // .anyRequest().permitAll())
          .formLogin(Customizer.withDefaults())
          .logout(logout -> logout.invalidateHttpSession(true).deleteCookies("JSESSIONID"));
        return http.build();

    // http.csrf(csrf -> csrf.disable()) 
    // .authorizeHttpRequests(auth -> auth
    //     .anyRequest().permitAll() 
    // )
    // .formLogin(Customizer.withDefaults()) 
    // .httpBasic(httpBasic -> httpBasic.disable()) 
    // .logout(logout -> logout.invalidateHttpSession(true).deleteCookies("JSESSIONID"));
    // return http.build();

  }

@Bean
public UserDetailsService userDetailsService() {
    return username -> {
        com.example.demo.model.User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return (UserDetails) user;
    };
}

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}

