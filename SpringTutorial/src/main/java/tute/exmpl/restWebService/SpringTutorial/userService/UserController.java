package tute.exmpl.restWebService.SpringTutorial.userService;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tute.exmpl.restWebService.SpringTutorial.exception.CustomizedResponseEntityExceptionHandler;

@RestController
public class UserController {

	@Autowired
	private UserDaoService service;

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	/*
	 * @Deprecated
	 * 
	 * @GetMapping("/users/{id}") public User retrieveUser(@PathVariable int id) {
	 * return service.findOne(id); }
	 */
	/**
	 * Step -1- Send the particular User details by id If User not found user not
	 * found exception with 404 status. check
	 * {@link CustomizedResponseEntityExceptionHandler} for more details.
	 * 
	 * Step -2- Introduce HATEOS
	 */
	@GetMapping("/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);

		if (user == null) {
			throw new UserNotFoundException("id :" + id);
		}

		// --------- HATEOS --------
		Resource<User> resourse = new Resource<User>(user);

		/*
		 * ControllerLinkBuilder class used to linked other resources from other methods.
		 * This path better than hard code mapping path [SERVER_PATH + /users] to the method.
		 * If path is changed hard corded all need to be changed. 
		 * */
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resourse.add(linkTo.withRel("all-users")); // link appear under 'all-users'
		// --------- HATEOS --------

		//return user;
		
		/*
		 * Return type changed hereby.
		 * Using HATEOS and return a Resource<User> instead of User.
		 * */
		return resourse;
	}

	/**
	 * Step - 1 - create User
	 */
	/*
	 * @Deprecated
	 * 
	 * @PostMapping("/users") public void createUser(@RequestBody User user) {
	 * service.save(user); }
	 */
	/**
	 * Step - 1 - just saving the user. (@RequestBody User user) used for binding
	 * return type was void
	 * 
	 * Step - 2 - create User We get response with 201 status after created a user.
	 * returned ResponseEntity<Object> and redirect to newly created user.
	 * 
	 * Step - 3 - Adding validation part by @Valid part check
	 * {@link CustomizedResponseEntityExceptionHandler} handleMethodArgumentNotValid
	 * method for validations stuff
	 */
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);

		if (user == null) {
			throw new UserNotFoundException("id :" + id);
		}
	}
}
