package starter.letapp.net.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable{
@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
@NonNull
private String title;
@NonNull
private String description;
@NonNull
private String city;
@NonNull
private String categorie;
@NonNull
private Date creationDate;
@ManyToMany
private List<Profile> profiles=new ArrayList<Profile>();
@OneToMany
private List<Interest> interests=new ArrayList<Interest>();
@ManyToOne
@NonNull
private AppUser owner;
@NonNull
private String state;
public Project(@NonNull String title, @NonNull String description, @NonNull String city ,String categorie,@NonNull AppUser owner,List<Profile> profiles) {
	super();
	this.title = title;
	this.description = description;
	this.categorie=categorie;
	this.city = city;
	this.profiles=profiles;
	this.owner=owner;
	this.creationDate=new Date();
	this.state="active";
}




}
