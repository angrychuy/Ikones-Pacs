/**
 * 
 */
package net.inelecom.service.dicom.services;

import java.util.List;
import java.util.Map;

import net.inelecom.service.dicom.model.DicomViewModel;

/**
 * @author JesusEspinoza
 *
 */
public interface DicomQuery {
	
	List<DicomViewModel> query(Map<String, String> matchingKeys, List<String> returnKeys,
								boolean fuzzy, String aet, String host, int port, QueryLevel level);
	
	String contructViewerLink(String studyUID);

}
