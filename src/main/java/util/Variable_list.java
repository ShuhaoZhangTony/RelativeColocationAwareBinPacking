package util;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by szhang026 on 3/16/2016.
 */
public class Variable_list extends LinkedList {
    public Variable getVariables(String source, String dest){
        Iterator i = this.iterator();
        while(i.hasNext()){
            Variable v = (Variable) i.next();
            if(v.source_label.equals(source)&&v.dest_label.equals(dest)){
                return v;
            }
        }
        return null;
    }

}
