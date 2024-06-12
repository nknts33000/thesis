package com.example.platform.searching;

import com.example.platform.model.Advert;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdvertIndexer {
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public AdvertIndexer(RestHighLevelClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    public void indexAdvert(Advert advert) throws IOException {
        IndexRequest request = new IndexRequest("adverts")
                .id(String.valueOf(advert.getAdvertId()))
                .source(objectMapper.writeValueAsString(advert), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        // Handle response if needed
    }
}
