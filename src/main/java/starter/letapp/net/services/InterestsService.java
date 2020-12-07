package starter.letapp.net.services;

import java.util.List;

import starter.letapp.net.entities.Interest;
import starter.letapp.net.technicals.InterestRequest;

public interface InterestsService {
public Interest addInterest(InterestRequest interestRequest);
public void deleteInterest(String id);
public Interest editInterest(String id,InterestRequest interestRequest);
public Interest getInterest(String id);
public List<Interest> getInterests(String username, String id);
}
