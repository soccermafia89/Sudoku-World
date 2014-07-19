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

-Current plan fills each tile with one out of n numbers (n is board length).  Thus the number of states a board can take is

n^(n^2) where n is board length.  This number grows FAST.

-Use Guava Table interface to simplify data structures and put them into a double array row/col format.

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
        
        this.applyStaticFilters(); // Filters that are true regardless of game.
        
        this.applyDefiniteFilters(initialWorld); // Filters that are true specific to a board.
//        this.applyRowFilters(initialWorld);
//        this.applyColFilters(initialWorld);
//        this.applyBoxFilters(initialWorld);
    }
    
    public void applyStaticFilters() {
        this.applyStaticRowColFilters();
        this.applyStaticBoxFilters();
    }
    
    // Apply the suduko rule that you can't have two of the same value within a row or col.
    public void applyStaticRowColFilters() {
    	
    	for(int value=0; value < boardLength; value++) {
    		for(int col=0; col < boardLength; col++) {
		    	for(int rowOffset1=0; rowOffset1 < boardLength -1; rowOffset1++) {
		    		
	    			int staticRowOffset1 = this.getOffset(rowOffset1, col);
	    			int staticColOffset1 = this.getOffset(col, rowOffset1);
		    		
		    		for (int rowOffset2=rowOffset1 + 1; rowOffset2 < boardLength; rowOffset2++) {
		    			int staticRowOffset2 = this.getOffset(rowOffset2, col);
	    				FilterList filter = this.quickCreateFilter(value, staticRowOffset1, staticRowOffset2);
	    				logger.info("Adding static row filter: " + filter);
	    				filters.add(filter);	
		    			
	    				// We can also cheat and get the col offset here by swapping the rows and cols.
		    			int staticColOffset2 = this.getOffset(col, rowOffset2);
	    				FilterList filter2 = this.quickCreateFilter(value, staticColOffset1, staticColOffset2);
	    				logger.info("Adding static col filter: " + filter2);
	    				filters.add(filter2);	
		
		    		}
		    	}
    		}
    	}
    }
    
    // Apply the suduko rule that you can't have two of the same value within a box.
    public void applyStaticBoxFilters() {
    	for(int value = 0; value < boardLength; value++) {
	        for(int box = 0; box < boardLength; box++) {
	        	for(int localBoxOffset1 = 0; localBoxOffset1 < boardLength -1; localBoxOffset1++) {
	    			
	        		int offset1 = this.getGlobalBoxOffset(box, localBoxOffset1);
	
	        		for(int localBoxOffset2 = localBoxOffset1 + 1; localBoxOffset2 < boardLength; localBoxOffset2++) {
	        			int offset2 = this.getGlobalBoxOffset(box, localBoxOffset2);
	    				FilterList filter = this.quickCreateFilter(value, offset1, offset2);
	    				logger.info("Adding static box filter: " + filter);
	    				filters.add(filter);	
	        		}
	        	}
	        }
    	}
    }
    
    // Apply filters containing the information of given preset tiles.
    public void applyDefiniteFilters(int[][] initialWorld) {
        for(int row = 0;row < initialWorld.length;row++) {
            for(int col = 0;col < initialWorld.length;col++) {
                int definedValue = initialWorld[row][col];
                
                if(definedValue != 0) {
                    for(int filterValue=0; filterValue < boardLength;filterValue++) {
                        if( filterValue != definedValue) {
                            int offset = this.getOffset(row, col);
                            FilterList newFilter = this.quickCreateFilter(filterValue, offset);
                            logger.info("Adding dynamic world filter: " + newFilter);
                            filters.add(newFilter);
                        }
                    }
                }
            }
        }
    }
    
