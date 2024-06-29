package com.study.library.dto;

import com.study.library.model.Book;
import com.study.library.model.Borrow;

/**
 * 图书借阅记录信息
 */
public class BookBorrow {
    private Book book;
    private Borrow borrow;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Borrow getBorrow() {
        return borrow;
    }

    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
    }
}
