package com.cncyj.mostbrain.game.kuaifanying;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.cncyj.mostbrain.R;

public class Gamemanager {
	Random rnd = new Random();
	public static int currentGamestate = 2;

	public final static int STATE_GAME_RUN = 2;
	public final static int STATE_GAME_FAIL = 4;
	public final static int STATE_GAME_WIN = 5;
	public final static int STATE_GAME_SETTING = 6;
	public final static int STATE_GAME_HELP = 7;
	public final static int STATE_GAME_MAIN = 8;
	public final static int STATE_GAME_READY = 9;
	public final static int STATE_GAME_LOADING = 10;
	public final static int STATE_GAME_TUIJIAN = 11;

	private int playerState = 0;
	public static int score = 0;
	public static int bastScore = 0;
	public static boolean isSoundopen = true;
	private static Gamemanager gameManager = null;

	static boolean isRun = true;

	public static Gamemanager getGamemanager() {
		if (gameManager == null) {
			gameManager = new Gamemanager();
		}
		return gameManager;
	}

	public int firstColorIndex = 0;

	public void resetGame() {
		firstColorIndex = 0;
	}

	private int blockMoveV = 5;
	private int starty = 150;
	private int playerx = 140;
	private int lastBlockY = 0;
	private int blockw = 180;
	private int blockoffx = 160;
	private int blockoffy = 150;
	private int blockfy = 0;// 200 400之间
	private int playerMoveA = 5;
	private int playerMaxV = 40;
	private int playerMoveV = 0;
	private int playerY = Tool.screenHeightC - 200;
	private int playerW = 30;
	private int playerH = 50;
	private int playerIndex = 1;

	private int boyAndGirlW = 90;

	private int playerAnimationIndex = 0;
	private int playerRunAnimationMaxIndex = 5;// 0-5
	public int[] color = { 0xffFFFF00, 0xffEE82EE, 0xffB3EE3A, 0xff63B8FF,
			0xffEE4000 };
	public Bitmap playerGirl = null;
	public Bitmap playerBoy = null;
	private Bitmap loadingImg = null;
	Vector<Block> vblock = new Vector<Gamemanager.Block>();

	private Button mainStart = null;
	private int buttonw = 220;
	private int buttonh = 70;
	private Button fillRestart = null;
	private Button backToMain = null;
	private Button mainSetting = null;
	private Button settingBack = null;
	private Bitmap onimg = null;
	private Bitmap offimg = null;
	private Button musicBackBtn = null;
	private Button musicEffectBtn = null;
	private Button shakeBtn = null;
	private Bitmap jideImg = null;
	boolean isNewScore = false;
	private Bitmap newimg = null;
	private int k = 6;
	
