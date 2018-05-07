package swali.utils


object WordUtils {
    val pluralWhitelist = setOf("vat", "api")

    fun isPlural(s: String): Boolean = s in pluralWhitelist || s.isNotEmpty() //TODO: implement
}