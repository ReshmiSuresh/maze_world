import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.StrictMath.abs;

/**
 * Created by reshmi on 9/27/16.
 */
public class BlindRobotProblem extends SearchProblem
{
    private int goalx, goaly;
    private char[][] maze;
    private int[][] directions;

    public class Coordinates
    {
        private int x;

        public int getY() {
            return y;
        }

        private int y;

        public int getX() {
            return x;
        }

        Coordinates(int x_coordinate, int y_coordinate)
        {
            x = x_coordinate;
            y = y_coordinate;
        }

    }

    public BlindRobotProblem(int gx, int gy, char[][] robot_maze)
    {
        goalx = gx;
        goaly = gy;
        maze = robot_maze;
        directions = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        ArrayList<Coordinates> possible_starting_nodes = new ArrayList<>();
        int rows = maze.length;
        int columns = maze[0].length;
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                if(maze[i][j] != '#')
                {
                    System.out.println("i = " + i);
                    System.out.println("j = " + j);
                    Coordinates new_coordinate = new Coordinates(i, j);
                    possible_starting_nodes.add(new_coordinate);
                }
            }
        }
        System.out.println("possible_starting_nodes = " + possible_starting_nodes);
        startNode = new BlindRobotProblem.MazeNode(possible_starting_nodes, 0);
    }

    private class MazeNode implements SearchProblem.SearchNode
    {
        ArrayList<Coordinates> starting_nodes;
        float average_X = 0, average_Y = 0;

        private int depth;

        public MazeNode(ArrayList<Coordinates> possible_starting_nodes, int d)
        {
            this.starting_nodes = possible_starting_nodes;

            depth = d;

            int total_elements = starting_nodes.size();
            for(int i=0;i<total_elements;i++)
            {
                average_X += starting_nodes.get(i).getX();
                average_Y += starting_nodes.get(i).getY();
            }
            average_X /= total_elements;
            average_Y /= total_elements;
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
        public ArrayList<SearchProblem.SearchNode> getSuccessors()
        {
            ArrayList<SearchProblem.SearchNode> legal_coordinates = new ArrayList<>();
            ArrayList<Coordinates>  all_coordinates = new ArrayList<>();
//            System.out.println("directions = " + directions.length);
            for(int j=0;j<starting_nodes.size();j++)
            {
                for (int i = 0; i < directions.length; i++)
                {
                    int x = starting_nodes.get(j).getX() + directions[i][0];
                    int y = starting_nodes.get(j).getY() + directions[i][1];
                    if (isLegalCoordinate(x, y))
                    {
                        Coordinates node = new Coordinates(x, y);
                        all_coordinates.add(node);
//                    System.out.println("node = " + node);
                    }
                }
            }
            BlindRobotProblem.MazeNode successors = new BlindRobotProblem.MazeNode(all_coordinates, depth + 1);
            legal_coordinates.add(successors);
            return legal_coordinates;
        }

        @Override
        public boolean goalTest()
        {
            for(int i=0;i<starting_nodes.size();i++)
            {
                if (goalx != starting_nodes.get(i).getX() || goaly != starting_nodes.get(i).getY())
                    return false;
            }
            return true;
        }

//        @Override
//        public boolean equals(Object other) {
//            return Arrays.equals(starting_nodes, ((MazeNode) other).starting_nodes);
//        }

        @Override
        public int getDepth() {
            return 0;
        }

        @Override
        public int hashCode() {
            return (int) (average_X*10 + average_Y);
        }

        @Override
        public String toString() {

            // you write this method
            String str = ""+hashCode();
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
            return abs(average_X - goalx) + abs(average_Y - goaly);
        }
    }

}
