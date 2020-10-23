package starter.letapp.net.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import starter.letapp.net.dao.ProjectRepository;
import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.Project;
import starter.letapp.net.technicals.ProjectRequest;
@Service
public class ProjectsServiceImp implements ProjectsService {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UsersService usersService;

	@Override
	public Project addProject(ProjectRequest projectRequest) {
	
		AppUser user = this.usersService.getUser(projectRequest.getUsername());
		Project project = new Project(projectRequest.getTitle(), projectRequest.getDescription(),projectRequest.getContry(), projectRequest.getCity(),projectRequest.getCategorie(),user,
				projectRequest.getProfiles());
		return this.projectRepository.save(project);
	}

	@Override
	public void deleteProject(Long id) {
		Project project=this.getProject(id);
		this.projectRepository.delete(project);

	}

	@Override
	public Project editProject(Long id, ProjectRequest projectRequest) {
		Project project=this.getProject(id);
		project.setTitle(projectRequest.getTitle());
		project.setDescription(projectRequest.getDescription());
		project.setProfiles(projectRequest.getProfiles());
		return this.projectRepository.save(project);
	}

	@Override
	public Project getProject(Long id) {
		Optional<Project> projectOpt = this.projectRepository.findById(id);
		if (!projectOpt.isPresent())
			throw new RuntimeException("Projet Introuvable");
		return projectOpt.get();
	}

	@Override
	public List<Project> getProjects(String username,String contry, String city, String categorie, String keyword,
			String state) {
	if(keyword.equals("*"))
		keyword="%%";
	if(city.equals("*"))
		city="%%";
	if(contry.equals("*"))
		contry="%%";
	if(categorie.equals("*"))
		categorie="%%";
	return this.projectRepository.getProjectsByDetails(username,contry,city,categorie,keyword,state);
	}

}
