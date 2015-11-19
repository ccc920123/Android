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
 * 检查填好的Sukodu是否正确
 * */
public class CheckSudoku {

	private Context _context ;			//静态上下文
	private CountTime timer ;			//传入的timer控件用以得到结束时间
	private Activity _Activity ;		//游戏Activity对象
	private Handler _handler ;			//Handler
	private int handler_count ;			//用于handler计数
	private ProgressDialogControler _pdc ; //dialog
	
	//Sharedperference
	private SharedPreferences shared ;
	private SharedPreferences.Editor editor ;
	
	private static final String MAX_STAGE = "max_stage" ;
	private int stage ;
	
	private static final String STAGE_SCORE = "score" ;
	
	private ArrayList<String[]> numList ;			//玩家填入的数字 (TIPS:是从1开始的 到9结束)
	private String[] itemList ;					//子容器
	private static final String DEFAULT_NULL = "0" ;	//ArrayList中默认的空位子
	
	private ArrayList<ArrayList<String>> horizontalList ;	//横排9个数组	
	private ArrayList<String> horizontalItem ;				//横排子容器
	private ArrayList<ArrayList<String>> verticalList ;		//竖排9个数组
	private ArrayList<String> verticalItem ;				//竖排子容器
	private ArrayList<ArrayList<String>> squareList ;		//九个九宫格的数组
	private ArrayList<String> rightList ;					//九宫格数组
	
	boolean horizontal = true ;					//横排是否正确
	boolean vertical = true ;					//竖排是否正确
	boolean square = true ;						//九宫格内是否正确
	
	private Runnable run ;
	
	public CheckSudoku(Context context, int stage, Handler handler, ProgressDialogControler pdc){
		this._context = context ;
		this._handler = handler ;
		this.stage = stage ;
		this._pdc = pdc ;
		
		horizontalList = new ArrayList<ArrayList<String>>() ;
		verticalList = new ArrayList<ArrayList<String>>() ;
		squareList = new ArrayList<ArrayList<String>>() ;
		//对比标准
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
		
		
		//得到sukodu信息
		this.numList = numList ;
		this.timer = timer ;
		
		//读出数独盘面信息
		sortSudoku() ;
		//横排是否符合1-9不重复
		for(int i=0;i<9;i++){
			horizontal = horizontalList.get(i).containsAll(rightList) ;
			//
//			for(String str : rightList){
//System.out.println("rightList : "+str) ; 
//			}
//System.out.println("horizontal" + horizontal + ":" + i) ;
			if(!horizontal){
				handler_count = i ;
				//错误提示
				_handler.post(new Runnable(){
					@Override
					public void run(){
						Toast.makeText(_context, "第"+(handler_count+1)+"行"+"错了啊，亲", 1500).show() ;
					}
				}) ;
				//取消dialog
				_handler.post(run) ;
				return false ;
			}
		}
		for(int i=0;i<9;i++){
			vertical = verticalList.get(i).containsAll(rightList) ;
//System.out.println("vertical" + vertical + ":" + i) ;
			if(!vertical){
				handler_count = i ;
				//错误提示
				_handler.post(new Runnable(){
					@Override
					public void run(){
						Toast.makeText(_context, "第"+(handler_count+1)+"列"+"错了啊，亲", 1500).show() ;
					}
				}) ;
				//取消dialog
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
				//错误提示
				_handler.post(new Runnable(){
					@Override
					public void run(){
						Toast.makeText(_context, "第"+(handler_count+1)+"个九宫格"+"错了啊，亲", 1500).show() ;
					}
				}) ;
				//取消dialog
				_handler.post(run) ;
			
				return false ;
			}
		}
		//取消dialog
		_handler.post(run) ;
		
		//得到SharedPerference，用以存储数据，这个数据用来存储玩家玩过的关卡成绩
		shared = this._context.getSharedPreferences("passGame", Activity.MODE_PRIVATE) ;
		editor = shared.edit() ;
		//得到本次获得星星数量
		float rating = timer.getRating() ;
		float newRating = rating ;
System.out.println("CheckSudoku 111 本次rating:"+rating) ;
		//得到原来存储的信息
		float oldRating = shared.getFloat(STAGE_SCORE+stage, 0) ;
		rating = rating>oldRating ? rating : oldRating ;
System.out.println("CheckSudoku 115 判断后存入的rating:"+rating+" 位置："+STAGE_SCORE+stage) ;
		//存入最大的stage信息
		editor.putFloat(STAGE_SCORE+stage, rating) ;
		editor.commit() ;
		
		
		//得到SharedPerference，用以存储数据，这个数据用来辨认玩家通过到哪一关了
		//得到原来存储的信息
		int oldStage = shared.getInt(MAX_STAGE, 0) ;
		stage = stage>oldStage ? stage : oldStage ;
System.out.println("checksudoku line 176"+stage) ;
		//存入最大的stage信息
		editor.putInt(MAX_STAGE, stage) ;
		editor.commit() ;
		
		//启动进入下一关的dialog
		Intent intent = new Intent(_context,RatingActivity.class) ;
		intent.putExtra("score", newRating) ;
		intent.putExtra("stage", stage) ;
		((Activity)_context).startActivityForResult(intent, 4) ;
		return true;
	}
	
	/**
	 * 将给入的sudoku分类放入3个容器 用以检查
	 * */
	private void sortSudoku(){
		
		//竖排存储
		for(int i=1;i<10;i++){
			itemList = numList.get(i);
			verticalItem = new ArrayList<String>() ;
			for(int j=1;j<10;j++){
				verticalItem.add(itemList[j]) ;
			}
			verticalList.add(verticalItem) ;
		}
		//横排存储
		for(int i=1;i<10;i++){
			horizontalItem = new ArrayList<String>() ;
			for(int j=1;j<10;j++){
				horizontalItem.add((numList.get(j))[i]) ;
			}
			horizontalList.add(horizontalItem) ;
		}
		//九宫格存储
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
