package com.midoShop.midoShop.DocumentService.Services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.midoShop.midoShop.DocumentService.Models.MyDocES;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Repositories.ElasticDocRepo;
import com.midoShop.midoShop.DocumentService.Utils.EsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Supplier;

@Component
public class DocWithElasticSearchService {

    private final ElasticsearchClient elasticsearchClient;

    private final ElasticDocRepo elasticDocRepo;

    public DocWithElasticSearchService(ElasticsearchClient elasticsearchClient, ElasticDocRepo elasticDocRepo) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticDocRepo = elasticDocRepo;
    }


    @KafkaListener(topics = "my-doc" , groupId = "my-documents-group")
    public void SaveWithElasticSearch(MyDocES myDocument){
        elasticDocRepo.save(myDocument);
    }

    public SearchResponse<MyDocES> MatchSearch(String potName) throws IOException {
        Supplier<Query> supplier = EsUtil.MatchQuerySupplier(potName);

        return elasticsearchClient.search(i-> i
                .index("my_products")
                .query(supplier.get())
                , MyDocES.class
        );

    }


}
