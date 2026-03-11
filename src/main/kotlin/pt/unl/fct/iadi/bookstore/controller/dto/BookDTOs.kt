package pt.unl.fct.iadi.bookstore.controller.dto

import jakarta.validation.constraints.*
import java.math.BigDecimal

// --- Requests ---

data class BookCreateRequest(
    @field:NotBlank(message = "ISBN cannot be blank")
    val isbn: String,

    @field:NotBlank @field:Size(min = 1, max = 120, message = "Title must be between 1 and 120 characters")
    val title: String,

    @field:NotBlank @field:Size(min = 1, max = 80, message = "Author must be between 1 and 80 characters")
    val author: String,

    @field:NotNull @field:DecimalMin(value = "0.0", inclusive = false, message = "Price must be strictly greater than 0")
    val price: BigDecimal,

    @field:NotBlank @field:Pattern(regexp = "^https?://.*", message = "Image must be a valid URL starting with http:// or https://")
    val image: String
)

data class BookPartialUpdateRequest(
    @field:Size(min = 1, max = 120)
    val title: String? = null,

    @field:Size(min = 1, max = 80)
    val author: String? = null,

    @field:DecimalMin(value = "0.0", inclusive = false)
    val price: BigDecimal? = null,

    @field:Pattern(regexp = "^https?://.*")
    val image: String? = null
)


data class BookResponse(
    val isbn: String,
    val title: String,
    val author: String,
    val price: BigDecimal,
    val image: String
)