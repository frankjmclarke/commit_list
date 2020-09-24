package com.fclarke.commitlist

import com.fclarke.commitlist.pojo.CommitModel
import com.fclarke.commitlist.pojo.ExampleModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CommitService {
    @GET("commits/")
    fun getCommitJSON(): Call<List<CommitModel>>

    @GET("commits")
    fun getExampleJSON(): Call<List<ExampleModel>>

}