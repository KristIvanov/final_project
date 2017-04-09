package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.math.NumberUtils;

import dbModel.CategoryDAO;
import dbModel.DBManager;
import managers.PostManager;
import model.Category;
import model.InvalidInputException;

@WebServlet("/addPost")
@MultipartConfig
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String fileName = "index.jsp";
	private static String errorMsg = " ";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("username") != null ) {
			String postName = req.getParameter("postname").trim();
			String username = (String) req.getSession().getAttribute("username");
			String postdescription = req.getParameter("postdescription").trim();
			String categoryName = req.getParameter("category");
			System.out.println(categoryName);
			LocalDateTime date = LocalDateTime.now();
			String destinationName = req.getParameter("destinationname").trim();
			String longitude = req.getParameter("longitude");
			String latitude = req.getParameter("latitude");
			System.out.println(longitude);
			String hashtags = req.getParameter("hashtags").trim();
			String[] keywords = hashtags.split(" ");
			System.out.println(keywords.length);
	        String postPicUrl=null;
			if(req.getPart("photo") != null) {
				Part postPic = req.getPart("photo");//handles data from <input type=file name=photo>
				InputStream postPicStream = postPic.getInputStream();
				File dir = new File("C:\\travelBook\\postsPics");
				if(!dir.exists()){
					dir.mkdir();
				}
				File postPicFile = new File(dir, postName+"-post-pic" + new Random().nextInt(28472) +"."+ postPic.getContentType().split("/")[1]);
				Files.copy(postPicStream, postPicFile.toPath());
				postPicUrl = postPicFile.getAbsolutePath();
			} 
			
			String postVideoUrl=null;
			if(req.getPart("video") != null) {
				Part postVideo = req.getPart("video");
				InputStream postVideoStream = postVideo.getInputStream();
				File dir = new File("C:\\travelbook\\postsVideos");
				if(!dir.exists()) {
					dir.mkdir();
				}
				File postVideoFile = new File(dir,postName+"post-video"+ new Random().nextInt(23994) + "."+postVideo.getContentType().split("/")[1]);
				Files.copy(postVideoStream, postVideoFile.toPath());
				postVideoUrl= postVideoFile.getAbsolutePath();
			}
			//saving old inputs in session to have filled inputs if something was invalid
			req.getSession().setAttribute("postname", postName);
			req.getSession().setAttribute("postdescription", postdescription);
			req.getSession().setAttribute("category", categoryName);
			req.getSession().setAttribute("destinationname", destinationName);
			req.getSession().setAttribute("longitude", longitude);
			req.getSession().setAttribute("latitude", latitude);
			req.getSession().setAttribute("hashtags", hashtags);
	
			
			try {
				this.validateData(postName,postdescription,destinationName,longitude,latitude);
				PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement("SELECT category_id FROM categories WHERE name=?");
				ps.setString(1, categoryName);
				ResultSet rs = ps.executeQuery();
				rs.next();
				Long categoryId = rs.getLong("category_id");
				Category category = CategoryDAO.getInstance().getALlCategoriesByID().get(categoryId);
				PostManager.getInstance().addNewPost(postName, username, postdescription, category, date, destinationName, NumberUtils.toDouble(longitude), NumberUtils.toDouble(latitude),postPicUrl, keywords,postVideoUrl);
				fileName= "index.jsp";
			} catch (InvalidInputException e) {
				fileName= "addPost.jsp";
			} catch (SQLException e) {
				fileName= "addPost.jsp";
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		else {
			fileName = "login.jsp";
		}
		resp.sendRedirect(fileName);
	}

	public static String getErrorMsg() {
		return AddPostServlet.errorMsg;
	}
	
	private  void validateData(String postName, String postDescription, String destinationName,String longitude, String latitude) throws InvalidInputException{
		if(postName == null || postName.isEmpty()) {
			errorMsg = "Invalid post name!";
			throw new InvalidInputException("Invalid post name!");
		}
		if(!checkString(postDescription)) {
			errorMsg = "Invalid post description!";
			throw new InvalidInputException("Invalid post description!");
		}
		if(!checkString(destinationName)) {
			errorMsg = "Invalid destination name!";
			throw new InvalidInputException("Invalid destination name!");
		}
		if(!NumberUtils.isParsable(longitude)) {
			errorMsg = "Invalid longitude!";
			throw new InvalidInputException("Invalid longitude!");
		}
		if(!NumberUtils.isParsable(latitude)) {
			errorMsg = "Invalid latitude!";
			throw new InvalidInputException("Invalid latitude!");
		}
	}
	
	private boolean checkString(String name) {
		if (name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}
	

}