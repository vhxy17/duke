package com.golden.storage;

/**
 * Storage: deals with loading tasks from the file and saving tasks in the file
 * save / write to file
 * load file - read / parse file - returns customList
 */


import com.golden.core.CustomList;
import com.golden.task.Task;
import com.golden.exceptions.storageErrors.*;
import com.golden.parser.TaskParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
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
        this.file = new File(filepath);
        ensureDirectoryExistsOrCreate(file);   // note: file may not exist yet
    }

    /** Ensure parent directory exists (or create it), and is a directory & writable. */
    private void ensureDirectoryExistsOrCreate(File targetFile) throws StorageFileNotFoundException {
        File dir = targetFile.getParentFile();
        if (dir == null) return;    // indicates filepath in current working directory

        if (!dir.exists()) {
            // try to create the directory tree for first run
            if (!dir.mkdirs()) {
                throw new StorageFileNotFoundException(
                        "Directory does not exist and could not be created: " + dir.getPath());
            }
            if (!dir.isDirectory()) {
                throw new StorageFileNotFoundException("This is not a directory: " + dir.getPath());
            }
            if (!dir.canWrite()) {
                throw new StorageFileNotFoundException("Directory is not writable: " + dir.getPath());
            }
        }
    }

    /** Ensure file exists (or create it), and is a file, is readable and writable. */
    private void ensureFileExistsOrCreate() throws StorageFileNotFoundException {
        try {
            // validate directory exists first
            ensureDirectoryExistsOrCreate(file);    //re-check in case directory got deleted
            if (!file.exists()) {
                // create empty file on first run
                if (!file.createNewFile()) {
                    throw new StorageFileNotFoundException(
                            "Failed to create file: " + file.getPath());
                }
            }
            if (!file.isFile()) {
                throw new StorageFileNotFoundException(
                        "Path is not a regular file: " + file.getPath());
            }
            if (!file.canRead()) {
                throw new StorageFileNotFoundException(
                        "File is not readable: " + file.getPath());
            }
            if (!file.canWrite()) {
                throw new StorageFileNotFoundException(
                        "File is not writable: " + file.getPath());
            }
        } catch (java.io.IOException e){
            throw new StorageFileNotFoundException(
                "I/O error ensuring file exists: " + file.getPath());
        }
    }

    /**
     * Reads the Storage-object-bound file line-by-line and returns a list of parsed Task objects.
     * Wraps java.io.FileNotFoundException into StorageFileNotFoundException.
     */
    public ArrayList<Task> loadFile() throws StorageFileNotFoundException,
            StorageFileParseException {
        // Ensure folder & file exis; create empty file on first run
        ensureFileExistsOrCreate();

        final ArrayList<Task> tasks = new ArrayList<Task>();
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
                    "Unable to open file: " + file.getPath()
            );
        }
        return tasks;
    }
    public File writeToFile(CustomList tasklist) throws StorageFileParseException {

        // This will create the file on first run (if missing)
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
            // Rethrow/wrap as a checked StorageException
            throw new StorageFileParseException("Failed to write tasks to: " + file.getPath());
        }
    }
}


