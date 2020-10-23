package starter.letapp.net.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Profile {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
@NonNull
private String label;
public Profile(@NonNull String label) {
	super();
	this.label = label;
}

}
