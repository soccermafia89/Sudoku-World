/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.sudoku;

import ethier.alex.world.addon.FilterListBuilder;
import ethier.alex.world.addon.PartitionBuilder;
import ethier.alex.world.core.data.FilterList;
import ethier.alex.world.core.data.Partition;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
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
        

*/
public final class Sudoku {
    
    private static Logger logger = Logger.getLogger(Sudoku.class);
    int[] radices;
    Collection<FilterList> filters;
    int boardLength;
    int boxLength;// = SQRT(boardLength)
    
    public Sudoku(int[][] initialWorld) {
        boardLength = initialWorld.length;
        boxLength = (int) Math.sqrt(boardLength);
        if(boxLength * boxLength != boardLength) {
            throw new RuntimeException("Invalid board/box length: " + boardLength + "/" + boxLength);
        }
        
        filters = new ArrayList<FilterList>();
        radices = new int[boardLength * boardLength];
        
        for(int i=0; i < radices.length;i++) {
            radices[i] = boardLength;
        }
        
        this.applyStaticFilters();
        
        this.applyDefiniteFilters(initialWorld);
        this.applyRowFilters(initialWorld);
        this.applyColFilters(initialWorld);
        this.applyBoxFilters(initialWorld);
    }
    
    public void applyStaticFilters() {
        logger.error("TODO:NOT FINISHED IMPLEMENTATION");
        this.applyStaticRowFilters();
        this.applyStaticColFilters();
        this.applyStaticBoxFilters();
    }
    
    public void applyStaticRowFilters() {
        // First generate a base set of offsets, then update them for each column and value.
        
        // Now transform the offsets for all the cases we need.
        for(int value=0; value < boardLength; value++) {
            for(int col=0; col < boardLength; col++) {
                logger.error("TODO:NOT FINISHED IMPLEMENTATION");
            }
        }
    }
    
    public void applyStaticColFilters() {
        
    }
    
    public void applyStaticBoxFilters() {
        
    }
    
    public void applyBoxFilters(int[][] initialWorld) {
        for(int row = 0;row < initialWorld.length;row++) {
            for(int col = 0;col < initialWorld.length;col++) {
                int value = initialWorld[row][col];
                                
                if(value == 0) {
                    
                    int pos = this.getOffset(row, col);
                    
                    //Get the position of the upper left box
                    int boxColPos = col - (col % boxLength);
                    int boxRowPos = row - (row % boxLength);
                    int boxPos = boxRowPos + boardLength*boxColPos;
                    
                    for(int boxRow=0; boxRow < boxLength;boxRow++) {
                        for(int boxCol=0; boxCol < boxLength;boxCol++) {
                            int offset = boxPos + boxRow + boardLength*boxCol;
                            
                            if(offset != pos) {
                                FilterList newFilter = this.quickCreateFilter(value, offset);
                                logger.info("Adding definite box filter: " + newFilter);
                                filters.add(newFilter);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void applyColFilters(int[][] initialWorld) {
        for(int row = 0;row < initialWorld.length;row++) {
            for(int col = 0;col < initialWorld.length;col++) {
                int value = initialWorld[row][col];
                
                if(value == 0) {
                    
                    for(int i=0;i < boardLength;i++) {
                        int offset = this.getOffset(row, col);
                        FilterList newFilter = this.quickCreateFilter(value, offset);
                        logger.info("Adding definite col filter: " + newFilter);
                        filters.add(newFilter);
                    }
                }
            }
        }
    }
    
    public void applyRowFilters(int[][] initialWorld) {
        for(int row = 0;row < initialWorld.length;row++) {
            for(int col = 0;col < initialWorld.length;col++) {
                int value = initialWorld[row][col];
                
                if(value == 0) {
                    
                    for(int filterRow=0;filterRow < boardLength;filterRow++) {
                        int offset = this.getOffset(filterRow, col);
                        FilterList newFilter = this.quickCreateFilter(value, offset);
                        logger.info("Adding definite row filter: " + newFilter);
                        filters.add(newFilter);
                    }
                }
            }
        }
    }
    
    public void applyDefiniteFilters(int[][] initialWorld) {
        for(int row = 0;row < initialWorld.length;row++) {
            for(int col = 0;col < initialWorld.length;col++) {
                int value = initialWorld[row][col];
                
                if(value == 0) {
                    for(int i=0; i < boardLength;i++) {
                        if( (i + 1) != value) {
                            int offset = row + col*initialWorld[row].length;
                            FilterList newFilter = this.quickCreateFilter(value, offset);
                            logger.info("Adding definite filter: " + newFilter);
                            filters.add(newFilter);
                        }
                    }
                }
            }
        }
    }
    
    public Partition createRootPartition() {
        
        return PartitionBuilder.newInstance()
                .setBlankWorld()
                .setRadices(radices)
                .addFilters(filters)
                .getPartition();
    }
    
    public int getOffset(int row, int col) {
        return row + boardLength*col;
    }
    
    public FilterList quickCreateFilter(int value, int offset) {
        String filterStr = StringUtils.leftPad("", radices.length, "*");
        filterStr = filterStr.substring(0, offset) + value + filterStr.substring(offset + 1);


        return FilterListBuilder.newInstance()
                .setQuick(filterStr)
                .getFilterList();
    }
}
