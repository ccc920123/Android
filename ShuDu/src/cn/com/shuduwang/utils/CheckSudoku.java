package cn.com.shuduwang.utils;

import java.util.ArrayList;

import cn.com.shuduwang.RatingActivity;

import android.app.Activity;
import android.content.Context ;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

/**
 * �����õ�Sukodu�Ƿ���ȷ
 * */
public class CheckSudoku {

	private Context _context ;			//��̬������
	private CountTime timer ;			//�����timer�ؼ����Եõ�����ʱ��
	private Activity _Activity ;		//��ϷActivity����
	private Handler _handler ;			//Handler
	private int handler_count ;			//����handler����
	private ProgressDialogControler _pdc ; //dialog
	
	//Sharedperference
	private SharedPreferences shared ;
	private SharedPreferences.Editor editor ;
	
	private static final String MAX_STAGE = "max_stage" ;
	private int stage ;
	
	private static final String STAGE_SCORE = "score" ;
	
	private ArrayList<String[]> numList ;			//������������ (TIPS:�Ǵ�1��ʼ�� ��9����)
	private String[] itemList ;					//������
	private static final String DEFAULT_NULL = "0" ;	//ArrayList��Ĭ�ϵĿ�λ��
	
	private ArrayList<ArrayList<String>> horizontalList ;	//����9������	
	private ArrayList<String> horizontalItem ;				//����������
	private ArrayList<ArrayList<String>> verticalList ;		//����9������
	private ArrayList<String> verticalItem ;				//����������
	private ArrayList<ArrayList<String>> squareList ;		//�Ÿ��Ź��������
	private ArrayList<String> rightList ;					//�Ź�������
	
	boolean horizontal = true ;					//�����Ƿ���ȷ
	boolean vertical = true ;					//�����Ƿ���ȷ
	boolean square = true ;						//�Ź������Ƿ���ȷ
	
	private Runnable run ;
	
	public CheckSudoku(Context context, int stage, Handler handler, ProgressDialogControler pdc){
		this._context = context ;
		this._handler = handler ;
		this.stage = stage ;
		this._pdc = pdc ;
		
		horizontalList = new ArrayList<ArrayList<String>>() ;
		verticalList = new ArrayList<ArrayList<String>>() ;
		squareList = new ArrayList<ArrayList<String>>() ;
		//�Աȱ�׼
		rightList = new ArrayList<String>() ;
		for(int i=1;i<10;i++){
			rightList.add(""+i) ;
		}
		rightList.trimToSize() ;
		//
		run = new Runnable(){
			@Override
			public void run(){
				_pdc.myDismiss() ;
			}
		} ;
		
		
	}
	
	public boolean isRight(ArrayList<String[]> numList, CountTime timer){
//		_handler.post(new Runnable(){
//			@Override
//			public void run(){
//				_pdc = new ProgressDialogControler(_context) ; 
//			}
//		}) ;
		
		
		//�õ�sukodu��Ϣ
		this.numList = numList ;
		this.timer = timer ;
		
		//��������������Ϣ
		sortSudoku() ;
		//�����Ƿ����1-9���ظ�
		for(int i=0;i<9;i++){
			horizontal = horizontalList.get(i).containsAll(rightList) ;
			//
//			for(String str : rightList){
//System.out.println("rightList : "+str) ; 
//			}
//System.out.println("horizontal" + horizontal + ":" + i) ;
			if(!horizontal){
				handler_count = i ;
				//������ʾ
				_handler.post(new Runnable(){
					@Override
					public void run(){
						Toast.makeText(_context, "��"+(handler_count+1)+"��"+"���˰�����", 1500).show() ;
					}
				}) ;
				//ȡ��dialog
				_handler.post(run) ;
				return false ;
			}
		}
		for(int i=0;i<9;i++){
			vertical = verticalList.get(i).containsAll(rightList) ;
//System.out.println("vertical" + vertical + ":" + i) ;
			if(!vertical){
				handler_count = i ;
				//������ʾ
				_handler.post(new Runnable(){
					@Override
					public void run(){
						Toast.makeText(_context, "��"+(handler_count+1)+"��"+"���˰�����", 1500).show() ;
					}
				}) ;
				//ȡ��dialog
				_handler.post(run) ;
				return false ;
			}
		}
		for(int i=0;i<9;i++){
			
//			//
//			for(String str : squareList.get(i)){
//System.out.println("square : "+str) ; 
//			}
//			for(String str : rightList){
//System.out.println("rightList : "+str) ; 
//			}
			square = squareList.get(i).containsAll(rightList) ;
//System.out.println("square" + square + ":" + i) ;
			if(!square){
				handler_count = i ;
				//������ʾ
				_handler.post(new Runnable(){
					@Override
					public void run(){
						Toast.makeText(_context, "��"+(handler_count+1)+"���Ź���"+"���˰�����", 1500).show() ;
					}
				}) ;
				//ȡ��dialog
				_handler.post(run) ;
			
				return false ;
			}
		}
		//ȡ��dialog
		_handler.post(run) ;
		
		//�õ�SharedPerference�����Դ洢���ݣ�������������洢�������Ĺؿ��ɼ�
		shared = this._context.getSharedPreferences("passGame", Activity.MODE_PRIVATE) ;
		editor = shared.edit() ;
		//�õ����λ����������
		float rating = timer.getRating() ;
		float newRating = rating ;
System.out.println("CheckSudoku 111 ����rating:"+rating) ;
		//�õ�ԭ���洢����Ϣ
		float oldRating = shared.getFloat(STAGE_SCORE+stage, 0) ;
		rating = rating>oldRating ? rating : oldRating ;
System.out.println("CheckSudoku 115 �жϺ�����rating:"+rating+" λ�ã�"+STAGE_SCORE+stage) ;
		//��������stage��Ϣ
		editor.putFloat(STAGE_SCORE+stage, rating) ;
		editor.commit() ;
		
		
		//�õ�SharedPerference�����Դ洢���ݣ�������������������ͨ������һ����
		//�õ�ԭ���洢����Ϣ
		int oldStage = shared.getInt(MAX_STAGE, 0) ;
		stage = stage>oldStage ? stage : oldStage ;
System.out.println("checksudoku line 176"+stage) ;
		//��������stage��Ϣ
		editor.putInt(MAX_STAGE, stage) ;
		editor.commit() ;
		
		//����������һ�ص�dialog
		Intent intent = new Intent(_context,RatingActivity.class) ;
		intent.putExtra("score", newRating) ;
		intent.putExtra("stage", stage) ;
		((Activity)_context).startActivityForResult(intent, 4) ;
		return true;
	}
	
