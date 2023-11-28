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

import edu.gatech.cs2340.team33.runecrawl.Model.Game.Attempt;
import edu.gatech.cs2340.team33.runecrawl.Model.Game.Leaderboard;
import edu.gatech.cs2340.team33.runecrawl.Model.Player.Player;

/**
 * EndViewModel is responsible for updating the UI at the end of a game attempt,
 * including updating the leaderboard and changing the end screen based on the player's status.
 */
public class EndViewModel extends ViewModel {
    private static final String GAME_OVER_TEXT = "GAME OVER";
    private static final int GAME_OVER_TEXT_COLOR = Color.RED;

    /**
     * Updates the leaderboard with the current game attempt.
     *
     * @param context            The application context.
     * @param currentAttemptView TextView to display the current attempt.
     * @param leaderboardTable   TableLayout for the leaderboard.
     */
    public void updateLeaderboard(Context context, TextView currentAttemptView,
                                  TableLayout leaderboardTable) {
        Player player = Player.getInstance();
        if (player != null) {
            Attempt currentAttempt = new Attempt(player);
            currentAttemptView.setText(currentAttempt.toString());
        } else {
            currentAttemptView.setVisibility(View.GONE);
        }

        List<Attempt> topAttempts = Leaderboard.getInstance().getTopAttempts();
        for (Attempt attempt : topAttempts) {
            TableRow row = new TableRow(context);
            addCellToRow(row, attempt.getUsername());
            addCellToRow(row, String.valueOf(attempt.getScore()));
            addCellToRow(row, attempt.getDateTime());
            leaderboardTable.addView(row);
        }
    }

    /**
     * Changes the end screen based on whether the player is alive.
     *
     * @param topMessage    TextView for the top message.
     * @param trophy        ImageView for the trophy graphic.
     * @param tombstone     ImageView for the tombstone graphic.
     * @param tombstoneName TextView for the tombstone text.
     */
    public void updateEndScreen(TextView topMessage, ImageView trophy,
                                ImageView tombstone, TextView tombstoneName) {
        Player player = Player.getInstance();
        if (player.isDead()) {
            topMessage.setText(GAME_OVER_TEXT);
            topMessage.setTextColor(GAME_OVER_TEXT_COLOR);
            trophy.setVisibility(View.INVISIBLE);
            tombstoneName.setText(player.getUsername());
        } else {
            tombstone.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Adds a cell with given content to a TableRow.
     *
     * @param row     The TableRow to which the cell is added.
     * @param content The content for the cell.
     */
    private void addCellToRow(TableRow row, String content) {
        TextView cell = new TextView(row.getContext());
        cell.setText(String.format("  %s", content));
        row.addView(cell);
    }
}