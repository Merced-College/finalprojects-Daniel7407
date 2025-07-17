/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Executes the delete command with provided arguments on the FileManager.
 * Validates arguments and calls the corresponding FileManager method.
 */

package commands;
import filesystem.FileManager;

public class DeleteCommand implements Command{
    @Override
    public void execute(String[] args, FileManager fm){
        if(args.length < 2){
            System.out.println("'delete' requires one argument");
        } else {
            fm.delete(args[1]);
        }

    }
}
