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
        assertFalse(ten.isFaceCard())
        assertTrue(jack.isFaceCard())
        assertTrue(queen.isFaceCard())
        assertTrue(king.isFaceCard())
        assertFalse(ace.isFaceCard())
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
        assertEquals(5, cards?.size)
    }

    @Test
    fun `create deck from file`() {
        // Given
        val cards = createDeck("deck_five_cards.txt")

        // Then
        assertEquals(5, cards.size)
    }

    @Test
    fun `create deck without file`() {
        // Given
        val cards = createDeck()

        // Then
        assertEquals(52, cards.size)
        assertEquals(cards.size, cards.distinct().size)
    }

    @Test
    fun `remove first n cards from deck`() {
        // Given
        val cards = createDeck()
        val originalSize = cards.size

        // When
        val n = 8
        val card9 = cards[n]
        cards.removeFirst(n)

        // Then
        assertEquals(originalSize - n, cards.size)
        assertTrue(cards.first() == card9)
    }
}