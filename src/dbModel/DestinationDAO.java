package dbModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.Destination;
import model.InvalidInputException;
import model.Location;

public class DestinationDAO {
	
	private static DestinationDAO instance;
	
	private DestinationDAO() {
	}
	
	public static synchronized DestinationDAO getInstance() {
		if(instance == null) 
			instance = new DestinationDAO();
		return instance;
	}
	
	public synchronized Set<Destination> getDestinations() throws SQLException {
		
		HashSet<Destination> destinations = new HashSet<>();
		Connection con = DBManager.getInstance().getConnection();
		try {
			con.setAutoCommit(false);
			PreparedStatement destinationPs = con.prepareStatement("SELECT destination_id,name,description,fk_location_id FROM destinations");
			ResultSet destinationRS = destinationPs.executeQuery();
			while(destinationRS.next()) {
				PreparedStatement locationST = con.prepareStatement("SELECT longtitude, latitude FROM locations WHERE location_id = ?");
				locationST.setLong(1, destinationRS.getLong("fk_location_id"));
				ResultSet locationRs = locationST.executeQuery();
				destinations.add(new Destination
										(destinationRS.getString("name"), 
										new Location(locationRs.getDouble("latitude"),
										locationRs.getDouble("longtitude"))));
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Collections.unmodifiableSet(destinations);

	}
	

}
