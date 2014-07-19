package ethier.alex.resistance;

import java.util.Collection;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import ethier.alex.world.core.data.ElementList;
import ethier.alex.world.core.data.Partition;
import ethier.alex.world.core.processor.Processor;
import ethier.alex.world.core.processor.SimpleProcessor;
import ethier.alex.world.sudoku.Sudoku;


/**

 @author alex
 */
public class SudokuTest {

    private static Logger logger = Logger.getLogger(SudokuTest.class);

    @BeforeClass
    public static void setUpClass() {
        BasicConfigurator.configure();
    }

    @Test
    public void testSimpleSuduko() throws Exception {
        System.out.println("");
        System.out.println("");
        System.out.println("********************************************");
        System.out.println("********        Basic Test  1      *********");
        System.out.println("********************************************");
        System.out.println("");
        System.out.println("");
        
        int boxLength = 2;
        
        int boardLength = boxLength*boxLength;

        int[][] world = new int[boardLength][boardLength];
        
        for(int row = 0; row < boardLength; row++) {
            for(int col = 0; col < boardLength; col++) {
                world[row][col] = 0;
            }
        }
        
//        world[0][0] = 1;
        
        Sudoku game = new Sudoku(world);
        Partition partition = game.createRootPartition();
        
        Processor processor = new SimpleProcessor(partition);
        processor.runAll();
        
        Collection<ElementList> completedPartitions = processor.getCompletedPartitions();
        
        for(ElementList completePartition : completedPartitions) {
        	System.out.println("Found completed partition: " + completePartition);
        }
        
        System.out.println("Number of possibilities: " + completedPartitions.size());
    }
}
