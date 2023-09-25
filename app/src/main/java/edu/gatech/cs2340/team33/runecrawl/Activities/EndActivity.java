package edu.gatech.cs2340.team33.runecrawl.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import edu.gatech.cs2340.team33.runecrawl.R;

/**
 * The ending screen. Displays the leaderboard and whether the players won the game or not.
 * This will be implemented in the later sprints, right now the screen is empty.
 */
public class EndActivity extends AppCompatActivity {
    /**
     * Initializes the ending screen of the game.
     * Uses a XML layout to show whether the user won or not, and the leaderboard.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_screen);
    }
}