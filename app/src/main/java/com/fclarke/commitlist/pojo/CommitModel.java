package com.fclarke.commitlist.pojo;

import com.fclarke.commitlist.unused.Author_;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommitModel {

    //@SerializedName("author")
    //@Expose
    //public AuthorModel author;
    @SerializedName("committer")
    @Expose
    public CommitCommitterModel committer;//for date
    @SerializedName("message")
    @Expose
    public String message;/***used***/
    //@SerializedName("tree")
    //@Expose
    //public Tree tree;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("comment_count")
    @Expose
    public Integer commentCount;
    //@SerializedName("verification")
    //@Expose
    //public Verification verification;

}