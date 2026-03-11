package pt.unl.fct.iadi.bookstore.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.unl.fct.iadi.bookstore.controller.dto.*

@RequestMapping("/books")
@Tag(name = "Bookstore API", description = "Endpoints for managing books and their reviews")
interface BookstoreAPI {

    // --- Book Operations ---

    @GetMapping
    @Operation(summary = "US1 - List all books")
    fun listBooks(): ResponseEntity<List<BookResponse>>

    @PostMapping
    @Operation(summary = "US2 - Create a book")
    fun createBook(@Valid @RequestBody request: BookCreateRequest): ResponseEntity<Unit>

    @GetMapping("/{isbn}")
    @Operation(summary = "US3 - Get a single book")
    fun getBook(
        @PathVariable isbn: String,
        @RequestHeader(value = "Accept-Language", defaultValue = "en") language: String
    ): ResponseEntity<BookResponse>

    @PutMapping("/{isbn}")
    @Operation(summary = "US4 - Replace a book (Upsert)")
    fun replaceBook(
        @PathVariable isbn: String,
        @Valid @RequestBody request: BookCreateRequest
    ): ResponseEntity<BookResponse>

    @PatchMapping("/{isbn}")
    @Operation(summary = "US5 - Partially update a book")
    fun partiallyUpdateBook(
        @PathVariable isbn: String,
        @Valid @RequestBody request: BookPartialUpdateRequest
    ): ResponseEntity<BookResponse>

    @DeleteMapping("/{isbn}")
    @Operation(summary = "US6 - Delete a book")
    fun deleteBook(@PathVariable isbn: String): ResponseEntity<Unit>

    // --- Review Operations ---

    @GetMapping("/{isbn}/reviews")
    @Operation(summary = "US7 - List reviews for a book")
    fun listReviews(@PathVariable isbn: String): ResponseEntity<List<ReviewResponse>>

    @PostMapping("/{isbn}/reviews")
    @Operation(summary = "US8 - Create a review for a book")
    fun createReview(
        @PathVariable isbn: String,
        @Valid @RequestBody request: ReviewCreateRequest
    ): ResponseEntity<Unit>

    @PutMapping("/{isbn}/reviews/{reviewId}")
    @Operation(summary = "US9 - Replace a review")
    fun replaceReview(
        @PathVariable isbn: String,
        @PathVariable reviewId: Long,
        @Valid @RequestBody request: ReviewCreateRequest
    ): ResponseEntity<ReviewResponse>

    @PatchMapping("/{isbn}/reviews/{reviewId}")
    @Operation(summary = "US10 - Partially update a review")
    fun partiallyUpdateReview(
        @PathVariable isbn: String,
        @PathVariable reviewId: Long,
        @Valid @RequestBody request: ReviewPartialUpdateRequest
    ): ResponseEntity<ReviewResponse>

    @DeleteMapping("/{isbn}/reviews/{reviewId}")
    @Operation(summary = "US11 - Delete a review")
    fun deleteReview(
        @PathVariable isbn: String,
        @PathVariable reviewId: Long
    ): ResponseEntity<Unit>
}