package tute.exmpl.restWebService.SpringTutorial.helloService;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSourcers;

	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}

	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}

	/**
	 * Step -1
	 * @RequestHeader is used for bind {@link Locale} instant
	 * helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale)
	 * 
	 * Step -2
	 * No need to always use locale instant, Replaced it with {@link LocaleContextHolder}
	 * */
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized() {
//		return messageSourcers.getMessage("good.morning.message", null, locale);
		return messageSourcers.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}