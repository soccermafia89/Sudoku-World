/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.implement2;

import ethier.alex.world.core.data.FilterList;
import ethier.alex.world.core.data.FilterState;
import org.apache.log4j.Logger;

/**

 @author alex
 */
public class Board {

    private static Logger logger = Logger.getLogger(Board.class);
    int[] board;
    TileState[] boardState;

    public Board() {
        board = new int[81];
        boardState = new TileState[81];

        for (int i = 0; i < boardState.length; i++) {
            boardState[i] = TileState.UNKNOWN;
        }
    }
    
    public int getBoardValue(int row, int col) {
        int offset = Board.getOffset(row, col);
        return board[offset];
    }
    
    public TileState getTileState(int row, int col) {
        int offset = Board.getOffset(row, col);
        return boardState[offset];
    }
    
//    public int[] getBoardValues() {
//        return board;
//    }
//    
//    public TileState[] getTileStates() {
//        return boardState;
//    }

    public void updateRowCol(int row, int col, int value, TileState tileState) {
        int offset = Board.getOffset(row, col);

        board[offset] = value;
        boardState[offset] = tileState;
    }

//    public void updateBoxOffset(int box, int offset, int value) {
//        
//    }
    
//    public Iterator<Integer> getRowIterator(int col) {
//        
//    }

    public void printBoard() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < 9; row++) {
            stringBuilder.append("[");
            for (int col = 0; col < 9; col++) {
                int offset = Board.getOffset(row, col);
                int value = board[offset];

                if (boardState[offset] == TileState.UNKNOWN) {
                    stringBuilder.append("* ,");
                } else if (boardState[offset] == TileState.KNOWN) {
                    stringBuilder.append(value).append(" ,");
                } else if (boardState[offset] == TileState.GUESS) {
                    stringBuilder.append(value).append("',");
                }
            }
            stringBuilder.append("]\n");
        }

        logger.info(stringBuilder.toString());
    }
    
    public static int getOffset(int row, int col) {
        return row + 9 * col;
    }
    
    public static void printFilter(FilterList filter) {
                StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < 9; row++) {
            stringBuilder.append("[");
            for (int col = 0; col < 9; col++) {
                int offset = Board.getOffset(row, col);
                int value = filter.getFilter(offset).getOrdinal();
                FilterState filterState = filter.getFilterStates()[offset];

                if (filterState == FilterState.ALL) {
                    stringBuilder.append("* ,");
                } else if (filterState == FilterState.ONE) {
                    stringBuilder.append(value).append(" ,");
                }
            }
            stringBuilder.append("]\n");
        }

        logger.info(stringBuilder.toString());
    }
}
