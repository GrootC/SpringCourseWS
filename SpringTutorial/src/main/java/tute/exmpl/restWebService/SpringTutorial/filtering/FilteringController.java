package tute.exmpl.restWebService.SpringTutorial.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	/**
	 * STEP-1- return a SomeBean as 'return new
	 * SomeBean("value1","value2","value3");'
	 * 
	 * STEP-2- Assume only we need to send field1 and field2
	 * Used @MappingJacksonValue, @FilterProvider, @SimpleBeanPropertyFilter make an
	 * filter. We have mentioned our filter name as 'SomeBeanFilter'. At @SomeBean
	 * class level '@JsonFilter(value = "SomeBeanFilter")' annotation used.
	 */
	@GetMapping("/filtering")
	public MappingJacksonValue retriveSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");

		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);

		return mapping;
	}

	/**
	 * STEP-1- return a list of somebean
	 * 
	 * STEP-2- Assume only we need to send field2 and field3 in list 
	 * */
	@GetMapping("/filtering-list")
	public MappingJacksonValue retriveListOfSomeBean() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),
				new SomeBean("value12", "value22", "value32"));

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");

		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(list);
		mapping.setFilters(filters);
		
		return mapping;
	}
}
