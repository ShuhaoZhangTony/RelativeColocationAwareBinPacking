package util;

/**
 * Created by szhang026 on 3/16/2016.
 */
public class Variable {
    public String var_name;
    String source_label;//belongs to which node.
    String dest_label;//belongs to which node.
    public Variable(String source_label, String dest_label, String var_name){
        this.source_label=source_label;
        this.dest_label=dest_label;
        this.var_name=var_name;
    }

}
