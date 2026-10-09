package com.shopika.app.util

import java.text.NumberFormat
import java.util.Locale

object RupiahUtils {
    fun format(amount: Long): String {
        val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"))
        format.maximumFractionDigits = 0
        return format.format(amount).replace("Rp", "Rp")
    }

    fun format(amount: Double): String {
        return format(amount.toLong())
    }
}