	private Bitmap tipleft=null;
	private Bitmap tipright=null;

	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xffFFD700);
		paint.setTextAlign(Align.CENTER);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(2);
		paint.setTextSize(25);
		paint.setAntiAlias(true);
		int sw = Tool.screenWidth;
		int sh = Tool.screenHeight;
		canvas.save();
		float scalex = sw * 1.0f / 480;
		canvas.scale(scalex, scalex);

		switch (currentGamestate) {
		case STATE_GAME_SETTING: {

			if (settingBack == null) {
				Bitmap img=Tool.getImageFromDrawable(R.drawable.back_press);
				settingBack = new Button(10, 10, 100, 120, 0, 5, img);
			}
			settingBack.draw(canvas);
			paint.setStrokeWidth(1);
			paint.setTextSize(22);
			paint.setColor(0xff2B2B2B);
			paint.setTextAlign(Align.CENTER);
			canvas.drawText("音 乐", 150, 280, paint);
			canvas.drawText("音 效", 150, 380, paint);
			canvas.drawText("震 动", 150, 480, paint);
			if (onimg == null) {
				onimg = Tool.getImageFromDrawable(R.drawable.on);
			}
			if (offimg == null) {
				offimg = Tool.getImageFromDrawable(R.drawable.off);
			}
			if (musicBackBtn == null) {
				musicBackBtn = new Button(200, 260, 200, 50, 0, 6, onimg);
			}
			musicBackBtn.draw(canvas);
			if (musicEffectBtn == null) {
				musicEffectBtn = new Button(200, 360, 200, 50, 0, 7, offimg);
			}
			musicEffectBtn.draw(canvas);

			if (shakeBtn == null) {
				shakeBtn = new Button(200, 460, 200, 50, 0, 8, offimg);
			}
			shakeBtn.draw(canvas);

		}
			break;
		case STATE_GAME_FAIL: {
			paint.setColor(Color.BLACK);
			int rectw = 300;
			int recth = 200;
			canvas.drawRoundRect(
					new RectF(Tool.screenWidthC / 2 - rectw / 2, 100,
							Tool.screenWidthC / 2 - rectw / 2 + rectw,
							100 + recth), 45, 45, paint);
			rectw = 298;
			recth = 198;
			paint.setColor(Color.WHITE);
			canvas.drawRoundRect(
					new RectF(Tool.screenWidthC / 2 - rectw / 2, 101,
							Tool.screenWidthC / 2 - rectw / 2 + rectw,
							101 + recth), 45, 45, paint);

			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(1);
			paint.setTextSize(22);
			int offx = 20;
			canvas.drawText("分数", 140 + offx, 180, paint);
			canvas.drawText("" + score, 210 + offx, 180, paint);

			canvas.drawText("最高分", 140 + offx, 230, paint);
			canvas.drawText("" + bastScore, 210 + offx, 230, paint);

			if (isNewScore) {
				if (newimg == null) {
					newimg = Tool.getImageFromDrawable(R.drawable.news);
				}
				canvas.drawBitmap(newimg, 270, 185, null);
			}
			if (fillRestart == null) {
				Bitmap img = Tool.getImageFromDrawable(R.drawable.start);
				fillRestart = new Button((Tool.screenWidthC - buttonw) / 2,
						400, buttonw, buttonh, 0xff66CDAA, 1, img);
			}
			fillRestart.draw(canvas);
			if (backToMain == null) {
				Bitmap img =Tool.getImageFromDrawable(R.drawable.home);
				backToMain = new Button((Tool.screenWidthC - buttonw) / 2, 500,
						buttonw, buttonh, 0xff00B2EE, 3, img);
			}
			backToMain.draw(canvas);
		}
			break;
		case STATE_GAME_MAIN: {
			Paint mPaint = new Paint();
			mPaint.setColor(0xffffffff);
			canvas.drawRect(new Rect(0, 0, sw, sh), mPaint);
			if (jideImg == null) {
				jideImg = Tool.getImageFromDrawable(R.drawable.jide);
			}
			canvas.drawBitmap(jideImg,
					(Tool.screenWidthC - jideImg.getWidth()) / 2, 50, mPaint);
			if (mainStart == null) {
				Bitmap img = Tool.getImageFromDrawable(R.drawable.start);
				mainStart = new Button((Tool.screenWidthC - buttonw) / 2, 400,
						buttonw, buttonh, 0xff66CDAA, 2, img);
			}
			mainStart.draw(canvas);

			if (mainSetting == null) {
				Bitmap img = Tool.getImageFromDrawable(R.drawable.on);
				if (isSoundopen == false) {
					img = Tool.getImageFromDrawable(R.drawable.off);
				}
				mainSetting = new Button((Tool.screenWidthC - buttonw) / 2,
						500, buttonw, buttonh, 0xff00B2EE, 4, img);
			}
			mainSetting.draw(canvas);
//			if (tuijianbtn == null) {
//				Calendar c = Calendar.getInstance();
//				int year = c.get(Calendar.YEAR);
//				int month = c.get(Calendar.MONTH);
//				int day = c.get(Calendar.DAY_OF_MONTH);
//				if (year == 2015 && month == 0 && day <21) {
//					return;
//				}
//				
//				Bitmap img = Tool.getImageFromAssetsFile("tuijian.png");
//				tuijianbtn = new Button((Tool.screenWidthC - buttonw) / 2, 600,
//						buttonw, buttonh, 0xffFF7256, 9, img);
//			}
//			if(tuijianbtn!=null)
//			   tuijianbtn.draw(canvas);
		}
			break;

		case STATE_GAME_READY: {

			drawBlock(canvas);

			playerDraw(canvas);
			paint.setColor(Color.BLACK);
			paint.setTextSize(40);
			String str = new String("点击开始");
			int[] ak = {0,0,0,0,1,2,3,4,5,6,6,5,4,3,2,1,0,0,0,0};
			for (int i = 0; i < str.length(); i++) {
				canvas.drawText(str.substring(i, i + 1), 179 + i * 45,
						449 - ak[k - i]*2, paint);
			}
			k++;
			if (k > (ak.length - 1))
				k = 4;

			if (tipleft==null){
				tipleft=Tool.getImageFromDrawable(R.drawable.left);
			}
			if (tipright==null){
				tipright=Tool.getImageFromDrawable(R.drawable.right);
			}
			paint.setColor(Color.WHITE);
			paint.setTextAlign(Align.LEFT);
			paint.setTextSize(25);
			canvas.drawText("点击火箭到左边", 5, Tool.screenHeightC-tipleft.getHeight()-30, paint);
			canvas.drawText("点击火箭到右边", Tool.screenWidthC-180, Tool.screenHeightC-tipleft.getHeight()-30, paint);
			canvas.drawBitmap(tipleft, 0,Tool.screenHeightC-tipleft.getHeight(), null);
			canvas.drawBitmap(tipright,Tool.screenWidthC-tipright.getWidth()  ,Tool.screenHeightC-tipright.getHeight(), null);
		}
			break;
		case STATE_GAME_RUN: {

			drawBlock(canvas);
			playerDraw(canvas);
			paint.setColor(Color.BLACK);
			paint.setTextSize(40);
			canvas.drawText("" + score, 440, 50, paint);
		}
			break;
		case STATE_GAME_LOADING: {
			Paint mPaint = new Paint();
			mPaint.setColor(0xff4F4F4F);
			canvas.drawRect(new Rect(0, 0, sw, sh), mPaint);
			if (System.currentTimeMillis() - loadingStartTime <= 500) {
				return;
			}

			if (loadingImg == null) {
				loadingImg = Tool.getImageFromDrawable(R.drawable.ico);
			}
			canvas.save();
			canvas.translate((Tool.screenWidthC) / 2, (Tool.screenHeightC) / 2);
			if (System.currentTimeMillis() - loadingStartTime <= 800) {
				float scale = 8;
				scale = 8 - 7 * (System.currentTimeMillis() - loadingStartTime - 500) / 300;
				canvas.scale(scale, scale);
			}
			canvas.drawBitmap(loadingImg, -loadingImg.getWidth() / 2,
					-loadingImg.getHeight() / 2, null);
		}
			break;
		}
		canvas.restore();
	}

	public void update() {
		switch (currentGamestate) {
		case STATE_GAME_RUN: {
			blockMoveUpdate();

		}
			break;
		case STATE_GAME_LOADING: {
			if (System.currentTimeMillis() - loadingStartTime > 2000) {
				changeState(STATE_GAME_MAIN);
				GameView.speedTime = 33;
			}
		}
			break;
		}
	}

	private int playerJumpState = 1;
	private int playerFirstJumpH = 150;
	private int playerSecondJumpH = 250;

	private void checkCollideV(int addv) {

	}

	private boolean checkCollide() {

		return false;
	}

	private void playerDraw(Canvas canvas) {

		if (playerGirl == null) {
			playerGirl = Tool.getImageFromDrawable(R.drawable.desktop_rocket_launch);
		}
		if (playerBoy == null) {
			playerBoy =  Tool.getImageFromDrawable(R.drawable.desktop_rocket_launch2);
		}
		if (isfail) {
			playerState = failstate;
			canvas.save();
			Random ran = new Random();
			canvas.translate(ran.nextInt(5) - 2, ran.nextInt(5) - 2);

		}
		if (playerState == 0) {
			playerx = Tool.screenWidthC / 2- playerW;// 
		} else if (playerState == 1) {
			playerx = 0;
		} else if (playerState == 2) {
			playerx = Tool.screenWidthC- playerW- playerBoy.getHeight();//
		}
		playerW = playerGirl.getWidth();
		playerH = playerGirl.getHeight();
		Paint paint = new Paint();
		canvas.drawBitmap(playerGirl, playerx, playerY, paint);
		canvas.drawBitmap(playerBoy, playerx + playerW, playerY, paint);
		if (isfail) {
			canvas.restore();

		}
	}

	private void createBlock() {
		vblock.removeAllElements();
		lastBlockY = 50;
		blockMoveV = 7;
		score = 0;
		Block block;
		blockoffy = 200;
		isfail = false;
		Random ran = new Random();
		for (int i = 0; i < 10; i++) {
			int offyran = ran.nextInt(80);
			int blockh = ran.nextInt(100) + 50;
			block = new Block(ran.nextInt(3), lastBlockY, blockh);
			int color = getBlockColor();
			block.setColor(color);
			
			vblock.addElement(block);
			lastBlockY -= (blockh + blockoffy + offyran);
		}

		// SoundManager.PlayEffect(SoundManager.LoadSoundEffect("sound/go.mp3"),
		// false);
	}

	private int gametime = 0;

	private void initPlayerData() {

		playerState = 0;
	}

	boolean isfail = false;
	int failstate = 0;
	int failindex = 0;

	private void blockMoveUpdate() {
		if (isfail) {
			playerState = failstate;
			failindex++;
			if (failindex > 50) {
				changeState(STATE_GAME_FAIL);
				
			}
			return;
		}

		for (int i = 0; i < vblock.size(); i++) {
			Block block = vblock.elementAt(i);
			block.move(blockMoveV);
			if (block.y + block.h > playerY && block.y < playerY + 40) {
				if (block.type != playerState) {
					failstate = playerState;
					isfail = true;
					failindex = 0;
					SoundManager.PlayEffect(
							SoundManager.LoadSoundEffect("sound/die2.mp3"),
							false);
					return;
				}
			}

			if (block.y > playerY && block.type == playerState
					&& block.isPass == false) {
				block.isPass = true;
				score++;
				if (score % 3 == 0) {
					blockMoveV++;
				}
				SoundManager.PlayEffect(
						SoundManager.LoadSoundEffect("sound/pass_block.mp3"),
						false);
			}
		}
		for (int i = 0; i < vblock.size(); i++) {
			Block block = vblock.elementAt(i);
			if (block.y > Tool.screenHeightC + 100) {
				vblock.remove(block);
			}
		}

		if (vblock.size() < 7) {
			Block block;
			blockoffy = 200;
			lastBlockY = vblock.lastElement().y - 300;
			Random ran = new Random();
			for (int i = 0; i < 5; i++) {
				int offyran = ran.nextInt(80);
				int blockh = ran.nextInt(100) + 50;
				block = new Block(ran.nextInt(3), lastBlockY, blockh);
				int color = getBlockColor();
				block.setColor(color);
				vblock.addElement(block);
				lastBlockY -= (blockh + blockoffy + offyran);
			}
		}
	}

	private void drawBlock(Canvas canvas) {

		for (int i = 0; i < vblock.size(); i++) {
			Block block = vblock.elementAt(i);
			block.draw(canvas);
		}

	}

	private int getBlockColor() {
		firstColorIndex++;
		if (firstColorIndex >= color.length) {
			firstColorIndex = 0;
		}
		return color[firstColorIndex];
	}

	// 4 mainsetting 5 settingback 6 back music 7 effect music 8 shake 9 tuijian
	// 10 2048 11bw 12 more
	public void buttonOntouch(int tag) {
		switch (tag) {
		case 1: {
			changeState(STATE_GAME_READY);
			SoundManager.PlayEffect(
					SoundManager.LoadSoundEffect("sound/ready.mp3"), false);
			// Random ran =new Random();
			// SoundManager.PlayBackgroundMusic("sound/back/"+ran.nextInt(6)+"stage.mp3",
			// true);
		}
			return;
		case 2: {
			changeState(STATE_GAME_READY);
			SoundManager.PlayEffect(
					SoundManager.LoadSoundEffect("sound/ready.mp3"), false);
			// Random ran =new Random();
			// SoundManager.PlayBackgroundMusic("sound/back/"+ran.nextInt(6)+"stage.mp3",
			// true);
		}
			return;
		case 3: {
			changeState(STATE_GAME_MAIN);
		}
			break;
		case 4: {
			// changeState(STATE_GAME_SETTING);
			isSoundopen = !isSoundopen;
			Bitmap img = null;
			if (isSoundopen) {
				SoundManager.SetEffectVolume(10);
				img = Tool.getImageFromDrawable(R.drawable.on);
			} else {
				SoundManager.SetEffectVolume(0);
				img =Tool.getImageFromDrawable(R.drawable.off);
			}
			mainSetting.setBitmap(img);
		}
			break;
		case 5: {
			changeState(oldGameState);
		}
			break;
		case 6: {

		}
			break;
		case 7: {

		}
			break;
		case 8: {

		}
			break;
		case 9: {
			changeState(STATE_GAME_TUIJIAN);
		}
			break;
		}
		SoundManager.PlayEffect(
				SoundManager.LoadSoundEffect("sound/button1.mp3"), false);
	}

	private long loadingStartTime = 0;
	private int oldGameState = -1;

	public void changeState(int state) {
		oldGameState = currentGamestate;
		currentGamestate = state;
		switch (currentGamestate) {
		case STATE_GAME_LOADING: {
			loadingStartTime = System.currentTimeMillis();
			GameView.speedTime = 10;
		}
			break;
		case STATE_GAME_READY: {
			Random ran = new Random();
			firstColorIndex = ran.nextInt(5);
			initPlayerData();
			createBlock();
		}
			break;
		case STATE_GAME_RUN: {

		}
			break;
		case STATE_GAME_WIN: {
			saveGameData();
		}
			break;
		case STATE_GAME_FAIL: {
			isNewScore = false;
			if (score > bastScore) {
				bastScore = score;
				saveGameData();
				isNewScore = true;
			}
			gametime++;
			if (gametime % 3 == 2) {
//				 MainActivity.app.showads();
			}else if (gametime>10){
//				 MainActivity.app.showads();
			}
		}
			break;
		}
		if (oldGameState == STATE_GAME_RUN) {
			SoundManager.StopBackgroundMusic();
		}
	}

	int lastTouchX;
	int lastTouchY;
	boolean isEnable = false;

	public void onTouchEvent(MotionEvent event) {
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastTouchX = (int) event.getX();
				lastTouchY = (int) event.getY();
				isEnable = true;
				switch (currentGamestate) {
				case STATE_GAME_READY: {

					changeState(STATE_GAME_RUN);
				}
					break;
				case STATE_GAME_RUN: {
					if (lastTouchX < Tool.screenWidth / 2) {
						playerState = 1;
					} else {
						playerState = 2;
					}
				}
					break;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				if (currentGamestate == STATE_GAME_RUN) {
					playerState = 0;
				}
				break;
			}

			switch (currentGamestate) {
			case STATE_GAME_MAIN: {
				mainStart.onTouchEvent(event);
				mainSetting.onTouchEvent(event);
			}
				break;
			case STATE_GAME_FAIL: {
				if (fillRestart != null)
					fillRestart.onTouchEvent(event);
				if (backToMain != null)
					backToMain.onTouchEvent(event);
			}
				break;
			case STATE_GAME_SETTING: {
				settingBack.onTouchEvent(event);
				musicBackBtn.onTouchEvent(event);
				musicEffectBtn.onTouchEvent(event);
			}
				break;
			case STATE_GAME_TUIJIAN: {
				settingBack.onTouchEvent(event);
			}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showHelpInfo() {
		// MainActivity.app.Toast("滑动数字，将相同数字融合相加，你要做的就是合成 2048 获胜!");
		changeState(STATE_GAME_HELP);
	}

	private void saveGameData() {
		byte[] arrayOfByte = new byte[1024];
		if (isSoundopen)
			arrayOfByte[1] = 1;
		else {
			arrayOfByte[1] = 0;
		}

		intToBytes(arrayOfByte, 5, bastScore);

		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(
					LiveActivity.context.getFilesDir() + "/gamedata.sav");
			localFileOutputStream.write(arrayOfByte);
			localFileOutputStream.flush();
			localFileOutputStream.close();
			return;
		} catch (Exception localException) {
			Tool.Log("save error");
			localException.printStackTrace();
		}
	}

	public void LoadGameData() {
		FileInputStream inputestream = null;
		int size = 0;
		try {

			try {
				inputestream = LiveActivity.context
						.openFileInput("gamedata.sav");

			} catch (Exception e) {
				Tool.Log("error 0 ");
				e.printStackTrace();
			}
			size = inputestream.available();
			if (size != 0) {
			}
		} catch (Exception e) {
			size = 0;
			showHelpInfo();
			return;
		}

		byte[] data = new byte[size];

		try {
			inputestream.read(data);
			inputestream.close();
		} catch (Exception e) {
			showHelpInfo();
			Tool.Log("error 1 ");
			return;
		}
		if (data[1] == 1) {
			isSoundopen = true;
		} else {
			isSoundopen = false;
		}

		bastScore = bytesToInt(data, 5, false);

	}

	static void intToBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
		paramArrayOfByte[paramInt1] = (byte) (0xFF & paramInt2 >>> 24);
		paramArrayOfByte[(paramInt1 + 1)] = (byte) (0xFF & paramInt2 >>> 16);
		paramArrayOfByte[(paramInt1 + 2)] = (byte) (0xFF & paramInt2 >>> 8);
		paramArrayOfByte[(paramInt1 + 3)] = (byte) (paramInt2 & 0xFF);
	}

	static int bytesToInt(byte[] src, int offset, boolean reverse) {
		int res;

		if (reverse) {
			res = (src[offset + 3] & 0xff) << 24
					| (src[offset + 2] & 0xff) << 16
					| (src[offset + 1] & 0xff) << 8 | (src[offset] & 0xff);
		} else {
			res = (src[offset] & 0xff) << 24 | (src[offset + 1] & 0xff) << 16
					| (src[offset + 2] & 0xff) << 8 | (src[offset + 3] & 0xff);
		}

		return res;
	}

	class Block {
		int x;
		int y;
		int w;
		int h;
		int color;
		boolean isPass = false;
		int type = 0;

		Block(int type, int y, int h) {
			this.y = y;
			this.h = h;
			this.type = type;
			w = Tool.screenWidthC - boyAndGirlW;
		}

		public void setColor(int c) {
			color = c;
		}

		public void draw(Canvas canvas) {

			Paint paint = new Paint();
			paint.setColor(color);
			switch (type) {
			case 0: {
				canvas.drawRect(new Rect(0, y, w / 2, y + h), paint);
				canvas.drawRect(new Rect(w / 2 + boyAndGirlW, y, w / 2
						+ boyAndGirlW + w / 2, y + h), paint);
			}
				break;
			case 2: {
				canvas.drawRect(new Rect(0, y, w, y + h), paint);
			}
				break;
			case 1: {
				canvas.drawRect(
						new Rect(boyAndGirlW, y, boyAndGirlW + w, y + h), paint);
			}
				break;

			}

		}

		public void move(int v) {
			y += v;
		}
	}

	class Button {
		int x;
		int y;
		int w;
		int h;
		int color;
		int tag;
		Bitmap img;
		int state; // 0 正常 1 按下 2 点击

		public Button(int x, int y, int w, int h, int color, int tag, Bitmap img) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.color = color;
			this.tag = tag;
			this.img = img;
			state = 0;
		}

		public void setBitmap(Bitmap img) {
			this.img = img;
		}

		public void draw(Canvas canvas) {
			canvas.save();
			Paint paint = new Paint();
			paint.setColor(color);
			if (color != 0)
				canvas.drawRoundRect(new RectF(x, y, x + w, y + h), 45, 45,
						paint);
			if (state == 0)
				if (img != null) {
					canvas.drawBitmap(img, x + w / 2 - img.getWidth() / 2, y
							+ h / 2 - img.getHeight() / 2, null);
				}
			if (state == 1 || state == 2) {
				float scale = 1.15f;
				canvas.save();
				canvas.translate(x + w / 2 * scale, y + h / 2 * scale);
				canvas.scale(scale, scale);
				if (color != 0)
					canvas.drawRoundRect(new RectF(-w / 2 * scale, -h / 2
							* scale, -w / 2 * scale + w, +-h / 2 * scale + h),
							45, 45, paint);
				canvas.restore();
				if (img != null) {
					canvas.save();
					canvas.translate(x + w / 2, y + h / 2);
					canvas.scale(scale, scale);
					canvas.drawBitmap(img, -img.getWidth() / 2,
							-img.getHeight() / 2, null);
					canvas.restore();
				}

				if (state == 2) {
					buttonOntouch(tag);
					state = 0;
				}
			}
			canvas.restore();
		}

		public void onTouchEvent(MotionEvent event) {
			int dx = (int) event.getX();
			int dy = (int) event.getY();
			float scale = 1f * Tool.screenWidth / 480;
			dx = (int) (dx / scale);
			dy = (int) (dy / scale);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (Tool.isPointInRect(dx, dy, x, y, w, h)) {
					state = 1;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				Tool.Log("1  dx=" + dx + "  dy=" + dy + " x==" + x + "   y="
						+ y);
				if (Tool.isPointInRect(dx, dy, x, y, w, h)) {
					state = 1;
					Tool.Log("2");
				} else {
					state = 0;
				}
				Tool.Log("3");
				break;
			case MotionEvent.ACTION_CANCEL:

				state = 0;

				break;
			case MotionEvent.ACTION_UP:
				if (Tool.isPointInRect(dx, dy, x, y, w, h)) {
					if (state == 1)
						state = 2;
				} else {
					state = 0;
				}
				break;
			}

		}
	}
}
