package com.example.filesearch.model;

public class SearchResult {

    private Long id;
    private String fileName;
    private String text;

    public SearchResult(String fileName, String text) {
        this.fileName = fileName;
        this.text = text;
    }

    public SearchResult(Long id, String fileName, String text) {
        this.id = id;
        this.fileName = fileName;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
