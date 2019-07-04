package tute.exmpl.restWebService.SpringTutorial;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class SpringTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTutorialApplication.class, args);
	}

	/**
	 * Step -1
	 * {@link SessionLocaleResolver} has used.
	 * 
	 * Step -2
	 * {@link AcceptHeaderLocaleResolver} used as local resolver. 
	 * because now use acceptHeader
	 * */
	@Bean
	public LocaleResolver localResolver() {
//		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		AcceptHeaderLocaleResolver localResolver = new AcceptHeaderLocaleResolver();
		localResolver.setDefaultLocale(Locale.US);
		return localResolver;
	}

	/*
	 * For access messages_XX.properties files at resources folder
	 * Note : There is a internal messageSource bean also, conflict might happen.
	 * Note : whenever use messageSource() as method name, internal messageSource is override in here, 
	 * 		  this is the cause for run application without any exception
	 * 
	 * This bean can be replaced by application.properties file spring.messages.basename property.
	 * */
	/*
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
	*/
}
