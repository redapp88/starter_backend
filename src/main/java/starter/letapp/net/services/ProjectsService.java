package starter.letapp.net.services;

import java.util.List;

import starter.letapp.net.entities.Project;
import starter.letapp.net.technicals.ProjectRequest;

public interface ProjectsService {
public Project addProject(ProjectRequest projectRequest);
public void deleteProject(Long id);
public Project editProject(Long id,ProjectRequest projectRequest);
public Project getProject(Long id);
public List<Project> getProjects(String username, String contry, String city,String categorie, String keyword,String state);
}
