package Persistence;

import Domain.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for handling the persistence of text data to and from a file.
 * This class provides methods to check for the existence of the file, create the file,
 * write text data to the file, and read alphabet data from the file.
 * @author Bruno Ruano
 */
public class TextPersistence {
    /**
     * Constructs a new TextPersistence object.
     */
    public TextPersistence(){
    }

    /**
     * Checks if the text data file exists.
     *
     * @return true if the text data file exists, false otherwise.
     */
    public boolean existsText() {
        String directory = "./data";
        String nameFile = "texts.txt";

        // Create a Path object with the complete file path
        Path pathFile = Paths.get(directory, nameFile);

        // Check if the file exists
        return Files.exists(pathFile);
    }

    /**
     * Creates the text data file.
     *
     * @return true if the file is successfully created, false otherwise.
     */
    public boolean createText() {
        String directory = "./data";
        String nameFile = "texts.txt";

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
     * Writes text data to the file.
     *
     * @param texts A map of text data to be saved.
     * @return true if the data is successfully written, false otherwise.
     */
    public boolean writeText(HashMap<String, HashMap<String, Text>> texts) {
        String directory = "./data";
        String nameFile = "texts.txt";

        File f = new File(directory, nameFile);
        boolean written = false;

        try {
            FileWriter fileWriter = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            StringBuilder text = new StringBuilder();

            // Write data to the file
            for(Map.Entry<String, HashMap<String,Text>> entry1 : texts.entrySet()) {
                for (Map.Entry<String, Text> entry2 : entry1.getValue().entrySet()) {
                    String title = entry2.getKey();
                    String textUser = entry2.getValue().getText();
                    String wordlist = entry2.getValue().getWordList().toString();
                    String user = entry2.getValue().getUser();
                    String alphabet = entry2.getValue().getNameAlphabet();

                    text.append(title).append("\n").append(textUser).append("\n").append(wordlist).append("\n").append(user).append("\n").append(alphabet).append("\n").append("---").append("\n");
                }
            }
            writer.write(text.toString());
            writer.close();

            written = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return written;
    }

    /**
     * Reads text data from the file.
     *
     * @return A map of text data read from the file.
     */
    public HashMap<String, HashMap<String, Text>> readText() {
        HashMap<String, HashMap<String, Text>> texts = new HashMap<>();
        String directory = "./data";
        String nameFile = "texts.txt";

        File f = new File(directory, nameFile);

        try {
            FileReader fileReader = new FileReader(f);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            String title = null;
            String text = null;
            String wordlist = null;
            String user = null;
            String alphabet = null;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                if (title == null) {
                    title = line;
                } else if (line.equals("---")) {
                    // Reached the end of a block, add the text to the map
                    title = null;
                    text = null;
                    wordlist = null;
                    user = null;
                    alphabet = null;
                } else {
                    text = line;
                    wordlist = reader.readLine();
                    user = reader.readLine();
                    alphabet = reader.readLine();
                }

                if(title != null && text != null) {
                    HashMap<String, Integer> wordMap = new HashMap<>();
                    if(!wordlist.equals("{}") ) {
                        String[] s = wordlist.split(", ");

                        for (int i = 0; i < s.length; ++i) {
                            String word;
                            String[] aux;
                            if(i == 0) {
                                word = s[i].substring(1);
                                if(i == s.length-1) {
                                    word = s[i].substring(0,s[i].length()-1);
                                }
                            }else if(i == s.length-1) {
                                word = s[i].substring(0,s[i].length()-1);
                            }else {
                                word = s[i];
                            }
                            aux = word.split("=");
                            wordMap.put(aux[0],Integer.parseInt(aux[1]));

                        }
                    }

                    Text t = new Text(title, text, user, alphabet);

                    if (texts.containsKey(user)) {
                        texts.get(user).put(title, t);
                    } else {
                        HashMap<String, Text> map = new HashMap<>();
                        map.put(title, t);
                        texts.put(user, map);
                    }
                }
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return texts;
    }
}
