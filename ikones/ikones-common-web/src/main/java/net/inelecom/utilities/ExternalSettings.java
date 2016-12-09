/**
 * 
 */
package net.inelecom.utilities;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.omnifaces.cdi.Eager;

/**
 * @author JesusEspinoza
 *
 */
@Named
@Eager
@ApplicationScoped
public class ExternalSettings {
	
	private final String PACS_ADDRESS = "net.inelecom.pacs.address";
	private final String PACS_PORT = "net.inelecom.pacs.port";
	private final String PACS_AETITLE = "net.inelecom.pacs.aetitle";

	private String pacsAddress;
	private int pacsPort;
	private String aeTitle;
	
	public String getPacsAddress() {
		return pacsAddress;
	}
	
	public int getPacsPort() {
		return pacsPort;
	}
	
	public String getAeTitle() {
		return aeTitle;
	}
	
	public ExternalSettings() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void loadSettings() {
		Properties p = new Properties(System.getProperties());
		this.pacsAddress = p.getProperty(PACS_ADDRESS);
		this.pacsPort = Integer.parseInt(p.getProperty(PACS_PORT));
		this.aeTitle = p.getProperty(PACS_AETITLE);
	}

}
