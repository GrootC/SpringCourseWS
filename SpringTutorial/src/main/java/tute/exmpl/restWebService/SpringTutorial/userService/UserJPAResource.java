package tute.exmpl.restWebService.SpringTutorial.userService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

/**
 * 2019-02-04 {@link UserJPAResource} is a copy of {@link UserController} and
 * hereby cover all JPA related user resources.
 */
@RestController
public class UserJPAResource {

	@Autowired
	private UserDaoService service;

	/**
	 * JPA related repo
	 */
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
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
	 * 
	 * JPA Part
	 * -----------
	 * Optional<User> returned, check null by '!user.isPresent()'
	 * Get actual user object by 'user.get()'
	 */
	@GetMapping("/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		/* ##### Change due to JPA #####
		User user = service.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException("id :" + id);
		}
		*/
		Optional<User> user = userRepository.findById(id);
		
		if (!user.isPresent()) {
			throw new UserNotFoundException("id :" + id);
		}

		// --------- HATEOS --------
		 /*  ##### Change due to JPA #####
		  * Resource<User> resourse = new Resource<User>(user); 
		  */
		
		Resource<User> resourse = new Resource<User>(user.get());

		/*
		 * ControllerLinkBuilder class used to linked other resources from other methods.
		 * This path better than hard code mapping path [SERVER_PATH + /users] to the method.
		 * If path is changed hard corded all need to be changed. 
		 * */
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resourse.add(linkTo.withRel("all-users")); // link appear under 'all-users'
		// --------- END HATEOS --------

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
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		/* 	##### Change due to JPA #####
		 * 
		 *	User savedUser = service.save(user);
		 */

		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		/*
		 *  ##### Change due to JPA #####
		 * 
		 * User user = service.deleteById(id);
		 * 
		 * if (user == null) { throw new UserNotFoundException("id :" + id); }
		 */
		
		// if user is not preset exception will be thrown by it self
		userRepository.deleteById(id);
	}
	
	/**
	 * 2019-01-04 : Implementation to get posts for given user id
	 * */
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllPostsByUserId(@PathVariable int id) {
		Optional<User> userOptional =  userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id :" + id);
		}
		
		return userOptional.get().getPosts();
	}
	
	/**
	 * Creation of a post for a user
	 * */
	@PostMapping("/jpa/users/{id}/post")
	public ResponseEntity<Object> createUser(@PathVariable int id , @RequestBody Post post) {
		Optional<User> userOptional =  userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id :" + id);
		}
		
		User user = userOptional.get();
		post.setUser(user);
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
}
