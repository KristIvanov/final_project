package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
				InputStream profilePicStream = profilePic.getInputStream();
				String username = (String) ses.getAttribute("username");
				File dir = new File("C:\\travelBook\\usersProfilePics");
				if(!dir.exists()){
					dir.mkdir();
				}
				File profilePicFile = new File(dir, username+"-profile-pic."+ profilePic.getContentType().split("/")[1]);
				Files.copy(profilePicStream, profilePicFile.toPath());
				UsersManager.getInstance().addProfilePic(username, profilePicFile.getAbsolutePath());

			}
	}

}
}
