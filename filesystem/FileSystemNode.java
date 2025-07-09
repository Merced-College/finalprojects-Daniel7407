package filesystem;
import java.util.ArrayList; 

public class FileSystemNode{
    NodeType type; 
    String name; 
    ArrayList<FileSystemNode> children; 

    public FileSystemNode(String name, NodeType type){
        this.name = name; 
        this.type = type; 
        if(type == NodeType.FOLDER) {
            this.children = new ArrayList<>(); 
        }
    }

    public String getName(){
        return name; 
    }

    public NodeType getType(){
        return type;
    }

    public ArrayList<FileSystemNode> getChildren(){
        return children; 
    }

    public boolean addChild(FileSystemNode child){
        if(this.type != NodeType.FOLDER){
            throw new UnsupportedOperationException("Can't add child to a FILE node."); 
        }

        for (FileSystemNode node: children){
            if(node == child){
                return false; 
            }
        }

        return children.add(child);
    }

    public boolean removeChild(FileSystemNode child){
        if(child.type != NodeType.FOLDER){
            throw new UnsupportedOperationException("Can't add child to a FILE node."); 
        }

        return children.remove(child); 
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true; 
        if (obj instanceof FileSystemNode other){
            return this.name.equals(other.getName()); 
        }

        return false; 
    }
}