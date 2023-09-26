package com.kotlin.board.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formatDateToString(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}