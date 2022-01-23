class Move{
  Square start;
  Square landing;
  int score;

  public Move(Square x, Square y, int s){
    start = x;
    landing = y;
    score = s;
  }

  public Move(){
    
  }

  public Square getStart(){
    return start;
  }

  public Square getLanding(){
    return landing;
  }

  public int getScore(){
    return score;
  }

}
