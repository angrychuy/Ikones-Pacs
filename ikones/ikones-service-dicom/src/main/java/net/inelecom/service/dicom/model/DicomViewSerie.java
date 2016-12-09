/**
 * 
 */
package net.inelecom.service.dicom.model;

import java.util.Date;

/**
 * @author JesusEspinoza
 *
 */
public class DicomViewSerie {
	/**
	 * Fields
	 */
	private Date seriesDate;
	private Date seriesTime;
	private String modality;
	private String description;
	private String seriesIUID;
	private String seriesNumber;
	private String institutionName;
	private String bodyPartExamined;
	
	/**
	 * Accessors
	 */
	public Date getSeriesDate() {
		return seriesDate;
	}
	public Date getSeriesTime() {
		return seriesTime;
	}
	public String getModality() {
		return modality;
	}
	public String getDescription() {
		return description;
	}
	public String getSeriesIUID() {
		return seriesIUID;
	}
	public String getSeriesNumber() {
		return seriesNumber;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public String getBodyPartExamined() {
		return bodyPartExamined;
	}
	public void setSeriesDate(Date seriesDate) {
		this.seriesDate = seriesDate;
	}
	public void setSeriesTime(Date seriesTime) {
		this.seriesTime = seriesTime;
	}
	public void setModality(String modality) {
		this.modality = modality;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSeriesIUID(String seriesIUID) {
		this.seriesIUID = seriesIUID;
	}
	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public void setBodyPartExamined(String bodyPartExamined) {
		this.bodyPartExamined = bodyPartExamined;
	}
	
}
