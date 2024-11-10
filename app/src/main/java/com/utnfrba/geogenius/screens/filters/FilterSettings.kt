package com.utnfrba.geogenius.screens.filters


object FilterSettings {
    private var cafeChecked: Boolean = false
    private var museumChecked: Boolean = false
    private var parkChecked: Boolean = false

    fun getCafeChecked(): Boolean {
        return cafeChecked
    }

    fun setCafeChecked(newValue: Boolean) {
        cafeChecked = newValue
    }

    fun getMuseumChecked(): Boolean {
        return museumChecked
    }

    fun setMuseumChecked(newValue: Boolean) {
        museumChecked = newValue
    }

    fun getParkChecked(): Boolean {
        return parkChecked
    }

    fun setParkChecked(newValue: Boolean) {
        parkChecked = newValue
    }
}
