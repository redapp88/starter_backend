package starter.letapp.net.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import starter.letapp.net.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	@Query("select p from Project p where p.owner.username like :username and  p.city like :city and p.categorie like :categorie and (p.title like :keyword or p.description like :keyword) and p.state like :state order by p.creationDate desc ")
	public List<Project> getProjectsByDetails(
			@Param(value="username")String username,
			@Param(value="city")String city,
			@Param(value="categorie")String categorie,
			@Param(value="keyword")String keyword,
			@Param(value="state")String state);

}