//    public void applyStaticColFilters() {
//    	for(int value=0; value < boardLength; value++) {
//    		for(int row=0; row < boardLength; row++) {
//		    	for(int colOffset1=0; colOffset1 < boardLength -1; colOffset1++) {
//		    		for (int colOffset2=colOffset1 + 1; colOffset2 < boardLength -1; colOffset2++) {
//		    			int offset1 = this.getOffset(row, colOffset1);
//		    			int offset2 = this.getOffset(row, colOffset2);
//	    				FilterList filter = this.quickCreateFilter(value, offset1, offset2);
//	    				logger.info("Adding static col filter: " + filter);
//	    				filters.add(filter);			
//		    		}
//		    	}
//    		}
//    	}
//    }
    
    
//    public void applyBoxFilters(int[][] initialWorld) {
//        for(int row = 0;row < initialWorld.length;row++) {
//            for(int col = 0;col < initialWorld.length;col++) {
//                int value = initialWorld[row][col];
//                                
//                if(value == 0) {
//                    
//                    int pos = this.getOffset(row, col);
//                    
//                    //Get the position of the upper left box
//                    int boxColPos = col - (col % boxLength);
//                    int boxRowPos = row - (row % boxLength);
//                    int boxPos = boxRowPos + boardLength*boxColPos;
//                    
//                    for(int boxRow=0; boxRow < boxLength;boxRow++) {
//                        for(int boxCol=0; boxCol < boxLength;boxCol++) {
//                            int offset = boxPos + boxRow + boardLength*boxCol;
//                            
//                            if(offset != pos) {
//                                FilterList newFilter = this.quickCreateFilter(value, offset);
//                                logger.info("Adding definite box filter: " + newFilter);
//                                filters.add(newFilter);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
    
//    public void applyColFilters(int[][] initialWorld) {
//        for(int row = 0;row < initialWorld.length;row++) {
//            for(int col = 0;col < initialWorld.length;col++) {
//                int value = initialWorld[row][col];
//                
//                if(value == 0) {
//                    
//                    for(int i=0;i < boardLength;i++) {
//                        int offset = this.getOffset(row, col);
//                        FilterList newFilter = this.quickCreateFilter(value, offset);
//                        logger.info("Adding definite col filter: " + newFilter);
//                        filters.add(newFilter);
//                    }
//                }
//            }
//        }
//    }
//    
//    public void applyRowFilters(int[][] initialWorld) {
//        for(int row = 0;row < initialWorld.length;row++) {
//            for(int col = 0;col < initialWorld.length;col++) {
//                int value = initialWorld[row][col];
//                
//                if(value == 0) {
//                    
//                    for(int filterRow=0;filterRow < boardLength;filterRow++) {
//                        int offset = this.getOffset(filterRow, col);
//                        FilterList newFilter = this.quickCreateFilter(value, offset);
//                        logger.info("Adding definite row filter: " + newFilter);
//                        filters.add(newFilter);
//                    }
//                }
//            }
//        }
//    }
    
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
    
    public int getBox(int row, int col) {
    	int offset = this.getOffset(row, col);
    	return offset / boardLength;
    }
    
    public int getLocalBoxOffset(int row, int col) {
    	int offset = this.getOffset(row, col);
    	return offset % boardLength;
    }
    
    // Boxes are counted the same way as tiles, left to right, then top bottom.
    // Offsets within boxes are also counted the same way.
    public int getGlobalBoxOffset(int box, int boxOffset) {
    	return boxOffset + box*boardLength;
    }
    
    public FilterList quickCreateFilter(int value, int offset) {
        String filterStr = StringUtils.leftPad("", radices.length, "*");
        filterStr = filterStr.substring(0, offset) + value + filterStr.substring(offset + 1);


        return FilterListBuilder.newInstance()
                .setQuick(filterStr)
                .getFilterList();
    }
    
    public FilterList quickCreateFilter(int value, int offset1, int offset2) {
    	
        String filterStr = StringUtils.leftPad("", radices.length, "*");
        filterStr = filterStr.substring(0, offset1) + value + filterStr.substring(offset1 + 1);
        filterStr = filterStr.substring(0, offset2) + value + filterStr.substring(offset2 + 1);

        return FilterListBuilder.newInstance()
                .setQuick(filterStr)
                .getFilterList();
    }
}
