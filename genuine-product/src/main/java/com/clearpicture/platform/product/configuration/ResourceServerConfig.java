package com.clearpicture.platform.product.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


/**
 * ResourceServerConfig
 * Created by nuwan on 7/21/18.
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ConfigProperties configs;

    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) {
        //configurer.resourceId(configs.getAuth().getResourceId());
                //.tokenServices(tokenServices())
                //.tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()

                .antMatchers("/ps.html").permitAll()
                .antMatchers("/**").permitAll();
    }

}
