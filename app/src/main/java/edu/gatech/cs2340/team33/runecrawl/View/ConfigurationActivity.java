package edu.gatech.cs2340.team33.runecrawl.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.gatech.cs2340.team33.runecrawl.R;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.Configuration;

/**
 * ConfigurationActivity is where the user is able to select customize their experience.
 * This activity allows for user input and options for difficulty and a character.
 */
public class ConfigurationActivity extends AppCompatActivity {

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

        Configuration viewModel = new ViewModelProvider(this).get(Configuration.class);

        // Find necessary text and buttons from the XML layout
        EditText nameInput = findViewById(R.id.nameInput);
        TextView errorMessage = findViewById(R.id.errorMessage);
        RadioGroup difficultyGroup = findViewById(R.id.difficultyGroup);
        RadioGroup characterGroup = findViewById(R.id.characterGroup);
        Button playButton = findViewById(R.id.playButton);

        // Adjust the game difficulty based off user input
        difficultyGroup.setOnCheckedChangeListener((group, id) -> viewModel.setDifficulty(id));

        // Adjust the character archetype based off user input
        characterGroup.setOnCheckedChangeListener((group, id) -> viewModel.setArchetype(id));

        // Set up a click listener for the Start Game button and creates a player object
        playButton.setOnClickListener((View view) -> {
            try {
                String playerName = nameInput.getText().toString();
                viewModel.constructPlayer(playerName);

                Intent nextActivity = new Intent(this, GameActivity.class);
                startActivity(nextActivity);
            } catch (IllegalArgumentException exception) {
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(exception.getMessage());
            }
        });
    }
}