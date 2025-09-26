package com.midoShop.midoShop.DocumentService.Repositories;

import com.midoShop.midoShop.DocumentService.Models.MyDocES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticDocRepo extends ElasticsearchRepository<MyDocES , Long> {
}
