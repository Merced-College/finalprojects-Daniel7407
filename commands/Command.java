/*
 * Daniel Pulido-Alaniz
 * 7/17/2025
 * 
 * Sets up the Command interface
 */

package commands;
import filesystem.FileManager; 

public interface Command {
    void execute(String[] args, FileManager fileManager); 
}
