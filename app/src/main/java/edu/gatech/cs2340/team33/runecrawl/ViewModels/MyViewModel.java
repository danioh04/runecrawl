package edu.gatech.cs2340.team33.runecrawl.ViewModels;

import androidx.lifecycle.ViewModel;

import edu.gatech.cs2340.team33.runecrawl.Model.GameDifficulty;
import edu.gatech.cs2340.team33.runecrawl.Model.Player;
import edu.gatech.cs2340.team33.runecrawl.Model.PlayerType;

public class MyViewModel extends ViewModel {
    public MyViewModel() {
        super();
    }
    public void constuctPlayer(String username, GameDifficulty difficulty, PlayerType type) {
        Player.initialize(username, difficulty, type);
    }

    public int getPlayerScore() {
        return Player.getInstance().getScore();
    }

    public GameDifficulty getDifficulty() {
        return Player.getInstance().getDifficulty();
    }

    public String getPlayerName() {
        return Player.getInstance().getUsername();
    }

    public int getPlayerHP() {
        return Player.getInstance().getCurrentHp();
    }

    public void decreasePlayerScore() {
        Player.getInstance().decreaseScore();
    }

    public int getPlayerSprite() {
        return Player.getInstance().getType().getSpriteResId();
    }

    public Player getPlayerInstance() {
        return Player.getInstance();
    }

}
