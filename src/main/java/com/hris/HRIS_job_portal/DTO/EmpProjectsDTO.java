package com.hris.HRIS_job_portal.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter

public class EmpProjectsDTO {
    @Id
    private String id;
    private String title;
    private String company;
    private String role;
    private String startDate;
    private String endDate;
    private String demo;
    private String source;
    private String description;
}
