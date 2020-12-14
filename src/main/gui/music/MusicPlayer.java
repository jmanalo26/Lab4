package main.gui.music;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class MusicPlayer {

    private static final int PLAYERDAMAGECONTROL = 0;
    private static final int ENEMYDAMAGECONTROL = 1;

    private static Media media = new Media(MusicPlayer.class.getResource("/sound/mazeTheme.mp3").toExternalForm());
    private static MediaPlayer player = new MediaPlayer(media);
    private static MediaPlayer death = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/death_sound.mp3").toExternalForm()));
    private static MediaPlayer win = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/round_win.mp3").toExternalForm()));
    private static final ExecutorService ec = Executors.newFixedThreadPool(10);
    private static final AudioClip enemyHit = new AudioClip(MusicPlayer.class.getResource("/sound/enemyhit.mp3").toExternalForm());
    private static final AudioClip playerDamaged = new AudioClip(MusicPlayer.class.getResource("/sound/player_damaged.mp3").toExternalForm());

    public MusicPlayer() {
        if (media != null) {
            stopMusic();
        }
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


    public static void playMusic() {
        player.setAutoPlay(true);
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        player.play();
    }

    public static void playMusicDeath() {
        ec.shutdown();
        death = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/death_sound.mp3").toExternalForm()));
        death.play();
        death.setOnEndOfMedia(() -> {
            death.seek(Duration.ZERO);
            death.stop();
        });
    }

    public static void playMusicWinRound() {
        win = new MediaPlayer(new Media(MusicPlayer.class.getResource("/sound/round_win.mp3").toExternalForm()));
        win.play();
        win.setOnEndOfMedia(() -> {
            win.seek(Duration.ZERO);
            win.stop();
        });
    }

    public static void playMusicDamaged() {
        ec.execute(new Handler(PLAYERDAMAGECONTROL));
    }

    public static void playMusicEnemyHit() {
        ec.execute(new Handler(ENEMYDAMAGECONTROL));
    }

    public static void stopMusic() {
        player.stop();
    }


    static class Handler implements Runnable {
        private static int control;

        public Handler(int control) {
            Handler.control = control;
        }

        @Override
        public void run() {
            if (control == PLAYERDAMAGECONTROL) {
                playerDamaged.play();
            } else {
                enemyHit.play();
            }
        }
    }
}
