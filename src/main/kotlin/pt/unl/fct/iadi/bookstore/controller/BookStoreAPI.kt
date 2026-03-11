package pt.unl.fct.iadi.bookstore.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import pt.unl.fct.iadi.bookstore.controller.dto.BookCreateRequest
import pt.unl.fct.iadi.bookstore.controller.dto.BookPartialUpdateRequest
import pt.unl.fct.iadi.bookstore.controller.dto.BookResponse
import pt.unl.fct.iadi.bookstore.controller.dto.ErrorResponse
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewCreateRequest
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewPartialUpdateRequest
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewResponse

@RequestMapping("/books")
@Tag(name = "Bookstore API", description = "Endpoints for managing books and their reviews")
interface BookstoreAPI {

    @GetMapping
    @Operation(summary = "US1 - List all books")
    @ApiResponse(responseCode = "200", description = "Success")
    fun listBooks(): ResponseEntity<List<BookResponse>>

    @PostMapping
    @Operation(summary = "US2 - Create a book")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(
            responseCode = "400",
            description = "Validation Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Conflict",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun createBook(@Valid @RequestBody request: BookCreateRequest): ResponseEntity<Unit>

    @GetMapping("/{isbn}")
    @Operation(summary = "US3 - Get a single book")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Success",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BookResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun getBook(
        @PathVariable isbn: String,
        @RequestHeader(value = "Accept-Language", defaultValue = "en") language: String
    ): ResponseEntity<BookResponse>

    @PutMapping("/{isbn}")
    @Operation(summary = "US4 - Replace a book (Upsert)")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Replaced successfully",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BookResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "201",
            description = "Created via Upsert",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BookResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Validation Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun replaceBook(
        @PathVariable isbn: String,
        @Valid @RequestBody request: BookCreateRequest
    ): ResponseEntity<BookResponse>

    @PatchMapping("/{isbn}")
    @Operation(summary = "US5 - Partially update a book")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Updated successfully",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BookResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Validation Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun partiallyUpdateBook(
        @PathVariable isbn: String,
        @Valid @RequestBody request: BookPartialUpdateRequest
    ): ResponseEntity<BookResponse>

    @DeleteMapping("/{isbn}")
    @Operation(summary = "US6 - Delete a book")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "No Content"),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun deleteBook(@PathVariable isbn: String): ResponseEntity<Unit>

    @GetMapping("/{isbn}/reviews")
    @Operation(summary = "US7 - List reviews")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun listReviews(@PathVariable isbn: String): ResponseEntity<List<ReviewResponse>>

    @PostMapping("/{isbn}/reviews")
    @Operation(summary = "US8 - Create a review")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(
            responseCode = "400",
            description = "Validation Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun createReview(
        @PathVariable isbn: String,
        @Valid @RequestBody request: ReviewCreateRequest
    ): ResponseEntity<Unit>

    @PutMapping("/{isbn}/reviews/{reviewId}")
    @Operation(summary = "US9 - Replace a review")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Replaced",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ReviewResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Validation Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun replaceReview(
        @PathVariable isbn: String,
        @PathVariable reviewId: Long,
        @Valid @RequestBody request: ReviewCreateRequest
    ): ResponseEntity<ReviewResponse>

    @PatchMapping("/{isbn}/reviews/{reviewId}")
    @Operation(summary = "US10 - Partially update a review")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Updated",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ReviewResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Validation Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun partiallyUpdateReview(
        @PathVariable isbn: String,
        @PathVariable reviewId: Long,
        @Valid @RequestBody request: ReviewPartialUpdateRequest
    ): ResponseEntity<ReviewResponse>

    @DeleteMapping("/{isbn}/reviews/{reviewId}")
    @Operation(summary = "US11 - Delete a review")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "No Content"),
        ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    fun deleteReview(
        @PathVariable isbn: String,
        @PathVariable reviewId: Long
    ): ResponseEntity<Unit>
}