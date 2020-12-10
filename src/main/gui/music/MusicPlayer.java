package main.gui.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {

    private static Media media = new Media(MusicPlayer.class.getResource("/sound/mazeTheme.mp3").toExternalForm());
    private static MediaPlayer player = new MediaPlayer(media);
    private static MediaPlayer death = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/death_sound.mp3").toExternalForm()));
    private static MediaPlayer playerDamaged = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/player_damaged.mp3").toExternalForm()));
    private static MediaPlayer win = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/round_win.mp3").toExternalForm()));
    private static MediaPlayer enemyHit = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/enemyhit.mp3").toExternalForm()));


    public MusicPlayer() {
        if (media != null) {
            stopMusic();
        }
    }

    public static void playMusic() {
        player.setAutoPlay(true);
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        player.play();
    }

    public static void playMusicDeath() {
        death = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/death_sound.mp3").toExternalForm()));
        death.play();
        death.setOnEndOfMedia(() -> {
            death.seek(Duration.ZERO);
            death.stop();
        });
    }

    public static void playMusicWinRound() {
        win = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/round_win.mp3").toExternalForm()));
        win.setVolume(1);
        win.play();
        win.setOnEndOfMedia(() -> {
            win.seek(Duration.ZERO);
            win.stop();
        });
    }

    public static void playMusicDamaged() {
        playerDamaged = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/player_damaged.mp3").toExternalForm()));
        playerDamaged.play();
        playerDamaged.setOnEndOfMedia(() -> {
            playerDamaged.seek(Duration.ZERO);
            playerDamaged.stop();
        });
    }

    public static void playMusicEnemyHit() {
        enemyHit.setVolume(1);
        enemyHit.play();
        enemyHit.setOnEndOfMedia(() -> {
            enemyHit.seek(Duration.ZERO);
            enemyHit.stop();
        });
    }

    public static void stopMusic() {
        player.stop();
    }

    public static void setMusicMaze() {
        media = new Media(MusicPlayer.class.getResource("/sound/mazeTheme.mp3").toExternalForm());
        player = new MediaPlayer(media);
    }

    public static void setMusicDeathScreen() {
        media = new Media(MusicPlayer.class.getResource("/sound/death_screen.mp3").toExternalForm());
        player = new MediaPlayer(media);
    }

    public static void setMusicBoss() {
        media = new Media(MusicPlayer.class.getResource("/sound/boss.mp3").toExternalForm());
        player = new MediaPlayer(media);
    }

    public static void setMusicShooter() {
        media = new Media(MusicPlayer.class.getResource("/sound/shooter.mp3").toExternalForm());
        player = new MediaPlayer(media);
    }
}
