package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Parser {
	private static ArrayList<String> expr;
	private static int iterator;
	private static String token;
	
	public static HashMap<String, String> getTags(String expression) {
		iterator = 0;
		HashMap<String, String> tags = new HashMap<String, String>();
		
		expr = Normalize((expression+" ."));
		System.out.println(expr.toString());
		if(Program(tags))
			return tags;
		
		return null;
	}
	
	private static boolean Program(HashMap<String, String> tags) {
		nextToken();
		System.out.println("token @progran: "+token);
		StatementList(tags);
		
		if(token.equals(".") && iterator == expr.size())
			return true;
		
		return false;
	}
	
	private static boolean StatementList(HashMap<String, String> tags) {
		boolean valid = Statement(tags);
		if(!valid)
			return false;
		valid = NextStatement(tags);
		if(valid)
			return true;
		
		return false;
	}
	
	private static boolean Statement(HashMap<String, String> tags) {
		String id = token;
		nextToken();
		System.out.println("token @Statement: "+token);
		if(token.equals("=")) {
			nextToken();
			System.out.println("token @Statement: "+token);
			String value = token;
			if(tags.containsKey(id)) {
				String old_value = tags.get(id);
				tags.put(id, old_value+";"+value);
			}else {
				tags.put(id, value);
			}
			
			nextToken();
			System.out.println("token @Statement: "+token);
			return true;
		}
		return false;
	}
	
	private static boolean NextStatement(HashMap<String, String> tags) {
		boolean valid;
		if(token.equals(",")) {
			nextToken();
			System.out.println("token @NextStatement: "+token);
			return StatementList(tags);
		}else if(token.equals(".")) {
			return true;
		}
		return false;
	}
	
	private static ArrayList<String> Normalize(String expression) {
		ArrayList<String> list = new ArrayList<>(); 
		int begining = 0;
		for(int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			int end = expression.length();
			if(c == '=' || c == ',' || c == '.') {
				end = i;
				list.add((expression.substring(begining, end)).strip());
				begining = i+1;
				list.add(String.valueOf(c));
			}
		}
		return list;
	}
	private static void nextToken() {
		if(iterator < expr.size()) {
			token = expr.get(iterator++);
		}else {
			token = ".";
		}
	}
	

}
