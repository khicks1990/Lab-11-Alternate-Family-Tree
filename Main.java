
/**
 * Programming Challenge 21-2. Family Tree.
 */
import javax.swing.*;
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This binary tree class represents the genealogy tree.
 */
class BinaryTree
{

    class Node
    {

        String value;
        Node left, right;

        Node(String v, Node l, Node r)
        {
            value = v;
            left = l;
            right = r;
        }
    }

    private Node tree;  // root

    /**
     * Constructor.
     */
    public BinaryTree()
    {
        tree = null;
    }

    /**
     * root sets the root of an empty tree.
     *
     * @param name The value to use for the root.
     */
    public void addRoot(String name)
    {
        if (tree == null)
        {
            tree = new Node(name, null, null);
        }
    }

    /**
     * addLeftChild adds a child as a left child of an existing node.
     *
     * @param parent The parent node.
     * @parame child The child node.
     */
    public void addLeftChild(String parent, String child)
    {

    }

    /**
     * addRightChild adds a child as a right child of an existing node.
     *
     * @param parent The parent node.
     * @param child The child node.
     */
    public void addRightChild(String parent, String child)
    {

    }

    /**
     * locate returns a reference to the node in a tree containing a given
     * value.
     *
     * @param t The tree to search.
     * @value The value to search for.
     * @return The node containing the value, or null.
     */
    private Node locate(Node t, String value)
    {
        if (t == null)
        {
            return null;
        }
        if (t.value.equals(value))
        {
            return t;
        }
        Node leftResult = locate(t.left, value);
        if (leftResult != null)
        {
            return leftResult;
        } else
        {
            return locate(t.right, value);
        }
    }

    /**
     * This method returns a list of descendants of a a node with a given value.
     *
     * @param name: the value in the node whose descendants are to be returned.
     */
    public java.util.List<String> descendants(String name)
    {
        Node t = this.locate(tree, name);
        // desc will hold the list of descendants
        ArrayList<String> desc = new ArrayList<>();
        if (t != null)
        {
            this.descendants(t, desc);
        }
        return desc;
    }

    /**
     * Adds all the descendants of the node t to the the list desc.
     *
     * @param t The node
     * @param desc A list to hold the descendants of the node t.
     */
    private void descendants(Node t, java.util.List<String> desc)
    {
        // Add the value of the current node t to the desc list
        // Check if the current node t has a left child
        // Recursively call the descendants method for the left child of t
        // Check if the current node t has a right child
        // Recursively call the descendants method for the right child of t
    }

    /**
     * Accumulates all ancestors of name in the list ances.
     *
     * @param name The name of the node whose ancestors are to be accumulated.
     * @param t The root of a subtree in which name is believed to lie.
     * @param ances The list of accumulated ancestors.
     * @return true if a node with name was found in t, false otherwise.
     */
    private boolean ancestors(String name, Node t, LinkedList<String> ances)
    {
        if (t == null)
        {
            return false;
        }
        // Add current node to list of possible ancestors
        ances.add(t.value);
        if (t.value.equals(name))
        {
            return true;
        }
        // Name has not been found. Check the left subtree.
        if (ancestors(name, t.left, ances))
        {
            return true;
        }
        // Name has not been found. Check the right subtree.
        if (ancestors(name, t.right, ances))
        {
            return true;
        }
       // Name was not found in the subtree, remove name from ances
        // and backtrack.
        ances.removeLast();
        return false;
    }

    /**
     * ancestors.
     *
     * @param name The name of a node whose ancestors are to be computed.
     * @return The list of ancestors of the given node.
     */
    public java.util.List<String> ancestors(String name)
    {
        LinkedList<String> ances = new LinkedList<>();
        ancestors(name, tree, ances);
        return ances;
    }

    /**
     * Returns a graphical view of the structure of the tree.
     */
    Parent getView()
    {
        return BTreeDisplay.createBTreeDisplay(tree);
    }
}

public class Main extends Application
{

    private BinaryTree familyTree = new BinaryTree();
    private TextField cmdTextField = new TextField();
    private Parent treeView = null;
    private BorderPane rootPane = new BorderPane();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        rootPane.setPadding(new Insets(10));

        // Label and Command TextField will go at the bottom of the UI        
        HBox cmdHBox = new HBox(10);
        cmdHBox.getChildren().addAll(new Label("Enter command:"), cmdTextField);
        cmdHBox.setPadding(new Insets(10, 0, 0, 0));
        rootPane.setBottom(cmdHBox);

