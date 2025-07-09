package filesystem;
import java.util.Stack; 
import java.util.Iterator; 

public class FileManager {
    Stack<FileSystemNode> path = new Stack<>(); 

    public FileManager(){
        this(new FileSystemNode("/", NodeType.FOLDER)); 
    }

    public FileManager(FileSystemNode root){
        path.push(root);
    }

    public boolean cd(String path){
        if (path.equals("..")){
            if(this.path.size() < 2){
                return true; 
            } else {
                this.path.pop(); 
                return true; 
            }
        }

        FileSystemNode currentNode = this.path.peek(); 
        for (FileSystemNode node : currentNode.getChildren()){
            if (node.getType() == NodeType.FOLDER && path.equals(node.getName())){
                this.path.push(node); 
                return true; 
            }
        }

        return false;
    }

    public void ls(){
        if (path.peek().getChildren().size() == 0){
            System.out.println();
        }

        for (FileSystemNode node : path.peek().getChildren()){
            System.out.println("  " + node.getName());
        }

    }

    public void pwd(){
        if (path.size() < 2){
            System.out.println(path.peek().getName()); 
        } else {
            Iterator<FileSystemNode> it = path.iterator(); 

            it.next(); // skip root
            while (it.hasNext()){
                System.out.print("/" + it.next().getName()); 
            }
        }

        System.out.println(); 

    }
}
