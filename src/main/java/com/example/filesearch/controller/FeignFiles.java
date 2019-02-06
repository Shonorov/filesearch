package com.example.filesearch.controller;

import com.example.filesearch.model.SearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@FeignClient("files")
public interface FeignFiles {

    @GetMapping("/getByID")
    byte[] getFileById(@RequestParam("id") long id);

    @GetMapping(value = "/getMonoByID", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Mono<List<SearchResult>> getMonoByID(@RequestParam("id") long id);
}
