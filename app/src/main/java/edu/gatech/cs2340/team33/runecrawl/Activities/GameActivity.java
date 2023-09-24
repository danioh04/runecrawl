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
    private Player p = ConfigurationActivity.getPlayer();

    /**
     * Initializes the game activity screen
     * This method binds the XML layout to the game activity and sets up the base screen
     * with the few descroptors
     *
     * @param savedInstanceState Contains the activity's previously saved state if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        TextView playerName = findViewById(R.id.playerName);
        TextView difficulty = findViewById(R.id.difficulty);
        TextView hp = findViewById(R.id.hitpoints);
        ImageView spriteImage = findViewById(R.id.playerSprite);


        Button endButton = findViewById(R.id.endGameButton);
        endButton.setOnClickListener((View view ) -> {
            // TODO change the null to the end screen
            Intent nextActivity = new Intent(GameActivity.this, null);
            startActivity(nextActivity);
        });
        playerName.setText(p.getUsername());
        difficulty.setText((String) p.getDifficulty().toString());
        hp.setText(Integer.toString(p.getCurrentHp()));
        spriteImage.setImageResource(p.getType().getSpriteResId());




    }
}