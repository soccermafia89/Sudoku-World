package ethier.alex.world.sudoku;


import ethier.alex.world.sudoku.Sudoku2;
import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;


/**

 @author alex
 */
public class Sudoku2Test {

//    private static Logger logger = Logger.getLogger(Sudoku2Test.class);

    @BeforeClass
    public static void setUpClass() {
        BasicConfigurator.configure();
    }

    @Test
    public void testSimpleSuduko() throws Exception {
        System.out.println("");
        System.out.println("");
        System.out.println("********************************************");
        System.out.println("********          Basic Test       *********");
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
        
        Sudoku2 game = new Sudoku2(world);
        
    }
}
