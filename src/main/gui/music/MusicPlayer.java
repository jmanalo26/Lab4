package main.gui.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {

    private static Media media = new Media(MusicPlayer.class.getResource("/sound/mazeTheme.mp3").toExternalForm());
    private static MediaPlayer player = new MediaPlayer(media);

    public MusicPlayer() {
        if (media != null) {
            stopMusic();
        }
    }

    public void playMusic() {
        player.setAutoPlay(true);
        player.setOnEndOfMedia(new Runnable() {
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
        player.play();
    }

    public void playMusicDeath() {
        MediaPlayer death = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/death_sound.mp3").toExternalForm()));
        death.setCycleCount(1);
        death.play();
    }

    public void playMusicWinRound() {
        MediaPlayer win = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/round_win.mp3").toExternalForm()));
        win.setVolume(1);
        win.setCycleCount(1);
        win.play();
    }
 
    public void playMusicDamaged() {
        MediaPlayer playerDamaged = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/player_damaged.mp3").toExternalForm()));
        playerDamaged.setCycleCount(1);
        playerDamaged.play();
    }

    public void playMusicEnemyHit() {
        MediaPlayer enemyHit = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/enemyhit.mp3").toExternalForm()));
        enemyHit.setVolume(1);
        enemyHit.setCycleCount(1);
        enemyHit.play();
    }

    public void stopMusic() {
        player.stop();
    }
//
//    public void setMusicDeath() {
//        media = new Media(getClass().getResource("/sound/death_sound.mp3").toExternalForm());
//        player = new MediaPlayer(media);
//    }

    public void setMusicMaze() {
        media = new Media(getClass().getResource("/sound/mazeTheme.mp3").toExternalForm());
        player = new MediaPlayer(media);
    }

    public void setMusicDeathScreen() {
        media = new Media(getClass().getResource("/sound/death_screen.mp3").toExternalForm());
        player = new MediaPlayer(media);
    }

    public void setMusicBoss() {
        media = new Media(getClass().getResource("/sound/boss.mp3").toExternalForm());
        player = new MediaPlayer(media);
    }

    public void setMusicShooter() {
        media = new Media(getClass().getResource("/sound/shooter.mp3").toExternalForm());
        player = new MediaPlayer(media);
    }
}
