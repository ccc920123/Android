package cn.com.shuduwang;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import cn.com.shuduwang.dialog.AlerDialog;

public class ShuDuActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ǿ�ƺ��� portraitǿ������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		
	}
	
	/**
	 * button�����Ӧ����
	 * @param v �ؼ�
	 */
	public void isClick(View v){
		int id = v.getId();
		switch(id){
		case R.id.newgame://����Ϸ
			Intent intent = new Intent(this,MyViewPagerActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
        case R.id.continuegame://������Ϸ
			SharedPreferences shard = getSharedPreferences("continueGame", Activity.MODE_PRIVATE);
			int i = shard.getInt("stage", -1);
			System.out.println("i = "+i);
			if(i == -1){
				Toast.makeText(this, "û�пɼ�������Ϸ", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent inte = new Intent(this,Sudoku_Activity.class);
			inte.putExtra("stage", i);
			inte.putExtra("action", true);
			startActivity(inte);
			break;
        case R.id.aboutus://�����҂�
	      startActivity(new Intent(this,AboutusActivity.class));
	        break;
        case R.id.aboutgame://�P��[��
//          startActivity(new Intent(this,AboutgameActivity.class));
        	View view =  View.inflate(this, R.layout.aboutgame, null);
    		Dialog dialog = new Dialog(this, R.style.dialog);
    		dialog.getWindow().setContentView(view);
    		dialog.show();
    		dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
    		dialog.setCanceledOnTouchOutside(true);
        	break;
		}
	}
 
	/**
     * ��дback���ؼ��������ʱ�����û��Ƿ���Ҫ�˳���
     */
	@Override public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlerDialog.exitDialog(ShuDuActivity.this,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
AlerDialog.closeDialog();
										System.exit(0);				
						

						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							AlerDialog.closeDialog();
						}
					});

		}
		return super.onKeyDown(keyCode, event);
	}
	
	private Dialog dialog = null;
    private int width = 0;
    private int height = 0;
}