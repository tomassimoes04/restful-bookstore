
package pt.unl.fct.iadi.bookstore.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import pt.unl.fct.iadi.bookstore.controller.dto.ErrorResponse
import pt.unl.fct.iadi.bookstore.service.*

@RestControllerAdvice
class GlobalExceptionHandler {

    private fun getLanguage(request: HttpServletRequest): String {
        val header = request.getHeader("Accept-Language") ?: "en"
        return if (header.lowercase().startsWith("pt")) "pt" else "en"
    }

    @ExceptionHandler(BookNotFoundException::class)
    fun handleBookNotFound(ex: BookNotFoundException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val lang = getLanguage(request)
        val message = if (lang == "pt") "Livro com ISBN ${ex.isbn} não encontrado"
        else "Book with ISBN ${ex.isbn} not found"

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .header("Content-Language", lang)
            .body(ErrorResponse("NOT_FOUND", message))
    }

    @ExceptionHandler(ReviewNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleReviewNotFound(ex: ReviewNotFoundException): ErrorResponse {
        return ErrorResponse("NOT_FOUND", "Review with ID ${ex.id} not found")
    }

    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleBookConflict(ex: BookAlreadyExistsException): ErrorResponse {
        return ErrorResponse("CONFLICT", "Book with ISBN ${ex.isbn} already exists")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ErrorResponse {
        val errors = ex.bindingResult.fieldErrors.joinToString("; ") {
            "${it.field}: ${it.defaultMessage}"
        }
        return ErrorResponse("VALIDATION_ERROR", errors)
    }
}

