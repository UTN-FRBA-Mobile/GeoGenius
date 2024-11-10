package com.utnfrba.geogenius.widget

object WidgetSettings2 {
    private var bookmarkAmount: Int = 1

    fun getBookmarkAmount(): Int {
        return bookmarkAmount
    }

    fun setBookmarkAmount(newAmount: Int) {
        bookmarkAmount = newAmount
    }

}