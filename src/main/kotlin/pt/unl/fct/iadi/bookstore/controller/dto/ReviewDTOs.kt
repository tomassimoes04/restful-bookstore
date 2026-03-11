package pt.unl.fct.iadi.bookstore.controller.dto

import jakarta.validation.constraints.*


data class ReviewCreateRequest(
    @field:NotNull @field:Min(1) @field:Max(5, message = "Rating must be between 1 and 5")
    val rating: Int,

    @field:Size(max = 500, message = "Comment must not exceed 500 characters")
    val comment: String? = null
)

data class ReviewPartialUpdateRequest(
    @field:Min(1) @field:Max(5)
    val rating: Int? = null,

    @field:Size(max = 500)
    val comment: String? = null
)


data class ReviewResponse(
    val id: Long,
    val rating: Int,
    val comment: String?
)