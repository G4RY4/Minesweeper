package Minesweeper;

public class Bomb extends Field {
    public Bomb(Game game) {
        super(game, BOMB);
    }
    public void leftClickAction(int positionY, int positionX, Field[][] field) {
        game.loseGame();
        game.setIsFirstClick(true);
    }
}