import edu.princeton.cs.algorithms.Digraph;
import edu.princeton.cs.algorithms.DigraphGenerator;
import prefuse.data.Graph;

/**
 * Created by I309939 on 12/31/2015.
 */
public class TopologyProducer {

    public TopologyProducer() {

        Digraph dag = DigraphGenerator.dag(10, 10);
        System.out.println(dag);


        Graph g =new Graph();
        for(int i=0;i<10;i++){
            
        }


    }
}
