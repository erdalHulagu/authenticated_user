package com.adem.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adem.DTO.BookDTO;
import com.adem.domain.Book;
import com.adem.domain.Image;
import com.adem.domain.User;
import com.adem.exception.ResourceNotFoundException;
import com.adem.exception.message.ErrorMessage;
import com.adem.mapper.BookMapper;
import com.adem.repository.BookRepository;
import com.adem.repository.UserRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	public void createBookByUser(String imageId, BookDTO bookDTO) {

		User currentUser = userService.getCurrentUser();

		Image image = imageService.getImageById(imageId);

		Set<Image> images = new HashSet<>();

		images.add(image);

		Book book = bookMapper.bookDTOToBook(bookDTO);

		book.setImageOfBook(images);
		
		Set<User> usrSet=new HashSet<>();
		usrSet.add(currentUser);

		Book newBook = bookRepository.save(book);

		Set<Book> books = new HashSet<>();
		books.add(newBook);
		currentUser.setBooks(books);
	    newBook.setUsers(usrSet);
		
	}

	public BookDTO getBookById(Long bookId) {
		Book book = getBook(bookId);

		BookDTO bookDTO = bookMapper.bookToBookDTO(book);

		return bookDTO;

	}

	public Book getBook(Long id) {

		Book book = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return book;

	}

}
