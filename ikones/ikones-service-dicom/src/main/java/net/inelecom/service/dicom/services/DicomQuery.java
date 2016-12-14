/**
 * 
 */
package net.inelecom.service.dicom.services;

import java.util.List;
import java.util.Map;

import org.dcm4che2.data.DicomObject;

/**
 * @author JesusEspinoza
 *
 */
public interface DicomQuery {
	
	List<DicomObject> query(Map<String, String> matchingKeys, List<String> returnKeys,
								boolean fuzzy, String aet, String host, int port, QueryLevel level);
	
	String contructViewerLink(String studyUID);

}
