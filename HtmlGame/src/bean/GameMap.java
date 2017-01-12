package bean;

import java.util.HashMap;
import java.util.Map;

public class GameMap {

	private static Map<String, String> gameMap;
	
	public static Map<String, String> getMap()
	{
		if(gameMap==null)
		{
		gameMap=new HashMap<String, String>();
		}
		gameMap.put("jysdbydr", "../fishjoy/index.html");
		gameMap.put("jysdfdgg", "../flyhigher/index.html");
		gameMap.put("jysdmsd", "../isoland/index.html");
		gameMap.put("jysdqmfjdz", "../nationalwarplanes/index.html");
		gameMap.put("jysdzjdb", "../zhengjiudabing/index.html");
		
		return gameMap;
	}
	
}
