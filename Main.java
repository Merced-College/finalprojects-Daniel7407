/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Entry point for the Terminal File Manager application.
 * Everything was coded by me.
 *
 * Features:
 * - Initializes a virtual file system with a predefined structure (folders and files).
 * - Registers all supported commands (cd, ls, pwd, mkdir, touch, delete, find, stats, help, clear).
 * - Displays a welcome message and instructions for using commands.
 * - Provides a command loop for user input, allowing navigation and file management.
 * - Processes commands using a Command pattern (string-to-command mapping via HashMap).
 */

import filesystem.*; 
import commands.*; 
import java.util.HashMap;
import java.util.Scanner;

class Main{
    public static void main(String[] args) {
        HashMap<String, Command> commandMap = new HashMap<>(); 

        setUpCommands(commandMap); // Registers all supported commands in the map

        FileSystemNode root = setUpDefaultSystem(); 
        FileManager fm = new FileManager(root); 

        // Clear terminal screen (ANSI escape code)
        System.out.print("\033[H\033[2J");
        System.out.flush(); 

        // Welcome message
        System.out.println("""
        Welcome to Terminal File Manager!
        Here you can practice navigating and managing a virtual file system.

        Currently supported commands:
        - cd
        - ls
        - pwd
        - mkdir
        - touch
        - delete
        - find
        - stats
        - help

        Type `help [command]` to get more information on what each command does or just `help` to see all commands.
        """);

        Scanner scanner = new Scanner(System.in); 
        System.out.print("user@desktop: "); 
        String userInput = scanner.nextLine(); 

        // Main input loop until user types exit
        while(!userInput.equals("exit")){
            String[] stringCommand = userInput.split(" ");
            Command cmd = commandMap.get(stringCommand[0]);
            if(cmd != null){
                cmd.execute(stringCommand, fm); 
            } else {
                System.out.println("'" + stringCommand[0] + "' is an invalid command."); 
            }

            System.out.println(); 

            System.out.print("user@desktop: "); 
            userInput = scanner.nextLine(); 

        }

        scanner.close();
    }

    // Sets up command map for linking strings with its respective command
    public static void setUpCommands(HashMap<String, Command> commandMap){
        commandMap.put("cd", new CdCommand()); 
        commandMap.put("ls", new LsCommand()); 
        commandMap.put("pwd", new PwdCommand()); 
        commandMap.put("mkdir", new MkdirCommand()); 
        commandMap.put("delete", new DeleteCommand());
        commandMap.put("touch", new TouchCommand());
        commandMap.put("find", new FindCommand()); 
        commandMap.put("stats", new StatsCommand()); 
        commandMap.put("help", new HelpCommand()); 
        commandMap.put("clear", new ClearCommand()); 
    }

    // Creates a root node and populates it with folder and files
    public static FileSystemNode setUpDefaultSystem(){
        FileSystemNode root = new FileSystemNode("/", NodeType.FOLDER);

        // /home
        FileSystemNode home = new FileSystemNode("home", NodeType.FOLDER);
        root.addChild(home);

        // /home/user
        FileSystemNode user = new FileSystemNode("user", NodeType.FOLDER);
        home.addChild(user);

        // /home/user/documents
        FileSystemNode documents = new FileSystemNode("documents", NodeType.FOLDER);
        user.addChild(documents);

        // /home/user/documents/school/physics
        FileSystemNode school = new FileSystemNode("school", NodeType.FOLDER);
        FileSystemNode physics = new FileSystemNode("physics", NodeType.FOLDER);
        physics.addChild(new FileSystemNode("notes.pdf", NodeType.FILE));
        school.addChild(physics);
        documents.addChild(school);

        // /home/user/documents/work
        FileSystemNode work = new FileSystemNode("work", NodeType.FOLDER);
        work.addChild(new FileSystemNode("report.docx", NodeType.FILE));
        work.addChild(new FileSystemNode("budget.xlsx", NodeType.FILE));
        documents.addChild(work);

        // /home/user/downloads/archive
        FileSystemNode downloads = new FileSystemNode("downloads", NodeType.FOLDER);
        FileSystemNode archive = new FileSystemNode("archive", NodeType.FOLDER);
        archive.addChild(new FileSystemNode("old.zip", NodeType.FILE));
        downloads.addChild(new FileSystemNode("installer.exe", NodeType.FILE));
        downloads.addChild(archive);
        user.addChild(downloads);

        // /home/admin/scripts
        FileSystemNode admin = new FileSystemNode("admin", NodeType.FOLDER);
        FileSystemNode scripts = new FileSystemNode("scripts", NodeType.FOLDER);
        scripts.addChild(new FileSystemNode("cleanup.sh", NodeType.FILE));
        admin.addChild(scripts);
        home.addChild(admin);

        // /etc/network
        FileSystemNode etc = new FileSystemNode("etc", NodeType.FOLDER);
        etc.addChild(new FileSystemNode("config.cfg", NodeType.FILE));
        FileSystemNode network = new FileSystemNode("network", NodeType.FOLDER);
        network.addChild(new FileSystemNode("interfaces.conf", NodeType.FILE));
        network.addChild(new FileSystemNode("wifi.conf", NodeType.FILE));
        etc.addChild(network);
        root.addChild(etc);

        // /var/log
        FileSystemNode var = new FileSystemNode("var", NodeType.FOLDER);
        FileSystemNode log = new FileSystemNode("log", NodeType.FOLDER);
        log.addChild(new FileSystemNode("system.log", NodeType.FILE));
        log.addChild(new FileSystemNode("error.log", NodeType.FILE));
        var.addChild(log);

        // /var/tmp
        var.addChild(new FileSystemNode("tmp", NodeType.FOLDER));
        root.addChild(var);

        // /tmp
        root.addChild(new FileSystemNode("tmp", NodeType.FOLDER));

        // /usr/local/bin/customTool
        FileSystemNode usr = new FileSystemNode("usr", NodeType.FOLDER);
        FileSystemNode local = new FileSystemNode("local", NodeType.FOLDER);
        FileSystemNode usrBin = new FileSystemNode("bin", NodeType.FOLDER);
        usrBin.addChild(new FileSystemNode("customTool.out", NodeType.FILE));
        local.addChild(usrBin);
        usr.addChild(local);

        // /usr/share/docs
        FileSystemNode share = new FileSystemNode("share", NodeType.FOLDER);
        FileSystemNode docs = new FileSystemNode("docs", NodeType.FOLDER);
        docs.addChild(new FileSystemNode("readme.md", NodeType.FILE));
        share.addChild(docs);
        usr.addChild(share);

        root.addChild(usr);

        return root;
    }
    
}