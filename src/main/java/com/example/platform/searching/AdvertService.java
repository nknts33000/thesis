package com.example.platform.searching;

import com.example.platform.model.Advert;
import com.example.platform.model.Company;
import com.example.platform.repo.AdvertRepo;
import com.example.platform.repo.CompanyRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AdvertService {


    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;
    private final CompanyRepo companyRepo;
    private final AdvertRepo advertRepo;
    public AdvertService(RestHighLevelClient client,CompanyRepo companyRepo,AdvertRepo advertRepo) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
        this.companyRepo=companyRepo;
        this.advertRepo=advertRepo;
    }

    // Bulk Indexing
    public void bulkIndexAdverts(List<Advert> adverts) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Advert advert : adverts) {
            IndexRequest request = new IndexRequest("adverts")
                    .id(String.valueOf(advert.getAdvertId()))
                    .source(objectMapper.writeValueAsString(advert), XContentType.JSON);
            bulkRequest.add(request);
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        // Handle response if needed
    }

    @Transactional
    public void addAdvert(Map<String, String> requestBody) {
        Company company = companyRepo.findCompanyByCompanyId(Long.valueOf(requestBody.get("company")));

        Advert advert = new Advert(
                requestBody.get("jobTitle"),
                requestBody.get("jobSummary"),
                requestBody.get("location"),
                requestBody.get("contactInformation"),
                company
        );

        Advert savedAdvert = advertRepo.save(advert);

        try {
            indexAdvert(savedAdvert);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle indexing failure appropriately
        }
    }

    // Real-time Indexing
    public void indexAdvert(Advert advert) throws IOException {
        IndexRequest request = new IndexRequest("adverts")
                .id(String.valueOf(advert.getAdvertId()))
                .source(objectMapper.writeValueAsString(advert), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        // Handle response if needed
    }

    // Real-time Deletion
    public void deleteAdvert(long advertId) throws IOException {
        DeleteRequest request = new DeleteRequest("adverts", String.valueOf(advertId));
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        // Handle response if needed
    }

    public void indexAllAdverts() {
        List<Advert> allAdverts = advertRepo.findAll();
        for (Advert advert : allAdverts) {
            try {
                indexAdvert(advert);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle indexing failure appropriately, maybe log it
            }
        }
    }
}
