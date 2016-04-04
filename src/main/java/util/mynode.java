package util;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

import java.util.LinkedList;

/**
 * Created by I309939 on 1/2/2016.
 */
public class mynode extends SingleNode {
    private int pd = 0;//process demand.
    private float fd = 0;//fetch demand.
    private String selectivity=String.format("%.2f", Math.random());//selectivity
    public String getSelectivity() {
        return selectivity;
    }

    public void setSelectivity(String selectivity) {
        this.selectivity = selectivity;
    }


    private LinkedList<mynode> destinations = new LinkedList<mynode>();//every node could have multiple destinations.
    private LinkedList<mynode> parents = new LinkedList<mynode>();//every node could have multiple destinations.
    protected mynode(AbstractGraph graph, String id) {
        super(graph, id);
    }

    public mynode(AbstractGraph graph,String id, int pd, int fd) {
        super(graph,id);
    }

    public int getPd() {
        return pd;
    }

    public void setPd(int pd) {
        this.pd = pd;
    }

    public float getFd(boolean remote) {
        if (remote)
            return commonVar.RemoteFactor*fd;
        else
            return fd;
    }

    public LinkedList<mynode> getDestinations() {
        return destinations;
    }

    public void setDestinations(LinkedList<mynode> destinations) {
        this.destinations = destinations;
    }

    public void setFd(int fd) {
        this.fd = commonVar.Selectivity*fd;
    }

    public LinkedList<mynode> getParents() {
        return parents;
    }
}
