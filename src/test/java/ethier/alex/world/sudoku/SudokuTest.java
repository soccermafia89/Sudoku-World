package ethier.alex.world.sudoku;

//package ethier.alex.resistance;


//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import ethier.alex.world.core.data.ElementList;
//import ethier.alex.world.core.data.Partition;
//import ethier.alex.world.core.processor.Processor;
//import ethier.alex.world.core.processor.SimpleProcessor;
//import ethier.alex.world.sudoku.Sudoku;
//
//
///**
//
// @author alex
// */
//public class SudokuTest {
//
//    private static Logger logger = Logger.getLogger(SudokuTest.class);
//
//    @BeforeClass
//    public static void setUpClass() {
//        BasicConfigurator.resetConfiguration(); // One of the transitive dependencies is messing with the logger configuration.
//        BasicConfigurator.configure();
//    }
//
//    @Test
//    public void testSimpleSuduko() throws Exception {
//        System.out.println("");
//        System.out.println("");
//        System.out.println("********************************************");
//        System.out.println("********          Basic Test       *********");
//        System.out.println("********************************************");
//        System.out.println("");
//        System.out.println("");
//        
//        int boxLength = 2;
//        
//        int boardLength = boxLength*boxLength;
//
//        int[][] world = new int[boardLength][boardLength];
//        
//        for(int row = 0; row < boardLength; row++) {
//            for(int col = 0; col < boardLength; col++) {
//                world[row][col] = 0;
//            }
//        }
//        
////        world[0][0] = 1;
//        
//        Sudoku game = new Sudoku(world);
//        Partition partition = game.createRootPartition();
//        
//        Processor processor = new SimpleProcessor(partition);
//        processor.runAll();
//        
//        Collection<ElementList> completedPartitions = processor.getCompletedPartitions();
//        
//        for(ElementList completePartition : completedPartitions) {
//        	System.out.println("Found completed partition: " + completePartition);
//        }
//        
//        System.out.println("Number of possibilities: " + completedPartitions.size());
//    }
//    
////  @Test
//  public void testRealSudoku() throws Exception {
//      System.out.println("");
//      System.out.println("");
//      System.out.println("********************************************");
//      System.out.println("********         Real Test         *********");
//      System.out.println("********************************************");
//      System.out.println("");
//      System.out.println("");
//      
//      int boxLength = 3;
//      
//      
//      int boardLength = boxLength*boxLength;
//
//      int[][] world = new int[boardLength][boardLength];
//      
//      for(int row = 0; row < boardLength; row++) {
//          for(int col = 0; col < boardLength; col++) {
//              world[row][col] = 0;
//          }
//      }
//      
//      //
//      world[0][0] = 0;
//      world[8][0] = 7;
//      world[0][1] = 4;
//      world[1][1] = 2;
//      world[2][1] = 6;
//      world[4][1] = 8;
//      world[3][2] = 6;
//      world[7][2] = 8;
//      world[8][2] = 3;
//      world[2][3] = 1;
//      world[5][3] = 2;
//      world[6][3] = 3;
//      world[2][5] = 8;
//      world[3][5] = 5;
//      world[6][5] = 7;
//      world[0][6] = 6;
//      world[1][6] = 5;
//      world[5][6] = 0;
//      world[4][7] = 4;
//      world[6][7] = 5;
//      world[7][7] = 7;
//      world[8][7] = 2;
//      world[0][8] = 2;
//      world[8][8] = 5;
//      //
//
//      System.out.println("Initializing game.");
//      Sudoku game = new Sudoku(world);
//      Partition partition = game.createRootPartition();
//      
//      System.out.println("Computing possible states.");
//      Processor processor = new SimpleProcessor(partition);
//      processor.runAll();
//      
//      Collection<ElementList> completedPartitions = processor.getCompletedPartitions();
//      
//      for(ElementList completePartition : completedPartitions) {
//      	System.out.println("Found completed partition: " + completePartition);
//      }
//      
//      System.out.println("Number of possibilities: " + completedPartitions.size());
//  }
//    
////    @Test
//    public void testAdvancedSudoku() throws Exception {
//        System.out.println("");
//        System.out.println("");
//        System.out.println("********************************************");
//        System.out.println("********       Advanced Test       *********");
//        System.out.println("********************************************");
//        System.out.println("");
//        System.out.println("");
//        
//        int boxLength = 3;
//        
//        
//        int boardLength = boxLength*boxLength;
//
//        int[][] world = new int[boardLength][boardLength];
//        
//        for(int row = 0; row < boardLength; row++) {
//            for(int col = 0; col < boardLength; col++) {
//                world[row][col] = 0;
//            }
//        }
//        
//       
//        int prefill = 20;
//        System.out.println("Prefilling world with " + prefill + " entries.");
//        
//        Set<Integer> prefilled = new HashSet<Integer>();
//        while(prefilled.size() < prefill) {
//        	int row = (int) (boardLength*Math.random());
//        	int col = (int) (boardLength*Math.random());
//        	int offset = row + col*boardLength;
//        	prefilled.add(offset);
//        }
//        
//        for(Integer setInt : prefilled) {
//        	
//        	int row = setInt % boardLength;
//        	int col = setInt / boardLength;
//        	
//        	int value = (int) (boardLength*Math.random());
//        	while(!this.isValid(world, row, col, value)) {
//            	value = (int) (boardLength*Math.random());
//        	}
//        	
//        	world[row][col] = value;
//        }
//        
////        world[0][0] = 1;
//        System.out.println("Initializing game.");
//        Sudoku game = new Sudoku(world);
//        Partition partition = game.createRootPartition();
//        
//        System.out.println("Computing possible states.");
//        Processor processor = new SimpleProcessor(partition);
//        processor.runAll();
//        
//        Collection<ElementList> completedPartitions = processor.getCompletedPartitions();
//        
//        for(ElementList completePartition : completedPartitions) {
//        	System.out.println("Found completed partition: " + completePartition);
//        }
//        
//        System.out.println("Number of possibilities: " + completedPartitions.size());
//    }
//    
//    // Do a first order check for validity.
//    public boolean isValid(int[][] initialWorld, int row, int col, int value) {
////    	System.out.println("Validating offset: " + row + ", " + col);
//    	
//    	int boardLength = initialWorld.length;
//    	int boxLength = (int) Math.sqrt(boardLength);
//    	// First check row/col.
//    	for(int check=0; check < boardLength; check++ ) {
//    		int checkRowValue = initialWorld[check][col];
//    		if(check != row && checkRowValue == value) {
//    			return false;
//    		}
//    		
//    		int checkColValue = initialWorld[row][check];
//    		if(check != col && checkColValue == value) {
//    			return false;
//    		}
//    	}
//    	
//    	// Next check box.
//    	int boxRowStartOffset = row / boardLength;
//    	int boxColStartOffset = col / boardLength;
//    	
//    	for(int boxRow=0; boxRow < boxLength; boxRow++) {
//			int rowOffset = boxRowStartOffset + boxRow;
//
//    		for(int boxCol=0; boxCol < boxLength; boxCol++) {
//    			int colOffset = boxColStartOffset + boxCol;
//    			
//    			if(!(rowOffset == row && colOffset == col)) {
////    				System.out.println("Checking offset: " + rowOffset + ", " + colOffset);
//        			int checkValue = initialWorld[rowOffset][colOffset];
//        			if(checkValue == value) {
//        				return false;
//        			}
//    			}
//    		}
//    	}
//    	
//    	return true;
//    }
//}
