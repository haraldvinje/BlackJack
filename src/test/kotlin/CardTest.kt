import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CardTest {

    @Test
    fun isFaceCard() {
        // Given
        val ten = Card(Suit.C, CardValue(10))
        val jack = Card(Suit.C, CardValue(11))
        val queen = Card(Suit.C, CardValue(12))
        val king = Card(Suit.C, CardValue(13))
        val ace = Card(Suit.C, CardValue(14))

        // Then
        assertTrue(!ten.isFaceCard())
        assertTrue(jack.isFaceCard())
        assertTrue(queen.isFaceCard())
        assertTrue(king.isFaceCard())
        assertTrue(!ace.isFaceCard())
    }

    @Test
    fun isAce() {
        // Given
        val king = Card(Suit.C, CardValue(13))
        val ace = Card(Suit.C, CardValue(14))

        // Then
        assertTrue(!king.isAce())
        assertTrue(ace.isAce())
    }

    @Test
    fun `read cards from file`() {
        // Given
        val cards = readCardsFromFile("deck_five_cards.txt")

        // Then
        assertTrue(cards?.size == 5)
    }

    @Test
    fun `create deck from file`() {
        // Given
        val cards = createDeck("deck_five_cards.txt")

        // Then
        assertTrue(cards.size == 5)
    }

    @Test
    fun `create deck without file`() {
        // Given
        val cards = createDeck()

        // Then
        assertTrue(cards.size == 52)
        assertTrue(cards.distinct().size == cards.size)
    }

    @Test
    fun `remove first n cards from deck`() {
        // Given
        val cards = createDeck()
        val originalSize = cards.size

        // When
        val n = 8
        cards.removeFirst(n)

        // Then
        assertTrue(cards.size == originalSize - n)
    }
}