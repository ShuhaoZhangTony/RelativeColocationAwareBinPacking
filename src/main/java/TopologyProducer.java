import edu.princeton.cs.algorithms.Digraph;
import edu.princeton.cs.algorithms.DigraphGenerator;
import org.graphstream.ui.view.Viewer;
import util.mygraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by I309939 on 12/31/2015.
 */
public class TopologyProducer {
    public mygraph g;

    public TopologyProducer() throws FileNotFoundException {

        //Digraph dag = DigraphGenerator.dag(6, 8);
        //System.out.println(dag);


       // System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        g = new mygraph("Tutorial 1");
        Scanner sc = new Scanner(new File( getClass().getClassLoader().getResource("dag.txt").getFile()));

        sc.nextLine();
        while (sc.hasNext()) {
            String[] array = sc.nextLine().trim().split(":");
            String node = array[0];
            if (g.getNode(node) == null)
                g.addNode(node,5,3);
            g.addAttribute("weight",1);
            if (array.length > 1) {
                Scanner _sc = new Scanner(array[1].trim());
                while (_sc.hasNext()) {
                    String to = _sc.next();

                    if (g.getNode(to) == null)
                        g.addNode(to, 5, 3);
                    g.addEdge(node.concat(to), node, to, true);
                }
            }

        }

    g.display();


    }

}
