package pt.unl.fct.iadi.bookstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import pt.unl.fct.iadi.bookstore.controller.dto.*
import pt.unl.fct.iadi.bookstore.domain.Book
import pt.unl.fct.iadi.bookstore.service.BookstoreService

@RestController
class BookstoreController(private val service: BookstoreService) : BookstoreAPI {


    private fun Book.toDto() = BookResponse(isbn, title, author, price, image)
    private fun pt.unl.fct.iadi.bookstore.domain.Review.toDto() = ReviewResponse(id, rating, comment)


    override fun listBooks(): ResponseEntity<List<BookResponse>> {
        val response = service.getAllBooks().map { it.toDto() }
        return ResponseEntity.ok(response)
    }

    override fun createBook(request: BookCreateRequest): ResponseEntity<Unit> {
        val book = service.createBook(Book(request.isbn, request.title, request.author, request.price, request.image))

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{isbn}").buildAndExpand(book.isbn).toUri()
        return ResponseEntity.created(location).build()
    }

    override fun getBook(isbn: String, language: String): ResponseEntity<BookResponse> {
        val book = service.getBook(isbn)
        val contentLang = if (language.lowercase().startsWith("pt")) "pt" else "en"

        return ResponseEntity.ok().header("Content-Language", contentLang).body(book.toDto())
    }

    override fun replaceBook(isbn: String, request: BookCreateRequest): ResponseEntity<BookResponse> {
        val bookToSave = Book(isbn, request.title, request.author, request.price, request.image)
        val updatedBook = service.replaceBook(isbn, bookToSave)
        return ResponseEntity.ok(updatedBook.toDto())
    }

    override fun partiallyUpdateBook(isbn: String, request: BookPartialUpdateRequest): ResponseEntity<BookResponse> {
        val updatedBook = service.partiallyUpdateBook(isbn, request.title, request.author, request.price, request.image)
        return ResponseEntity.ok(updatedBook.toDto())
    }

    override fun deleteBook(isbn: String): ResponseEntity<Unit> {
        service.deleteBook(isbn)
        return ResponseEntity.noContent().build()
    }

    override fun listReviews(isbn: String): ResponseEntity<List<ReviewResponse>> {
        val response = service.getReviewsForBook(isbn).map { it.toDto() }
        return ResponseEntity.ok(response)
    }

    override fun createReview(isbn: String, request: ReviewCreateRequest): ResponseEntity<Unit> {
        val review = service.createReview(isbn, request.rating, request.comment)

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(review.id).toUri()
        return ResponseEntity.created(location).build()
    }

    override fun replaceReview(isbn: String, reviewId: Long, request: ReviewCreateRequest): ResponseEntity<ReviewResponse> {
        val updatedReview = service.replaceReview(isbn, reviewId, request.rating, request.comment)
        return ResponseEntity.ok(updatedReview.toDto())
    }

    override fun partiallyUpdateReview(isbn: String, reviewId: Long, request: ReviewPartialUpdateRequest): ResponseEntity<ReviewResponse> {
        val updatedReview = service.partiallyUpdateReview(isbn, reviewId, request.rating, request.comment)
        return ResponseEntity.ok(updatedReview.toDto())
    }

    override fun deleteReview(isbn: String, reviewId: Long): ResponseEntity<Unit> {
        service.deleteReview(isbn, reviewId)
        return ResponseEntity.noContent().build()
    }
}