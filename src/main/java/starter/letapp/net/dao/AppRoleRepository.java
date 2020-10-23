package starter.letapp.net.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import starter.letapp.net.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

	public AppRole findByRoleName(String string);

}
