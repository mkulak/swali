package swali.utils

import javatools.parsers.PlingStemmer


object WordUtils {
    fun isPlural(word: String): Boolean = PlingStemmer.isPlural(word)
}