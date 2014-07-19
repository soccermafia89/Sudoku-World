/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.sudoku;

import com.google.common.collect.Sets;
import ethier.alex.world.core.data.FilterList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/**

 @author alex
 */

/**

As we have found for normal sudoku, filling each tile with n possible states means the whole board has n^(n^2) states to fill which is too many.

This class will adopt a different strategy.  It will fill each row at a time.  A row can have n! states and there are n rows.
This mean the board now has (n!)^n states.  Still a big number but not as big as n^(n^2).

The ramifications of grouping states into larger groups needs to be closer examined.  If this does indeed increase
computation time, how is this actually possibly?  What underlying mathematical principle, or symmetry are we taking advantage of
to increase speed?

*/

public class Sudoku2 {
    
    private static Logger logger = Logger.getLogger(Sudoku2.class);
    int[] radices;
    Collection<FilterList> filters;
    int boardLength;
    int boxLength;// = SQRT(boardLength)
    
    public Sudoku2(int[][] initialWorld) {
        boardLength = initialWorld.length;
        boxLength = (int) Math.sqrt(boardLength);
        if(boxLength * boxLength != boardLength) {
            throw new RuntimeException("Invalid board/box length: " + boardLength + "/" + boxLength);
        }
        
        filters = new ArrayList<FilterList>();
        radices = new int[boardLength];
        
        for(int i=0; i < radices.length;i++) {
            radices[i] = this.factorial(boardLength);
        }
        
        this.applyStaticFilters(); // Filters that are true regardless of game.
        
//        this.applyDefiniteFilters(initialWorld); // Filters that are true specific to a board.
    }
    
    public void applyStaticFilters() {
        // First construct a mapping between radices to row states.
        Set<Integer> remainders = new HashSet<Integer>();
        for(int i=0; i < boardLength; i++) {
            remainders.add(i);
        }
        Collection<String> mappings = rowMapper(remainders);
        for(String mapping : mappings) {
            logger.info("Mapping Found: " + mapping);
        }
        
        // Calculate list of rules indicating which rows exclude other rows.
    }
    
    public Collection<String> rowMapper(Set<Integer> remainders) {
        Collection<String> mappings = new ArrayList<String>();

        if(remainders.size() == 1) {
            String tmpMapping = "" + remainders.iterator().next();
            mappings.add(tmpMapping);
            return mappings;
        }
        
        for(Integer i : remainders) {
            Set<Integer> newRemainders = Sets.newHashSet(remainders);
            newRemainders.remove(i);
            
            Collection<String> tmpMappings = rowMapper(newRemainders);
            for(String tmpMapping : tmpMappings) {
                mappings.add(i + tmpMapping);
            }
        }
        
        return mappings;
    }
    
    public int factorial(int n) {
        if(n == 1) {
            return 1;
        } else {
            return n*factorial(n-1);
        }      
    }
}
