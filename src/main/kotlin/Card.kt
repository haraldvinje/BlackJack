import java.io.File

enum class Suit {
    C, D, H, S
}

data class CardValue(
    val numericalValue: Int,
    val name: String = numericalValue.toString()
) {
    override fun toString(): String {
        return name
    }
}

data class Card(val suit: Suit, val value: CardValue) {
    fun isFaceCard(): Boolean {
        return value.numericalValue in setOf(11, 12, 13)
    }
    fun isAce(): Boolean {
        return value.numericalValue == 14
    }
    override fun toString(): String {
        return "${suit}${value}"
    }
}

fun stringToCard(cardString: String): Card {
    val suit = Suit.valueOf(cardString[0].toString())
    val value = cardString.substring(1)
    val numericalValue = when (value) {
        "J" -> 11
        "Q" -> 12
        "K" -> 13
        "A" -> 14
        else -> value.toInt()
    }
    return Card(suit, CardValue(numericalValue, value))
}


fun readCardsFromFile(filename: String): List<String>? {
    return try {
        File(filename).reader().readLines()[0].split(", ")
    } catch(e: Exception) {
        println("WARNING: Failed reading cards from input file ${filename}, creating a new deck.\nException: $e\n")
        null
    }
}

fun createDeck(filename: String? = null): MutableList<Card> {
    val suits = Suit.values()
    val values: List<CardValue> = IntRange(2, 10).map { CardValue(it) }.plus(
        setOf(
            CardValue(11, "J"),
            CardValue(12, "Q"),
            CardValue(13, "K"),
            CardValue(14, "A"),
        )
    )
    val allCards = suits.flatMap { suit -> values.map { value -> Card(suit, value) } }.shuffled().toMutableList()

    return if (filename != null) {
        readCardsFromFile(filename)?.map { stringToCard(it) }?.shuffled()?.toMutableList() ?: allCards
    } else {
        allCards
    }
}

fun MutableList<Card>.removeFirst(n: Int): List<Card> {
    val list = slice(0 until n)
    this.removeAll(list)
    return list
}