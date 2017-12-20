package com.omnipotence.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.async.AsyncExecutor;

/**
 * Manages the background music in the game.
 * @author Faizaan Datoo
 */
public class MusicManager {

	private Array<Music> musics;
	private Music currentMusic = null;
	private int currentId = -1;
	private float volume = 0f;

	public MusicManager() {
		musics = new Array<Music>();
		registerMusic();
		// Start the first track
		if (currentId == -1) {
			// Choose a random song and play it
			currentId = MathUtils.random(musics.size) - 1;
			getNextTrack();
			return;
		}
	}

	/**
	 * Register all the background music.
	 */
	private void registerMusic() {
		musics.add(AssetManager.getInstance().getMusic("music0"));
		musics.add(AssetManager.getInstance().getMusic("music1"));
		musics.add(AssetManager.getInstance().getMusic("music2"));
	}

	/**
	 * Play gameplay music while turning down the background music.
	 */
	public void playMusic(String name) {
        AssetManager.getInstance().getSound(name).play(1f);
	}

    /**
     * Play gameplay music while turning down the background music.
     */
    public void playMusic(final String[] list) {
        //currentMusic.stop();
        final Music[] musicList = new Music[list.length];
        for(int i = 0; i < list.length; i++) {
            musicList[i] = Gdx.audio.newMusic(AssetManager.getInstance().getAssetFile(list[i]));
        }
        for (int i = 0; i < musicList.length; i++) {
            musicList[i].setVolume(1f);
            musicList[i].setLooping(false);
            final int finalI = i;
            musicList[i].setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    if (finalI == list.length - 1) {
                        musicList[finalI].dispose();
                       // currentMusic.play();
                    } else {
                        musicList[finalI + 1].play();
                        musicList[finalI].dispose();
                    }
                }
            });
        }
        musicList[0].play();
    }

	/**
	 * Stop all playing music.
	 */
	public void dispose() {
		currentMusic.stop();
		musics.clear();
	}

	/**
	 * Get the next track from the music list.
	 */
	private void getNextTrack() {
		currentId++;
		if (currentId > musics.size - 1) currentId = 0;

		// Get the next music and set it to not loop and set the volume to the
		// set volume above.
		currentMusic = musics.get(currentId);
		currentMusic.setLooping(false);
		currentMusic.setVolume(volume);

		// Add the completion listener, so the music changes to the next when it
		// finishes.
		currentMusic.setOnCompletionListener(completionListener);

		currentMusic.play();
	}

	/** Play the next track when the song completes. */
	OnCompletionListener completionListener = new OnCompletionListener() {
		@Override
		public void onCompletion(Music music) {
			getNextTrack();
		}
	};

}