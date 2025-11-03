/**
 * Storage: deals with loading tasks from the file and saving tasks in the file
 * save / write to file
 * load file - read / parse file - returns customList
 */
import Exceptions.StorageErrors.StorageFileNotFoundException;
import Exceptions.StorageErrors.StorageFormatException;
import Exceptions.StorageException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    // this is a pointer to a file object that may or may not exist
    // final keyword used here to bind a storage instance to one filepath for its lifetime
    private final File file;

    public Storage(String filepath) throws StorageFileNotFoundException {
        if (filepath == null || filepath.trim().isEmpty()){
            throw new StorageFileNotFoundException("Empty path!");
        }
        File f = new File(filepath);
        validateFile(f);
        this.file = f;
    }

    private void validateFile(File file) throws StorageFileNotFoundException {
        // validate parent directory first
        File dir = file.getParentFile();
        if (dir !=  null) {
            if (!dir.exists()) {
                throw new StorageFileNotFoundException("Directory does not exist: " + dir.getPath());
            }
            if (!dir.isDirectory()) {
                throw new StorageFileNotFoundException("This is not a directory: " + dir.getPath());
            }
            if (!dir.canRead()) {
                throw new StorageFileNotFoundException("Directory is not readable: " + dir.getPath());
            }
        }
        // validate file existence, type, permissions
        if (!file.exists()){
            throw new StorageFileNotFoundException("File not found: " + file.getPath() + ".");
        }
        if (!file.isFile()){
            throw new StorageFileNotFoundException("This is not a regular file: " + file.getPath());
        }
        if (!file.canRead()) {
            throw new StorageFileNotFoundException("File is not readable: " + file.getPath());
        }
        if (!file.canWrite()) {
            throw new StorageFileNotFoundException("File is not writable: " + file.getPath());
        }
    }
    /**
     * Reads the Storage-object-bound file line-by-line and returns a list of parsed Task objects.
     * Wraps java.io.FileNotFoundException into StorageFileNotFoundException.
     */
    public ArrayList<Task> loadFile() throws StorageFileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        int lineNo = 0;
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                lineNo++;
                // skip blank or comment lines
                if (line.isBlank() || line.trim().startsWith("#")){
                    continue;
                }
                Task t = TaskParser.parseStorageLine(line, lineNo);
                if (t != null){
                    tasks.add(t);
                }
            }
        } catch (java.io.FileNotFoundException e) {
            // wrap JDK FileNotFoundException in custom Storage..Exception
            throw new StorageFileNotFoundException(
                    "Unable to open file -" + file.getPath()
            );
        } catch (StorageFormatException e){
            Helper.printFormattedReply(e.toString());
        }
        return tasks;
    }
    public File writeToFile(CustomList tasklist) throws StorageFormatException {

        try (FileWriter fw = new FileWriter(file, /* append */ false);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (Task t : tasklist) {
                if (t == null) continue;                 // defensive
                bw.write(t.serialise());                  // "T | 0 | desc", etc.
                bw.newLine();                            // platform newline
            }
            bw.flush();                                   // explicit (optional)
            return file;

        } catch (IOException e) {
            // If your project uses a checked StorageException, prefer rethrowing that.
            throw new StorageFormatException("Failed to write tasks to: " + file.getPath());
        }
    }
}


