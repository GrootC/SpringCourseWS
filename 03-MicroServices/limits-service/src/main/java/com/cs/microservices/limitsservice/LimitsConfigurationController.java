package com.cs.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.microservices.limitsservice.bean.LimitConfiguration;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	Configuration config;

	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		
//		return new LimitConfiguration(1000, 1);
		
		return new LimitConfiguration(config.getMaximum(), config.getMinimum());
	}
}
