package starter.letapp.net.technicals;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import starter.letapp.net.entities.Profile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
	public String username;
	private String title;
	private String description;
	private String city;
	private String categorie;
	private List<Profile> profiles;
	private String state;
}
