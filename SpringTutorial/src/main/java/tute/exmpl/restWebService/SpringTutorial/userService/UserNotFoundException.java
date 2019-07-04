package tute.exmpl.restWebService.SpringTutorial.userService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import tute.exmpl.restWebService.SpringTutorial.exception.CustomizedResponseEntityExceptionHandler;

/**
 * @ResponseStatus used before implement {@link CustomizedResponseEntityExceptionHandler}
 * Purpose is change status code 500 to 404
 * */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4881260374026099855L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
