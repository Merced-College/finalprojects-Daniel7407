/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Executes the ls command with provided arguments on the FileManager.
 * Validates arguments and calls the corresponding FileManager method.
 */

package commands;
import filesystem.FileManager;

public class LsCommand implements Command{
    @Override
    public void execute(String[] args, FileManager fm){
        if(args.length < 2){
            fm.ls(null); 
        } else {
            fm.ls(args[1]); 
        }
    }
}
