package week7.assignment;

/**
 * Root abstract tool class.
 */
abstract class GardenTool {
    public abstract String use();
}

/**
 * Concrete intermediate tool class overriding use().
 */
class CuttingTool extends GardenTool {
    @Override
    public String use() {
        return "Using the tool in the garden, blade sharpened first";
    }
}

/**
 * Subclass extending CuttingTool and compounding parent routine via super.use().
 */
class Pruner extends CuttingTool {
    @Override
    public String use() {
        return super.use() + ", then trimming branches precisely";
    }
}

/**
 * Problem 3: Backyard Toolshed Routine
 * Demonstrates a 3-level multilevel inheritance hierarchy where each level enriches parent functionality.
 */
public class BackyardToolshedRoutine {

    public static void main(String[] args) {
        try {
            CuttingTool c = new CuttingTool();
            Pruner p = new Pruner();

            System.out.println(c.use());
            System.out.println(p.use());
        } catch (Exception e) {
            System.err.println("Error running toolshed routine: " + e.getMessage());
        }
    }
}
