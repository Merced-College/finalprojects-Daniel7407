/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * This class simulates a basic command-line file manager operating on a virtual file system.
 * It uses a stack-based path structure to track the current working directory and supports 
 * common file system operations through various methods. Everything was coded by me. 
 *
 * Features:
 * - cd: Navigate between folders (including ".." for parent and null for root).
 * - ls: List directory contents (with optional sorting using "-s" flag).
 * - pwd: Print current working directory path.
 * - mkdir: Create new folders.
 * - touch: Create new files with an extension.
 * - delete: Remove files or folders from the current directory.
 * - find: Search for a file/folder starting from the current directory (returns relative path).
 * - stats: Display file system statistics (total files, folders, maximum depth).
 * - help: Show usage details for all supported commands or a specific one.
 * - clear: Clears the terminal output.
 */

package filesystem;
import java.util.*; 

public class FileManager {
    // Stack to maintain the current directory path
    private Stack<FileSystemNode> path = new Stack<>(); 

    // Map storing help descriptions for each supported command
    private static final Map<String, String> helpMap = Map.ofEntries(
        Map.entry("cd", "Usage: cd <folder_name>\n" +
            "Changes the current directory.\n" +
            "- Only one directory change allowed per command.\n" +
            "- Use '..' to go up one directory.\n" +
            "- No parameter returns you to the root directory."),
            
        Map.entry("ls", "Usage: ls [-s]\n" +
            "Lists files and folders in the current directory.\n" +
            "- Add '-s' flag to sort results alphabetically."),
            
        Map.entry("pwd", "Usage: pwd\n" +
            "Prints the current working directory path."),
            
        Map.entry("mkdir", "Usage: mkdir <folder_name>\n" +
            "Creates a new folder in the current directory."),
            
        Map.entry("touch", "Usage: touch <file_name>\n" +
            "Creates a new file in the current directory."),
            
        Map.entry("delete", "Usage: delete <name>\n" +
            "Deletes a file or folder in the current directory."),
            
        Map.entry("find", "Usage: find <name>\n" +
        "Searches for a file or folder starting from the current directory.\n" +
        "- Returns the path relative to the current directory, not the full root path."),

        Map.entry("stats", "Usage: stats\n" +
            "Displays file system statistics:\n" +
            "- Number of files\n" +
            "- Number of folders\n" +
            "- Maximum depth"),
            
        Map.entry("help", "Usage: help [command]\n" +
            "Displays help information for all commands or a specific command.")
    );

    // Constructor initializes FileManager with root node
    public FileManager(){
        this(new FileSystemNode("/", NodeType.FOLDER)); 
    }

    // Constructor accepts a custom root node
    public FileManager(FileSystemNode root){
        path.push(root); // Push root node onto the stack
    }

    // Recursive Merge Sort for string arrays
    private void mergeSort(String[] arr, String[] temp, int left, int right){
        if (left >= right){
            return; // Base case: one element
        }

        int mid = left + (right - left) / 2; 
        mergeSort(arr, temp, left, mid);  // Sort left half
        mergeSort(arr, temp, mid + 1, right); // Sort right half
        merge(arr, temp, left, mid, right);  // Merge sorted halves
    }

    // Merge two sorted halves of the array
    private void merge(String[] arr, String[] temp, int left, int mid, int right){
        // Copy elements into temp for merging
        for (int i = left; i <= right; i++){
            temp[i] = arr[i]; 
        }

        int i = left; 
        int j = mid + 1; 
        int k = left; 

        // Compare and place elements back into arr in sorted order
        while (i <= mid && j <= right){
            if (temp[i].compareTo(temp[j]) <= 0){
                arr[k++] = temp[i++]; // Take from left
            } else {
                arr[k++] = temp[j++]; // Take from right
            }
        }

        // Copy any remaining elements from left half
        while (i <= mid){
            arr[k++] = temp[i++]; 
        }
    }

    // Changes the current directory
    public boolean cd(String path){
        if (path == null){
            // Go to root by popping everything except root
            while (this.path.size() > 1){
                this.path.pop();
            }
            return true; 
        }

        if (path.equals("..")){
            // Move up one directory if not at root
            if (this.path.size() > 1){
                this.path.pop(); 
            }
            return true; 
        }

        // Search children for a matching folder
        FileSystemNode currentNode = this.path.peek(); 
        for (FileSystemNode node : currentNode.getChildren()){
            if (node.getType() == NodeType.FOLDER && path.equals(node.getName())){
                this.path.push(node); // Move into that folder
                return true; 
            }
        }

        return false; // Folder not found
    }

