import edu.princeton.cs.algorithms.Digraph;
import edu.princeton.cs.algorithms.DigraphGenerator;

/**
 * Created by I309939 on 12/31/2015.
 */
public class TopologyProducer {

    public TopologyProducer() {

        Digraph dag = DigraphGenerator.dag(10, 10);
        System.out.println(dag);
    }
}
