package com.example.filesearch.controller;

import com.example.filesearch.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class FileSearchController {

    @Autowired
    private FeignFiles files;

    @Autowired
    private FeignIndex index;

    private final WebClient filesClient = WebClient
            .builder()
            .baseUrl("http://localhost:8882")
            .build();

    @GetMapping("/search")
    public Map<byte[], String> getFilesByWords(@RequestParam("search") String search) {
        String[] words = search.split(" ");
        TreeSet<Long> documents = index.getIndexesByWord(words[0]);
        if (words.length > 1) {
            for (int i = 1; i < words.length; i++) {
                TreeSet<Long> temp = index.getIndexesByWord(words[i]);
                documents = retainCommon(documents, temp);
//                documents.retainAll(temp);
            }
        }
        return getFilesByIds(documents);
    }

    @GetMapping(value = "/searchFlux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getResults(@RequestParam("search") String search) {
        String[] words = search.split(" ");
        TreeSet<Long> documents = index.getIndexesByWord(words[0]);
        if (words.length > 1) {
            for (int i = 1; i < words.length; i++) {
                TreeSet<Long> temp = index.getIndexesByWord(words[i]);
                documents = retainCommon(documents, temp);
//                documents.retainAll(temp);
            }
        }
        List<Mono<String>> results = documents.stream()
                .map(id -> filesClient.get().uri("/getMonoByID?id={id}", id)
                        .retrieve()
                        .bodyToMono(String.class))
                .collect(Collectors.toList());
        return Flux.merge(results);
    }

    private TreeSet<Long> retainCommon(TreeSet<Long> a, TreeSet<Long> b) {
        for (Long aLong : a) {
            for (Long bLong : b) {
                if (aLong > bLong) {
                    b.iterator().next();
                    b.remove(bLong);
                    break;
                } else {
                    break;
                }
            }
        }
        return b;
    }

    private Map<byte[], String> getFilesByIds(TreeSet<Long> documents) {
        Map<byte[], String> response = new HashMap<>();
        for (Long id : documents) {
            byte[] temp = files.getFileById(id);
            response.put(temp, new String(temp));
        }
        return response;
    }

    private List<SearchResult> getSearchResultsByIds(TreeSet<Long> documents) {
        List<SearchResult> response = new ArrayList<>();
        for (Long id : documents) {
            byte[] temp = files.getFileById(id);
            response.add(new SearchResult("Filename", new String(temp)));
        }
        return response;
    }
}
