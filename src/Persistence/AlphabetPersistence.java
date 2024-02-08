package Persistence;

import Domain.Alphabet;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * A class for handling the persistence of alphabet data to and from a file.
 * This class provides methods to check for the existence of the file, create the file,
 * write alphabet data to the file, and read alphabet data from the file.
 * @author Rozhina Ahmadi
 */
public class AlphabetPersistence {

    /**
     * Constructs a new AlphabetPersistence object.
     */
    public AlphabetPersistence() {
    }

    /**
     * Checks if the alphabet file exists.
     *
     * @return true if the alphabet file exists, false otherwise.
     */
    public boolean existsFileAlphabet() {
        String directory = "./data";
        String nameFile = "alphabets.txt";
        // Create a Path object with the complete file path
        Path pathFile = Paths.get(directory, nameFile);
        // Check if the file exists
        return Files.exists(pathFile);
    }

    /**
     * Creates the alphabet file.
     *
     * @return true if the file is created successfully, false otherwise.
     */
    public boolean createFileAlphabet() {
        String directory = "./data";
        String nameFile = "alphabets.txt";

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
     * Writes alphabet data to the file.
     *
     * @param alphabets A map of alphabet data to be written to the file.
     * @return true if the data is successfully written to the file, false otherwise.
     */
    public boolean writeFile(HashMap<String, HashMap<String, Alphabet>> alphabets) {
        // Specify the folder path and file name
        String directory = "./data";
        String nameFile = "alphabets.txt";

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
            for (Map.Entry<String, HashMap<String, Alphabet>> entry1 : alphabets.entrySet()) {
                for (Map.Entry<String, Alphabet> entry2 : entry1.getValue().entrySet()) {
                    String user = entry1.getKey();
                    String name = entry2.getKey();
                    String alphabet = entry2.getValue().alphabet.toString();

                    text.append(user).append("\n")
                            .append(name).append("\n")
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
     * Reads alphabet data from the file.
     *
     * @return A map of alphabet data read from the file.
     */
    public HashMap<String, HashMap<String, Alphabet>> readFileAlphabet() {

        HashMap<String, HashMap<String, Alphabet>> alphabets = new HashMap<>();

        // Specify the folder path and file name
        String directory = "./data";
        String nameFile = "alphabets.txt";

        // Create a File object with the complete file path
        File f = new File(directory, nameFile);

        try {
            // Create a FileReader to read the file content
            FileReader fileReader = new FileReader(f);

            // Create a BufferedReader for easier reading
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            String user = null;
            String name = null;
            String alphabet = null;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                if (user == null) {
                    user = line;
                } else if (line.equals("---")) {
                    // Reached the end of a block, add the alphabet to the list
                    user = null;
                    name = null;
                    alphabet = null;
                } else {
                    name = line;
                    alphabet = reader.readLine();
                }

                if (user != null && name != null) {
                    TreeSet<String> a = new TreeSet<>();
                    if (!alphabet.equals("{}")) {
                        String[] s = alphabet.split(", ");

                        for (int i = 0; i < s.length; ++i) {
                            String letter;
                            if (i == 0) {
                                letter = s[i].substring(1);
                            } else if (i == s.length - 1) {
                                letter = s[i].substring(0, s[i].length() - 1);
                            } else {
                                letter = s[i];
                            }

                            a.add(letter);
                        }
                        a.add(" ");
                    }

                    Alphabet alpha = new Alphabet(user, name, a);

                    if (alphabets.containsKey(user)) {
                        alphabets.get(user).put(name, alpha);
                    } else {
                        HashMap<String, Alphabet> map = new HashMap<>();
                        map.put(name, alpha);
                        alphabets.put(user, map);
                    }
                }

            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return alphabets;
    }
}
