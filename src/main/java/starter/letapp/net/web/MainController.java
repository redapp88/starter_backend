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
import starter.letapp.net.technicals.PasswordRequest;
import starter.letapp.net.technicals.ProfileRequest;
import starter.letapp.net.technicals.ProjectRequest;
import starter.letapp.net.technicals.UserRequest;

@RestController
public class MainController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private ProjectsService projectsService;
	@Autowired
	private ProfilesService profilesService;
	@Autowired
	private InterestsService interestsService;

// ************* project function ***************
	@GetMapping("public/project/projects")
	public List<Project> projects(@RequestParam String username, @RequestParam String categorie,
			@RequestParam String city, @RequestParam String keyword,
			@RequestParam String state,@RequestParam (defaultValue = "false")boolean compatibles,@RequestParam (defaultValue = "*")String searcher) {
		return this.projectsService.getProjects(username, city, categorie, keyword, state,compatibles,searcher);
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

	@PutMapping("user/users")
	public List<AppUser> users(@RequestParam String keyword, @RequestParam String city,
			@RequestParam String state, @RequestBody List<Profile> profiles) {
		//System.out.println(profiles.length);
		//return null;
		return this.usersService.getUsers(keyword, city, profiles, state);
	}

	@GetMapping("user/user")
	public AppUser user(@RequestParam String username) {
		return this.usersService.getUser(username);
	}

	@PostMapping("public/user/add")
	public AppUser addUser(@RequestBody UserRequest userRequest) {
		return this.usersService.addUser(userRequest);
	}

	@PutMapping("user/user/edit")
	public AppUser editUser(@RequestParam String username, @RequestBody UserRequest userRequest) {
	//System.out.println(userRequest);
	//return null;
	return this.usersService.editUser(username, userRequest);
	}
	@PutMapping("user/user/editPassword")
	public void editPassword(@RequestParam String username, @RequestBody PasswordRequest passwordRequest) {
		 this.usersService.editPassword(username, passwordRequest);
	}
	@PutMapping("pubic/user/resetPassword")
	public void resetPassword (
	@RequestParam String username,
	@RequestBody PasswordRequest passwordRequest)
	{
	 this.usersService.resetPassword(username,passwordRequest.getPassword());
	}

	// ******** interests function ****************
	@GetMapping("user/interest/interests")
	public List<Interest> interests(@RequestParam String username, @RequestParam String id) {
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
	
	
	// ******** profiles function ****************
	@GetMapping("public/profile/profiles")
	public List<Profile> profiles(@RequestParam String keyword) {
		return this.profilesService.getProfiles(keyword);
	}

	@PostMapping("admin/profile/add")
	public Profile addProfile(@RequestBody ProfileRequest profileRequest) {
		return this.profilesService.addProfile(profileRequest);
	}

	@PutMapping("admin/profile/edit")
	public Profile editProfile(@RequestParam Long id, @RequestBody ProfileRequest profileRequest) {
		return this.profilesService.editProfile(id, profileRequest);
	}

	@DeleteMapping("admin/profile/delete")
	public void deleteProfile(@RequestParam Long id) {
		this.profilesService.deleteProfile(id);
	}

}