    // Lists contents of current directory (optionally sorted with -s)
    public void ls(String flags){
        if (flags != null && flags.equals("-s")){
            // Prepare array for sorting
            ArrayList<FileSystemNode> children = path.peek().getChildren(); 
            String[] names = new String[children.size()];

            for (int i = 0; i < children.size(); i++){
                names[i] = children.get(i).getName(); 
            }

            mergeSort(names, new String[names.length], 0, names.length - 1);

            for (String name : names){
                System.out.println(" " + name); 
            }
            return; // Done after sorting
        }
        
        // Print unsorted if no -s flag
        if (path.peek().getChildren().size() == 0){
            System.out.println();
        }

        for (FileSystemNode node : path.peek().getChildren()){
            System.out.println("  " + node.getName());
        }
    }

    // Prints the current working directory path
    public void pwd(){
        if (path.size() < 2){
            System.out.println(path.peek().getName()); 
        } else {
            Iterator<FileSystemNode> it = path.iterator(); 
            it.next(); // Skip root
            while (it.hasNext()){
                System.out.print("/" + it.next().getName()); 
            }
            System.out.println(); 
        }
    }

    // Creates a new folder in the current directory
    public void mkdir(String folderName){
        boolean success = path.peek().addChild(new FileSystemNode(folderName, NodeType.FOLDER)); 
        if(!success){
            System.out.println("'" + folderName + "' already exists. Please choose a different name.");
        }
    }

    // Creates a new file in the current directory
    public void touch(String fileName){
        if (fileName.split("\\.").length < 2){
            System.out.println("Invalid file name. Please include an extension."); 
            return; 
        }

        boolean success = path.peek().addChild(new FileSystemNode(fileName, NodeType.FILE));
        if(!success){
            System.out.println("'" + fileName + "' already exists. Please choose a different name.");
        }
    }

    // Deletes a file or folder in the current directory
    public void delete(String nodeName){
        boolean success = path.peek().removeChild(new FileSystemNode(nodeName, NodeType.FILE));
        if(!success){
            System.out.println("No folder or file named '" + nodeName + "'' exists.");
        }
    }

    // Finds a file or folder starting from the current directory
    public void find(String target){
        String path = find(new FileSystemNode(target, NodeType.FILE), this.path.peek(), "");
        if (path != null){
            System.out.println(path); 
        } else {
            System.out.println("'" + target + "' was not found in the current folder or any subfolders.");
        }
    }

    // Recursive helper for find()
    private String find(FileSystemNode target, FileSystemNode startNode, String path){
        String separator = path.isEmpty() || path.endsWith("/") ? "" : "/"; 
        String currentPath = path + separator + startNode.getName(); 

        if (startNode.equals(target)){
            return currentPath; // Target found
        }

        // Recurse into folders
        if (startNode.getType() == NodeType.FOLDER){
            for(FileSystemNode child : startNode.getChildren()){
                String result = find(target, child, currentPath); 
                if (result != null){
                    return result; // Found in subtree
                }
            }
        }

        return null; // Not found
    }

    // Prints file system statistics (files, folders, max depth)
    public void stats(){
        Queue<Map.Entry<FileSystemNode, Integer>> queue = new LinkedList<>(); 

        int numFiles = 0; 
        int numFolders = -1; // Start at -1 to exclude root
        int maxDepth = 0; 

        queue.add(Map.entry(path.firstElement(), 0)); // Start from root at depth 0

        while(!queue.isEmpty()){
            Map.Entry<FileSystemNode, Integer> entry = queue.poll(); 
            FileSystemNode current = entry.getKey(); 
            int depth = entry.getValue(); 

            maxDepth = Math.max(maxDepth, depth); // Track deepest level

            if (current.getType() == NodeType.FOLDER){
                numFolders++; 
                for(FileSystemNode child : current.getChildren()){
                    queue.add(Map.entry(child, depth + 1)); // Enqueue children with updated depth
                }
            } else {
                numFiles++; 
            }
        }

        System.out.println("Statistics:"); 
        System.out.println("Number of Files: " + numFiles); 
        System.out.println("Number of Folders: " + numFolders); 
        System.out.println("Maximum Depth: " + maxDepth); 
    }

    // Prints help for all commands or a specific command
    public void help(String command){
        if (command == null){
            System.out.println("Here are all available commands: "); 
            for(String key : helpMap.keySet()){
                System.out.println("- " + key);
            }
            return;
        }

        String helpText = helpMap.get(command); 
        if (helpText == null){
            System.out.println("'" + command + "' is not a valid command.");
        } else {
            System.out.println(helpText); 
        }
    }

    // Clears the terminal screen
    public void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush(); 
    }
}
