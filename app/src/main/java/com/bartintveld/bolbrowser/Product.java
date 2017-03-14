package com.bartintveld.bolbrowser;

import java.io.Serializable;

/**
 * Created by barti on 14-3-2017.
 */

public class Product implements Serializable {

    private String title;
    private String specsTag;
    private String summary;
    private String longDescription;
    private String smallImageUrl;
    private String largeImageUrl;


    //Constructor
    public Product(String title, String specsTag, String summary, String longDescription, String smallImageUrl, String largeImageUrl) {
        this.title = title;
        this.specsTag = specsTag;
        this.summary = summary;
        this.longDescription = longDescription;
        this.smallImageUrl = smallImageUrl;
        this.largeImageUrl = largeImageUrl;
    }

    //All getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecsTag() {
        return specsTag;
    }

    public void setSpecsTag(String specsTag) {
        this.specsTag = specsTag;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }
}
