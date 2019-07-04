package com.cs.microservices.limitsservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
// This bean will pick up properties declare with "limits-service" prefix
@ConfigurationProperties("limits-service")
public class Configuration {
	
	private int maximum;
	private int minimum;
	
	
	/*
	 * Make sure about setters.
	 * */
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	public int getMaximum() {
		return maximum;
	}
	public int getMinimum() {
		return minimum;
	}
	
}
