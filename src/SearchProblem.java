import java.util.*;

/**
 * Created by reshmi on 9/23/16.
 */
public abstract class SearchProblem
{
    protected SearchNode startNode;

    protected interface SearchNode
    {
        public ArrayList<SearchNode> getSuccessors();
        public boolean goalTest();
        public int getDepth();
        public float getPriority();
        public float getHeuristicValue();
    }

    public List<SearchNode> breadthFirstSearch()
    {
        List<SearchNode> path;
        Queue<SearchNode> queue = new LinkedList<SearchNode>();
        HashMap<SearchNode, SearchNode> visited = new HashMap<>();
        ArrayList<SearchNode> successors;

        queue.offer(startNode);
        while(!queue.isEmpty())
        {
            SearchNode temp = queue.poll();

            System.out.println(queue.size());
            if(temp.goalTest())
            {
//                System.out.println("reached");
                path = backchain(temp, visited);
//                System.out.println("temp = " + temp);
                return path;
            }

            successors = temp.getSuccessors();
            for(int i=0;i<successors.size();i++)
            {
                if(!visited.containsValue(successors.get(i)))
                {
                    queue.offer(successors.get(i));
                    visited.put(successors.get(i),temp);
                } else {
                    System.out.println("Came here");
                }
            }
        }
        return null;
    }

    // backchain should only be used by bfs, not the recursive dfs
    private List<SearchNode> backchain(SearchNode node,
                                         HashMap<SearchNode, SearchNode> visited)
    {

        List<SearchNode> extracted_path = new ArrayList<SearchNode>();  //list is in reverse order

        extracted_path.add(node);
//        System.out.println("node = " + node);
        while(node != startNode)
        {
            for(Map.Entry<SearchNode,SearchNode> entry : visited.entrySet())
            {
                if(entry.getKey().equals(node))
                {
//                    System.out.println("node = " + node);
                    node = entry.getValue();
                    extracted_path.add(node);
                    break;
                }
            }
        }

        return extracted_path;
    }

    public List<SearchNode> aStarSearch()
    {
//        Which nodes are visited.
        HashMap<SearchNode, Float> visited = new HashMap<>();
//        For each node, which node it can most efficiently be reached from.
        HashMap<SearchNode, SearchNode> arrived_from = new HashMap<>();


//        The set of currently discovered nodes still to be evaluated.
//        PriorityQueue<SearchNode> queue = new PriorityQueue<>();
        PriorityQueue<SearchNode> queue = new PriorityQueue<SearchNode>(10, new Comparator<SearchNode>() {
            public int compare(SearchNode o1, SearchNode o2) {
                return (o1.getPriority() > o2.getPriority()) ? 1 : 0;
            }
        });

        ArrayList<SearchNode> successors;

        arrived_from.put(startNode, null);
        queue.offer(startNode);
        while(!queue.isEmpty())
        {
            SearchNode temp = queue.poll();

            float priority = temp.getPriority();
            if(visited.containsKey(temp))
            {
                if (priority < visited.get(temp))
                {
                    continue;
                }
            }
            else
                visited.put(temp, priority);

            if(temp.goalTest())
            {
//                System.out.println("reached");
                List<SearchNode> path = backchain(temp, arrived_from);
//                System.out.println("temp = " + temp);
                return path;
            }

            successors = temp.getSuccessors();
            for(int i=0;i<successors.size();i++)
            {
                SearchNode next_node = successors.get(i);
                float new_priority = next_node.getPriority();
                if(!visited.containsKey(next_node) || visited.get(next_node) > new_priority)
                {
                    queue.offer(next_node);
                    arrived_from.put(next_node,temp);
                }
            }
        }
        return null;
    }



}
