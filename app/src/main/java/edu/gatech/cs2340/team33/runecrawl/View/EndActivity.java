package edu.gatech.cs2340.team33.runecrawl.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.R;
import edu.gatech.cs2340.team33.runecrawl.ViewModel.EndViewModel;

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
        if (!Player.getInstance().isAlive()) {
            TextView messageView = findViewById(R.id.wonMessage);
            messageView.setText("GAME OVER");
            messageView.setTextColor(Color.RED);
            ImageView trophy = findViewById(R.id.EndScreenGraphic);
            trophy.setVisibility(View.INVISIBLE);
            TextView nameView = findViewById(R.id.tombStoneName);
            nameView.setText(Player.getInstance().getUsername());
        } else {
            ImageView tombStone = findViewById(R.id.GameOverGraphic);
            tombStone.setVisibility(View.INVISIBLE);
        }
        EndViewModel viewModel = new ViewModelProvider(this).get(EndViewModel.class);

        TextView currentAttemptView = findViewById(R.id.currentAttempt);
        TableLayout leaderboardTable = findViewById(R.id.leaderboardTable);

        viewModel.addToLeaderboard(this, currentAttemptView, leaderboardTable);

        // Define a restart button to take the player back to the configuration screen
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setEnabled(true);

        restartButton.setOnClickListener((View view) -> {
            Intent nextActivity = new Intent(this, ConfigurationActivity.class);
            startActivity(nextActivity);
        });
    }
}