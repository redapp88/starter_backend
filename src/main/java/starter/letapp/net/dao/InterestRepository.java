package starter.letapp.net.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import starter.letapp.net.entities.Interest;

public interface InterestRepository extends JpaRepository<Interest, String> {

	public List<Interest> getByUserAndProject(String username, Long id);

}
