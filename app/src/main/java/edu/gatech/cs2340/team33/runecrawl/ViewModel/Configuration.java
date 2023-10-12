package edu.gatech.cs2340.team33.runecrawl.ViewModel;

import androidx.lifecycle.ViewModel;

import edu.gatech.cs2340.team33.runecrawl.Model.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerType;
import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * Configuration is a ViewModel class responsible for handling the
 * logic related to configuring the game settings,
 * such as setting the game difficulty and player archetype.
 * It interacts with the UI controllers and updates
 * the underlying model based on user inputs.
 */
public class Configuration extends ViewModel {
    private GameDifficulty difficulty;
    private PlayerType archetype;

    /**
     * Sets the game difficulty based on the selected radio button ID.
     *
     * @param id The ID of the selected radio button.
     */
    public void setDifficulty(int id) {
        if (id == R.id.easyButton) {
            difficulty = GameDifficulty.EASY;
        } else if (id == R.id.mediumButton) {
            difficulty = GameDifficulty.MEDIUM;
        } else if (id == R.id.hardButton) {
            difficulty = GameDifficulty.HARD;
        }
    }

    /**
     * Sets the player archetype based on the selected radio button ID.
     *
     * @param id The ID of the selected radio button.
     */
    public void setArchetype(int id) {
        if (id == R.id.mageButton) {
            archetype = PlayerType.MAGE;
        } else if (id == R.id.warriorButton) {
            archetype = PlayerType.WARRIOR;
        } else if (id == R.id.archerButton) {
            archetype = PlayerType.ARCHER;
        }
    }

    /**
     * Constructs and initializes a player object with the specified name,
     * game difficulty, and player archetype.
     * This method should be called after the game difficulty and player
     * archetype have been set.
     *
     * @param playerName The name of the player.
     */
    public void constructPlayer(String playerName) {
        Player.initialize(playerName, difficulty, archetype);
    }
}
