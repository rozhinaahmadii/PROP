package Persistence;

import Domain.KeyboardSaved;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * A class for handling the persistence of KeyboardSaved data to and from a file.
 * This class provides methods to check for the existence of the file, create the file,
 * write KeyboardSaved data to the file, and read KeyboardSaved data from the file.
 * @author Esther Lozano
 */
public class KeyboardsPersistence {
    /**
     * Constructs a new KeyboardsPersistence object.
     */
    public KeyboardsPersistence() {

    }

    /**
     * Checks if the KeyboardSaved file exists.
     *
     * @return true if the KeyboardSaved file exists, false otherwise.
     */
    public boolean existsFileKeyboard() {
        String directory = "./data";
        String nameFile = "keyboards.txt";

        // Create a Path object with the complete file path
        Path pathFile = Paths.get(directory, nameFile);

        // Return true if the file exists
        return Files.exists(pathFile);
    }

    /**
     * Creates the KeyboardSaved file.
     *
     * @return true if the file is created successfully, false otherwise.
     */
    public boolean createFileKeyboard() {
        String directory = "./data";
        String nameFile = "keyboards.txt";

        File f = new File(directory, nameFile);
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
     * Writes KeyboardSaved data to the file.
     *
     * @param keyboards A map of KeyboardSaved data to be written to the file.
     * @return true if the data is successfully written to the file, false otherwise.
     */
    public boolean writeFile(HashMap<String, HashMap<Integer, KeyboardSaved>> keyboards) {
        // Specify the folder path and file name
        String directory = "./data";
        String nameFile = "keyboards.txt";

        // Create a File object with the complete file path
        File f = new File(directory, nameFile);
        boolean written = false;

        try {
            // Create a FileWriter object that overwrites the content of the file
            FileWriter fileWriter = new FileWriter(f);

            // Create a BufferedWriter to write to the file
            BufferedWriter writer = new BufferedWriter(fileWriter);

            StringBuilder text = new StringBuilder();

            // Write to the file
            for (Map.Entry<String, HashMap<Integer, KeyboardSaved>> entry1 : keyboards.entrySet()) {
                for (Map.Entry<Integer, KeyboardSaved> entry2 : entry1.getValue().entrySet()) {
                    String id = entry2.getKey().toString();
                    String name = entry2.getValue().name;
                    String numRows = Integer.toString(entry2.getValue().numRows);
                    String numCols = Integer.toString(entry2.getValue().numCols);
                    String sizeKey = Float.toString(entry2.getValue().sizeKey);
                    StringBuilder layoutBuilder = new StringBuilder();
                    for (int i = 0; i < entry2.getValue().layout.size(); ++i) {
                        layoutBuilder.append(entry2.getValue().layout.get(i)).append(" ");
                    }
                    String layout = layoutBuilder.toString();
                    String user = entry2.getValue().getUsername();
                    String alphabet = entry2.getValue().getAlphabet();

                    text.append(id).append("\n")
                            .append(name).append("\n")
                            .append(numRows).append("\n")
                            .append(numCols).append("\n")
                            .append(sizeKey).append("\n")
                            .append(user).append("\n")
                            .append(alphabet).append("\n")
                            .append(layout).append("\n")
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
     * Reads KeyboardSaved data from the file.
     *
     * @return A map of KeyboardSaved data read from the file.
     */
    public HashMap<String, HashMap<Integer, KeyboardSaved>> readFileKeyboard() {
        HashMap<String, HashMap<Integer, KeyboardSaved>> keyboards = new HashMap<>();

        // Specify the folder path and file name
        String directory = "./data";
        String nameFile = "keyboards.txt";

        // Create a File object with the complete file path
        File f = new File(directory, nameFile);

        try {
            // Create a FileReader to read the file content
            FileReader fileReader = new FileReader(f);

            // Create a BufferedReader for easier reading
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            String id = null;
            String name = null;
            String numRows = null;
            String numCols = null;
            String sizeKey = null;
            String user = null;
            String alphabet = null;
            String layout = null;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                if (id == null) {
                    id = line;
                } else if (line.equals("---")) {
                    // Reached the end of a block, add the KeyboardSaved to the list
                    id = null;
                    name = null;
                    numRows = null;
                    numCols = null;
                    sizeKey = null;
                    user = null;
                    alphabet = null;
                    layout = null;
                } else {
                    name = line;
                    numRows = reader.readLine();
                    numCols = reader.readLine();
                    sizeKey = reader.readLine();
                    user = reader.readLine();
                    alphabet = reader.readLine();
                    layout = reader.readLine();
                }

                if (id != null && name != null) {
                    ArrayList<Integer> l = new ArrayList<>();
                    if (!layout.equals("")) {
                        String[] s = layout.split(" ");
                        for (String value : s) {
                            l.add(Integer.parseInt(value));
                        }
                    }
                    KeyboardSaved ks = new KeyboardSaved(Integer.parseInt(id), Integer.parseInt(numRows),
                            Integer.parseInt(numCols), Float.parseFloat(sizeKey), l, user, alphabet, name);

                    if (keyboards.containsKey(user)) {
                        keyboards.get(user).put(Integer.parseInt(id), ks);
                    } else {
                        HashMap<Integer, KeyboardSaved> map = new HashMap<>();
                        map.put(Integer.parseInt(id), ks);
                        keyboards.put(user, map);
                    }
                }
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyboards;
    }
}
