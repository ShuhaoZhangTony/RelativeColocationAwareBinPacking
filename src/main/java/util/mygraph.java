package util;


import org.graphstream.graph.Graph;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;

/**
 * Created by I309939 on 1/2/2016.
 */
public class mygraph extends SingleGraph {

    public mygraph(String id) {
        super(id);
        // All we need to do is to change the node factory
        setNodeFactory(new NodeFactory<SingleNode>() {
            public mynode newInstance(String id, Graph graph) {
                return new mynode((AbstractGraph) graph, id);
            }
        });
    }


    public mynode addNode(String id, int pd, int fd) {

        mynode n = this.addNode(id);
        n.addAttribute("ui.label", id);
        n.setPd(pd);
        n.setFd(fd);
        return n;
    }



}
