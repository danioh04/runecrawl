package edu.gatech.cs2340.team33.runecrawl.ViewModel;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.gatech.cs2340.team33.runecrawl.Model.GameAttempt;
import edu.gatech.cs2340.team33.runecrawl.Model.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;


/**
 * End is a ViewModel class responsible for handling the logic
 * related to adding to the leaderboard after a game attempt is
 * completed.
 */
public class EndViewModel extends ViewModel {
    /**
     * Creates a new game attempt and adds it to the leaderboard.
     *
     * @param context            The current state of the application.
     * @param currentAttemptView The statistics of the most recent attempt.
     * @param leaderboardTable   The leaderboard table.
     */
    public void addToLeaderboard(Context context,
                                 TextView currentAttemptView, TableLayout leaderboardTable) {
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
            TableRow row = new TableRow(context);
            addCellToRow(context, row, attempt.getUsername());
            addCellToRow(context, row, String.valueOf(attempt.getScore()));
            addCellToRow(context, row, attempt.getDateTime());
            leaderboardTable.addView(row);
        }
    }

    /**
     * helper method to change end screen based on if the player is alive.
     * @param context The end activity.
     * @param topMessage the message at the top of the screen.
     * @param trophy the trophy graphic.
     * @param tombstone the tombstone graphic.
     * @param tombstoneName the text on the tombstone.
     */
    public void changeEndScreen(Context context, TextView topMessage, ImageView trophy,
                                ImageView tombstone, TextView tombstoneName) {
        //Checks if player is alive
        if (!Player.getInstance().isAlive()) {
            // if player is dead

            // sets text at top of the screen to GAME OVER and makes color red.
            topMessage.setText("GAME OVER");
            topMessage.setTextColor(Color.RED);
            // Makes trophy invisible so only tombstone is visible.
            trophy.setVisibility(View.INVISIBLE);
            // sets the text on top of tombstone to player name.
            tombstoneName.setText(Player.getInstance().getUsername());
        } else {
            // if player is alive

            // makes tombstone invisible so only trophy is visible.
            tombstone.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Helper method to add a new row to the leaderboard
     *
     * @param context The current state of the application.
     * @param row     The row of the latest game attempt
     * @param content The statistics of the most recent attempt.
     */
    private void addCellToRow(Context context, TableRow row, String content) {
        TextView cell = new TextView(context);
        cell.setText(String.format("  %s", content));
        row.addView(cell);
    }
}
