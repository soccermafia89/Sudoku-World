package ethier.alex.resistance;

import ethier.alex.world.core.data.Partition;
import ethier.alex.world.sudoku.Sudoku;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public void testBasicResistance1() throws Exception {
        System.out.println("");
        System.out.println("");
        System.out.println("********************************************");
        System.out.println("********        Basic Test  1      *********");
        System.out.println("********************************************");
        System.out.println("");
        System.out.println("");

        int[][] world = new int[4][4];
        
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                world[row][col] = 0;
            }
        }
        
        world[0][0] = 1;
        
        Sudoku game = new Sudoku(world);
        Partition partition = game.createRootPartition();
    }
}
