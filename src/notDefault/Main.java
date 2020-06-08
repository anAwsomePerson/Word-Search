package notDefault;
import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader input;
    public static PrintWriter output;
    
    public static void main(String[] args) throws IOException{
        input = new BufferedReader(new FileReader("words.txt"));
        output = new PrintWriter(new BufferedWriter(new FileWriter("answer.txt")));
        stuff();
        input.close();
        output.close();
    }
	
    public static void stuff() throws IOException{
    	ArrayList<String> words = new ArrayList<String>();
        String word = input.readLine();
        
        while(word != null) {
        	word = word.trim().toLowerCase();
        	
        	if(word.length() > 0) {
        		words.add(word);
        	}
        	
        	word = input.readLine();
        }
        
        input.close();
        sortList(words);
        
        int side = words.get(0).length();
        char[][] answer = new char[side][side];
        ArrayList<String> wordsCopy = new ArrayList<String>(words);
        
        while(wordsCopy.size() > 0) {
        	side++;
        	answer = new char[side][side];
        	wordsCopy = new ArrayList<String>(words);
        	
        	for(int a = 0; a < side; a++) {
        		for(int b = 0; b < side; b++) {
        			answer[a][b] = ' ';
        		}
        	}
        	
        	while(true) {
        		word = wordsCopy.remove(0);
        		ArrayList<Position> positions = new ArrayList<Position>();
        		
        		for(int a = 0; a < side; a++) {
        			for(int b = 0; b < side; b++) {
        				if(word.length() + a < side) {
        					positions.add(new Position(a, b, 0));
        				}
        				
        				if(word.length() + a < side && word.length() + b < side) {
        					positions.add(new Position(a, b, 1));
        				}
        				
        				if(word.length() + b < side) {
        					positions.add(new Position(a, b, 2));
        				}
        			}
        		}
        		
        		while(positions.size() > 0) {
        			Position position = positions.remove((int)(positions.size() * Math.random()));
        			boolean willContinue = false;
        			
        			if(position.angle == 0) {
        				for(int a = 0; a < word.length(); a++) {
            				if(!canOverwrite(answer[position.row + a][position.column], word.charAt(a))) {
            					willContinue = true;
            				}
            			}
        				
        				if(willContinue) {
        					continue;
        				}
        				
        				for(int a = 0; a < word.length(); a++) {
            				answer[position.row + a][position.column] = word.charAt(a);
            			}
        			}else if(position.angle == 1) {
        				for(int a = 0; a < word.length(); a++) {
        					if(!canOverwrite(answer[position.row + a][position.column + a], word.charAt(a))) {
            					willContinue = true;
            				}
            			}
        				
        				if(willContinue) {
        					continue;
        				}
        				
        				for(int a = 0; a < word.length(); a++) {
            				answer[position.row + a][position.column + a] = word.charAt(a);
            			}
        			}else if(position.angle == 2) {
        				for(int a = 0; a < word.length(); a++) {
        					if(!canOverwrite(answer[position.row][position.column + a], word.charAt(a))) {
            					willContinue = true;
            				}
            			}
        				
        				if(willContinue) {
        					continue;
        				}
        				
        				for(int a = 0; a < word.length(); a++) {
            				answer[position.row][position.column + a] = word.charAt(a);
            			}
        			}else {
        				System.out.println("angle == " + position.angle);
        			}
        			
        			break;
        		}
        		
        		//System.out.println("reached1 " + wordsCopy.size());
        		if(wordsCopy.size() < 1) {
        			break;
        		}
        	}
        }
        
        for(int a = 0; a < side; a++) {
    		for(int b = 0; b < side; b++) {
    			output.print(answer[a][b] + ",");
    			/*if(out[a][b] == ' ') {
    			    output.print((char)(26 * Math.random() + 97));
    			}else {
    				
    			}*/
    		}
    		
    		output.print('\n');
    	}
        
        output.close();
        input = new BufferedReader(new FileReader("answer.txt"));
        output = new PrintWriter(new BufferedWriter(new FileWriter("puzzle.txt")));
        
        for(int a = 0; a < side; a++) {
        	String line = input.readLine();
        	
        	for(int b = 0; b < line.length(); b++) {
        		if(line.charAt(b) == ' ') {
    			    output.print((char)(26 * Math.random() + 97));
    			}else {
    				output.print(line.charAt(b));
    			}
        	}
        	
        	output.print('\n');
        }
    }
    
    public static boolean canOverwrite(char a, char b) {
    	return(a == ' ' || b == ' ' || a == b);
    }
    
    public static void sortList(ArrayList<String> list){
    	for(int i = list.size() - 1; i >= 0; i --){
    		trickleList(list, i, 0);
    	}
    	
    	for(int i = 1; i < list.size(); i ++){
    		swapList(list, 0, list.size() - i);
    		trickleList(list, 0, i);
    	}
    }
    
    public static void trickleList(ArrayList<String> list, int target, int sorted){
    	while(2 * target + 1 < list.size() - sorted){
    		//System.out.println(target);
    		
    		if((list.get(target).length() <= list.get(2 * target + 1).length()) && (list.size() - sorted <= 2 * target + 2)){
    			return;
    		}
    		
    		if((list.get(target).length() <= list.get(2 * target + 1).length()) 
    				&& (list.get(target).length() <= list.get(2 * target + 2).length())){
    			return;
    		}
    		
    		if(2 * target + 2 >= list.size() - sorted){
    			swapList(list, target, 2 * target + 1);
    			target = 2 * target + 1;
    			continue;
    		}
    		
    		if(list.get(2 * target + 1).length() <= list.get(2 * target + 2).length()){
    			swapList(list, target, 2 * target + 1);
    			target = 2 * target + 1;
    		}else{
    			swapList(list, target, 2 * target + 2);
    			target = 2 * target + 2;
    		}
    	}
    }
    
    public static void swapList(ArrayList<String> list, int i, int j) {
        String temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
