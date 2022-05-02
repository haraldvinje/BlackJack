import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val deck: MutableList<Card> = if (args.isNotEmpty()) {
        createDeck(args[0])
    } else {
        createDeck()
    }

    val game = Game(deck)

    if (game.isOver()) {
        game.printResult()
        exitProcess(0)
    }

    while (game.isSamsMove()) {
        game.printGameState()
        println("Choose option by pressing one of the following keys:\nH: Hit\nS: Stand")
        when (readln().uppercase().trim()) {
            "H" -> game.hitSam()
            "S" -> game.setSamStands()
            else -> println("Invalid input")
        }
    }

    if (game.isOver()){
        game.printResult()
        exitProcess(0)
    }

    game.dealerMoves()
    game.printResult()
}