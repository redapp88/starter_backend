package starter.letapp.net.technicals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import starter.letapp.net.entities.Interest;
import starter.letapp.net.entities.Profile;
import starter.letapp.net.entities.Project;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private String username;
	private String password;
	private String name;
	private String mail;
	private String description;
	private String phone;
	private String city;
	private List<Project> projects;
	private List<Profile> profiles;
}