package com.fclarke.commitlist.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//commit.committer
public class CommitCommitterModel {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("date")/***used***/
    @Expose
    public String date;

}