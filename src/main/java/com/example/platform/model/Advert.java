package com.example.platform.model;

import com.example.platform.repo.CompanyRepo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long advertId;
    @Column(nullable = false)
    private String jobTitle;
    @Column(nullable = false)
    private String jobSummary;
    private String location;
    private String contactInformation;



    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "APPLICANTS",
    joinColumns = @JoinColumn(name = "id"),
    inverseJoinColumns = @JoinColumn(name = "advertId"))
    private List<User> applicants;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="companyId",referencedColumnName = "companyId")
    private Company company;

    public Advert(){}
    public Advert(String jobTitle,
     String jobSummary,
     String location,
     String contactInformation,
                  Company company
    ){
        this.jobTitle=jobTitle;
        this.jobSummary=jobSummary;
        this.location=location;
        this.contactInformation=contactInformation;
        this.company=company;
    }

}