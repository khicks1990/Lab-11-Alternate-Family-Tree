import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Arrays;

/**
 * This class represents a binary tree for a genealogy tree.
 */
class BinaryTree {

    // Nested class representing a node in the binary tree
    class Node {
        String value; // The name or value of the node
        Node left, right; // References to left and right children

        Node(String v, Node l, Node r) {
            value = v;
            left = l;
            right = r;
        }
    }

    private Node tree;  // Root of the binary tree

    public BinaryTree() {
        tree = null; // Initialize the tree as empty
    }

    // Method to add a root node to the tree
    public void addRoot(String name) {
        if (tree == null) {
            tree = new Node(name, null, null);
        }
    }

    // Method to add a left child to a specified parent node
    public void addLeftChild(String parent, String child) {
        // Locate the parent node
        // Ensure the left child is empty
        // Create and assign the left child
    }

    // Method to add a right child to a specified parent node
    public void addRightChild(String parent, String child) {
        // Locate the parent node
        // Ensure the right child is empty
        // Create and assign the right child
    }

    // Method to locate a node by its value
    private Node locate(Node t, String value) {
        if (t == null) return null; // Base case: tree is empty
        if (t.value.equals(value)) return t; // Node found
        Node leftResult = locate(t.left, value); // Search in the left subtree
        if (leftResult != null) return leftResult; // Node found in left subtree
        return locate(t.right, value); // Search in the right subtree
    }

    // Method to get all descendants of a specified node
    public List<String> descendants(String name) {
        // Locate the node by name
        // Create List to hold descendants
        // Populate the list if node is found
        // Return the list of descendants
    }

    // Helper method to recursively collect descendants
    private void descendants(Node t, java.util.List<String> desc) {
        desc.add(t.value); // Add current node to descendants list
        if (t.left != null) descendants(t.left, desc); // Recurse for left child
        if (t.right != null) descendants(t.right, desc); // Recurse for right child
    }

    // Method to get all ancestors of a specified node
    public List<String> ancestors(String name) {
        java.util.LinkedList<String> ances = new java.util.LinkedList<>(); // List to hold ancestors
        ancestors(name, tree, ances); // Start the search from the root
        return ances;
    }

    // Helper method to recursively find ancestors
    private boolean ancestors(String name, Node t, java.util.LinkedList<String> ances) {
        if (t == null) return false; // Base case: tree is empty
        ances.add(t.value); // Add current node to ancestors list
        if (t.value.equals(name)) return true; // Node found
        // Recurse through left and right children
        if (ancestors(name, t.left, ances)) return true;
        if (ancestors(name, t.right, ances)) return true;
        ances.removeLast(); // Remove current node if not part of the ancestors
        return false;
    }

    // Method to get the root of the tree
    public Node getRoot() {
        return tree;
    }
}

public class FamilyTree extends JFrame {
    private BinaryTree familyTree = new BinaryTree(); // Instance of the binary tree
    private JTextField cmdTextField = new JTextField(20); // Input field for commands
    private JPanel treeViewPanel; // Panel to display the tree structure

    public FamilyTree() {
        initUI(); // Initialize the user interface
    }

    // Method to set up the UI components
    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        setTitle("Family Tree"); // Set window title
        setLayout(new BorderLayout(5, 5)); // Set layout

        // Help text area for commands
        JTextArea cmdHelpTextArea = new JTextArea(6, 40);
        cmdHelpTextArea.setEditable(false);
        cmdHelpTextArea.setBorder(new EmptyBorder(5,5,5,5));
        cmdHelpTextArea.setText(String.join("\n",
            Arrays.asList(
                "Available Commands Are:",
                "               root name",
                "               left parent child",
                "               right parent child",
                "               descendants person",
                "               ancestors person"
            )
        ));
        add(new JScrollPane(cmdHelpTextArea), BorderLayout.NORTH); // Add help text area

