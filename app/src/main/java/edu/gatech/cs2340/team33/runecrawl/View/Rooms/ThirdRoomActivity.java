package edu.gatech.cs2340.team33.runecrawl.View.Rooms;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import edu.gatech.cs2340.team33.runecrawl.Model.PlayerMovementStrategy;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerObserver;
import edu.gatech.cs2340.team33.runecrawl.Model.Strategies.ThirdRoomStrategy;
import edu.gatech.cs2340.team33.runecrawl.R;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.RoomViewModel;

/**
 * This is the third different room that the player will see.
 * The room has two doors, one leading to the previous room
 * and the other leading to the end.
 */
public class ThirdRoomActivity extends AppCompatActivity implements PlayerObserver {
    private final PlayerMovementStrategy movementStrategy = new ThirdRoomStrategy();
    private final RoomViewModel room = new RoomViewModel(24, 905, 16, 1855);

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
        setContentView(R.layout.room3_screen);

        // Obtain references to UI components
        TextView playerName = findViewById(R.id.playerName);
        TextView difficulty = findViewById(R.id.difficulty);
        TextView hp = findViewById(R.id.hitpoints);
        TextView score = findViewById(R.id.score);
        ConstraintLayout screenLayout = findViewById(R.id.room3);

        // Populate UI components with player details
        room.populateUIComponents(playerName, difficulty, hp);

        // Start decrementing the timer
        room.startScoreDecrementTimer(ThirdRoomActivity.this, score);

        // Start enemy movement
        room.startEnemyMovement();

        // Set up the screen's canvas
        room.addToCanvas(this, screenLayout);

        // Make the current class an observer to be notified when a collision occurs
        room.addObserver(this);
    }

    /**
     * Handles what happens when a key is pressed to move the character.
     *
     * @param keyCode The code of the key that was pressed.
     * @param event   The object associated with the pressed key.
     * @return If the action has been completed successfully.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        room.onKeyDown(movementStrategy, keyCode);
        room.isCollision(410, 75);
        room.isCollision(670, 75);
        return true;
    }

    /**
     * Handles what happens when a collision has occurred
     * between the character and a door.
     */
    @Override
    public void collisionOccurred() {
        room.removeObserver(this);
        if (room.isCollision(410, 75)) {
            room.moveToNextScreen(this, SecondRoomActivity.class);
        }
        if (room.isCollision(670, 75)) {
            room.moveToEndScreen(this);
        }
    }
}
