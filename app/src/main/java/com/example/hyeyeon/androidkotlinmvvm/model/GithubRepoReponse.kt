package com.example.hyeyeon.androidkotlinmvvm.model

import com.google.gson.annotations.SerializedName

class GithubRepoReponse {
    @SerializedName("total_count")
    var totalCount = 0

    @SerializedName("incomplete_results")
    var incompleteResults = false

    var items = mutableListOf<GithubRepoInfo>()

    class GithubRepoInfo {
        var id = 0L
        var name = ""
        var description = ""
        var owner = Owner()
    }

    class Owner {
        var id = 0L

        @SerializedName("login")
        var name = ""

        @SerializedName("avatar_url")
        var avatarUrl = ""

    }
}