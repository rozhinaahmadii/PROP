package DomainControllers;

import Domain.Statistics;
import Domain.DistanceMatrix;
import Domain.FlowMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Controller for statistics management.
 * This controller manages statistics data associated with users, keyboards, and texts.
 * The external key is the user's name, the intermediate key is the keyboard identifier,
 * and the internal key is the text name.
 * @author Esther Lozano
 */
public class StatisticsController {
    /** HashMap to store statistics. */
    private HashMap<String, HashMap<Integer, HashMap<String, Statistics>>> statistics;

    /**
     * Constructor for the StatisticsController class.
     * Initializes the data structure to store statistics.
     */
    public StatisticsController() {
        this.statistics = new HashMap<>();
    }

    /**
     * Checks if statistics exist for a specific user, keyboard, and text.
     *
     * @param user      The user's name.
     * @param idKeyboard The keyboard identifier.
     * @param nameText   The text name.
     * @return true if statistics exist, false otherwise.
     */
    public boolean existStatistics(String user, Integer idKeyboard, String nameText) {
        return (statistics.containsKey(user) && statistics.get(user).containsKey(idKeyboard) && statistics.get(user).get(idKeyboard).containsKey(nameText));
    }

    /**
     * Gets the velocity associated with a specific user, keyboard, and text.
     *
     * @param user      The user's name.
     * @param idKeyboard The keyboard identifier.
     * @param nameText   The text name.
     * @return The keyboard velocity associated with the specified user and text.
     */
    public double getVelocity(String user, Integer idKeyboard, String nameText) {
        return statistics.get(user).get(idKeyboard).get(nameText).velocityKeyboard;
    }

    /**
     * Calculates velocity based on flow and distance matrices and stores statistics.
     *
     * @param user      The user's name.
     * @param titleWL   The title of the word list.
     * @param mapWL     The map of words with their frequencies.
     * @param alphabet      The set of alphabet characters.
     * @param idKeyboard The keyboard identifier.
     * @param layout    The keyboard layout.
     * @param numRows   The number of rows on the keyboard.
     * @param numCols   The number of columns on the keyboard.
     * @param size      The size of a keyboard key.
     */
    public void calculateVelocityFile(String user, String titleWL, HashMap<String, Integer> mapWL,
                                      TreeSet<String> alphabet, int idKeyboard, ArrayList<Integer> layout,
                                      int numRows, int numCols, float size) {
        FlowMatrix fm = new FlowMatrix();
        DistanceMatrix dm = new DistanceMatrix();
        fm.initializeMatrix(alphabet.size());
        fm.calculateTransitions(mapWL, alphabet);
        dm.initializeMatrix(numRows, numCols);
        dm.calculateDistanceMatrix(numRows, numCols);
        Statistics s = new Statistics(titleWL, idKeyboard, user);
        s.calculateVelocity(fm, dm, layout, size);
        if (!statistics.containsKey(user)) {
            HashMap<String, Statistics> ts = new HashMap<>();
            ts.put(titleWL, s);
            HashMap<Integer, HashMap<String, Statistics>> kts = new HashMap<>();
            kts.put(idKeyboard, ts);
            statistics.put(user, kts);
        } else {
            if (!statistics.get(user).containsKey(idKeyboard)) {
                HashMap<String, Statistics> ts = new HashMap<>();
                ts.put(titleWL, s);
                statistics.get(user).put(idKeyboard, ts);
            } else {
                statistics.get(user).get(idKeyboard).put(titleWL, s);
            }
        }
    }
}
