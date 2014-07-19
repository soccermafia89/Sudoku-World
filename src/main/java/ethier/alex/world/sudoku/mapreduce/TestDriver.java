/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.sudoku.mapreduce;

import com.google.common.base.Stopwatch;
import ethier.alex.world.core.data.ElementList;
import ethier.alex.world.core.data.Partition;
import ethier.alex.world.core.processor.Processor;
import ethier.alex.world.mapreduce.core.WorldRunner;
import ethier.alex.world.mapreduce.processor.DistributedProcessor;
import ethier.alex.world.sudoku.Sudoku;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**

 @author alex
 */
public class TestDriver {

    private static Logger logger = Logger.getLogger(TestDriver.class);

    public static void main(String[] args) throws Exception {
        TestDriver testDriver = new TestDriver();
        testDriver.drive(args);
    }

    public TestDriver() {
    }

    public void drive(String[] args) throws Exception {
        this.runExpertGame(args);
    }

    public void runExpertGame(String[] args) throws Exception {
        System.out.println("");
        System.out.println("");
        System.out.println("********************************************");
        System.out.println("********      Run Expert Game      *********");
        System.out.println("********************************************");
        System.out.println("");
        System.out.println("");

        Stopwatch stopWatch = new Stopwatch();
        stopWatch.start();

        int boxLength = 3;


        int boardLength = boxLength * boxLength;

        int[][] world = new int[boardLength][boardLength];

        for (int row = 0; row < boardLength; row++) {
            for (int col = 0; col < boardLength; col++) {
                world[row][col] = 0;
            }
        }

        //
        world[0][0] = 0;
        world[8][0] = 7;
        world[0][1] = 4;
        world[1][1] = 2;
        world[2][1] = 6;
        world[4][1] = 8;
        world[3][2] = 6;
        world[7][2] = 8;
        world[8][2] = 3;
        world[2][3] = 1;
        world[5][3] = 2;
        world[6][3] = 3;
        world[2][5] = 8;
        world[3][5] = 5;
        world[6][5] = 7;
        world[0][6] = 6;
        world[1][6] = 5;
        world[5][6] = 0;
        world[4][7] = 4;
        world[6][7] = 5;
        world[7][7] = 7;
        world[8][7] = 2;
        world[0][8] = 2;
        world[8][8] = 5;
        //

        System.out.println("Initializing game.");
        Sudoku game = new Sudoku(world);
        Partition partition = game.createRootPartition();

        System.out.println("Computing possible states.");
        Properties props = new Properties();
//        Processor processor = new SimpleProcessor(partition);
        props.put(WorldRunner.RUN_INTITIAL_PARTITIONS_KEY, "2000");
        props.put("mapred.output.compress", "true");
        props.put("mapred.output.compression.codec", "org.apache.hadoop.io.compress.SnappyCodec");
        props.put("mapred.output.compression.type", "BLOCK");
        Processor processor = new DistributedProcessor(partition, "/sudoku", props, args);
        processor.runAll();

        Collection<ElementList> completedPartitions = processor.getCompletedPartitions();

        for (ElementList completePartition : completedPartitions) {
            System.out.println("Found completed partition: " + completePartition);
        }

        System.out.println("Number of possibilities: " + completedPartitions.size());


        stopWatch.stop();
        long minutes = stopWatch.elapsed(TimeUnit.MINUTES);
        logger.info("Sudoku solved in " + minutes + " minutes.");
    }

    public void assertTrue(boolean bool) {
        if (bool == true) {
            return;
        } else {
            throw new RuntimeException("Assertion Failed.");
        }
    }
}
