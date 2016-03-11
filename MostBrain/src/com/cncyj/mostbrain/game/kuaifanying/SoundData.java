package com.cncyj.mostbrain.game.kuaifanying;
 
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

class SoundData {
	private MediaPlayer player;
	private int hashcode;
	private boolean bPause;
	public boolean isPause() {
		return bPause;
	}
	public void setPause(boolean bPause) {
		this.bPause = bPause;
	}

	public SoundData(String path) {
		hashcode = path.hashCode();
		try {

			AssetFileDescriptor assetFileDescritor = LiveActivity.app
					.getApplicationContext().getAssets().openFd(path);
			player = new MediaPlayer();
			player.setDataSource(assetFileDescritor.getFileDescriptor(),
					assetFileDescritor.getStartOffset(),
					assetFileDescritor.getLength());
			player.prepare();
			 
		} catch (Exception e) {
		 	e.printStackTrace();
			player = null;
		}
      
	}
	public boolean IsValid()
	{
		return player!=null;
	}
	public int getHashcode() {
		return hashcode;
	}

	public void play(boolean bLoop) {
		
		if (player != null) {
			
//			player.stop();
			player.setLooping(bLoop);
			try {
				 
				player.seekTo(0);
				player.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public void pause() {
		if (player != null) {
			player.pause();
		}
	}

	public void resume() {
		if (player != null) {
			player.start();
		}
	}

	public void stop() {
		if (player != null) {
			player.stop();
		}
	}

	public void unload() {
		if (player != null) {
			player.release();
		}
	}
	public boolean isPlaying()
	{
		if (player != null) {
			return player.isPlaying();
		}
		return false;
	}
}