import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameTest {

    @Test
    fun `sam wins if both start with blackjack`() {
        // Given
        val cards = listOf("CA", "DA", "CK", "DK").map { stringToCard(it) }.toMutableList()

        val game = Game(cards)

        // Then
        assertTrue(game.isOver() && game.samWon())
    }

    @Test
    fun `dealer wins if both start with 22`() {
        // Given
        val cards = listOf("CA", "DA", "HA", "SA").map { stringToCard(it) }.toMutableList()
        val game = Game(cards)

        // Then
        assertTrue(game.isOver() && !game.samWon())
    }

    @Test
    fun `sam is finished when score is 17 or above`() {
        // Given
        val cards = listOf("C8", "D8", "H6", "S8", "C3", "C5", "C9").map { stringToCard(it) }.toMutableList()
        val game = Game(cards)

        // When
        game.hitSam()

        // Then
        assertTrue(!game.isOver() && !game.isSamsMove())
    }

    @Test
    fun `dealer draws until score is higher than sam's`() {
        // Given
        val cards = listOf("C8", "D8", "H6", "S8", "C3", "C5", "C9").map { stringToCard(it) }.toMutableList()
        val game = Game(cards)

        // When
        game.samStands()
        game.dealerMoves()

        // Then
        assertTrue(game.samHand.score() < game.dealerHand.score())
    }
}