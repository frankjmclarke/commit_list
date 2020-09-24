package com.fclarke.commitlist.pojo;

import com.fclarke.commitlist.unused.Author;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleModel {

    @SerializedName("sha")/***used***/
    @Expose
    public String sha;
    @SerializedName("node_id")
    @Expose
    public String nodeId;
    @SerializedName("commit")
    @Expose
    public CommitModel commit;//for committer.date message
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("html_url")
    @Expose
    public String htmlUrl;
    @SerializedName("comments_url")
    @Expose
    public String commentsUrl;
    @SerializedName("author")
    @Expose
    public AuthorModel author;
    @SerializedName("committer")
    @Expose
    public CommitterModel committer;// for login html_url avatar_url
    //@SerializedName("parents")
    //@Expose
    //public List<Parent> parents = null;

}