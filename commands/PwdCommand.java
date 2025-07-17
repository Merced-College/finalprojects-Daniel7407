/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Executes the pwd command on the FileManager.
 */

package commands;
import filesystem.FileManager;

public class PwdCommand implements Command{
    @Override
    public void execute(String[] args, FileManager fm){
        fm.pwd(); 
    }
}
