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
public class Sudoku {
    
    private static Logger logger = Logger.getLogger(Sudoku.class);
    int[] radices;
    Collection<FilterList> filters;
    
    public Sudoku(int[][] initialWorld) {
        filters = new ArrayList<FilterList>();
        radices = new int[81];
        
        for(int i=0; i < radices.length;i++) {
            radices[i] = 10;
        }
        
        this.applyDefiniteFilters(initialWorld);
        this.applyRowFilters(initialWorld);
        this.applyColFilters(initialWorld);
        this.applyBoxFilters(initialWorld);
    }
    
    public void applyBoxFilters(int[][] initialWorld) {
        for(int row = 0;row < initialWorld.length;row++) {
            for(int col = 0;col < initialWorld.length;col++) {
                int value = initialWorld[row][col];
                                
                if(value == 0) {
                    
                    int pos = row + col*9;
                    
                    //Get the position of the upper left box
                    int boxColPos = col - (col % 3);
                    int boxRowPos = row - (row % 3);
                    int boxPos = boxRowPos + 9*boxColPos;
                    
                    for(int boxRow=0; boxRow < 3;boxRow++) {
                        for(int boxCol=0; boxCol < 3;boxCol++) {
                            int offset = boxPos + boxRow + 9*boxCol;
                            
                            if(offset != pos) {
                                String filterStr = StringUtils.leftPad("", radices.length, "*");
                                filterStr = filterStr.substring(0, offset) + value + filterStr.substring(offset + 1);


                                FilterList newFilter = FilterListBuilder.newInstance()
                                        .setQuick(filterStr)
                                        .getFilterList();

                                logger.info("Adding definite row filter: " + newFilter);
                                filters.add(newFilter);
                            }
                        }
                    }
                    
                    for(int i=0;i < 9;i++) {
                        int offset = -1;
                        
                        String filterStr = StringUtils.leftPad("", radices.length, "*");
                        filterStr = filterStr.substring(0, offset) + value + filterStr.substring(offset + 1);
                                         

                        FilterList newFilter = FilterListBuilder.newInstance()
                                .setQuick(filterStr)
                                .getFilterList();

                        logger.info("Adding definite row filter: " + newFilter);
                        filters.add(newFilter);
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
                    
                    for(int i=0;i < 9;i++) {
                        int offset = row + 9*i;
                        
                        String filterStr = StringUtils.leftPad("", radices.length, "*");
                        filterStr = filterStr.substring(0, offset) + value + filterStr.substring(offset + 1);
                                         

                        FilterList newFilter = FilterListBuilder.newInstance()
                                .setQuick(filterStr)
                                .getFilterList();

                        logger.info("Adding definite row filter: " + newFilter);
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
                    
                    for(int i=0;i < 9;i++) {
                        int offset = i + 9*col;
                        
                        String filterStr = StringUtils.leftPad("", radices.length, "*");
                        filterStr = filterStr.substring(0, offset) + value + filterStr.substring(offset + 1);
                                         

                        FilterList newFilter = FilterListBuilder.newInstance()
                                .setQuick(filterStr)
                                .getFilterList();

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
                    for(int i=0; i < 9;i++) {
                        if( (i + 1) != value) {
                            String filterStr = StringUtils.leftPad("", radices.length, "*");
                            int offset = row + col*initialWorld[row].length;

                            filterStr = filterStr.substring(0, offset) + value + filterStr.substring(offset + 1);

                            FilterList newFilter = FilterListBuilder.newInstance()
                                    .setQuick(filterStr)
                                    .getFilterList();

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
}
