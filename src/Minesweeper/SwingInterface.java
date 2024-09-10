package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingInterface implements ActionListener {
    final static int EASY_MODE_SIZE = 10, MEDIUM_MODE_SIZE = 20, HARD_MODE_SIZE = 25, margin = 20, ONGOING = 0, LOST = -1, WON = 1;
    private JFrame jFrame;
    private JPanel wholeInterface, gameBoard;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem newGame, load, save;
    private JButton[][] boardField;
    private JLabel timerLabel;
    private Toolkit toolkit;
    private Dimension dimension;
    private Game game;
    private int sizeOfTheBoard;
    public SwingInterface() {
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        jFrame = new JFrame("Minesweeper");
        jFrame.setBounds(dimension.width/3, dimension.height/12, dimension.height*2/3, dimension.height*5/6);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        wholeInterface = new JPanel(null);
        gameBoard = new JPanel(null);
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New Game");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");

        newGame.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        menu.add(newGame);
        menu.add(load);
        menu.add(save);

        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);

        /*timerLabel = new JLabel();
        timerLabel.setBounds(jFrame.getWidth() / 3, 3/2 * margin, jFrame.getWidth() / 6, jFrame.getHeight() / 10);
        wholeInterface.add(timerLabel);*/

        jFrame.add(gameBoard);
        jFrame.add(wholeInterface);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == newGame) {
            if (event.getSource() == newGame) {
                Object[] options = {"Easy", "Medium", "Hard"};
                int choice = JOptionPane.showOptionDialog(jFrame,
                        "Choose difficulty level:",
                        "New Game",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                switch (choice) {
                    case 0 -> sizeOfTheBoard = EASY_MODE_SIZE;
                    case 1 -> sizeOfTheBoard = MEDIUM_MODE_SIZE;
                    case 2 -> sizeOfTheBoard = HARD_MODE_SIZE;
                    default -> {}
                }

                game = new Game(sizeOfTheBoard);

                //inicjalizacja pól bez bomb
                game.initializeFields();
                drawBoard(sizeOfTheBoard);
            }
        }
        else if (event.getSource() == load) {

        }
        else if (event.getSource() == save) {

        }
    }

    public void drawBoard(int sizeOfTheBoard) {
        gameBoard.removeAll();

        gameBoard.setSize(wholeInterface.getHeight()*4/5, wholeInterface.getHeight()*4/5);
        gameBoard.setBounds((wholeInterface.getWidth() - gameBoard.getWidth()) / 2, wholeInterface.getY() + margin*6, gameBoard.getWidth(), gameBoard.getHeight());
        boardField = new JButton[sizeOfTheBoard][sizeOfTheBoard];
        for (int y = 0; y < sizeOfTheBoard; y++) {
            for (int x = 0; x < sizeOfTheBoard; x++) {
                boardField[y][x] = new JButton();
                boardField[y][x].setSize(gameBoard.getWidth() / sizeOfTheBoard, gameBoard.getHeight() / sizeOfTheBoard);
                boardField[y][x].setBounds(x * boardField[y][x].getWidth(), y * boardField[y][x].getHeight(), boardField[y][x].getWidth(), boardField[y][x].getHeight());

                boardField[y][x].setBackground(Color.lightGray);
                boardField[y][x].setText(game.getFieldSymbol(y, x));

                if(game.getFieldSymbol(y,x) != null && game.getFieldSymbol(y,x) != "F") {
                    boardField[y][x].setBackground(Color.darkGray);
                    if (game.getFieldSymbol(y, x) == "0")
                        boardField[y][x].setText(null);

                    if(game.getGameState() == ONGOING)
                        boardField[y][x].setEnabled(false);
                }

                //boardField[y][x].setFont(new Font("Arial", Font.PLAIN, 20));

                boardField[y][x].addMouseListener(new fieldMouseListener(y,x));

                if(game.getGameState() == LOST && game.isBomb(y, x)) {
                        boardField[y][x].setBackground(Color.RED);
                        boardField[y][x].setText(null);
                }

                gameBoard.add(boardField[y][x]);
            }
        }
        gameBoard.revalidate();
        gameBoard.repaint();
    }
    private class fieldMouseListener extends MouseAdapter {
        private final int y, x;
        public fieldMouseListener(int y, int x) {
            this.y = y;
            this.x = x;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                if(game.getIsFirstClick()) {
                    game.initializeBombs(y,x); // dodanie bomb do gry
                    game.setIsFirstClick(false);
                }

                if(game.getGameState() == ONGOING)
                    game.handlingLeftClick(y, x);
                else {
                    game.initializeFields(); // usuwam wszystkie bomby zeby moc potem dodac nowe
                    game.setGameState(ONGOING);
                    game.setIsFirstClick(true);
                }
            }
            else if(SwingUtilities.isRightMouseButton(e))
                game.handlingRightClick(y,x);

            //wyswietlenie zaktualizowanej planszy
            drawBoard(sizeOfTheBoard);
        }
    }
}
