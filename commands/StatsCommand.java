/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Executes the stats command on the FileManager.
 */

package commands;
import filesystem.FileManager;

public class StatsCommand implements Command{
    @Override
    public void execute(String[] args, FileManager fm){
        fm.stats(); 
    }
}
