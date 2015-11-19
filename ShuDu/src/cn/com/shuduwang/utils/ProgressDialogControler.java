package cn.com.shuduwang.utils;


import android.app.ProgressDialog;
import android.content.Context; ;

public class ProgressDialogControler {
	private Context context ;
	private ProgressDialog pd ;
	public ProgressDialogControler(Context context){
		this.context = context ;
		onCreateDialog();
	}
	private void onCreateDialog(){
		pd = new ProgressDialog(context);
		pd.setTitle("...�Ե�...");
		pd.setIcon(context.getResources().getDrawable(
				android.R.drawable.ic_dialog_alert));
		pd.setMessage("��������֤�С���");
		pd.show();
	}
	public void myDismiss(){
		pd.dismiss() ;
	}
}
