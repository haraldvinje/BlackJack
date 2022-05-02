fun cardToPoints(card: Card): Int {
    return if (card.isFaceCard()) {
        10
    } else if (card.isAce()){
        11
    } else {
        card.value.numericalValue
    }
}

data class Hand(val cards: MutableList<Card>, val isDealer: Boolean = false) {
    init {
        require(cards.size == 2) { "A hand must be initialized with exactly 2 cards" }
    }
    fun score() = cards.sumOf { cardToPoints(it) }
    fun isBlackJack() = score() == 21

    override fun toString(): String {
        val player = if (isDealer) "dealer" else "sam"
        val cardsString  = cards.fold(""){ acc, card -> "$acc, $card"}.trimStart(',', ' ')
        return "${player}: $cardsString"
    }
}


class Game(private val deck: MutableList<Card>) {

    val samHand: Hand
    val dealerHand: Hand
    private var samStands = false

    init {
        require(deck.size >= 4) { "Deck must have at least 4 cards" }
        val (card1, card2, card3, card4) = deck.removeFirst(4)
        samHand = Hand(mutableListOf(card1, card3))
        dealerHand = Hand(mutableListOf(card2, card4), isDealer = true)
    }

    fun setSamStands() {
        samStands = true
    }

    fun isSamsMove(): Boolean {
        return deck.isNotEmpty() && !samStands && samHand.score() < 17
    }

    fun hitSam() {
        if (isSamsMove()) samHand.cards.add(deck.removeFirst())
    }

    fun dealerMoves() {
        while (dealerHand.score() <= samHand.score() && deck.isNotEmpty()) {
            dealerHand.cards.add(deck.removeFirst())
        }
    }

    fun isOver(): Boolean {
        return deck.isEmpty() ||
                samHand.score() >= 21 ||
                dealerHand.score() >= 21 ||
                (samHand.score() > 17 && dealerHand.score() > samHand.score())
    }

    fun samWon(): Boolean {
        return isOver() &&
            samHand.isBlackJack()
                || (samHand.score() < 21
                && (samHand.score() > dealerHand.score() || (dealerHand.score() > 21)))
    }

    fun printGameState() {
        val dealerHandWithHiddenCard = dealerHand.toString().replace(Regex(": [A-Z\\d]{2,3},"), ": XX,")
        println(samHand)
        println(dealerHandWithHiddenCard)
    }

    fun printResult() {
        val winner = if (samWon()) "sam" else "dealer"
        println("The game is over. Results:")
        println(winner)
        println(samHand)
        println(dealerHand)
    }

}