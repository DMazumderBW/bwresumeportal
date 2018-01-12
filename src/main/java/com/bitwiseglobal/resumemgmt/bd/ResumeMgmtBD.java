package com.bitwiseglobal.resumemgmt.bd;

import com.bitwiseglobal.resumemgmt.dto.ResumeDisplayDTO;
import com.bitwiseglobal.resumemgmt.entityvo.Resume;
import com.bitwiseglobal.resumemgmt.entityvo.Skill;
import com.bitwiseglobal.resumemgmt.entityvo.User;
import com.bitwiseglobal.resumemgmt.repository.IResumeRepository;
import com.bitwiseglobal.resumemgmt.repository.ISkillRepository;
import com.bitwiseglobal.resumemgmt.repository.IUserRepository;
import com.bitwiseglobal.resumemgmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;



@Service
public class ResumeMgmtBD {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IResumeRepository resumeRepository;

	@Autowired
	ISkillRepository skillRepository;

	@Autowired
	UserService userService;

	public void addUser() {
		User user = new User();
		user.setUserId("TestID");
		user.setPassword("test");
		user.setFirstName("Test");
		user.setLastName("test");
		user.setMiddleName("Test");
		userRepository.save(user);
	}

	public void addSkill() {
		Skill skill = new Skill();
		skill.setName("Java");
		skillRepository.save(skill);

	}

	//Created by: Aditya More
	public void softDeleteResume(int resumeid) {
		Resume resume = new Resume();
		resume = resumeRepository.findOne(BigInteger.valueOf(resumeid));
		System.out.println("Finding resume...");
		System.out.println("Found Resume : "+ resume.getName());
		resume.setSoft_delete("YES");
		resume.setDeleteTimestamp(new Timestamp(new Date().getTime()));
		resumeRepository.save(resume);
		System.out.println("...Saved");
	}

	public void restoreSoftDeleteResume(int resumeid){
		Resume resume = new Resume();
		resume = resumeRepository.findOne(BigInteger.valueOf(resumeid));
		resume.setSoft_delete("NO");
		resumeRepository.save(resume);
	}
	/**
	 * will return skills for provided skill ids
	 * @param skills
	 * @return
	 */
	public List<Skill> getSkills(String skills) {
		String[] skillsList=skills.split(",");
		List<BigInteger> ids=new ArrayList<BigInteger>();

		for (String skill : skillsList) {
			BigInteger skillInt=new BigInteger(skill);
			ids.add(skillInt);
		}
		return (List<Skill>) skillRepository.findAll(ids);
	}

	/**
	 * will return all skills
	 * @return
	 */
	public Iterable<Skill> getSkills() {
		System.out.println("In getSkills - ResumeMgmtBD");
		return skillRepository.findAll();
	}


	public void addResume() {
		BigInteger j1 = new BigInteger("43");
		BigInteger j2 = new BigInteger("44");
		User user = userService.findLoggedInUser();
		System.out.println("current active user: "+user.getFirstName() + user.getLastName());

		Skill skill1 = skillRepository.findOne(j1);
		Skill skill2 = skillRepository.findOne(j2);

		Set<Skill> skills = new HashSet<>();
		skills.add(skill1);
		skills.add(skill2);

		Resume resume = new Resume();
		resume.setFilePath("abcd");
		resume.setName("Resume2");
		resume.setUser(user);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		resume.setUploadTimestamp(timestamp);
		resume.setSkills(skills);
		resumeRepository.save(resume);
	}

	/**
	 *
	 * @param resumeName
	 * @param skills
	 */
	public Resume addResume(String resumeName, String skills) {

		// retrieve skills
		Set<Skill> skillSet=new HashSet<Skill>(getSkills(skills));

		User user = userService.findLoggedInUser();

		Resume resume = new Resume();
		resume.setFilePath(resumeName);
		resume.setName(resumeName);
		resume.setUser(user);
		resume.setSkills(skillSet);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		resume.setUploadTimestamp(timestamp);
		resume.setSoft_delete("NO");
		return resumeRepository.save(resume);


	}

	public Set<ResumeDisplayDTO> getResumeBySkills(String commaSeperatedSkillsStr) {

		Set<Skill> skillSet = new HashSet<>();
		for(String skillStr : commaSeperatedSkillsStr.split(",")) {
			skillSet.add(skillRepository.findOne(new BigInteger(skillStr)));
		}
		return getResumeBySkills(skillSet);
	}

	public Set<ResumeDisplayDTO> getResumeBySkills(Set<Skill> skills) {
		Set<ResumeDisplayDTO> resumeDisplayDTOSet = new TreeSet<>();
		for (Skill skill : skills) {
			for (Resume resume : skill.getResumes()) {
				if(resume.getSoft_delete().equals("NO"))
					resumeDisplayDTOSet.add(convertToPresentationDTO(resume));
			}
		}
		return resumeDisplayDTOSet;
	}

	public Set<ResumeDisplayDTO> getDeletedResumeBySkills(String commaSeperatedSkillsStr) {

		Set<Skill> skillSet = new HashSet<>();
		for(String skillStr : commaSeperatedSkillsStr.split(",")) {
			skillSet.add(skillRepository.findOne(new BigInteger(skillStr)));
		}
		return getDeletedResumeBySkills(skillSet);
	}
	public Set<ResumeDisplayDTO> getDeletedResumeBySkills(Set<Skill> skills) {
		Set<ResumeDisplayDTO> resumeDisplayDTOSet = new TreeSet<>();
		for (Skill skill : skills) {
			for (Resume resume : skill.getResumes()) {
				if(resume.getSoft_delete().equals("YES"))
					resumeDisplayDTOSet.add(convertToPresentationDTO(resume));
			}
		}
		return resumeDisplayDTOSet;
	}

	public Set<ResumeDisplayDTO> getDeletedResume(){

		Set<ResumeDisplayDTO> resumeList = new TreeSet<>();
		List<Resume> resumes = new ArrayList<>();

		resumeRepository.findAll().forEach(resumes::add);

		for(Resume resume : resumes){
			if(resume.getSoft_delete().equals("YES"))
				resumeList.add(convertToPresentationDTO(resume));
		}


		return resumeList;
	}


	private ResumeDisplayDTO convertToPresentationDTO(Resume resume) {
		ResumeDisplayDTO resumeDisplayDTO = new ResumeDisplayDTO();
		resumeDisplayDTO.setResumeId(resume.getResumeID().intValue());
		resumeDisplayDTO.setResumeName(resume.getName());
		resumeDisplayDTO.setUploadedBy(resume.getUser().getUserId());
		resumeDisplayDTO.setUploadLink(resume.getFilePath());
		resumeDisplayDTO.setUploadedTime(resume.getUploadTimestamp().toString());
		resumeDisplayDTO.setResumeSkills(convertSetToCommaSeperatedString(resume.getSkills()));
		return resumeDisplayDTO;
	}

	private String convertSetToCommaSeperatedString(Set<Skill> skills) {
		StringBuilder returnValue = new StringBuilder("");
		for (Skill skill : skills) {
			returnValue.append(skill.getName()).append(",");
		}
		return returnValue.toString().substring(0, returnValue.length()-1);
	}
}