	/**
	 * �������sudoku�������3������ ���Լ��
	 * */
	private void sortSudoku(){
		
		//���Ŵ洢
		for(int i=1;i<10;i++){
			itemList = numList.get(i);
			verticalItem = new ArrayList<String>() ;
			for(int j=1;j<10;j++){
				verticalItem.add(itemList[j]) ;
			}
			verticalList.add(verticalItem) ;
		}
		//���Ŵ洢
		for(int i=1;i<10;i++){
			horizontalItem = new ArrayList<String>() ;
			for(int j=1;j<10;j++){
				horizontalItem.add((numList.get(j))[i]) ;
			}
			horizontalList.add(horizontalItem) ;
		}
		//�Ź���洢
		ArrayList<String> a = new ArrayList<String>() ;
		ArrayList<String> b = new ArrayList<String>() ;
		ArrayList<String> c = new ArrayList<String>() ;
		ArrayList<String> d = new ArrayList<String>() ;
		ArrayList<String> e = new ArrayList<String>() ;
		ArrayList<String> f = new ArrayList<String>() ;
		ArrayList<String> g = new ArrayList<String>() ;
		ArrayList<String> h = new ArrayList<String>() ;
		ArrayList<String> _i = new ArrayList<String>() ;
		//
		for(int i=1;i<10;i++){
			itemList = numList.get(i);
			for(int j=1;j<10;j++){
				switch(i){
				case 1:
				case 2:
				case 3:
					switch(j){
					case 1:
					case 2:
					case 3:
						a.add(itemList[j]) ;
						break;
					case 4:
					case 5:
					case 6:
						d.add(itemList[j]) ;
						break;
					case 7:
					case 8:
					case 9:
						g.add(itemList[j]) ;
						break;
						
					}
					break ;
				case 4:
				case 5:
				case 6:
					switch(j){
					case 1:
					case 2:
					case 3:
						b.add(itemList[j]) ;
						break;
					case 4:
					case 5:
					case 6:
						e.add(itemList[j]) ;
						break;
					case 7:
					case 8:
					case 9:
						h.add(itemList[j]) ;
						break;
						
					}
					break;
				case 7:
				case 8:
				case 9:
					switch(j){
					case 1:
					case 2:
					case 3:
						c.add(itemList[j]) ;
						break;
					case 4:
					case 5:
					case 6:
						f.add(itemList[j]) ;
						break;
					case 7:
					case 8:
					case 9:
						_i.add(itemList[j]) ;
						break;
						
					}
					break ;
				}
			}
			squareList.add(a) ;
			squareList.add(b) ;
			squareList.add(c) ;
			squareList.add(d) ;
			squareList.add(e) ;
			squareList.add(f) ;
			squareList.add(g) ;
			squareList.add(h) ;
			squareList.add(_i) ;
		}
	}
}
