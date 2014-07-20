/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.sudoku;

import com.google.common.collect.*;
import ethier.alex.world.addon.FilterListBuilder;
import ethier.alex.world.core.data.FilterList;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**

 @author alex
 */
/**

 As we have found for normal sudoku, filling each tile with n possible states means the whole board has n^(n^2) states to fill which is too many.

 This class will adopt a different strategy. It will fill each row at a time. A row can have n! states and there are n rows.
 This mean the board now has (n!)^n states. Still a big number but not as big as n^(n^2).

 The ramifications of grouping states into larger groups needs to be closer examined. If this does indeed increase
 computation time, how is this actually possibly? What underlying mathematical principle, or symmetry are we taking advantage of
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
        if (boxLength * boxLength != boardLength) {
            throw new RuntimeException("Invalid board/box length: " + boardLength + "/" + boxLength);
        }

        filters = new ArrayList<FilterList>();
        radices = new int[boardLength];

        for (int i = 0; i < radices.length; i++) {
            radices[i] = this.factorial(boardLength);
        }

        this.applyStaticFilters(); // Filters that are true regardless of game.

//        this.applyDefiniteFilters(initialWorld); // Filters that are true specific to a board.
    }

    public void applyStaticFilters() {
        // First construct a mapping between radices to row states.
        Set<Integer> remainders = new HashSet<Integer>();
        for (int i = 0; i < boardLength; i++) {
            remainders.add(i);
        }

        List<String> mappings = rowMapper(remainders);

        for (int i = 0; i < mappings.size(); i++) {
            logger.info("Mapping Found: " + i + " => " + mappings.get(i));
        }

        // Calculate list of rules indicating which rows exclude other rows.
        Multimap<Integer, Integer> conflicts = HashMultimap.create();



        for (int i = 0; i < mappings.size(); i++) {
            for (int j = i; j < mappings.size(); j++) {

                String mapping1 = mappings.get(i);
                String mapping2 = mappings.get(j);

                logger.info("Comparing mapping " + i + " to " + j + " (" + mapping1 + ")" + " to " + "(" + mapping2 + ")");


                for (int index = 0; index < boardLength; index++) {
//                    
                    char char1 = mapping1.charAt(index);
                    char char2 = mapping2.charAt(index);
//                    
                    if (char1 == char2) {
                        logger.info("\tConflict detected at: " + index);
                        conflicts.put(i, j);
                        break;
//                        FilterList filter = this.quickCreateFilter(char1, i, j);
//                        logger.info("Static Filter Built: " + filter);
                    }
                }
            }
        }

        for (Integer mapping1 : conflicts.keySet()) {

            Collection<Integer> conflictingMappings = conflicts.get(mapping1);

            for (int mapping2 : conflictingMappings) {

                for (int i = 0; i < boardLength - 1; i++) {
                    for (int j = i + 1; j < boardLength; j++) {
                        FilterList filter1 = this.quickCreateFilter(mapping1, i, mapping2, j);
                        FilterList filter2 = this.quickCreateFilter(mapping2, j, mapping1, i);
                        logger.info("Created static filter: " + filter1);
                        logger.info("Created static filter: " + filter2);
                        filters.add(filter1);
                        filters.add(filter2);
                    }
                }
            }
        }
    }

    public List<String> rowMapper(Set<Integer> remainders) {
        List<String> mappings = new ArrayList<String>();

        if (remainders.size() == 1) {
            String tmpMapping = "" + remainders.iterator().next();
            mappings.add(tmpMapping);
            return mappings;
        }

        for (Integer i : remainders) {
            Set<Integer> newRemainders = Sets.newHashSet(remainders);
            newRemainders.remove(i);

            Collection<String> tmpMappings = rowMapper(newRemainders);
            for (String tmpMapping : tmpMappings) {
                mappings.add(i + tmpMapping);
            }
        }

        return mappings;
    }

    public int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    // Consider adding a new way to build filters to the filter builder class.
    public FilterList quickCreateFilter(int value1, int offset1, int value2, int offset2) {

        String filterStr = StringUtils.leftPad("", radices.length, "*");
        filterStr = filterStr.substring(0, offset1) + value1 + filterStr.substring(offset1 + 1);
        filterStr = filterStr.substring(0, offset2) + value2 + filterStr.substring(offset2 + 1);
        logger.error("TODO, since ordinals are greater than 10, the quick filter build method no longer works.");
        return FilterListBuilder.newInstance().setQuick(filterStr).getFilterList();
    }
}
