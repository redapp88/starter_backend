package starter.letapp.net.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import starter.letapp.net.entities.Interest;

public interface InterestRepository extends JpaRepository<Interest, String> {
	@Query("select i from Interest i where i.user.username like :username and CAST(i.project.id as string) like :id order by i.creationDate desc")
	public List<Interest> getByUserAndProject(@Param(value="username") String username,@Param(value="id") String id);

}
