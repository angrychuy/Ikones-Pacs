/**
 * 
 */
package net.inelecom.service.dicom.model;

import java.util.Date;
import java.util.List;

/**
 * @author Jesus Espinoza
 *
 */
public class DicomViewModel {
	/**
	 * Fields
	 */
	private String patient;
	private String studyIUID;
	private String osirix;
	private String studyDescription;
	private String weasis;
	private Date studyDate;
	private String modality;
	private Date birthdate;
	private List<DicomViewSerie> series;
	
	/**
	 * Accessors
	 */
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getStudyIUID() {
		return studyIUID;
	}
	public void setStudyIUID(String studyIUID) {
		this.studyIUID = studyIUID;
	}
	public String getOsirix() {
		return osirix;
	}
	public void setOsirix(String osirix) {
		this.osirix = osirix;
	}
	public String getStudyDescription() {
		return studyDescription;
	}
	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}
	public String getWeasis() {
		return weasis;
	}
	public void setWeasis(String weasis) {
		this.weasis = weasis;
	}
	public Date getStudyDate() {
		return studyDate;
	}
	public void setStudyDate(Date studyDate) {
		this.studyDate = studyDate;
	}
	public String getModality() {
		return modality;
	}
	public void setModality(String modality) {
		this.modality = modality;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public List<DicomViewSerie> getSeries() {
		return series;
	}
	public void setSeries(List<DicomViewSerie> series) {
		this.series = series;
	}
}
