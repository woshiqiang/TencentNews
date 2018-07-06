package com.android.myapp;

/**
 * @author JianQiang Ding
 * @Date 2018-07-02.
 */
public class News {
    public String title;
    public String link;
    public String author;
    public String pubDate;
    public String description;


    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
