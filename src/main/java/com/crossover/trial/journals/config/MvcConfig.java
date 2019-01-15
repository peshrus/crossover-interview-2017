package com.crossover.trial.journals.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/journals").setViewName("journals");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/publisher/browse").setViewName("publisher/browse");
        registry.addViewController("/403").setViewName("403");
    }

}