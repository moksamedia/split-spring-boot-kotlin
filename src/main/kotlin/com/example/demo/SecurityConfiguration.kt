package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    // Disable CORS and enable HTTP Basic
    override fun configure(http: HttpSecurity) {
        http
            .cors().and().csrf().disable()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .httpBasic();
    }

    // Create two users: admin and user
    @Bean
    fun users(): UserDetailsService {
        val user = User.builder()
            .username("user")
            .password("{noop}user")
            .roles("USER")
            .build()
        val admin = User.builder()
            .username("admin")
            .password("{noop}admin")
            .roles("USER", "ADMIN")
            .build()
        val test = User.builder()
            .username("test")
            .password("{noop}test")
            .roles("USER", "TEST")
            .build()
        return InMemoryUserDetailsManager(user, admin, test)
    }

}
