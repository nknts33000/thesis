package com.example.platform.elasticsearchrepo;

import com.example.platform.ElasticSearchModel.AdvertES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface AdvertElasticsearchRepository extends ElasticsearchRepository<AdvertES, String> {

}