        // Tree view panel to display the binary tree
        treeViewPanel = new JPanel(new BorderLayout());
        treeViewPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(treeViewPanel), BorderLayout.CENTER); // Add tree view panel

        // Command panel with input field
        JPanel cmdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmdPanel.add(new JLabel("Enter command:"));
        cmdTextField.setPreferredSize(new Dimension(300, 25));
        cmdPanel.add(cmdTextField);
        add(cmdPanel, BorderLayout.SOUTH); // Add command panel

        // Event handling for command input
        cmdTextField.addActionListener(new CmdListener());

        setSize(800, 600); // Set window size
        setLocationRelativeTo(null); // Center the window on screen
    }

    // Inner class to handle command input events
    private class CmdListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = cmdTextField.getText().trim(); // Get command text
            cmdTextField.setText(""); // Clear input field
            String[] cmdParts = cmd.split("\\s+"); // Split command into parts

            if (cmdParts.length == 0) return; // No command entered

            // Process the command based on the first part
            switch (cmdParts[0].toLowerCase()) {
                case "root":
                    if (cmdParts.length > 1) {
                        familyTree.addRoot(cmdParts[1]); // Add root
                        updateTreeView(); // Update the tree view
                    }
                    break;
                case "left":
                    if (cmdParts.length > 2) {
                        familyTree.addLeftChild(cmdParts[1], cmdParts[2]); // Add left child
                        updateTreeView(); // Update the tree view
                    }
                    break;
                case "right":
                    if (cmdParts.length > 2) {
                        familyTree.addRightChild(cmdParts[1], cmdParts[2]); // Add right child
                        updateTreeView(); // Update the tree view
                    }
                    break;
                case "ancestors":
                    if (cmdParts.length > 1) {
                        List<String> ancestors = familyTree.ancestors(cmdParts[1]); // Get ancestors
                        showListDialog("Ancestors of " + cmdParts[1], ancestors); // Show dialog
                    }
                    break;
                case "descendants":
                    if (cmdParts.length > 1) {
                        List<String> descendants = familyTree.descendants(cmdParts[1]); // Get descendants
                        showListDialog("Descendants of " + cmdParts[1], descendants); // Show dialog
                    }
                    break;
            }
        }
    }

    // Method to update the tree view display
    private void updateTreeView() {
        treeViewPanel.removeAll(); // Clear existing view
        JPanel treeDisplay = BTreeDisplay.createBTreeDisplay(familyTree.getRoot()); // Create new display
        treeViewPanel.add(treeDisplay, BorderLayout.CENTER); // Add display to panel
        treeViewPanel.revalidate(); // Refresh the panel
        treeViewPanel.repaint(); // Repaint the panel
    }

    // Method to show a dialog with a list of names
    private void showListDialog(String title, List<String> items) {
        JTextArea textArea = new JTextArea(10, 20);
        textArea.setEditable(false); // Make text area read-only
        items.forEach(item -> textArea.append(item + "\n")); // Add items to text area
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), title, JOptionPane.PLAIN_MESSAGE); // Show dialog
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FamilyTree().setVisible(true)); // Launch the GUI
    }
}

// Class to display the binary tree visually
class BTreeDisplay {
    static JPanel createBTreeDisplay(BinaryTree.Node node) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Set border

        if (node == null) {
            panel.add(new JLabel("Empty Tree"), BorderLayout.CENTER); // Display message if tree is empty
            return panel;
        }

        // Create a text field to display the node's value
        JTextField nodeField = new JTextField(node.value);
        nodeField.setEditable(false); // Make field read-only
        nodeField.setHorizontalAlignment(JTextField.CENTER);
        nodeField.setPreferredSize(new Dimension(80, 25));
        JPanel nodePanel = new JPanel();
        nodePanel.add(nodeField); // Add node field to panel

        panel.add(nodePanel, BorderLayout.NORTH); // Add node panel to the top of the display

        if (node.left != null || node.right != null) {
            JPanel childrenPanel = new JPanel();
            childrenPanel.setLayout(new BoxLayout(childrenPanel, BoxLayout.X_AXIS)); // Horizontal layout
            childrenPanel.add(Box.createHorizontalGlue()); // Add glue for spacing

            if (node.left != null) {
                childrenPanel.add(createBTreeDisplay(node.left)); // Recursively display left child
                childrenPanel.add(Box.createHorizontalStrut(20)); // Add space
            }

            if (node.right != null) {
                childrenPanel.add(createBTreeDisplay(node.right)); // Recursively display right child
                childrenPanel.add(Box.createHorizontalGlue()); // Add glue for spacing
            }

            panel.add(childrenPanel, BorderLayout.CENTER); // Add children panel to the center
        }

        return panel; // Return the constructed panel
    }
}