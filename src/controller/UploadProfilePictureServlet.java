package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import managers.UsersManager;

@WebServlet("/uploadProfilePicture")
@MultipartConfig
public class UploadProfilePictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession();
		if(ses.getAttribute("logged")!= null){
			boolean logged = (Boolean) ses.getAttribute("logged");
			if(logged){
				
				Part profilePic = request.getPart("photo");//handles data from <input type=file name=photo>
				String extension = request.getParameter("extension");
				System.out.println(request.getParameter("extension"));
				InputStream profilePicStream = profilePic.getInputStream();
				String username = (String) ses.getAttribute("username");
				File dir = new File("C:\\travelBook\\usersProfilePics");
				if(!dir.exists()){
					dir.mkdir();
				}
				if(UsersManager.getInstance().getRegisteredUsers().get(username).getPhotoURL()!=null) {
					File usersPicture = new File(UsersManager.getInstance().getRegisteredUsers().get(username).getPhotoURL());
					usersPicture.delete();
				}
				File profilePicFile = new File(dir, username+"-profile-pic."+extension);
				Files.copy(profilePicStream, profilePicFile.toPath());
				UsersManager.getInstance().addProfilePic(username, profilePicFile.getAbsolutePath());
				response.setStatus(200);
				response.getWriter().append("Picture successfully uploaded!");
				//TODO figure out why the ajax is not working as it supposed to be?!?!?

			}
	}

}
}
