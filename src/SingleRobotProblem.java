import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.StrictMath.abs;

/**
 * Created by reshmi on 9/23/16.
 */
public class SingleRobotProblem extends SearchProblem
{
    private int goalx, goaly;
    private char[][] maze;
    private int[][] directions;

    public SingleRobotProblem(int sx, int sy, int gx, int gy, char[][] robot_maze)
    {
        startNode = new MazeNode(sx, sy, 0);
        goalx = gx;
        goaly = gy;
        maze = robot_maze;
        directions = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    }

    private class MazeNode implements SearchNode
    {
        private int[] state;

        private int depth;

        public MazeNode(int x, int y, int d)
        {
            state = new int[2];
            this.state[0] = x;
            this.state[1] = y;

            depth = d;
        }

        private boolean isLegalCoordinate(int x, int y)
        {
            if(x>=0 && x<=goalx && y>=0 && y<=goaly)
            {
//                System.out.println("x = " + x);
//                System.out.println("y = " + y);
//                System.out.println("__________________________");
//                System.out.println("maze[x][y] = " + maze[x][y]);
                if(maze[x][y] != '#')
                    return true;
            }
            return false;
        }

        @Override
        public ArrayList<SearchNode> getSuccessors()
        {
            ArrayList<SearchNode> legal_coordinates = new ArrayList<>();
//            System.out.println("directions = " + directions.length);
            for(int i=0;i<directions.length;i++)
            {
                int x = state[0]+directions[i][0];
                int y = state[1]+directions[i][1];
                if(isLegalCoordinate(x, y))
                {
                    MazeNode node = new MazeNode(x, y, depth+1);
                    legal_coordinates.add(node);
//                    System.out.println("node = " + node);
                }
            }
            return legal_coordinates;
        }

        @Override
        public boolean goalTest() {
            if(goalx == state[0] && goaly == state[1])
                return true;
            return false;
        }

        @Override
        public boolean equals(Object other) {
            return Arrays.equals(state, ((MazeNode) other).state);
        }

        @Override
        public int getDepth() {
            return 0;
        }
        @Override
        public int hashCode() {
            return state[0] * 10 + state[1];
        }

        @Override
        public String toString() {

            // you write this method
            String str = ""+hashCode();
            return str;
        }

        @Override
        public int getPriority()
        {
            return getHeuristicValue() + depth;
        }

        @Override
        public int getHeuristicValue()
        {
            return abs(state[0] - goalx) + abs(state[1] - goaly);
        }
    }

}
