import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameTest {

    @Test
    fun `sam wins if both start with blackjack`() {
        // Given
        val cards = listOf("CA", "DA", "CK", "DK").map { stringToCard(it) }.toMutableList()

        val game = Game(cards)

        // Then
        assertTrue(game.isOver())
        assertTrue(game.samWon())
    }

    @Test
    fun `dealer wins if both start with 22`() {
        // Given
        val cards = listOf("CA", "DA", "HA", "SA").map { stringToCard(it) }.toMutableList()
        val game = Game(cards)

        // Then
        assertTrue(game.isOver())
        assertTrue(!game.samWon())
    }

    @Test
    fun `sam is finished when score is 17 or above`() {
        // Given
        val cards = listOf("C8", "D8", "H6", "S8", "C3", "C5", "C9").map { stringToCard(it) }.toMutableList()
        val game = Game(cards)

        // When
        game.hitSam()

        // Then
        assertTrue(!game.isOver())
        assertTrue(!game.isSamsMove())
    }

    @Test
    fun `dealer draws until score is higher than sam's`() {
        // Given
        val cards = listOf("C8", "D8", "H10", "S8", "C3", "C5", "C9").map { stringToCard(it) }.toMutableList()
        val game = Game(cards)

        // When
        game.setSamStands()
        game.dealerMoves()

        // Then
        assertTrue(game.samHand.score() < game.dealerHand.score())
    }

    @Test
    fun `deck with low score`() {
        // Given
        val cards = listOf("C3", "H4", "D4", "C4", "S7").map { stringToCard(it) }.toMutableList()
        val game = Game(cards)

        // When
        game.hitSam()
        game.dealerMoves()

        // Then
        assertTrue(game.isOver())
        assertTrue(game.samWon())
    }

    @Test
    fun `deck with too few cards throws exception`() {
        // Given
        val cards = listOf("C3", "H4", "D4").map { stringToCard(it) }.toMutableList()
        assertThrows(java.lang.IllegalArgumentException::class.java) {
           Game(cards)
        }
    }
}