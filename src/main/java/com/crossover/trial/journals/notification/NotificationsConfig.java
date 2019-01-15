package com.crossover.trial.journals.notification;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.util.Properties;

@Configuration
@EnableAsync
@EnableScheduling
public class NotificationsConfig {
	@Bean
	public VelocityEngine velocityEngine() {
		final VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();
		final Properties velocityProperties = new Properties();
		velocityProperties.put("resource.loader", "class");
		velocityProperties.put("class.resource.loader.class",
		                       "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngineFactoryBean.setVelocityProperties(velocityProperties);

		return velocityEngineFactoryBean.getObject();
	}
}