import util.Variable;
import util.mygraph;
import util.mynode;

import java.util.Iterator;
import java.util.LinkedList;

import util.*;

/**
 * Created by I309939 on 12/31/2015.
 */
public class main {
    static mygraph topo;
    static Variable_list VarList;
    static int R = 10;//External input events for first unit of time.
    static int C = 50;//available CPU cycles
    static int n = 4;//number of CPU sockets.
    static int m;//number of executors.

    public static void main(String[] args) throws Exception {

        //load the topology.
        topo = new Topology().g;
        m = topo.getNodeCount();
        //load the allocation plan.
        topo.prepareAllocationPlan();
        for (int i = 0; i < m; i++) {

            if (i < 2) {
                topo.allocateNode(0, i);
            } else topo.allocateNode(1, i);
        }
        //determine how many variables are there.
        //List<List<Double>> partitionPlan = new ArrayList<List<Double>>();//first dimension stands for source, the second dimension stands for destination.
        VarList = new Variable_list();
        buildVarListwith_h();


        for (int s = 0; s < n; s++) {
            System.out.println(L(s));
        }
    }
    //build variable list with h constraint function.
    private static void buildVarListwith_h() {
        int i = 0;
        Iterator it = topo.iterator();
        while (it.hasNext()) {
            mynode mn = (mynode) it.next();
            LinkedList<mynode> destinations = mn.getDestinations();
            int size = destinations.size();
            int h = 0;
            if (size > 1) {
                int j;
                mynode mn_dest;
                StringBuffer sb = new StringBuffer();

                for (j = 0; j < size; j++) {
                    mn_dest = destinations.get(j);
                    VarList.add(new Variable(mn.getId(), mn_dest.getId(), "P_" + i));
                    sb.append("P_" + i);
                    if (j != size - 1)
                        sb.append("+");
                    i++;
                }
                System.out.println(("h_" + (++h) + ":").concat(sb.toString().concat("=1")));
            } else if (size == 1) {
                mynode mn_dest = destinations.peek();
                VarList.add(new Variable(mn.getId(), mn_dest.getId(), "1"));//only single destination, no variable needed.
            }
        }
    }

    //build variable list with h constraint function.
    private static void buildVarListwithout_h() {
        int i = 0;
        Iterator it = topo.iterator();
        while (it.hasNext()) {
            mynode mn = (mynode) it.next();
            LinkedList<mynode> destinations = mn.getDestinations();
            int size = destinations.size();
            int h = 0;
            if (size > 1) {
                int j;
                mynode mn_dest;
                StringBuffer sb = new StringBuffer();

                for (j = 0; j < size; j++) {
                    mn_dest = destinations.get(j);
                    VarList.add(new Variable(mn.getId(), mn_dest.getId(), "P_" + i));
                    sb.append("P_" + i);
                    if (j != size - 1)
                        sb.append("+");
                    i++;
                }
                System.out.println(("h_" + (++h) + ":").concat(sb.toString().concat("=1")));
            } else if (size == 1) {
                mynode mn_dest = destinations.peek();
                VarList.add(new Variable(mn.getId(), mn_dest.getId(), "1"));//only single destination, no variable needed.
            }
        }
    }


    //build L for each socket
    public static String L(int i) {
        String ans = "";
        for (int j = 0; j < m; j++) {
            if (topo.executor_allocate(String.valueOf(j)) == i) {
                //j is allocated to this socket.
                ans = ans.concat("+" + l(String.valueOf(j)));
            }
        }
        if (ans.startsWith("+")) {
            ans = ans.substring(1);
        }
        return "L" + i + "=" + ans;
    }

    private static String l(String executor_j) {

        //process demand.
        String h1 = "";
        String input = Input(executor_j);
        if (input.startsWith("+")) {
            input = input.substring(1);
        }

        h1 = input.concat("*").concat(String.valueOf(topo.getNode(executor_j).getPd()));

        //push demand.
        String h2 = "";
        String output = input.concat("*").concat(topo.getNode(executor_j).getSelectivity());
        Iterator<mynode> it = topo.finAllRemoteDest(executor_j).listIterator();
        while (it.hasNext()) {
            mynode mj_ = it.next();
            h2 = h2
                    .concat("+")
                    .concat(output)
                    .concat("*")
                    .concat(VarList.getVariables(executor_j, mj_.getId()).var_name)
                    .concat("*")
                    .concat("5")//push cost.
            ;
        }
        return h1.concat(h2);
    }

    private static String Input(String executor_j) {//input I_j
        if (executor_j.equals("0")) return "R";
        else {
            LinkedList<mynode> parents = (topo.getNode(executor_j)).getParents();
            String ans = "";
            Iterator it = parents.iterator();
            while (it.hasNext()) {
                String executor_j_parent = ((mynode) it.next()).getId();
                ans = ans.concat("+");
                ans = ans.concat(VarList.getVariables(executor_j_parent, executor_j).var_name);
                ans = ans.concat("*");
                String input_parent = Input(executor_j_parent);
                if (input_parent.startsWith("+")) {
                    input_parent = input_parent.substring(1);
                }
                ans = ans.concat(input_parent);
                ans = ans.concat("*");
                ans = ans.concat(String.valueOf(topo.getNode(executor_j_parent).getSelectivity()));
            }
            return ans;
        }
    }
}