package pt.unl.fct.iadi.bookstore.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class BookCreateRequest(
    @field:NotBlank(message = "ISBN cannot be blank")
    val isbn: String,

    @field:NotBlank
    @field:Size(min = 1, max = 120)
    val title: String,

    @field:NotBlank
    @field:Size(min = 1, max = 80)
    val author: String,

    @field:NotNull
    @field:DecimalMin(value = "0.0", inclusive = false)
    val price: BigDecimal,

    @field:NotBlank
    @field:Pattern(regexp = "^https?://.*")
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
    @field:Schema(description = "The unique ISBN of the book", example = "9780134685991")
    val isbn: String,

    @field:Schema(description = "The title of the book", minLength = 1, maxLength = 120, example = "Effective Java")
    val title: String,

    @field:Schema(description = "The author of the book", minLength = 1, maxLength = 80, example = "Joshua Bloch")
    val author: String,

    @field:Schema(description = "The price", minimum = "0", exclusiveMinimum = true, example = "45.99")
    val price: BigDecimal,

    @field:Schema(description = "Cover image URL", example = "https://example.com/cover.jpg")
    val image: String
)