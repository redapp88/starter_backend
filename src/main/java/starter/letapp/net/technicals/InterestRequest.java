package starter.letapp.net.technicals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestRequest {
	private String  username;
	private Long id;
	private String state;
	private String message;
	private String direction;
}
