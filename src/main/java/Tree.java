import org.StructureGraphic.v1.DSTreeNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amir on 23.03.16.
 */
public class Tree implements DSTreeNode {
    String node;
    boolean isLeaf = false;
    List<Tree> children = new ArrayList<>();

    public Tree(String node) {
        this.node = node;
    }

    public Tree(String node, boolean isLeaf) {
        this.node = node;
        this.isLeaf = isLeaf;
    }

    public void addChild(Tree child) {
        children.add(child);
    }

    @Override
    public DSTreeNode[] DSgetChildren() {
        return children.toArray(new DSTreeNode[children.size()]);
    }

    @Override
    public Object DSgetValue() {
        return node;
    }

    @Override
    public Color DSgetColor() {
        return isLeaf ? Color.red : Color.black;
    }
}
