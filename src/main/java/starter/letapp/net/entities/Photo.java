package starter.letapp.net.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Photo implements Serializable {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
Long id;
String url;
String filename;
@Lob
private byte[] image;
public Photo(String url) {
	super();
	this.url = url;

}

}
