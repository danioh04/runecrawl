package edu.gatech.cs2340.team33.runecrawl.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.gatech.cs2340.team33.runecrawl.R;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.EndViewModel;

/**
 * EndActivity is responsible for displaying the game's ending screen.
 * It shows the result of the game (win/loss) and the leaderboard.
 * This activity utilizes the EndViewModel for managing UI logic.
 */
public class EndActivity extends AppCompatActivity {
    /**
     * Sets up the ending screen UI.
     * This includes displaying the game result, the leaderboard, and a restart button.
     *
     * @param savedInstanceState Contains the activity's previously saved state if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_screen);
        EndViewModel viewModel = new ViewModelProvider(this).get(EndViewModel.class);

        // Initialize UI components
        TextView messageView = findViewById(R.id.wonMessage);
        ImageView trophy = findViewById(R.id.EndScreenGraphic);
        TextView tombstoneName = findViewById(R.id.tombStoneName);
        ImageView tombstone = findViewById(R.id.GameOverGraphic);

        // Update end screen based on game results
        viewModel.updateEndScreen(messageView, trophy, tombstone, tombstoneName);

        // Setup and display the leaderboard
        TextView currentAttemptView = findViewById(R.id.currentAttempt);
        TableLayout leaderboardTable = findViewById(R.id.leaderboardTable);
        viewModel.updateLeaderboard(this, currentAttemptView, leaderboardTable);

        // Setup the restart button to return to the game configuration screen
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(view -> {
            Intent nextActivity = new Intent(this, ConfigurationActivity.class);
            startActivity(nextActivity);
        });
    }
}
