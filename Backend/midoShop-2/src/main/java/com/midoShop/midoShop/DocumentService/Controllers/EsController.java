package com.midoShop.midoShop.DocumentService.Controllers;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.midoShop.midoShop.DocumentService.Models.MyDocES;
import com.midoShop.midoShop.DocumentService.Models.MyDocument;
import com.midoShop.midoShop.DocumentService.Services.DocWithElasticSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/search")
public class EsController {

    private final DocWithElasticSearchService docWithElasticSearchService;


    public EsController(DocWithElasticSearchService docWithElasticSearchService) {
        this.docWithElasticSearchService = docWithElasticSearchService;
    }

    @GetMapping("/get/{potName}")
    public ResponseEntity<List<String>> GetQueries(@PathVariable String potName) throws IOException {

        SearchResponse<MyDocES> myDocESSearchResponse = docWithElasticSearchService.MatchSearch(potName);
        List<Hit<MyDocES>> hits = myDocESSearchResponse.hits().hits();
        List<MyDocES> myDocuments = new ArrayList<>();

        for(Hit<MyDocES> myDocESHit : hits){
            myDocuments.add(myDocESHit.source());
        }

        List<String> GetNames = new ArrayList<>();
        for(MyDocES myDocES : myDocuments){
            GetNames.add(myDocES.getName());
        }
        return ResponseEntity.ok(GetNames);
    }


}
