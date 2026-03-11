package pt.unl.fct.iadi.bookstore.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ReviewCreateRequest(
    @field:NotNull @field:Min(1) @field:Max(5)
    val rating: Int,

    @field:Size(max = 500)
    val comment: String? = null
)

data class ReviewPartialUpdateRequest(
    @field:Min(1) @field:Max(5)
    val rating: Int? = null,

    @field:Size(max = 500)
    val comment: String? = null
)

data class ReviewResponse(
    @field:Schema(description = "The unique review ID", example = "1")
    val id: Long,

    @field:Schema(description = "Rating from 1 to 5", minimum = "1", maximum = "5", example = "5")
    val rating: Int,

    @field:Schema(description = "Optional comment", maxLength = 500, example = "Great read!")
    val comment: String?
)