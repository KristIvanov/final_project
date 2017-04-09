package dbModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import model.Category;
import model.InvalidInputException;

public class CategoryDAO {
	
	private static CategoryDAO instance;
	
	private ConcurrentHashMap<Long, Category> categories;

	private CategoryDAO() {
		categories= new ConcurrentHashMap<>();
		try {
			PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement("SELECT  category_id,name,description FROM categories");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				categories.put(rs.getLong("category_id"), new Category(rs.getString("name"), rs.getString("description")));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		
	}
	
	public static synchronized CategoryDAO getInstance() {
		if(instance == null) 
			instance = new CategoryDAO();
		return instance;
	}
	
	public Map<Long, Category> getALlCategoriesByID() {
		return Collections.unmodifiableMap(categories);
	}
	public ArrayList<Category> getCategories() {
		ArrayList<Category> categories = new ArrayList<>();
		for(Entry<Long, Category> entry: this.categories.entrySet()) {
			categories.add(entry.getValue());
		}
		return categories;
	}

}
