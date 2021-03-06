package starter.letapp.net.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
	@Autowired
	private InterestsService interestsService;
	@Autowired
	private Environment environment;
	@Override
	public Project addProject(ProjectRequest projectRequest) {
		
		AppUser user = this.usersService.getUser(projectRequest.getUsername());
		this.verifyMaxProject(user.getUsername());
		Project project = new Project(projectRequest.getTitle(), projectRequest.getDescription(), projectRequest.getCity(),projectRequest.getCategorie(),user,
				projectRequest.getProfiles());
		project=this.projectRepository.save(project);
		//user.getProjects().add(project);
		return project;
	}

	@Override
	public void deleteProject(Long id) {
		Project project=this.getProject(id);
		this.interestsService.getInterests("*", String.valueOf(project.getId())).forEach(i->{
			this.interestsService.deleteInterest(i.getId());
		});;
		this.projectRepository.delete(project);

	}

	@Override
	public Project editProject(Long id, ProjectRequest projectRequest) {
		Project project=this.getProject(id);
		if(!project.getState().equals("active") && projectRequest.getState().equals("active"))
			this.verifyMaxProject(projectRequest.getUsername());
		project.setTitle(projectRequest.getTitle());
		project.setDescription(projectRequest.getDescription());
		project.setCity(projectRequest.getCity());
		project.setProfiles(projectRequest.getProfiles());
		project.setCategorie(projectRequest.getCategorie());
		project.setState(projectRequest.getState());
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
	public List<Project> getProjects(String username, String city, String categorie, String keyword, String state,
			boolean compatibles,String searcher) {

		keyword = "%" + keyword + "%";
		if (username.equals("*"))
			username = "%%";
		if (city.equals("*"))
			city = "%%";

		if (categorie.equals("*"))
			categorie = "%%";
		if (state.equals("*"))
			state = "%%";
		
		if (!searcher.equals("*") && compatibles == true) {
			System.out.println("*****"+compatibles);
			return this.compatibleProjects(
					this.projectRepository.getProjectsByDetails(username, city, categorie, keyword, state),
					this.usersService.getUser(searcher));

		} else {
			System.out.println(username);
			System.out.println(city);
			System.out.println(categorie);
			System.out.println(keyword);
			System.out.println(state);
			return this.projectRepository.getProjectsByDetails(username, city, categorie, keyword, state);
		}
	}

	@Override
	public Project save(Project project) {
return this.projectRepository.save(project);
	}
	private List<Project> compatibleProjects(List<Project> projects,AppUser user){
		List<Project> pjts=new ArrayList<Project>();
		projects.forEach(p->{
			if(!CollectionUtils.intersection(p.getProfiles(), user.getProfiles()).isEmpty())
				pjts.add(p);
			
		});
		return pjts;
	}
@Override
	public void verifyMaxProject(String username) {
		System.out.println(this.getProjects(username, "*", "*", "", "active", false, "*").size());

		if(this.getProjects(username, "*", "*", "", "active", false, "*").size() > new Integer(environment.getProperty("my.maxprojects")).intValue())
			throw new RuntimeException("Vous avez atteins le maximum de projets actifs desactiver ou supprimer un projet pour pouvoir ajouter un autre");
		
	}
}
