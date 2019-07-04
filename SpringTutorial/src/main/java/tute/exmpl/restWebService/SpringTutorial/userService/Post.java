package tute.exmpl.restWebService.SpringTutorial.userService;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String description;
	
	/**
	 * Fetch Type marked as LAZY for avoid recursive.
	 * Now need to explicitly call post.getUser to retrive user.Unless you call it won't retrive.
	 * 
	 * @JsonIgnore added to prevent the Infinite recursion. 
	 * */
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User user;

	public Post() {
	
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Note that do not include user object into toString method.
	 * Recursive might be happend and run into out of memory.
	 * */
	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}
	
}
