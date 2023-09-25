package edu.gatech.cs2340.team33.runecrawl.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import edu.gatech.cs2340.team33.runecrawl.Objects.General.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Objects.General.PlayerType;
import edu.gatech.cs2340.team33.runecrawl.Objects.Player;
import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * ConfigurationActivity is where the user is able to select customize their experience.
 * This activity allows for user input and options for difficulty and a character.
 */
public class ConfigurationActivity extends AppCompatActivity {
    private GameDifficulty difficulty;
    private PlayerType archetype;
    private static Player player;

    /**
     * Initializes the activity's user interface when it's created.
     * This method binds the XML layout to the activity and sets up click listeners
     * for the buttons.
     *
     * @param savedInstanceState Contains the activity's previously saved state if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration_screen);

        // Find necessary text and buttons from the XML layout
        EditText nameInput = findViewById(R.id.nameInput);
        TextView errorMessage = findViewById(R.id.errorMessage);
        RadioGroup difficultyGroup = findViewById(R.id.difficultyGroup);
        RadioGroup characterGroup = findViewById(R.id.characterGroup);
        Button playButton = findViewById(R.id.playButton);

        // Adjust the game difficulty based off user input
        difficultyGroup.setOnCheckedChangeListener((RadioGroup group, int id) -> {
            if (id == R.id.easyButton) {
                difficulty = GameDifficulty.EASY;
            } else if (id == R.id.mediumButton) {
                difficulty = GameDifficulty.MEDIUM;
            } else if (id == R.id.hardButton) {
                difficulty = GameDifficulty.HARD;
            }
        });

        // Adjust the character archetype based off user input
        characterGroup.setOnCheckedChangeListener((RadioGroup group, int id) -> {
            if (id == R.id.mageButton) {
                archetype = PlayerType.MAGE;
            } else if (id == R.id.warriorButton) {
                archetype = PlayerType.WARRIOR;
            } else if (id == R.id.archerButton) {
                archetype = PlayerType.ARCHER;
            }
        });

        // Set up a click listener for the Start Game button and creates a player object
        playButton.setOnClickListener((View view) -> {
            try {
                String playerName = nameInput.getText().toString();
                player = new Player(playerName, difficulty, archetype);
                Intent nextActivity = new Intent(ConfigurationActivity.this, GameActivity.class);
                startActivity(nextActivity);
            } catch (Exception exception) {
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(exception.getMessage());
            }
        });
    }

    /**
     * Exposes the configured player object to other screens.
     *
     * @return The player object.
     */
    public static Player getPlayer() {
        return player;
    }
}