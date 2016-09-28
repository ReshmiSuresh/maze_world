import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.pow;

/**
 * Created by reshmi on 9/24/16.
 */
public class MultiRobotProblem extends SearchProblem
{
    private int goalx[], goaly[];
    private int totalRobots;
    private char[][] maze;
    private int[][] directions;

    public MultiRobotProblem(int[] sx, int[] sy, int sr, int[] gx, int[] gy, char[][] robot_maze)
    {
        totalRobots = sr;
        startNode = new MultiRobotProblem.MazeNode(sx, sy, 0, 0);
        goalx = new int[sr];
        goaly = new int[sr];
        for(int i=0;i<sr;i++)
        {
            goalx[i] = gx[i];
            goaly[i] = gy[i];
        }
        maze = robot_maze;
        directions = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {0, 0}};
    }

    private class MazeNode implements SearchProblem.SearchNode
    {
        protected int[][] state;

        protected int depth, turn;

        public MazeNode(int[] x, int[] y, int d, int t)
        {
            state = new int[totalRobots][2];
            for(int i=0;i<totalRobots;i++)
            {
                state[i] = new int[2];
                this.state[i][0] = x[i];
                this.state[i][1] = y[i];
            }
            turn = t;
            depth = d;
        }

        private boolean isLegalCoordinate(int x, int y)
        {
            int rows = maze.length;
            int columns = maze[0].length;
            if(x>=0 && x<rows && y>=0 && y<columns)
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
        public ArrayList<SearchProblem.SearchNode> getSuccessors()
        {
            ArrayList<SearchProblem.SearchNode> legal_coordinates = new ArrayList<>();
            System.out.println(Arrays.toString(state[turn]));
            for(int i=0;i<directions.length;i++)
            {
                int x = state[turn][0]+directions[i][0];
                int y = state[turn][1]+directions[i][1];
                if(isLegalCoordinate(x, y) && ifNoCollision(x, y, turn))
                {
                    int[] new_x = new int[totalRobots];
                    int[] new_y = new int[totalRobots];

                    for(int j=0;j<totalRobots;j++)
                    {
                        if(turn == j)
                        {
                            new_x[j] = x;
                            new_y[j] = y;
                        }
                        else
                        {
                            new_x[j] = state[j][0];
                            new_y[j] = state[j][1];
                        }
                    }
                    MultiRobotProblem.MazeNode node = new MultiRobotProblem.MazeNode(new_x, new_y, depth+1, (turn+1)%totalRobots);
                    legal_coordinates.add(node);
                    System.out.println("node = " + node);
                }
            }
            return legal_coordinates;
        }

        @Override
        public boolean goalTest()
        {
//            System.out.println(Arrays.toString(state));
            for(int i = 0;i<totalRobots;i++)
            {
                if (goalx[i] != state[i][0] || goaly[i] != state[i][1])
                {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean equals(Object other) {
            return Arrays.equals(state, ((MultiRobotProblem.MazeNode) other).state);
        }

        @Override
        public int getDepth() {
            return 0;
        }
        @Override
        public int hashCode()
        {
            int hashValue = 0;
            for(int i=0;i<totalRobots;i++)
            {
                hashValue += pow(10, i) * state[i][0] * 10 + state[i][1];
            }
            return hashValue;
        }

        @Override
        public String toString() {

            // you write this method
            String str;
            str = "{";
            for(int i=0;i<totalRobots;i++)
                str += "{" + state[i][0] + ", " + state[i][1] + "}";
            str += "}";
            return str;
        }

        @Override
        public float getPriority()
        {
            return getHeuristicValue() + depth;
        }

        @Override
        public float getHeuristicValue()
        {
            int heuristicValue = 0;
            for(int i=0;i<totalRobots;i++)
                heuristicValue += abs(state[i][0] - goalx[i]) + abs(state[i][1] - goaly[i]);
            return heuristicValue;
        }

        private boolean ifNoCollision(int x, int y, int t)
        {
            for(int i=0;i<totalRobots;i++)
            {
                if(t != i)
                {
                    if (state[i][0] == x && state[i][1] == y)
                        return false;
                }
            }
            return true;
        }
    }

}
