package tute.exmpl.restWebService.SpringTutorial.userService;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// @ApiModel - added with swagger api documentation, for more describe User.
@ApiModel(description="All details about user")
// Adding JPA for picture.
@Entity
public class User {

	/*
	 * @Id and @GeneratedValue coming with JPA.
	 * */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	/**
	 * @Size for validation purpose, if validation failed message will be showed
	 * */
	@Size(min = 2, message = "Name should contains more than 2 letters.")
	@ApiModelProperty(notes="Name should contain at least 2 letters. ")
	private String name;

	/**
	 * @Past for validation purpose
	 * 
	 * @ApiModelProperty to provide additional property details in swagger api doc
	 * */
	@Past
	@ApiModelProperty(notes="Date can not be future.")
	private Date dateOfBirth;
	
	
	/**
	 * 2019-02-04, Adding JPA relationship with Post entity.
	 * */
	@OneToMany(mappedBy="user")
	private List<Post> posts;

	private User() {
		
	}
	
	public User(Integer id, String name, Date dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + "]";
	}

}
