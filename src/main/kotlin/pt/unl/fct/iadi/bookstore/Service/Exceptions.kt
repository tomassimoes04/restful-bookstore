package pt.unl.fct.iadi.bookstore.service

class BookNotFoundException(val isbn: String) : RuntimeException()
class BookAlreadyExistsException(val isbn: String) : RuntimeException()
class ReviewNotFoundException(val id: Long) : RuntimeException()