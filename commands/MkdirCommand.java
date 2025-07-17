/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Executes the mkdir command with provided arguments on the FileManager.
 * Validates arguments and calls the corresponding FileManager method.
 */

package commands;
import filesystem.FileManager;

public class MkdirCommand implements Command{
    @Override
    public void execute(String[] args, FileManager fm){
        if(args.length < 2){
            System.out.println("'mkdir' requires one argument");
        } else {
            fm.mkdir(args[1]);
        }
    }
}
