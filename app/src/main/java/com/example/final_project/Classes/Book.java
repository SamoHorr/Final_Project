package com.example.final_project.Classes;

public class Book {

    //intialising
    private String id;
    private String title;
    private String subtitles;
    private String author;
    private String publisher;
    private String language;
    private String isbn_10;
    private String isbn_13;
    private String pages;
    private String year;
    private String rating;
    private String description;
    private String price;
    private String image;
    private String url;
    private String url_pdf;


    //constructors
    public Book() {

    }

    public Book(String title) {
        this.title = title;
    }
    public Book(String id, String title, String subtitles, String author, String publisher, String language, String isbn_10, String isbn_13, String pages, String year, String rating, String description, String price, String image, String url, String url_pdf) {
        this.title = title;
        this.subtitles = subtitles;
        this.author = author;
        this.publisher = publisher;
        this.language = language;
        this.isbn_10 = isbn_10;
        this.isbn_13 = isbn_13;
        this.pages = pages;
        this.year = year;
        this.rating = rating;
        this.description = description;
        this.price = price;
        this.image = image;
        this.url = url;
        this.url_pdf = url_pdf;
    }

    //constructor for db


    public Book(String title, String subtitles,  String description , String image , String author, String publisher, String year , String language , String rating) {
        this.image = image;
        this.rating = rating ;
        this.pages = pages;
        this.year = year;
        this.title = title;
        this.description = description;
        this.subtitles = subtitles;
        this.author = author;
        this.publisher = publisher;
        this.language = language;
    }

    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsbn_10() {
        return isbn_10;
    }

    public void setIsbn_10(String isbn_10) {
        this.isbn_10 = isbn_10;
    }

    public String getIsbn_13() {
        return isbn_13;
    }

    public void setIsbn_13(String isbn_13) {
        this.isbn_13 = isbn_13;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }

    public void setUrl_pdf(String url_pdf) {
        this.url_pdf = url_pdf;
    }
}
