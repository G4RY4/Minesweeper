package Minesweeper;

public class nonBomb extends Field{
    public nonBomb(Game game) {
        super(game, NON_BOMB);
    }
    public void leftClickAction(int positionY, int positionX, Field[][] field) {
        symbol = intToString(howManyBombsAround(positionY, positionX, field));
        if(symbol.equals("0") && !isRevealed)
            checkingNumberOfBombsAroundOfEveryNeighbour(positionY, positionX, field);
    }
    private int howManyBombsAround(int positionY, int positionX, Field[][] field) {
        int numberOfBombsAround = 0;
        for(int i = -1; i <= 1; i ++)
            for(int j = -1; j <= 1; j++)
               if((i!=0 || j!=0) && isWithinBoard(positionY + i, positionX + j) &&
                       field[positionY + i][positionX + j].fieldType == BOMB)
                   numberOfBombsAround++;

        return numberOfBombsAround;
    }
    private void checkingNumberOfBombsAroundOfEveryNeighbour(int positionY, int positionX, Field[][] field) {
        field[positionY][positionX].isRevealed = true;
        for(int i = -1; i <= 1; i ++)
            for(int j = -1; j <= 1; j++)  //field[positionY + i][positionX + j] to sasiad ktorego aktualnie sprawdzamy !
                if((i!=0 || j!=0) && isWithinBoard(positionY + i, positionX + j)) {
                    field[positionY + i][positionX + j].symbol = intToString(howManyBombsAround(positionY + i, positionX + j, field));
                    if(field[positionY + i][positionX + j].symbol.equals("0") && !field[positionY + i][positionX + j].isRevealed) // dla kazdego pola bez bomby obok sprawdzamy wszystkich sasiadow
                        checkingNumberOfBombsAroundOfEveryNeighbour(positionY + i, positionX + j, field);
                }
    }
    private String intToString(int numberOfBombsAround) {
        return switch (numberOfBombsAround) {
            case 0 -> "0";
            case 1 -> "1";
            case 2 -> "2";
            case 3 -> "3";
            case 4 -> "4";
            case 5 -> "5";
            case 6 -> "6";
            case 7 -> "7";
            case 8 -> "8";
            default -> null;
        };
    }
    private boolean isWithinBoard(int positionY, int positionX) {
        return positionY >=0 && positionY < game.getSizeOfTheBoard() && positionX >= 0 && positionX < game.getSizeOfTheBoard();
    }
}
