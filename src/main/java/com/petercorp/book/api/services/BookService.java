package com.petercorp.book.api.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.petercorp.book.api.entity.BookEntity;
import com.petercorp.book.api.repositories.BookRepository;
import org.springframework.data.domain.Sort;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<BookEntity> getAllBook() {
		List<BookEntity> book = new ArrayList<BookEntity>();
		book = bookRepository.findAll();
		if (book.size() == 0) {
			book = getBookFromService();
			// Save Book If table is empty
			bookRepository.saveAll(book);
		} else {
			int createWeek = 0;
			int curretWeek = 0;
			String createDate = book.get(0).getCreate_date().toString();
			createWeek = getWeekNumber(createDate);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = df.format(new Date());
			curretWeek = getWeekNumber(currentDate);
			if ((curretWeek > createWeek)) {
				// Delete All 
				bookRepository.deleteAll();
				book = getBookFromService();
				// Save Book If table is empty
				bookRepository.saveAll(book);
			}
		}
		return book;
	}

	// Get Book From External Service
	public List<BookEntity> getBookFromService() {
		RestTemplate restTemplate = new RestTemplate();
		String uriRec = "https://scb-test-book-publisher.herokuapp.com/books/recommendation";
		String uriBook = "https://scb-test-book-publisher.herokuapp.com/books";
		Vector vResultRec = restTemplate.getForObject(uriRec, Vector.class);
		Vector vResultBook = restTemplate.getForObject(uriBook, Vector.class);
		System.out.println("vResultRec :: " + vResultRec);
		System.out.println("vResultBook :: " + vResultBook);

		Vector vAllBook = new Vector();
		vAllBook.addAll(vResultRec);
		// Sort book name by alphabet ascending
		customSortVector(vResultRec);
		customSortVector(vResultBook);

		for (int i = 0; i < vResultBook.size(); i++) {
			HashMap hmBook = new HashMap();
			hmBook = (HashMap) vResultBook.get(i);
			if (vAllBook.contains(hmBook)) {
				int idx = vAllBook.indexOf(hmBook);
				hmBook.put("is_recommended", true);
				vAllBook.remove(idx);
				vAllBook.add(idx, hmBook);
			} else {
				hmBook.put("is_recommended", false);
				vAllBook.add(hmBook);
			}
		}
		System.out.println("vAllBook >>> " + vAllBook);
		List<BookEntity> book = new ArrayList<BookEntity>();
		for (int i = 0; i < vAllBook.size(); i++) {
			HashMap hmAllBook = new HashMap();
			hmAllBook = (HashMap) vAllBook.get(i);
			BookEntity bookEntity = new BookEntity();
			book.add(bookEntity.builder().author_name((String) hmAllBook.get("author_name"))
					.book_name((String) hmAllBook.get("book_name")).id((Integer) hmAllBook.get("id"))
					.price(Double.parseDouble(String.valueOf(hmAllBook.get("price"))))
					.is_recommended((boolean) hmAllBook.get("is_recommended")).build());
		}

		System.out.println("book >>> " + book);
		return book;
	}
	
	
	public Optional<BookEntity> getBookById(int id) {
		return bookRepository.findById(id);
	}
	
	

	public void customSortVector(Vector vSort) {
		Vector vResult = new Vector();

		Collections.sort(vSort, new Comparator<HashMap<String, Object>>() {
			@Override
			public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
				return ((String) o1.get("book_name")).compareTo((String) o2.get("book_name"));
			}
		});

	}

	private int getWeekNumber(String strDate) {
		String format = "yyyy-MM-dd";
		int week = 0;
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date;
		try {
			date = df.parse(strDate);
			Calendar calen = Calendar.getInstance();
			calen.setTime(date);
			week = calen.get(Calendar.WEEK_OF_YEAR);
			System.out.println("str date input " + strDate);
			System.out.println("week " + week);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return week;
	}
	

}
