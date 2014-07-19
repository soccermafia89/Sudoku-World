/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.implement2;

import ethier.alex.world.implement2.Board;
import ethier.alex.world.addon.PartitionBuilder;
import ethier.alex.world.core.data.FilterList;
import ethier.alex.world.core.data.Partition;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;

/**

 @author alex
 */

/*

TODO: PLANS

-Use Guava Table interface to simplify data structures and put them into a double array row/col format.
-Come up with a recursive way to continually guess more cells if needed.  Guesses need to keep track of their state
    so that they can properly be rolled back if determined to be invalid.

-Instead of inferencing new filters.  
    And then running through a try of guesses,
    Remove all static filters:
        -remove all cases where two of the same number exist on the same row, col, box, etc.
        -consider reverting to old suduko class.
        

*/

public class Sudoku2Old {
    
    private static Logger logger = Logger.getLogger(Sudoku2Old.class);
    int[] radices;
    Collection<FilterList> filters;
    Board board;
    
    public Sudoku2Old(Board initialBoard) {
        filters = new ArrayList<FilterList>();
        radices = new int[81];
        
        for(int i=0; i < radices.length;i++) {
            radices[i] = 10;
        }
        
        board = initialBoard;
    }
    
    public Collection<FilterList> getFilters(int row, int col) {
        Collection<FilterList> filters = new ArrayList<FilterList>();

//        int offset = Board.getOffset(row, col);
        TileState tileState = board.getTileState(row, col);
        if (tileState == TileState.UNKNOWN) {
            return filters;
        } else {
            filters.addAll(this.generateRowFilters(row, col));
            filters.addAll(this.generateColFilters(row, col));
            filters.addAll(this.generateBoxFilters(row, col));
        }

        return filters;
    }

    private Collection<FilterList> generateRowFilters(int row, int col) {
//        for(int i=0;i < 9;i++) {
//            
//            int offset = Board.getOffset(row, col);
//            int value = board.getBoardValue(row, col);
//
//            String filterStr = StringUtils.leftPad("", radices.length, "*");
//            filterStr = filterStr.substring(0, offset) + value + filterStr.substring(offset + 1);
//
//
//            FilterList newFilter = FilterListBuilder.newInstance()
//                    .setQuick(filterStr)
//                    .getFilterList();
//
//            logger.info("Adding definite row filter: " + newFilter);
//            filters.add(newFilter);
//        }
        return null;
    }

    private Collection<FilterList> generateColFilters(int row, int col) {
        return null;
    }

    private Collection<FilterList> generateBoxFilters(int row, int col) {
        return null;
    }
    
    public Partition createRootPartition() {
        
        return PartitionBuilder.newInstance()
                .setBlankWorld()
                .setRadices(radices)
                .addFilters(filters)
                .getPartition();
    }
}
