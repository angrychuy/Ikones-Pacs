/**
 * 
 */
package net.inelecom.service.dicom.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.net.ConfigurationException;
import org.dcm4che2.tool.dcmqr.DcmQR;

import net.inelecom.ikones.common.CompareUtil;
import net.inelecom.service.dicom.model.DicomViewModel;


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
	public List<DicomViewModel> query(Map<String, String> matchingKeys, List<String> returnKeys, boolean fuzzy,
			String aet, String host, int port, QueryLevel level) {
		List<DicomViewModel> resultSet = null;
		
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
		
		if(!CompareUtil.isEmpty(result)) {
			resultSet = mapDicomObjectToViewModel(result);
		}
				
		return resultSet;
	}
	
	@Override
	public String contructViewerLink(String studyUID) {
		return null;
	}
	
	private List<DicomViewModel> mapDicomObjectToViewModel(final List<DicomObject> studies) {
		final List<DicomViewModel> result = new ArrayList<DicomViewModel>();
		
		for (DicomObject dicom : studies) {
			DicomViewModel data = new DicomViewModel();
			data.setPatient(dicom.getString(Tag.PatientName).replace("^", " "));
			data.setStudyDescription(dicom.getString(Tag.StudyDescription));
			data.setStudyIUID(dicom.getString(Tag.StudyInstanceUID));
			data.setStudyDate(dicom.getDate(Tag.StudyDate));
			data.setBirthdate(dicom.getDate(Tag.PatientBirthDate));
			String[] modalities = dicom.getStrings(Tag.ModalitiesInStudy);
			if(modalities.length > 1) {
				StringBuilder builder = new StringBuilder();
				for(String mod : modalities) {
					builder.append(mod);
					builder.append("/");
				}
				data.setModality(builder.toString().substring(0, builder.toString().length() - 1));
			} else {
				data.setModality(dicom.getString(Tag.ModalitiesInStudy));
			}
			
			result.add(data);
		}
		
		return result;
	}

}
