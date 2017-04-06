package dbModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import model.Category;
import model.InvalidInputException;

public class CategoryDAO {
	
	private static CategoryDAO instance;
	
	public ConcurrentHashMap<Long, Category> categories;

	private CategoryDAO() {
		categories= new ConcurrentHashMap<>();
		categories= getALlCategories();
	}
	
	public static synchronized CategoryDAO getInstance() {
		if(instance == null) 
			instance = new CategoryDAO();
		return instance;
	}
	
	private ConcurrentHashMap<Long, Category> getALlCategories() {
		ConcurrentHashMap<Long, Category> cats = new ConcurrentHashMap<>();
		try {
			PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement("SELECT  category_id,name,description FROM categories");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				cats.put(rs.getLong("category_id"), new Category(rs.getString("name"), rs.getString("description")));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		
		return cats;
		
	}
	

}
