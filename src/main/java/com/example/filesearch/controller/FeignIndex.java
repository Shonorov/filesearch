package com.example.filesearch.controller;

import com.example.filesearch.model.SearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.TreeSet;

@Component
@FeignClient("index")
public interface FeignIndex {

    @GetMapping("/getByWord")
    TreeSet<Long> getIndexesByWord(@RequestParam("word") String word);


}
