/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Represents a node in a virtual file system, which can be either a FILE or a FOLDER.
 * Everything was coded by me.
 *
 * Features:
 * - Stores the node's name and type (FILE or FOLDER).
 * - Folders maintain a list of child nodes, while files do not.
 * - Supports adding and removing child nodes for folders.
 * - Provides getter methods for name, type, and children.
 * - Equality is based on the node's name only (not type or contents).
 */

package filesystem;
import java.util.ArrayList; 

public class FileSystemNode {
    private NodeType type; // Indicates whether this node is a FILE or FOLDER
    private String name;   // Name of the file or folder
    private ArrayList<FileSystemNode> children; // Initialized only if node is a FOLDER

    // Constructor: sets name and type, initializes children if it's a folder
    public FileSystemNode(String name, NodeType type) {
        this.name = name; 
        this.type = type; 
        if (type == NodeType.FOLDER) {
            this.children = new ArrayList<>(); // Folders can hold children
        }
    }

    // Returns the name of the node
    public String getName() {
        return name; 
    }

    // Returns the type of the node (FILE or FOLDER)
    public NodeType getType() {
        return type;
    }

    // Returns the list of children (only for folders)
    public ArrayList<FileSystemNode> getChildren() {
        return children; 
    }

    // Adds a child node if this is a folder and doesn't already contain it
    public boolean addChild(FileSystemNode child) {
        if (this.type != NodeType.FOLDER) { // Can't add children to files
            throw new UnsupportedOperationException("Can't add child to a FILE node."); 
        }

        // Check for duplicates by name
        for (FileSystemNode node : children) {
            if (node.equals(child)) {
                return false; 
            }
        }

        return children.add(child); // Add new child
    }

    // Removes a child node if this is a folder
    public boolean removeChild(FileSystemNode child) {
        if (this.type != NodeType.FOLDER) { // Can't remove from a file
            throw new UnsupportedOperationException("Can't remove a child from a FILE node."); 
        }

        return children.remove(child); 
    }

    // Equality check based on node name only
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; 
        if (obj instanceof FileSystemNode other) {
            return this.name.equals(other.getName()); 
        }

        return false; 
    }
}