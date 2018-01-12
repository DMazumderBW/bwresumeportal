package com.bitwiseglobal.resumemgmt.dto;

public class ResumeDisplayDTO implements Comparable<ResumeDisplayDTO>{
	Integer resumeId;
	String resumeName;
	String uploadedBy;
	String uploadedTime;
	String uploadLink;
	String resumeSkills;
	
	public String getResumeName() {
		return resumeName;
	}
	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getUploadedTime() {
		return uploadedTime;
	}
	public void setUploadedTime(String uploadedTime) {
		this.uploadedTime = uploadedTime;
	}
	public String getUploadLink() {
		return uploadLink;
	}
	public void setUploadLink(String uploadLink) {
		this.uploadLink = uploadLink;
	}
	public String getResumeSkills() {
		return resumeSkills;
	}
	public void setResumeSkills(String resumeSkills) {
		this.resumeSkills = resumeSkills;
	}
	public Integer getResumeId() {
		return resumeId;
	}
	public void setResumeId(Integer resumeId) {
		this.resumeId = resumeId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resumeId == null) ? 0 : resumeId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResumeDisplayDTO other = (ResumeDisplayDTO) obj;
		if (resumeId == null) {
			if (other.resumeId != null)
				return false;
		} else if (!resumeId.equals(other.resumeId))
			return false;
		return true;
	}

	@Override
	public int compareTo(ResumeDisplayDTO that) {
		if ( null == that || null == that.getResumeId() || this.getResumeId() == that.getResumeId())
			return 0;
		if(that.getResumeId() > this.getResumeId())
			return 1;
		return -1;
	}
}

