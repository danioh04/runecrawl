package edu.gatech.cs2340.team33.runecrawl.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import edu.gatech.cs2340.team33.runecrawl.Objects.General.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Objects.General.PlayerType;
import edu.gatech.cs2340.team33.runecrawl.Objects.Player;
import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * This is the Game Activity Class that has the main screen that we will play on.
 * Currently the goal is to display usernmae, hp, diffciulty and the sprite picked
 */
public class GameActivity extends AppCompatActivity {
    // creates an instance of player that we can use to get its attributes
    private Player player = ConfigurationActivity.getPlayer();

    /**
     * Initializes the game activity screen.
     * This method binds the XML layout to the game activity and sets up the base screen
     * with the few descriptors.
     *
     * @param savedInstanceState Contains the activity's previously saved state if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        // Make the three text views for name, difficulty level and HP that shows up in right
        TextView playerName = findViewById(R.id.playerName);
        TextView difficulty = findViewById(R.id.difficulty);
        TextView hp = findViewById(R.id.hitpoints);
        // gets the image for the sprite that will show up in top left
        ImageView spriteImage = findViewById(R.id.playerSprite);

        // create the end button game to leave the game and see the end screen
        Button endButton = findViewById(R.id.endGameButton);
        endButton.setOnClickListener((View view ) -> {
            Intent nextActivity = new Intent(GameActivity.this, EndActivity.class);
            startActivity(nextActivity);
        });
        // how the items are meant to be displayed for the user to see
        playerName.setText(player.getUsername());
        difficulty.setText((String)player.getDifficulty().toString());
        hp.setText(Integer.toString(player.getCurrentHp()));
        spriteImage.setImageResource(player.getType().getSpriteResId());




    }
}