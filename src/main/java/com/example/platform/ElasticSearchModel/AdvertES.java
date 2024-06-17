package com.example.platform.ElasticSearchModel;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


@Data
@Getter
@Setter
@Document(indexName = "advertindex")
public class AdvertES {

    @Id
    private String id;
    @Field
    private String jobTitle;
    @Field
    private String jobSummary;
    private String location;
    private String contactInformation;
    private String company;

    public AdvertES() {
    }

    public AdvertES(String id,String jobTitle,
                    String jobSummary,
                    String location,
                    String contactInformation,
                    String company
    ) {
        this.id=id;
        this.jobTitle = jobTitle;
        this.jobSummary = jobSummary;
        this.location = location;
        this.contactInformation = contactInformation;
        this.company = company;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}