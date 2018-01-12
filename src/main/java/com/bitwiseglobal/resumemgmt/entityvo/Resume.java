package com.bitwiseglobal.resumemgmt.entityvo;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Resume {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="resume_id", nullable=false, length=20)
	BigInteger resumeID;

	String name;

	@Column(name="file_path", nullable=false, length=200)
	String filePath;

	@Column(name="upload_timestamp")
	Timestamp uploadTimestamp;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_key")
	User user;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "resume_skill", joinColumns = @JoinColumn(name = "resume_id", referencedColumnName = "resume_id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "skill_id"))
	Set<Skill> skills;

	//Updates - Aditya More
	@JoinColumn(name = "soft_delete")
	String soft_delete;

	@JoinColumn(name = "delete_timestamp")
	Timestamp deleteTimestamp;

	public Timestamp getDeleteTimestamp() {
		return deleteTimestamp;
	}

	public void setDeleteTimestamp(Timestamp deleteTimestamp) {
		this.deleteTimestamp = deleteTimestamp;
	}

	public BigInteger getResumeID() {
		return resumeID;
	}

	public void setResumeID(BigInteger resumeID) {
		this.resumeID = resumeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Timestamp getUploadTimestamp() {
		return uploadTimestamp;
	}

	public void setUploadTimestamp(Timestamp uploadTimestamp) {
		this.uploadTimestamp = uploadTimestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public String getSoft_delete() {
		return soft_delete;
	}

	public void setSoft_delete(String soft_delete) {
		this.soft_delete = soft_delete;
	}
}

