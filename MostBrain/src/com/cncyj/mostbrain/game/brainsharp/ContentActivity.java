package com.cncyj.mostbrain.game.brainsharp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cncyj.mostbrain.R;
import com.cncyj.mostbrain.game.brainsharp.modle.Canginfo;
import com.cncyj.mostbrain.game.brainsharp.spl.DbMananger;
import com.cncyj.mostbrain.game.brainsharp.utli.ContentData;
import com.cncyj.mostbrain.game.brainsharp.utli.DateUtil;
import com.cncyj.mostbrain.game.brainsharp.utli.SharedPreferencesForLogin;

public class ContentActivity extends Activity implements OnClickListener {

	private TextView tx_content;
	private Button btn_daan;
	private TextView tx_title;
	private Button content_shoucang_app;
	String type="10001";
	
	
	private SharedPreferencesForLogin index_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_content);
		
		index_info = new SharedPreferencesForLogin(ContentActivity.this,"pageindex");
		
		Intent intent=getIntent();
		type=intent.getStringExtra("typedate");
		Log.i("type", type);
		
		tx_content=(TextView) this.findViewById(R.id.btn_content);
		btn_daan=(Button) this.findViewById(R.id.btn_daan);
		tx_title=(TextView)this.findViewById(R.id.content_title);
		init();
		setTitle(type);
		
	}
	
	
	public void setTitle(String type){
		if(type.equals("10001")){
			tx_title.setText("��Ц����");
		}else if(type.equals("10002")){
			tx_title.setText("��Ĭ����");
		}else if(type.equals("10003")){
			tx_title.setText("Ȥζ����");
		}else if(type.equals("10004")){
			tx_title.setText("��������");
		}else if(type.equals("10005")){
			tx_title.setText("��������");
		}else if(type.equals("10006")){
			tx_title.setText("��������");
		}else if(type.equals("10007")){
			tx_title.setText("�ۺ�����");
		}else if(type.equals("10008")){
			tx_title.setText("��ɫ����");
		}else if(type.equals("10009")){
			tx_title.setText("��Ȥ����");
		}
		else{
			tx_title.setText("�Խת��");
		}
	}
	public void init(){
		
		 int x=getTypeIndex(type);  //�õ��±�
		 String[] typecontent=getTypeData(type);
		 String content=typecontent[x];
		 String[] valuedata=content.split("#");  //�õ�ĳһ��������ʹ�
		 tx_content.setText(valuedata[0]);
		
	     findViewById(R.id.btn_left).setOnClickListener(this);
	     findViewById(R.id.btn_right).setOnClickListener(this);
	     findViewById(R.id.btn_content).setOnClickListener(this);
	     findViewById(R.id.fuzhi).setOnClickListener(this);
	     findViewById(R.id.fenxiang).setOnClickListener(this);
	     findViewById(R.id.btn_daan).setOnClickListener(this);
	     findViewById(R.id.content_shoucang_app).setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id=v.getId();
		switch (id) {
		case R.id.btn_left:
			int x=getTypeIndex(type);  //�õ��±�
			
			
			getContentleft(type,x); //�������͵��±�
			
			int new_x=getTypeIndex(type); //�µ��±�
			
			String[] typecontent=getTypeData(type);
			
			String content=typecontent[new_x];
			
			String[] valuedata=content.split("#");  //�õ�ĳһ��������ʹ�
			
			tx_content.setText(valuedata[0]);
			
			btn_daan.setText("�鿴��");
			
			break;
		case R.id.btn_right:
			
			int y=getTypeIndex(type);
			
			
			getContentright(type,y); //�������͵��±�
			
			int new_y=getTypeIndex(type); //�µ��±�
		
			String[] rtypecontent=getTypeData(type);
			
			String rcontent=rtypecontent[new_y];
			
			String[] rvaluedata=rcontent.split("#");  //�õ�ĳһ��������ʹ�
			
			tx_content.setText(rvaluedata[0]); //��������
			
			btn_daan.setText("�鿴��");
			
			break;
		case R.id.btn_daan:
			int z=getTypeIndex(type);  //�õ��±�
			
			String[] ztypecontent=getTypeData(type);
			
			String zcontent=ztypecontent[z];
			
			String[] zvaluedata=zcontent.split("#");  //�õ�ĳһ��������ʹ�
			
			btn_daan.setText(zvaluedata[1]);
		break;
		case R.id.fenxiang:
			
			 int xx=getTypeIndex(type);  //�õ��±�
			 String[] xxtypecontent=getTypeData(type);
			 String xxcontent=xxtypecontent[xx];
			 String[] xxvaluedata=xxcontent.split("#");  //�õ�ĳһ��������ʹ�
			
			Intent intent=new Intent(Intent.ACTION_SEND);   
	        intent.setType("text/plain");
//	        intent.setType("image/png");
	        //���ͼƬ
//	        File f = new File(Environment.getExternalStorageDirectory() + "/Pictures/2.png");
//	        Uri u = Uri.fromFile(f);
//	        intent.putExtra(Intent.EXTRA_STREAM, R.drawable.ic_launcher);
	        
	        intent.putExtra(Intent.EXTRA_SUBJECT, "����");   
	        intent.putExtra(Intent.EXTRA_TEXT, xxvaluedata[0]+"\n���� ��"+xxvaluedata[1]);    
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	        startActivity(Intent.createChooser(intent, getTitle()));
			break;
		case R.id.content_shoucang_app: //�ղ�
			
			int k=getTypeIndex(type);  //�õ��±�
			String[] ktypecontent=getTypeData(type);
			String kcontent=ktypecontent[k];
			
			DbMananger db=new DbMananger(this);
			long count=db.getCount(type, String.valueOf(k));
			if(count==0){
				Canginfo cang=new Canginfo();
				cang.setAppcontent(kcontent);
				cang.setAppindex(String.valueOf(k));
				cang.setApptype(type);
				db.insertState(cang);
				Toast.makeText(ContentActivity.this, "�ղسɹ�", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(ContentActivity.this, "���ղ�", Toast.LENGTH_SHORT).show();
			}
			
//			String[] kvaluedata=kcontent.split("#");  //�õ�ĳһ��������ʹ�
//			String cangdata=index_info.getValue("shoucangData");
//			String[] ccang=cangdata.split("&");
//			for(int i=0;i<ccang.length;i++){
//				if(ccang[i].equals(kcontent)){
//					
//				}
//			}
//			String cangcontent=cangdata+"&"+kcontent;
//			 
//			index_info.putValue("shoucangData", cangcontent);
			break;
		case R.id.fuzhi:
			 int yy=getTypeIndex(type);  //�õ��±�
			 String[] yytypecontent=getTypeData(type);
			 String yycontent=yytypecontent[yy];
			 String[] yyvaluedata=yycontent.split("#");  //�õ�ĳһ��������ʹ�
			ClipboardManager cm =(ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
			//���ı����ݸ��Ƶ�������
			cm.setText(yyvaluedata[0]+"���ǣ�"+yyvaluedata[1]);
			Toast.makeText(ContentActivity.this, "���Ƴɹ�", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
	
	//�õ���������
	public String[] getTypeData(String s){
		ContentData content=new ContentData();
		String[] result=null;
		if(s.equals("10001")){
			result=content.getGaoxiaodata();
		}else if(s.equals("10002")){
			result=content.getYoumodata();
		}else if(s.equals("10003")){
			result=content.getQuweidata();
		}else if(s.equals("10004")){
			result=content.getDongwudata();
		}else if(s.equals("10005")){
			result=content.getJindiandata();
		}else if(s.equals("10006")){
			result=content.getZhengrendata();
		}else if(s.equals("10007")){
			result=content.getZonghedata();
		}else if(s.equals("10008")){
			result=content.getTesedata();
		}else if(s.equals("10009")){
			result=content.getFengqudata();
		}
		return result;
	}
	
	//���������±�(��һ��)
	public void getContentright(String type,int i){
		if(getTypeData(type).length==i+1){
			i=0;
		}
		if(type.equals("10001")){
			index_info.putValue("gaoxiao_index", String.valueOf(i+1));
		}
		if(type.equals("10002")){
			index_info.putValue("youmo_index", String.valueOf(i+1));
		}
		if(type.equals("10003")){
			index_info.putValue("quwei_index", String.valueOf(i+1));
		}
		if(type.equals("10004")){
			index_info.putValue("dongwu_index", String.valueOf(i+1));
		}
		if(type.equals("10005")){
			index_info.putValue("jindian_index", String.valueOf(i+1));
		}
		if(type.equals("10006")){
			index_info.putValue("zhengren_index", String.valueOf(i+1));
		}
		if(type.equals("10007")){
			index_info.putValue("zonghe_index", String.valueOf(i+1));
		}
		if(type.equals("10008")){
			index_info.putValue("tese_index", String.valueOf(i+1));
		}
		if(type.equals("10009")){
			index_info.putValue("fengqu_index", String.valueOf(i+1));
		}
	}
	//���������±�(��һ��)
	public void getContentleft(String type,int i){
		if(type.equals("10001")){
			if(i==0){
				index_info.putValue("gaoxiao_index", String.valueOf(0));
			}else{
				index_info.putValue("gaoxiao_index", String.valueOf(i-1));
			}
		}else if(type.equals("10002")){
			if(i==0){
				index_info.putValue("youmo_index", String.valueOf(0));
			}else{
				index_info.putValue("youmo_index", String.valueOf(i-1));
			}
		}else if(type.equals("10003")){
			if(i==0){
				index_info.putValue("quwei_index", String.valueOf(0));
			}else{
				index_info.putValue("quwei_index", String.valueOf(i-1));
			}
		}else if(type.equals("10004")){
			if(i==0){
				index_info.putValue("dongwu_index", String.valueOf(0));
			}else{
				index_info.putValue("dongwu_index", String.valueOf(i-1));
			}
		}else if(type.equals("10005")){
			if(i==0){
				index_info.putValue("jindian_index", String.valueOf(0));
			}else{
				index_info.putValue("jindian_index", String.valueOf(i-1));
			}
		}else if(type.equals("10006")){
			if(i==0){
				index_info.putValue("zhengren_index", String.valueOf(0));
			}else{
				index_info.putValue("zhengren_index", String.valueOf(i-1));
			}
		}else if(type.equals("10007")){
			if(i==0){
				index_info.putValue("zonghe_index", String.valueOf(0));
			}else{
				index_info.putValue("zonghe_index", String.valueOf(i-1));
			}
		}else if(type.equals("10008")){
			if(i==0){
				index_info.putValue("tese_index", String.valueOf(0));
			}else{
				index_info.putValue("tese_index", String.valueOf(i-1));
			}
		}else if(type.equals("10009")){
			if(i==0){
				index_info.putValue("fengqu_index", String.valueOf(0));
			}else{
				index_info.putValue("fengqu_index", String.valueOf(i-1));
			}
		}
	}
	
	//�õ������±�
	public int getTypeIndex(String type){
		int index=0;
		if(type.equals("10001")){
			if(index_info.getValue("gaoxiao_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("gaoxiao_index"));
			}
		}else if(type.equals("10002")){
			if(index_info.getValue("youmo_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("youmo_index"));
			}
		}else if(type.equals("10003")){
			if(index_info.getValue("quwei_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("quwei_index"));
			}
		}else if(type.equals("10004")){
			if(index_info.getValue("dongwu_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("dongwu_index"));
			}
		}else if(type.equals("10005")){
			if(index_info.getValue("jindian_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("jindian_index"));
			}
		}else if(type.equals("10006")){
			if(index_info.getValue("zhengren_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("zhengren_index"));
			}
		}else if(type.equals("10007")){
			if(index_info.getValue("zonghe_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("zonghe_index"));
			}
		}else if(type.equals("10008")){
			if(index_info.getValue("tese_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("tese_index"));
			}
		}else if(type.equals("10009")){
			if(index_info.getValue("fengqu_index")==null){
				index=0;
			}else{
				index=Integer.parseInt(index_info.getValue("fengqu_index"));
			}
		}
		return index;
	}
	
}
