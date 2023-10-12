package edu.gatech.cs2340.team33.runecrawl.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.gatech.cs2340.team33.runecrawl.Model.GameAttempt;
import edu.gatech.cs2340.team33.runecrawl.Model.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
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

        TextView currentAttemptView = findViewById(R.id.currentAttempt);
        TableLayout leaderboardTable = findViewById(R.id.leaderboardTable);

        // Show current attempt above leaderboard
        Player player = Player.getInstance();
        if (player != null) {
            GameAttempt currentAttempt = new GameAttempt(player);
            currentAttemptView.setText(currentAttempt.toString());
        } else {
            currentAttemptView.setVisibility(View.GONE); // Hide if no current player
        }

        // Populate leaderboard
        List<GameAttempt> topAttempts = Leaderboard.getInstance().getTopAttempts();

        for (GameAttempt attempt : topAttempts) {
            TableRow row = new TableRow(this);
            addCellToRow(row, attempt.getUsername());
            addCellToRow(row, String.valueOf(attempt.getScore()));
            addCellToRow(row, attempt.getDateTime());
            leaderboardTable.addView(row);
        }

        // Define a restart button to take the player back to the configuration screen
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener((View view) -> {
            Intent nextActivity = new Intent(this, ConfigurationActivity.class);
            startActivity(nextActivity);
        });
    }

    private void addCellToRow(TableRow row, String content) {
        TextView cell = new TextView(this);
        cell.setText(String.format("  %s", content));
        row.addView(cell);
    }
}