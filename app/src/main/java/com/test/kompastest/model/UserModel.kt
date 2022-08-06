package com.test.kompastest.model

data class SearchModel(
    val total_count : Int,
    val items : ArrayList<UserData>
)

data class UserData(
    val login : String,
    val avatar_url : String
)

data class NameModel(
    val name : String,
    val bio : String
)

data class RepoModel(
    val name : String,
    val description : String,
    val stargazers_count : Int,
    val updated_at : String
)