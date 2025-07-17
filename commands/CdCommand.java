/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Executes the cd command with provided arguments on the FileManager.
 * Validates arguments and calls the corresponding FileManager method.
 */

package commands;
import filesystem.FileManager;

public class CdCommand implements Command{
    @Override
    public void execute(String[] args, FileManager fm){
        if(args.length < 2){
            fm.cd(null);
        } else {
            boolean taskCompleted = fm.cd(args[1]); 
            if (!taskCompleted){
                System.out.println(args[1] + " is not a valid folder name");
            }
        }
    }
}
