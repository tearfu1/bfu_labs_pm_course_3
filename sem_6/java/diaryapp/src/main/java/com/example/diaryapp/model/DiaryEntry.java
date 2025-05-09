package com.example.diaryapp.model;

import java.time.LocalDate;

public class DiaryEntry {
    private int id;
    private String title;
    private String content;
    private LocalDate entryDate;
    private Category category;

    public DiaryEntry(String title, String content, LocalDate entryDate, Category category) {
        this.title = title;
        this.content = content;
        this.entryDate = entryDate;
        this.category = category;
    }

    public DiaryEntry(int id, String title, String content, LocalDate entryDate, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.entryDate = entryDate;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryName() {
        return category != null ? category.getName() : "Без категории";
    }
}