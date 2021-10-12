package com.example.neworkrequestsproject.domain.model

data class PageOfUsers (
    val page: Long,

    //@Json(name = "per_page")
    val perPage: Long,

    val total: Long,

    //@Json(name = "total_pages")
    val totalPages: Long,

    val data: List<User>,
) {
    //public fun toJson() = klaxon.toJsonString(this)

    /*companion object {
        public fun fromJson(json: String) = klaxon.parse<Welcome10>(json)
    }*/
}

data class User (
    val id: Long,
    val email: String,

    //@Json(name = "first_name")
    val firstName: String,

    //@Json(name = "last_name")
    val lastName: String,

    val avatar: String
)
