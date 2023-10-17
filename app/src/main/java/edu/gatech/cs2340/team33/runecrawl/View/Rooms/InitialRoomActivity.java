package edu.gatech.cs2340.team33.runecrawl.View.Rooms;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.R;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.Room;

/**
 * This is the Game Activity Class that has the main screen that the user will play on.
 * Currently the goal is to display username, HP, difficulty, and the sprite picked.
 */
public class InitialRoomActivity extends AppCompatActivity {
    private final Player player = Player.getInstance();
    private final Room room = new Room();

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
        setContentView(R.layout.room1_screen);

        // Obtain references to UI components
        TextView playerName = findViewById(R.id.playerName);
        TextView difficulty = findViewById(R.id.difficulty);
        TextView hp = findViewById(R.id.hitpoints);
        TextView score = findViewById(R.id.score);
        ImageView spriteImage = findViewById(R.id.playerSprite);
        Button endButton = findViewById(R.id.endGameButton);
        Button nextButton = findViewById(R.id.nextButton);

        // Populate UI components with player details
        room.populateUIComponents(playerName, difficulty, hp, spriteImage, player);

        // Start decrementing the timer
        room.startScoreDecrementTimer(InitialRoomActivity.this, score, player);

        // Set up a click listener for the end game button
        endButton.setOnClickListener((View view) -> room.moveToEndScreen(InitialRoomActivity.this));

        // Set up a click listener for the next button
        nextButton.setOnClickListener((View view) ->
                room.moveToNextScreen(InitialRoomActivity.this, SecondRoomActivity.class));
    }
}
