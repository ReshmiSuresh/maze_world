import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class RobotDriver
{
    private static void printSuccessors(SingleRobotProblem problem)
    {
        System.out.println("problem.startNode = " + problem.startNode);
        List<SearchProblem.SearchNode> successors = problem.startNode.getSuccessors();
        System.out.println("successors of startNode = " + successors);

        for(int i=0;i<successors.size();i++)
            System.out.println("successors of " + successors.get(i) + " = " + successors.get(i).getSuccessors());

    }

    public static void main(String[] args) throws IOException
    {
        int ROWS = 7, COLS = 7;
        char[][] maze = new char [ROWS][COLS];

        BufferedReader input = new BufferedReader(new FileReader("maze.txt"));

        for(int i=0;i<ROWS;i++)
        {
            String line = input.readLine();
            maze[i] = line.toCharArray();
//            System.out.println(maze[i]);
        }

        SingleRobotProblem singleRobotProblem = new SingleRobotProblem(0, 0, 6, 6, maze);
//        printSuccessors(singleRobotProblem);

        List<SearchProblem.SearchNode> path;

        path = singleRobotProblem.breadthFirstSearch();
        System.out.println("bfs path length:  " + path.size() + " " + path);
        System.out.println("--------");

        path = singleRobotProblem.aStarSearch();
        System.out.println("A* path length:  " + path.size() + " " + path);
        System.out.println("--------");

//        int[] sx = {6, 0, 1};
//        int[] sy = {0, 1, 4};
//        int[] gx = {5, 4, 6};
//        int[] gy = {6, 6, 6};
//        MultiRobotProblem multiRobotProblem = new MultiRobotProblem(sx, sy, 3, gx, gy, maze);
//
//        path = multiRobotProblem.breadthFirstSearch();
//        System.out.println("bfs path length:  " + path.size() + " " + path);
//        System.out.println("--------");
//
//        path = multiRobotProblem.aStarSearch();
//        System.out.println("A* path length:  " + path.size() + " " + path);
//        System.out.println("--------");

        BlindRobotProblem blindRobotProblem = new BlindRobotProblem(6, 6, maze);
        printSuccessors(singleRobotProblem);

//        path = blindRobotProblem.breadthFirstSearch();
//        System.out.println("bfs path length:  " + path.size() + " " + path);
//        System.out.println("--------");
//
//        path = blindRobotProblem.aStarSearch();
//        System.out.println("A* path length:  " + path.size() + " " + path);
//        System.out.println("--------");

    }
}
