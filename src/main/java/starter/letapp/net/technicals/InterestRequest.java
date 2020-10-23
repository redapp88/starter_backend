package starter.letapp.net.technicals;

import lombok.Data;
import lombok.NonNull;
@Data
public class InterestRequest {
	private String  username;
	private Long id;
	private String state;
	private String message;
	private String direction;
}
