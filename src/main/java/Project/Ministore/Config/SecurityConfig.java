//package Project.Ministore.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//
//@Configuration
//public class SecurityConfig {
//
//        @Autowired
//        private AuthenticationSuccessHandler authenticationSuccessHandler;
//
//        @Autowired
//        @Lazy
//        private AuthFailureHandlerImpl authenticationFailureHandler;
//
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        @Bean
//        public UserDetailsService userDetailsService() {
//            return new UserDetailsServiceImpl();
//        }
//
//        @Bean
//        public DaoAuthenticationProvider authenticationProvider() {
//            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//            authenticationProvider.setUserDetailsService(userDetailsService());
//            authenticationProvider.setPasswordEncoder(passwordEncoder());
//            return authenticationProvider;
//        }
//
//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
//        {
//            http.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
//                    .authorizeHttpRequests(req->req.requestMatchers("/user/**").hasRole("USER")
//                            .requestMatchers("/admin/**").hasRole("ADMIN")
//                            .requestMatchers("/**").permitAll())
//                    .formLogin(form->form.loginPage("/signin")
//                            .loginProcessingUrl("/login")
////						.defaultSuccessUrl("/")
//                            .failureHandler(authenticationFailureHandler)
//                            .successHandler(authenticationSuccessHandler))
//                    .logout(logout->logout.permitAll());
//
//            return http.build();
//        }
//
//    }
//
