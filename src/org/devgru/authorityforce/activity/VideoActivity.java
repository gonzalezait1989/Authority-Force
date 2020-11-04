/*
 * Aitor Gonzalez Martinez <aitor.gonzalez.martinez@gmail.com>
 * Xavier Martinez de Francisco <martinez.x0@gmail.com> 
 * Toni Trigo Diaz <solidsnake17@hotmail.com>
 * 
 * Copyleft (c) 2013 
 *
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 * 
 */

package org.devgru.authorityforce.activity;

import java.io.IOException;

import org.devgru.authorityforce.R;
import org.devgru.authorityforce.audio.AudioPlayer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.VideoView;

/**
 * VideoActivity.java
 * 
 * VideoActivity plays the intro video of the game. On it's completion or screen
 * touched, launches the main game activity.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class VideoActivity extends Activity implements OnPreparedListener,
	OnCompletionListener, OnTouchListener {

    // ===========================================================
    // Constants
    // ===========================================================

    /* The ID which this view has to play */
    private static final String VIDEO_PATH = "mfx/video_presentation.mp4";

    // ===========================================================
    // Fields
    // ===========================================================

    /* The player used to play the video. */
    private MediaPlayer mMediaPlayer;
    /* The holder associated with the VideoView used to play the video. */
    private SurfaceHolder mHolder;
    /* The view used to play the video. */
    private VideoView mVideoView;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.layout_video_activity);

	// Stop music of menu
	AudioPlayer.getInstance().stopMusic();

	// Gets the video view from the xml file.
	this.mVideoView = (VideoView) findViewById(R.id.video);

	// Register the on touch listener to the video view.
	this.mVideoView.setOnTouchListener(this);

	AssetFileDescriptor descriptor;

	try {
	    descriptor = this.getAssets().openFd(VIDEO_PATH);
	    long start = descriptor.getStartOffset();
	    long end = descriptor.getLength();

	    this.mMediaPlayer = new MediaPlayer();
	    this.mMediaPlayer.setScreenOnWhilePlaying(true);
	    this.mMediaPlayer.setOnCompletionListener(this);

	    this.mMediaPlayer.setDataSource(descriptor.getFileDescriptor(),
		    start, end);

	    this.mMediaPlayer.setOnPreparedListener(this);
	    this.mHolder = mVideoView.getHolder();
	    this.mHolder.addCallback(new SurfaceHolder.Callback() {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
		    mHolder = holder;
		    mMediaPlayer.setDisplay(mHolder);
		    try {
			mMediaPlayer.prepare();
		    } catch (IllegalStateException e) {
			e.printStackTrace();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format,
			int width, int height) {
		}
	    });

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /**
     * When the screen is touched, the video player stops and the main game
     * activity is launched.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
	if (event.getAction() == MotionEvent.ACTION_UP) {
	    this.launchMainMenu();
	}
	return true;
    }

    /**
     * Makes the player start once all resources are ready.
     */
    @Override
    public void onPrepared(MediaPlayer pMediaPlayer) {
	pMediaPlayer.start();
    }

    /**
     * When the video ends launches the main game activity.
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
	this.launchMainMenu();
    }

    /**
     * When the Activity is paused stop and releases the player and destroy the
     * activity.
     */
    @Override
    protected void onPause() {
	super.onPause();
	this.stopPlayingVideo();
	this.finish();
    }

    @Override
    public boolean onKeyDown(int pKeyCode, KeyEvent pKevent) {
	this.launchMainMenu();
	return false;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Stops the video reproduction and release the used resources.
     */
    private void stopPlayingVideo() {
	if (this.mMediaPlayer != null && this.mMediaPlayer.isPlaying()) {
	    this.mMediaPlayer.stop();
	    this.mMediaPlayer.release();
	}
    }

    /**
     * Starts the main game activity.
     */
    private void launchMainMenu() {
	if (!AuthorityForceActivity.isActive()) {
	    startActivity(new Intent(this, AuthorityForceActivity.class));
	} else {
	    this.finish();
	    AudioPlayer.getInstance().setVolumeMaster();
	}
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
