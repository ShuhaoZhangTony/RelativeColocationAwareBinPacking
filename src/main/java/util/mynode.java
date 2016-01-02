package util;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;
/**
 * Created by I309939 on 1/2/2016.
 */
public class mynode extends SingleNode {
    private int pd = 0;//process demand.
    private float fd = 0;//fetch demand.

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

    public void setFd(int fd) {
        this.fd = commonVar.Selectivity*fd;
    }
}
