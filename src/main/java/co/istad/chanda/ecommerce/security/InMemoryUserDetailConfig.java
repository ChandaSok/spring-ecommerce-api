package co.istad.chanda.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUserDetailConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager configureUserDetails() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        //
        UserDetails customerUser = User.withUsername("customer")
                .password(passwordEncoder().encode("istad123"))
                .roles("CUSTOMER")
                .build();

        UserDetails staffUser = User.withUsername("staff")
                .password(passwordEncoder().encode("istad123"))
                .roles("STAFF")
                .build();

        UserDetails adminUser = User.withUsername("admin")
                .password(passwordEncoder().encode("istad123"))
                .roles("ADMIN")
                .build();


        manager.createUser(customerUser);
        manager.createUser(staffUser);
        manager.createUser(adminUser);

        return manager;
    }

}
