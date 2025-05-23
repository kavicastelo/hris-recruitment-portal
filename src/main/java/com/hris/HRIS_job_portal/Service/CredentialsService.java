package com.hris.HRIS_job_portal.Service;

import com.hris.HRIS_job_portal.Model.CompanyModel;
import com.hris.HRIS_job_portal.Model.CredentialsModel;
import com.hris.HRIS_job_portal.Model.EmployeeModel;
import com.hris.HRIS_job_portal.Repository.CompanyRepository;
import com.hris.HRIS_job_portal.Repository.CredentialsRepository;
import com.hris.HRIS_job_portal.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public CredentialsModel addCredentials(CredentialsModel credentials) {
        Optional<CredentialsModel> optionalCredentials = Optional.ofNullable(credentialsRepository.findByEmail(credentials.getEmail()));
        if (optionalCredentials.isPresent()) {
            return null;
        } else {
            EmployeeModel emp = new EmployeeModel();
            emp.setFirstname(credentials.getFirstname());
            emp.setLastname(credentials.getLastname());
            emp.setEmail(credentials.getEmail());

            Map<String, Boolean> profileCompleted = new HashMap<>();
            profileCompleted.put("name", true);
            profileCompleted.put("email", true);
            profileCompleted.put("resume", false);
            profileCompleted.put("occupation", false);
            profileCompleted.put("profilePic", false);
            profileCompleted.put("coverPic", false);
            profileCompleted.put("intro", false);
            profileCompleted.put("skills", false);
            profileCompleted.put("experiences", false);
            profileCompleted.put("education", false);
            profileCompleted.put("projects", false);
            profileCompleted.put("certificates", false);
            profileCompleted.put("contactInfo", false);
            profileCompleted.put("socialLinks", false);
            emp.setProfileCompleted(profileCompleted);

            if (credentials.getUserLevel().equals("2") || credentials.getUserLevel().equals("3") || credentials.getUserLevel().equals("4")) {
                CompanyModel cmp = new CompanyModel();

                Map<String, Boolean> cmpProfileCompleted = new HashMap<>();
                cmpProfileCompleted.put("name", false);
                cmpProfileCompleted.put("email", false);
                cmpProfileCompleted.put("logo", false);
                cmpProfileCompleted.put("coverPic", false);
                cmpProfileCompleted.put("image1", false);
                cmpProfileCompleted.put("image2", false);
                cmpProfileCompleted.put("image3", false);
                cmpProfileCompleted.put("story", false);
                cmpProfileCompleted.put("founderName", false);
                cmpProfileCompleted.put("foundedDate", false);
                cmpProfileCompleted.put("location", false);
                cmpProfileCompleted.put("numberOfEmployees", false);
                cmpProfileCompleted.put("website", false);
                cmpProfileCompleted.put("socialLinks", false);

                cmp.setProfileCompleted(cmpProfileCompleted);
                cmp.setCompanyLevel(credentials.getUserLevel());

                CompanyModel savedCmp = companyRepository.save(cmp);
                emp.setCompanyId(savedCmp.getId());
                credentials.setCompanyId(savedCmp.getId());
            }

            EmployeeModel savedEmp = employeeRepository.save(emp);

            credentials.setEmployeeId(savedEmp.getId());

            return credentialsRepository.save(credentials);
        }
    }

    public Iterable<CredentialsModel> getAllCredentials() {
        return credentialsRepository.findAll();
    }

    public Optional<CredentialsModel> getCredentials(String employeeId) {
        return credentialsRepository.findByEmployeeId(employeeId);
    }

    public CredentialsModel getCredentialsByEmail(String email) {
        return credentialsRepository.findByEmail(email);
    }

    public Optional<CredentialsModel> getCredentialsByEmployeeId(String employeeId) {
        return credentialsRepository.findByEmployeeId(employeeId);
    }

    public CredentialsModel updateCredentials(String employeeId, CredentialsModel credentials) {
        Optional<CredentialsModel> optionalCredentials = credentialsRepository.findById(credentials.getId());
        if (optionalCredentials.isPresent()) {
            CredentialsModel credentials1 = optionalCredentials.get();
            credentials1.setEmployeeId(employeeId);
            credentials1.setFirstname(credentials.getFirstname());
            credentials1.setLastname(credentials.getLastname());
            credentials1.setEmail(credentials.getEmail());
            credentials1.setPassword(credentials.getPassword());
            credentials1.setRole(credentials.getRole());
            return credentialsRepository.save(credentials1);
        }
        return null;
    }

    public CredentialsModel updatePassword(String credentialsId, String password) {
        Optional<CredentialsModel> optionalCredentials = credentialsRepository.findById(credentialsId);
        if (optionalCredentials.isPresent()) {
            CredentialsModel credentials1 = optionalCredentials.get();
            credentials1.setPassword(password);
            return credentialsRepository.save(credentials1);
        }
        return null;
    }

    public CredentialsModel deleteCredentials(String employeeId) {
        return credentialsRepository.deleteByEmployeeId(employeeId);
    }

    public void findAndUpdateCompanyLevel(String companyId, String userLevel) {
        Optional<CredentialsModel> optionalCredentials = credentialsRepository.findByCompanyId(companyId);
        if (optionalCredentials.isPresent()) {
            CredentialsModel credentials1 = optionalCredentials.get();
            credentials1.setUserLevel(userLevel);
            credentialsRepository.save(credentials1);
        }
    }
}
