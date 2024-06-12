package com.example.platform.searching;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdvertSearcher {
    private final RestHighLevelClient client;


    public AdvertSearcher(RestHighLevelClient client) {
        this.client = client;
    }

    public SearchResponse searchAdverts(String searchTerm) throws IOException {
        SearchRequest searchRequest = new SearchRequest("adverts");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("jobTitle", searchTerm));
        searchRequest.source(sourceBuilder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
