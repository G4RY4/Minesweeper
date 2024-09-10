package Minesweeper;

abstract public class Field {
    protected static final int BOMB = 0, NON_BOMB = 1;
    protected int fieldType;
    protected boolean isRevealed;
    protected String symbol;
    protected Game game;
    public Field(Game game, int fieldType) {
        this.game = game;
        this.fieldType = fieldType;
        this.symbol = null;
        this.isRevealed = false;
    }
    abstract public void leftClickAction(int positionY, int positionX, Field[][] field);
    public void rightClickAction(int positionY, int positionX) {
        if (this.symbol == null)
            this.symbol = "F";
        else if(this.symbol.equals("F"))
            this.symbol = null;
    }
    public int getFieldType() {
        return fieldType;
    }
    public String getSymbol() {
        return symbol;
    }
}
