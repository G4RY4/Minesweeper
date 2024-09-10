package Minesweeper;

import java.util.Random;
import javax.swing.Timer;

public class Game {
    final static int EASY_MODE_SIZE = 10, MEDIUM_MODE_SIZE = 20, HARD_MODE_SIZE = 25, BOMB = 0, NON_BOMB = 1, ONGOING = 0, LOST = -1, WON = 1;
    private int sizeOfTheBoard, numberOfBombs, positionX, positionY, gameState;
    private boolean isFirstClick;
    private Field field[][];
    private Timer timer;
    public Game(int sizeOfTheBoard) {
        this.sizeOfTheBoard = sizeOfTheBoard;
        this.numberOfBombs = howManyBombs(sizeOfTheBoard);
        this.isFirstClick = true;
        this.gameState = ONGOING;
        this.field = new Field[sizeOfTheBoard][sizeOfTheBoard];
    }
    protected void initializeFields() {
        for(int y = 0; y < sizeOfTheBoard; y++)
            for (int x = 0; x < sizeOfTheBoard; x++)
                field[y][x] = new nonBomb(this);
    }
    protected void initializeBombs(int yPositionOfCursor, int xPositionOfCursor){
        Random random = new Random();
        for (int i = 0; i < numberOfBombs; i++) {
            do {
                positionY = random.nextInt(sizeOfTheBoard);
                positionX = random.nextInt(sizeOfTheBoard);
            } while (field[positionY][positionX].getFieldType() == BOMB || isInEmptyFirstClickSpace(yPositionOfCursor, xPositionOfCursor));
            field[positionY][positionX] = new Bomb(this);
        }
    }
    private boolean isInEmptyFirstClickSpace(int yPositionOfCursor, int xPositionOfCursor) {
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (positionY == yPositionOfCursor + i && positionX == xPositionOfCursor + j)
                    return true;
        return false;
    }
    private int howManyBombs(int sizeOfTheBoard) {
        return switch (sizeOfTheBoard) {
            case EASY_MODE_SIZE -> 16;
            case MEDIUM_MODE_SIZE -> 64;
            case HARD_MODE_SIZE -> 100;
            default -> -1;
        };
    }
    public boolean isBomb(int positionY, int positionX) {
        return field[positionY][positionX].getFieldType() == BOMB;
    }
    public void handlingLeftClick(int positionY, int positionX) {
        field[positionY][positionX].leftClickAction(positionY,positionX,field);
    }
    public void handlingRightClick(int positionY, int positionX) {
        field[positionY][positionX].rightClickAction(positionY,positionX);
    }
    public String getFieldSymbol(int positionY, int positionX) {
        return field[positionY][positionX].getSymbol();
    }
    public boolean getIsRevealed(int positionY, int positionX) {
        return field[positionY][positionX].isRevealed;
    }
    public boolean getIsFirstClick() {
        return isFirstClick;
    }
    public void setIsFirstClick(boolean isFirstClick) {
        this.isFirstClick = isFirstClick;
    }
    public int getSizeOfTheBoard() {
        return sizeOfTheBoard;
    }
    public void loseGame() {
        gameState = LOST;
    }
    public int getGameState() {
        return gameState;
    }
    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
}