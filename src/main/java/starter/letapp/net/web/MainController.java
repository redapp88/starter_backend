package starter.letapp.net.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.Interest;
import starter.letapp.net.entities.Profile;
import starter.letapp.net.entities.Project;
import starter.letapp.net.services.InterestsService;
import starter.letapp.net.services.ProfilesService;
import starter.letapp.net.services.ProjectsService;
import starter.letapp.net.services.UsersService;
import starter.letapp.net.technicals.InterestRequest;
import starter.letapp.net.technicals.ProjectRequest;
import starter.letapp.net.technicals.UserRequest;

@RestController
public class MainController {

	@Autowired
	private UsersService UsersService;
	@Autowired
	private ProjectsService projectsService;
	@Autowired
	private ProfilesService profilesService;
	@Autowired
	private InterestsService interestsService;

// ************* project function ***************
	@GetMapping("/projects")
	public List<Project> projects(@RequestParam String username, @RequestParam String categorie,
			@RequestParam String contry, @RequestParam String city, @RequestParam String keyword,
			@RequestParam String state) {
		return this.projectsService.getProjects(username, contry, city, categorie, keyword, state);
	}

	@GetMapping("public/project")
	public Project project(@RequestParam Long id) {
		return this.projectsService.getProject(id);
	}

	@PostMapping("/user/project/add")
	public Project addProject(@RequestBody ProjectRequest projectRequest) {
		return this.projectsService.addProject(projectRequest);
	}

	@PutMapping("/user/project/edit")
	public Project editProject(@RequestParam Long id, @RequestBody ProjectRequest projectRequest) {
		return this.projectsService.editProject(id, projectRequest);
	}

	@DeleteMapping("/user/project/delete")
	public void deleteProject(@RequestParam Long id) {
		this.projectsService.deleteProject(id);
	}

	// ************** user functions *************

	@GetMapping("public/users")
	public List<AppUser> users(@RequestParam String keyword, @RequestParam String contry, @RequestParam String city,
			@RequestParam String state, @RequestParam List<Profile> profiles) {
		return this.UsersService.getUsers(keyword,contry, city, profiles, state);
	}

	@GetMapping("public/user")
	public AppUser user(@RequestParam String username) {
		return this.UsersService.getUser(username);
	}

	@PostMapping("user/addUser")
	public AppUser addUser(@RequestBody UserRequest userRequest) {
		return this.UsersService.addUser(userRequest);
	}

	@PutMapping("user/editUser")
	public AppUser editUser(@RequestParam String username, @RequestBody UserRequest userRequest) {
		return this.UsersService.editUser(username, userRequest);
	}

	// ******** interests function ****************
	@GetMapping("user/interest/interests")
	public List<Interest> interests(@RequestParam String username, @RequestParam Long id) {
		return this.interestsService.getInterests(username, id);
	}

	@PostMapping("user/interest/add")
	public Interest addInterest(@RequestBody InterestRequest interestRequest) {
		return this.interestsService.addInterest(interestRequest);
	}

	@PutMapping("user/interest/edit")
	public Interest editInterest(@RequestParam String id, @RequestBody InterestRequest interestRequest) {
		return this.interestsService.editInterest(id, interestRequest);
	}

	@DeleteMapping("user/interest/delete")
	public void deleteInterest(@RequestParam String id) {
		this.interestsService.deleteInterest(id);
	}

}
