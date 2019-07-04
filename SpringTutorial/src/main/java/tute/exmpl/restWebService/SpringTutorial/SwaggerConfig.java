package tute.exmpl.restWebService.SpringTutorial;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//Mention as Configuration
@Configuration
//Enable Swagger
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("User@123", "http://www.usereRest.com", "user@123.co");
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("User Rest Tilte", "User Description", "1.0", "urn:tos",
			DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

	public static final Set<String> PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json","application/xml"));
	
	// Bean - Docket
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.produces(PRODUCES_AND_CONSUMES)
				.consumes(PRODUCES_AND_CONSUMES);
	}

}
