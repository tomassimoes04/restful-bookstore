package pt.unl.fct.iadi.bookstore.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.bookstore.domain.Book
import pt.unl.fct.iadi.bookstore.domain.Review
import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class BookstoreService {

    private val books = ConcurrentHashMap<String, Book>()
    private val reviews = ConcurrentHashMap<Long, Review>()
    private val reviewIdGenerator = AtomicLong(1)


    fun getAllBooks(): List<Book> = books.values.toList()

    fun createBook(book: Book): Book {
        if (books.putIfAbsent(book.isbn, book) != null) {
            throw BookAlreadyExistsException(book.isbn)
        }
        return book
    }

    fun getBook(isbn: String): Book {
        return books[isbn] ?: throw BookNotFoundException(isbn)
    }

    fun replaceBook(isbn: String, book: Book): Book {
        val updatedBook = book.copy(isbn = isbn)
        books[isbn] = updatedBook
        return updatedBook
    }

    fun partiallyUpdateBook(isbn: String, title: String?, author: String?, price: BigDecimal?, image: String?): Book {
        val existingBook = getBook(isbn)
        title?.let { existingBook.title = it }
        author?.let { existingBook.author = it }
        price?.let { existingBook.price = it }
        image?.let { existingBook.image = it }
        return existingBook
    }

    fun deleteBook(isbn: String) {
        if (books.remove(isbn) == null) {
            throw BookNotFoundException(isbn)
        }
        val reviewsToRemove = reviews.values.filter { it.bookIsbn == isbn }.map { it.id }
        reviewsToRemove.forEach { reviews.remove(it) }
    }


    fun getReviewsForBook(isbn: String): List<Review> {
        return reviews.values.filter { it.bookIsbn == isbn }
    }

    fun createReview(isbn: String, rating: Int, comment: String?): Review {
        getBook(isbn) // Validate book exists
        val id = reviewIdGenerator.getAndIncrement()
        val review = Review(id, isbn, rating, comment)
        reviews[id] = review
        return review
    }

    private fun getReviewAndValidateBook(isbn: String, reviewId: Long): Review {
        getBook(isbn)
        val review = reviews[reviewId] ?: throw ReviewNotFoundException(reviewId)
        if (review.bookIsbn != isbn) {
            throw ReviewNotFoundException(reviewId)
        }
        return review
    }

    fun replaceReview(isbn: String, reviewId: Long, rating: Int, comment: String?): Review {
        val existingReview = getReviewAndValidateBook(isbn, reviewId)
        existingReview.rating = rating
        existingReview.comment = comment
        return existingReview
    }

    fun partiallyUpdateReview(isbn: String, reviewId: Long, rating: Int?, comment: String?): Review {
        val existingReview = getReviewAndValidateBook(isbn, reviewId)
        rating?.let { existingReview.rating = it }
        comment?.let { existingReview.comment = it }
        return existingReview
    }

    fun deleteReview(isbn: String, reviewId: Long) {
        getReviewAndValidateBook(isbn, reviewId)
        reviews.remove(reviewId)
    }
}