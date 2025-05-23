package com.hris.HRIS_job_portal.Model;

import com.hris.HRIS_job_portal.DTO.EmpProjectsDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "portal_emp_projects")
public class EmpProjectsModel {
    @Id
    private String id;
    private String employeeId;
    @Field("projects")
    private List<EmpProjectsDTO> projects;
}
