// Name:   Shuhbhrangshu Debsarkar
// Date:
 
import java.util.*;
import java.io.*;
 
/* Resource classes and interfaces
 * for use with Graphs3: EdgeList,
 *              Graphs4: DFS-BFS
 *          and Graphs5: EdgeListCities
 */
 
/**************** Graphs 3: EdgeList *****/
interface VertexInterface
{
   public String getName();
   public HashSet<Vertex> getAdjacencies();
   
   /*
     postcondition: if the set already contains a vertex with the same name, the vertex v is not added
                    this method should be O(1)
   */
   public void addAdjacent(Vertex v);
   /*
     postcondition:  returns as a string one vertex with its adjacencies, without commas.
                     for example, D [C A]
     */
   public String toString(); 
 
} 
 
/*************************************************************/
class Vertex implements VertexInterface, Comparable<Vertex> //2 vertexes are equal if and only if they have the same name
{
   private final String name;
   private HashSet<Vertex> adjacencies;
   
  /* enter your code here  */
   public Vertex(String newname)
   {
      name = newname;
      adjacencies = new HashSet<Vertex>();
   }
   public String getName()
   {
      return name;
   }
   public HashSet<Vertex> getAdjacencies()
   {
      return adjacencies;
   }
   public int compareTo(Vertex v)
   {
      return name.hashCode() - v.getName().hashCode();
   }
   public boolean equals(Object other)
   {
      return (other instanceof Vertex) && (name == ((Vertex)other).getName());
   }
   public int hashCode()
   {
      return name.hashCode();
   }
   
   /*
     postcondition: if the set already contains a vertex with the same name, the vertex v is not added
                    this method should be O(1)
   */
   public void addAdjacent(Vertex v)
   {
      adjacencies.add(v);
   }
   /*
     postcondition:  returns as a string one vertex with its adjacencies, without commas.
                     for example, D [C A]
     */
   public String toString()
   {
      String s = name + " " + "[";
      for(Vertex v : adjacencies)
         s += v.getName() + " ";
      if(adjacencies.size() == 0)  
         s += "]";
      else
         s = s.substring(0,s.length()-1) + "]";
      return s;
   }
 
}   
 
/*************************************************************/
interface AdjListInterface 
{
   public Set<Vertex> getVertices();
   public Vertex getVertex(String vName);
   public Map<String, Vertex> getVertexMap();  //this is just for codepost testing
   
   /*      
      postcondition: if a Vertex with the name v exists, then the map is unchanged.
                     addVertex should work in O(log n)
   */
   public void addVertex(String vName);
   
   /*
      precondition:  both Vertexes, source and target, are already stored in the graph.
      postcondition:  addEdge should work in O(1)
   */
   public void addEdge(String source, String target); 
   
   /*
       returns the whole graph as one string, e.g.:
       A [C]
       B [A]
       C [C D]
       D [C A]
     */
   public String toString(); 
 
}
 
  
/********************** Graphs 4: DFS and BFS *****/
interface DFS_BFS
{
   public List<Vertex> depthFirstSearch(String name);
   public List<Vertex> breadthFirstSearch(String name);
   /*   extra credit methods */
 //  public List<Vertex> depthFirstRecur(String name);
   //public List<Vertex> depthFirstRecurHelper(Vertex v, ArrayList<Vertex> reachable);
}
 
/****************** Graphs 5: Edgelist with Cities *****/
interface EdgeListWithCities
{
   public void readData(String cities, String edges) throws FileNotFoundException;
   public int edgeCount();
   public int vertexCount();
   public boolean isReachable(String source, String target);
   public boolean isStronglyConnected(); //return true if every vertex is reachable from every 
                                          //other vertex, otherwise false 
}
 
 
/*************  start the Adjacency-List graph  *********/
public class AdjList implements AdjListInterface,  DFS_BFS//, EdgeListWithCities
{
   //we want our map to be ordered alphabetically by vertex name
   private Map<String, Vertex> vertexMap = new TreeMap<String, Vertex>();
      
   /* constructor is not needed because of the instantiation above */
   public boolean isReachable(String source, String target)
   {
      List<Vertex> l = depthFirstSearch(source);
      List<String> sl = new List<String>();
      for(Vertex v : l)
         sl.add(v.getName());
      return sl.contains(target);
   }
   public boolean isStronglyConnected()
   {
      for(String s : vertexMap.keyset())
      {
         for(String s1: vertexMap.keySet())
            {
               if(!isReachable(s, s1))
                  return false;
            }
      }
      return true;
   }
   readData(String cities, String edges)
   {
      Scanner sc=new Scanner(new File(cities));
      while(sc.hasNextLine())
      {
         String x = sc.next();
         vertexMap.put(x, new Vertex(x));
      
      }
      Scanner sca = new Scanner(new File(edges))
      {
         while(sca.hasNext())
         {
            String from = sca.next();
            String to = sca.next();
            addEdge(from, to);
         }
      }
   }
   public int edgeCount()
   {
      int count = 0;
      for(Vertex v: vertexMap.getvertices())
         count += v.getAdjacencies().size();
      return count; 
   }
  public int vertexCount()
   {
      return vertexMap.size();
   }
   public List<Vertex> depthFirstSearch(String name)
   {
      List<Vertex> l = new ArrayList<>();
      Stack<Vertex> sta = new Stack<>();
      for(Vertex x : vertexMap.get(name).getAdjacencies())
         sta.push(x);
      while(!sta.isEmpty())
      {
         Vertex v = sta.pop();
         if(!l.contains(v))
         {
            l.add(v);
            for(Vertex ver : v.getAdjacencies())
               sta.push(ver);
         }
      }
      return l;
   }
   public List<Vertex> breadthFirstSearch(String name)
   {
      List<Vertex> l = new ArrayList<>();
      Queue<Vertex> q = new LinkedList<>();
      for(Vertex x : vertexMap.get(name).getAdjacencies())
         q.add(x);
      while(!q.isEmpty())
      {
         Vertex v = q.remove();
         if(!l.contains(v))
         {
            l.add(v);
            for(Vertex ver : v.getAdjacencies())
               q.add(ver);
         }
      }
      return l;
   }
   /* enter your code here  */
   public Set<Vertex> getVertices()
   {
      Set<Vertex> vertices = new HashSet<>(); 
      for(String s : vertexMap.keySet())
         vertices.add(vertexMap.get(s));
      return vertices;
   }
   public Vertex getVertex(String vName)
   {
      return vertexMap.get(vName);
   }
   public Map<String, Vertex> getVertexMap()  //this is just for codepost testing
   {
      return vertexMap;
   }
   /*      
      postcondition: if a Vertex with the name v exists, then the map is unchanged.
                     addVertex should work in O(log n)
   */
   public void addVertex(String vName)
   {
      vertexMap.put(vName, new Vertex(vName));
   }
   
   /*
      precondition:  both Vertexes, source and target, are already stored in the graph.
      postcondition:  addEdge should work in O(1)
   */
   public void addEdge(String source, String target)
   {
      vertexMap.get(source).addAdjacent(vertexMap.get(target));
   }
   
   /*
       returns the whole graph as one string, e.g.:
       A [C]
       B [A]
       C [C D]
       D [C A]
     */
   public String toString()
   {
      String s = "";
      for(String str : vertexMap.keySet())
      {
         s+= vertexMap.get(str).toString() + "\n";
      }
      return s.substring(0,s.length()-1);
   }
 
 
 
 
 
 
}