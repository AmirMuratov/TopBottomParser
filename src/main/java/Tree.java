import java.util.Arrays;
import java.util.List;

/**
 * Created by amir on 23.03.16.
 */
public class Tree {
    String node;
    List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }
}
