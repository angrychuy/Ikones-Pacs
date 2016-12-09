/**
 * 
 */
package net.inelecom.services;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.ServletException;

import org.omnifaces.util.Faces;

/**
 * @author JesusEspinoza
 *
 */
@Named
@SessionScoped
public class LoginService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1570121134992092243L;

	/**
	 * 
	 */
	public LoginService() {
		// TODO Auto-generated constructor stub
	}
	
	public void logout() {
		try {
			Faces.logout();
			Faces.redirect("/ikones-web");
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
