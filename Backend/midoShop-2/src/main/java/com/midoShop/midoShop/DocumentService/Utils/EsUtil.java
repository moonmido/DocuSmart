package com.midoShop.midoShop.DocumentService.Utils;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.function.Supplier;

public class EsUtil {



    public static Supplier<Query> MatchQuerySupplier(String potname){
    return  ()->Query.of(q -> q.match(CreateMatchQuery(potname)));
    }

    public static MatchQuery CreateMatchQuery(String potonName){
        return new MatchQuery.Builder()
                .field("name")
                .query(potonName)
                .build();
    }

}
