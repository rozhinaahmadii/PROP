package Persistence;

import Domain.FileF;

import java.io.*;
import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * A class for handling the persistence of FileF data to and from a file.
 * This class provides methods to check for the existence of the file, create the file,
 * write FileF data to the file, and read FileF data from the file.
 * @author Bruno Ruano
 */
public class FileFPersistence {
    /**
     * Constructs a new FileFPersistence object.
     */
    public FileFPersistence() {
    }

    /**
     * Checks if the FileF file exists.
     *
     * @return true if the FileF file exists, false otherwise.
     */
    public boolean existsFileFileF() {
        String directory = "./data";
        String nameFile = "files.txt";

        // Create a Path object with the complete file path
        Path pathFile = Paths.get(directory, nameFile);

        // Check if the file exists
        return Files.exists(pathFile);
    }

    /**
     * Creates the FileF file.
     *
     * @return true if the file is created successfully, false otherwise.
     */
    public boolean createFileFileF() {
        String directory = "./data";
        String nameFile = "files.txt";

        java.io.File f = new java.io.File(directory, nameFile);
        boolean created = false;

        try {
            // Create the file
            created = f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return created;
    }

    /**
     * Writes FileF data to the file.
     *
     * @param files A map of FileF data to be written to the file.
     * @return true if the data is successfully written to the file, false otherwise.
     */
    public boolean writeFile(HashMap<String, HashMap<String, FileF>> files) {
        // Specify the folder path and file name
        String directory = "./data";
        String nameFile = "files.txt";

        // Create a File object with the complete file path
        java.io.File f = new java.io.File(directory, nameFile);
        boolean written = false;

        try {
            // Create a FileWriter object that overwrites the content of the file
            FileWriter fileWriter = new FileWriter(f);

            // Create a BufferedWriter to write to the file
            BufferedWriter writer = new BufferedWriter(fileWriter);

            StringBuilder text = new StringBuilder();

            // Write to the file
            for (Map.Entry<String, HashMap<String, FileF>> entry1 : files.entrySet()) {
                for (Map.Entry<String, FileF> entry2 : entry1.getValue().entrySet()) {
                    String title = entry2.getKey();
                    String wordlist = entry2.getValue().getWordList().toString();
                    String user = entry2.getValue().getUser();
                    String alphabet = entry2.getValue().getNameAlphabet();

                    text.append(title).append("\n")
                            .append(wordlist).append("\n")
                            .append(user).append("\n")
                            .append(alphabet).append("\n")
                            .append("---").append("\n");
                }
            }
            writer.write(text.toString());

            // Close the writer
            writer.close();

            written = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return written;
    }

    /**
     * Reads FileF data from the file.
     *
     * @return A map of FileF data read from the file.
     */
    public HashMap<String, HashMap<String, FileF>> readFileFileF() {
        HashMap<String, HashMap<String, FileF>> files = new HashMap<>();

        // Specify the folder path and file name
        String directory = "./data";
        String nameFile = "files.txt";

        // Create a File object with the complete file path
        java.io.File f = new java.io.File(directory, nameFile);

        try {
            // Create a FileReader to read the file content
            FileReader fileReader = new FileReader(f);

            // Create a BufferedReader for easier reading
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            String title = null;
            String wordlist = null;
            String user = null;
            String alphabet = null;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                if (title == null) {
                    title = line;
                } else if (line.equals("---")) {
                    // Reached the end of a block, add the FileF to the list
                    title = null;
                    wordlist = null;
                    user = null;
                    alphabet = null;
                } else {
                    wordlist = line;
                    user = reader.readLine();
                    alphabet = reader.readLine();
                }

                if (title != null && wordlist != null) {
                    HashMap<String, Integer> wordMap = new HashMap<>();

                    if (!wordlist.equals("{}")) {
                        String[] s = wordlist.split(", ");

                        for (int i = 0; i < s.length; ++i) {
                            String word;
                            String[] aux;
                            if (i == 0) {
                                word = s[i].substring(1);
                                if (i == s.length - 1) {
                                    word = s[i].substring(0, s[i].length() - 1);
                                }
                            } else if (i == s.length - 1) {
                                word = s[i].substring(0, s[i].length() - 1);
                            } else {
                                word = s[i];
                            }
                            aux = word.split("=");
                            wordMap.put(aux[0], Integer.parseInt(aux[1]));
                        }
                    }
                    FileF file = new FileF(title, user, wordMap, alphabet);

                    if (files.containsKey(user)) {
                        files.get(user).put(title, file);
                    } else {
                        HashMap<String, FileF> map = new HashMap<>();
                        map.put(title, file);
                        files.put(user, map);
                    }
                }
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
