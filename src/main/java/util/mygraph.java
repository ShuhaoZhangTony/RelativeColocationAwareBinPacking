package util;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by I309939 on 1/2/2016.
 */
public class mygraph extends SingleGraph {
    public int[] getAllocation_plan() {
        return allocation_plan;
    }

    public void setAllocation_plan(int[] allocation_plan) {
        this.allocation_plan = allocation_plan;
    }

    private int allocation_plan[];


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

    public LinkedList<mynode> finAllRemoteDest(String i){
        mynode mi=this.getNode(i);

        LinkedList<mynode> ans=new LinkedList<mynode>();
        Iterator<mynode> it=mi.getDestinations().listIterator();
        while(it.hasNext()){
            mynode mj=it.next();
            if(executor_allocate(mi.getId())!= executor_allocate(mj.getId())){
                ans.add(mj);
            }
        }
        return ans;
    }


    public boolean prepareAllocationPlan() {
        if (this.getNodeCount() == 0) return false;
        else allocation_plan = new int[this.getNodeCount()];
        return true;
    }

    public void allocateNode(int Socket, int node) {
        allocation_plan[node] = Socket;
    }
    public int executor_allocate(String node) {
        return allocation_plan[Integer.parseInt(node)];
    }

    public mynode getNode(String id) {
        return (mynode)this.nodeMap.get(id);
    }

    public <T extends Edge> T addEdge(String id, String from, String to, boolean directed){
        (this.getNode(from)).getDestinations().add(this.getNode(to));
        (this.getNode(to)).getParents().add(this.getNode(from));
        return super.addEdge(id,from,to,directed);
    }
}
