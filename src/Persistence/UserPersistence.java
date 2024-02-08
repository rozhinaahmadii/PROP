package Persistence;

import Domain.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for handling the persistence of user data to and from a file.
 * This class provides methods to check for the existence of the file, create the file,
 * write user data to the file, and read alphabet data from the file.
 * @author Jiahao Liu
 */
public class UserPersistence {
    public UserPersistence() {
    }

    /**
     * Checks if the user data file exists.
     *
     * @return true if the user data file exists, false otherwise.
     */
    public boolean existsFileUser() {
        String directory = "./data";
        String nameFile = "users.txt";

        // Create a Path object with the complete file path
        Path pathFile = Paths.get(directory, nameFile);

        // Check if the file exists
        return Files.exists(pathFile);
    }

    /**
     * Creates the user data file.
     *
     * @return true if the file is successfully created, false otherwise.
     */
    public boolean createFileUser() {
        String directory = "./data";
        String nameFile = "users.txt";

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
     * Writes user data to the file.
     *
     * @param users A map of user data to be saved.
     * @return true if the data is successfully written, false otherwise.
     */
    public boolean writeFile(HashMap<String, User> users) {
        String directory = "./data";
        String nameFile = "users.txt";

        File f = new File(directory, nameFile);
        boolean written = false;

        try {
            // Create a FileWriter to overwrite the file content
            FileWriter fileWriter = new FileWriter(f);

            // Create a BufferedWriter to write to the file
            BufferedWriter writer = new BufferedWriter(fileWriter);

            StringBuilder text = new StringBuilder();

            // Write data to the file
            for (Map.Entry<String, User> entry : users.entrySet()) {
                String username = entry.getKey();
                String password = entry.getValue().getPassword();

                text.append(username).append("\n").append(password).append("\n").append("---").append("\n");
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
     * Reads user data from the file.
     *
     * @return A map of user data read from the file.
     */
    public HashMap<String, User> readFileUser() {
        HashMap<String, User> users = new HashMap<>();

        String directory = "./data";
        String nameFile = "users.txt";

        File f = new File(directory, nameFile);

        try {
            // Create a FileReader to read the file content
            FileReader fileReader = new FileReader(f);

            // Create a BufferedReader for reading
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            String username = null;
            String password = null;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                if (username == null) {
                    username = line;
                } else if (line.equals("---")) {
                    // Reached the end of a block, add the user to the map
                    username = null;
                    password = null;
                } else {
                    password = line;
                }

                if(username != null && password != null) {
                    User u = new User(username, password);
                    users.put(username, u);
                }
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}