        //Create Help  nonEditable Text Area to go at top of UI
        String[] cmds =
        {
            "Available Commands Are:",
            "               root name",
            "               left parent child",
            "               right parent child",
            "               descendants person",
            "               ancestors person",
        };
        TextArea cmdHelpTextArea = new TextArea();
        cmdHelpTextArea.setEditable(false);
        for (String s : cmds)
        {
            cmdHelpTextArea.appendText(s + "\n");
        }
        rootPane.setTop(cmdHelpTextArea);

        // Stage and Scene stuff
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.setTitle("Family Tree");
        stage.show();

        // EventHandler stufff
        cmdTextField.setOnAction(new CmdListener());
    }

    private class CmdListener implements EventHandler<ActionEvent>
    {

        public void handle(ActionEvent evt)
        {
            String cmd = cmdTextField.getText();
            String[] cmdParts = cmd.split(" ");

            if (cmdParts[0].equalsIgnoreCase("root"))
            {
                if (cmdParts.length > 1)
                {
                    familyTree.addRoot(cmdParts[1]);
                    treeView = familyTree.getView();
                    rootPane.setCenter(treeView);
                }
            }
            if (cmdParts[0].equalsIgnoreCase("left"))
            {
                if (cmdParts.length > 2)
                {
                    // left parent child
                    familyTree.addLeftChild(cmdParts[1], cmdParts[2]);
                    treeView = familyTree.getView();
                    rootPane.setCenter(treeView);
                }
            }
            if (cmdParts[0].equalsIgnoreCase("right"))
            {
                if (cmdParts.length > 2)
                {
                    // left parent child
                    familyTree.addRightChild(cmdParts[1], cmdParts[2]);
                    treeView = familyTree.getView();
                    rootPane.setCenter(treeView);
                }
            }
            if (cmdParts[0].equalsIgnoreCase("ancestors"))
            {
                if (cmdParts.length > 1)
                {
                    java.util.List<String> ancestors = familyTree.ancestors(cmdParts[1]);
                    JTextArea tArea = new JTextArea();
                    tArea.setEditable(false);
                    for (String s : ancestors)
                    {
                        tArea.append(s + "\n");
                    }
                    JOptionPane.showMessageDialog(null, tArea);
                }
            }
            if (cmdParts[0].equalsIgnoreCase("descendants"))
            {
                if (cmdParts.length > 1)
                {
                    java.util.List<String> descendants = familyTree.descendants(cmdParts[1]);
                    JTextArea tArea = new JTextArea();
                    tArea.setEditable(false);
                    for (String s : descendants)
                    {
                        tArea.append(s + "\n");
                    }
                    JOptionPane.showMessageDialog(null, tArea);
                }
            }
        }
    }

}

class BTreeDisplay
{

    static Parent createBTreeDisplay(BinaryTree.Node tree)
    {
        if (tree == null)
        {
            return new javafx.scene.control.Label();
        }
        // Each node's value goes into a text field
        TextField nodeTf = new TextField(String.valueOf(tree.value));
        nodeTf.setAlignment(Pos.CENTER);
        nodeTf.setPrefColumnCount(4);
        HBox nodeTfHBox = new HBox();
        nodeTfHBox.setAlignment(Pos.CENTER);
        nodeTfHBox.getChildren().add(nodeTf);
        if (tree.left == null && tree.right == null)
        {
            return nodeTfHBox;
        }

           // View for binary tree is a BorderPane with node value
        // at the top, left subtree in Left and right subtree in Right
        BorderPane view = new BorderPane();
        view.setTop(nodeTfHBox);
        BorderPane.setAlignment(nodeTf, Pos.CENTER);

        if (tree.left != null)
        {
            Parent leftSubTreeView = createBTreeDisplay(tree.left);
            view.setLeft(leftSubTreeView);
            BorderPane.setAlignment(leftSubTreeView, Pos.TOP_LEFT);
        }

        view.setCenter(new javafx.scene.control.Label("    ")); // Spacer

        if (tree.right != null)
        {
            Parent rightSubTreeView = createBTreeDisplay(tree.right);
            view.setRight(rightSubTreeView);
            BorderPane.setAlignment(rightSubTreeView, Pos.TOP_RIGHT);
        }
        return view;
    }
}
