import java.util.*;

public class AIAgent {
  Random rand;

  public AIAgent() {
    rand = new Random();
  }

  /*
   * method randomMove takes as input a stack of potential moves that the AI
   * agent
   * can make. The agent uses a random num generator to select a move the inputted
   * Stack and returns this to the calling agent.
   * 
   */

  /*
   * Score System :
   * Pawn : 1
   * Knight/Bishop : 3
   * Rook : 5
   * Queen : 9
   * 
   */

  public Move SelectedStrategy(int strategy, Stack whiteMoves, Stack blackMoves) {
    Move move = new Move();
    // Random Move Selection
    int S = strategy;
    switch (S) {
      case 0:
        System.out.println(" You chose Random Move Selected");

        move = randomMove(whiteMoves);

        return move;

      case 1:
        System.out.println(" You chose Next Best Move Selected");
        move = nextBestMove(whiteMoves);

        return move;

      case 2:
        System.out.println("You chose 2 Levels Deep Selected");
        move = twoLevelsDeep(whiteMoves, blackMoves);

        return move;

    }

    return move;
  }

  public Move randomMove(Stack possibilities) {

    int moveID = rand.nextInt(possibilities.size());
    System.out.println("Agent randomly selected move : " + moveID);
    for (int i = 1; i < (possibilities.size() - (moveID)); i++) {
      possibilities.pop();
    }
    Move selectedMove = (Move) possibilities.pop();
    return selectedMove;
  }

  // tesing //black

  // Clone creates a new instance of the object and initializes its fields ontents
  // of the corresponding fields of this object
  public Move nextBestMove(Stack possibilitiesWhite) {

    int points = 0;
    // This is the return move
    Move selectedMove = null;

    Stack random = (Stack) possibilitiesWhite.clone();

    // Make sure there is possiblites
    // We will store them in the Move obj
    while (!possibilitiesWhite.isEmpty()) {

      Move move = (Move) possibilitiesWhite.pop();

      if (!(possibilitiesWhite.size() == 1)) {

        if (points < move.getScore()) {
          points = move.getScore();
          selectedMove = move;
        }
      }

      if ((possibilitiesWhite.size() == 1) && points == 0) {
        selectedMove = (Move) possibilitiesWhite.pop();
      }

    }

    if (points > 0) {

      return selectedMove;

    } else {
      // Makes a random move when there's no score to be added
      // We will feed the randomMove method with the random stock clone
      return randomMove(random);

    }

  }

  // Min - Max
  // Look into using NegaMax

  public Move twoLevelsDeep(Stack possibilitesWhite, Stack possibilitesBlack) {

    // Cloning the Object
    Stack WhiteClone = (Stack) possibilitesWhite.clone();
    Stack blackClone = (Stack) possibilitesBlack.clone();
    int score = 0;
    int chosenPieceScore = 0;
    Move bestMove = null;
    Move whiteMove;
    Move presentMove;
    Square blackPosition;

    int PawnValue = 1;
    int KnightValue = 3;
    int BishopValue = 3;
    int RookValue = 5;
    int QueenValue = 9;
    int KingValue = 10;

    while (possibilitesWhite.size() > 1) {
      whiteMove = (Move) possibilitesWhite.pop();
      presentMove = whiteMove;

      // Get the Best Score based on Score
      if (score > chosenPieceScore) {
        chosenPieceScore = score;
        bestMove = presentMove;
      }

      while (!blackClone.isEmpty()) {
        score = 0;
        blackPosition = (Square) blackClone.pop();
        if ((presentMove.getLanding().getXC() == blackPosition.getXC())
            && (presentMove.getLanding().getYC() == blackPosition.getYC())) {

          if (blackPosition.getName().equals("BlackQueen")) {
            score = QueenValue;
          } else if (blackPosition.getName().equals("BlackRook")) {
            score = RookValue;
          } else if (blackPosition.getName().equals("BlackKnight")) {
            score = KnightValue;

          } else if (blackPosition.getName().equals("BlackBishop")) {
            score = BishopValue;

          } else if (blackPosition.getName().equals("BlackPawn")) {
            score = PawnValue;
          } else {
            score = KingValue;
          }
        }

        // This will get the best move
        if (score > chosenPieceScore) {
          chosenPieceScore = score;
          bestMove = presentMove;
        }
      }

      // Get clone of the updated Possiblities Stack

      blackClone = (Stack) possibilitesBlack.clone();
    }

    // Make a Random Move
    if (chosenPieceScore > 0) {
      System.out.println("Selected AI Agent - Two Level Deep: " + chosenPieceScore);
      return bestMove;
    }
    return randomMove(WhiteClone);
  }

}
