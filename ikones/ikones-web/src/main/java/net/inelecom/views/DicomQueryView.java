/**
 * 
 */
package net.inelecom.views;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.inelecom.ikones.common.CompareUtil;
import net.inelecom.service.dicom.model.DicomViewModel;
import net.inelecom.service.dicom.services.DicomQuery;
import net.inelecom.service.dicom.services.QueryLevel;
import net.inelecom.utilities.ExternalSettings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JesusEspinoza
 *
 */
@Named
@SessionScoped
public class DicomQueryView implements Serializable {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 4535844112674644400L;
	
	@Inject
	private DicomQuery queryService;
	
	@Inject
	private ExternalSettings settings;
	
	private String patientId;
	private String patientName;
	private Date startDate;
	private Date endDate;
	private List<DicomViewModel> model;
	
	public String getPatientId() {
		return patientId;
	}

	public String getPatientName() {
		return patientName;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public List<DicomViewModel> getModel() {
		return model;
	}
	
	public void setModel(List<DicomViewModel> model) {
		this.model = model;
	}
	

	/**
	 * 
	 */
	public DicomQueryView() {
		// TODO Auto-generated constructor stub
	}
	
	public void searchStudies() {
		LOG.info("Starting dicom search");
		List<DicomObject> dicom = queryService.query(createRequestKeys(), 
													createReturnKeys(), 
													true, 
													settings.getAeTitle(), 
													settings.getPacsAddress(), 
													settings.getPacsPort(),
													QueryLevel.STUDY);
		
		model = mapDicomObjectToViewModel(dicom);
		
	}
	
	private List<String> createReturnKeys() {
		List<String> returnKeys = new ArrayList<String>();
		returnKeys.add("StudyDescription");
		returnKeys.add("StudyID");
		returnKeys.add("StudyInstanceUID");
		returnKeys.add("ModalitiesInStudy");
		returnKeys.add("PatientBirthDate");
		if(CompareUtil.isEmpty(startDate)&& CompareUtil.isEmpty(endDate))
			returnKeys.add("StudyDate");
		if(CompareUtil.isEmpty(patientName))
			returnKeys.add("PatientName");
		return returnKeys;
	}
	
	private Map<String, String> createRequestKeys() {
		Map<String, String> request = new HashMap<String, String>();
		if(!CompareUtil.isEmpty(patientName))
			request.put("PatientName", "*" + patientName + "*");
		else
			request.put("PatientName", "*");
		
		if(!CompareUtil.isEmpty(startDate) || !CompareUtil.isEmpty(endDate))
			request.put("StudyDate", 
				DateUtils.formatDA(startDate) 
				+ "-" + 
				DateUtils.formatDA(endDate));
		
		return request;
	}
	
	private List<DicomViewModel> mapDicomObjectToViewModel(final List<DicomObject> studies) {
		final List<DicomViewModel> result = new ArrayList<DicomViewModel>();

		if(!CompareUtil.isEmpty(studies)) {
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
		}
		
		return result;
	}

}
