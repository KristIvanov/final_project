package managers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import dbModel.DestinationDAO;
import model.Destination;

public class DestinationManager {
	
private static DestinationManager instance;
private static HashMap<Long,Destination> allDestinations; //destinationID-> destination

	
	private DestinationManager() {
		allDestinations = new HashMap<>();
		try {
			for(Destination d : DestinationDAO.getInstance().getDestinations()) {
				allDestinations.put(d.getDestination_id(), d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static synchronized DestinationManager getInstance() {
		if(instance == null) 
			instance = new DestinationManager();
		return instance;
	}
	
	public Map<Long, Destination> getDestinations() {
		return Collections.unmodifiableMap(allDestinations);
	}
	
	

}
