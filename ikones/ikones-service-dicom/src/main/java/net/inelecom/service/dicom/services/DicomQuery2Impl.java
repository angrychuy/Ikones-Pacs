/**
 * 
 */
package net.inelecom.service.dicom.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.net.ConfigurationException;
import org.dcm4che2.tool.dcmqr.DcmQR;

import net.inelecom.ikones.common.CompareUtil;


/**
 * @author JesusEspinoza
 *
 */
@ApplicationScoped
public class DicomQuery2Impl implements DicomQuery {

	/**
	 * 
	 */
	public DicomQuery2Impl() {
	}
	
	@PostConstruct
	public void init() {
		System.out.println("Starting dicom query service");
	}
	
	@Override
	public List<DicomObject> query(Map<String, String> matchingKeys, List<String> returnKeys, boolean fuzzy,
			String aet, String host, int port, QueryLevel level) {
		
		if(CompareUtil.isEmpty(matchingKeys) 
    			|| CompareUtil.isEmpty(returnKeys) 
    			|| CompareUtil.isEmpty(aet) 
    			|| CompareUtil.isEmpty(host))
    		throw new IllegalArgumentException("Parameters cannot be null");
		
		final DcmQR dcmqr = new DcmQR("INELECOM");
		dcmqr.setCalledAET(aet, true);
		dcmqr.setRemoteHost(host);
		dcmqr.setRemotePort(port);
		dcmqr.setConnectTimeout(10);
		
		if(level == QueryLevel.STUDY)
			dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.STUDY);
		else if(level == QueryLevel.SERIES)
			dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.SERIES);
		else
			//Other levels not implemented yet so default to study
			dcmqr.setQueryLevel(DcmQR.QueryRetrieveLevel.STUDY);
		
		dcmqr.setCFind(true);
		
		for(Map.Entry<String, String> entry : matchingKeys.entrySet()){
			dcmqr.addMatchingKey(Tag.toTagPath(entry.getKey()), entry.getValue());
		}
		
		for(String entry : returnKeys){
			dcmqr.addReturnKey(Tag.toTagPath(entry));
		}
		
		dcmqr.configureTransferCapability(true);
		dcmqr.setFuzzySemanticPersonNameMatching(fuzzy);
		dcmqr.setRelationQR(true);
		
		List<DicomObject> result = null;
		
		try {
			dcmqr.start();
			dcmqr.open();
			result = dcmqr.query();
			dcmqr.close();
		} catch (IOException e){
			
		} catch (InterruptedException e) {
			
		} catch (ConfigurationException e) {
			
		} finally {
			dcmqr.stop();
		}
		
		return result;
	}
	
	@Override
	public String contructViewerLink(String studyUID) {
		return null;
	}

}
