/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Executes the help command with provided arguments on the FileManager.
 * Validates arguments and calls the corresponding FileManager method.
 */

package commands;
import filesystem.FileManager;

public class HelpCommand implements Command{
    @Override
    public void execute(String[] args, FileManager fm){
        if(args.length < 2){
            fm.help(null); 
        } else {
            fm.help(args[1]);
        }
    }
}
