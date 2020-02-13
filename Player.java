import java.util.ArrayList;
import java.util.Random;

public class Player {
	private String name;
	private final int diskType;
	private int maxDepth;
	public Player(String name,int diskType,int maxDepth)
	{
		this.maxDepth = maxDepth;
		this.name=name;
		this.diskType=diskType;
	}
	
	public Player()
	{
		maxDepth=4;
		name="Nobody";
		diskType=GameBoard.black;
	}
	public Move MiniMax(GameBoard board)
	{	
		if(diskType==GameBoard.white)return  max_value(board, 0,Integer.MIN_VALUE,Integer.MAX_VALUE );
		else return min_value(new GameBoard(board), 0,Integer.MIN_VALUE,Integer.MAX_VALUE ); 
	}
	// The max and min functions are called interchangingly, one after another until a max depth is reached
		public Move max_value(GameBoard board, int depth,int a ,int b)
		{	
	        Random r = new Random();
	        if((board.IsTerminal()) || (depth == maxDepth)){
	        	Move lastmove=new Move(board.getLastMove());
	        	return lastmove;
	        }
	        Move v=new Move();
	        v.setEvaluate(Integer.MIN_VALUE);
	        ArrayList<GameBoard> succ=new ArrayList<GameBoard>(board.getChildren(GameBoard.white));
	        for(GameBoard s:succ){
	        	Move newmove=min_value(s,depth+1,a,b);
	        	if(v.getEvaluation()<=newmove.getEvaluation()){
	        		if(v.getEvaluation()==newmove.getEvaluation()){
	        			if (r.nextInt(2)==0) {
	        				v.setCol(s.getLastMove().getCol());
		        			v.setRow(s.getLastMove().getRow());
		        			v.setValue(GameBoard.white);
		        			v.setEvaluate(s.getLastMove().getEvaluation());
	        			}
	        		}else{
	        			
	        			v.setCol(newmove.getCol());
	        			v.setRow(newmove.getRow());
	        			v.setValue(GameBoard.white);
	        			v.setEvaluate(newmove.getEvaluation());
	        		}
	        	}
	        	if(v.getEvaluation()>=b) {
	        		if(depth!=0) {
	    	        	v.setRow(board.getLastMove().getRow());
	    	        	v.setCol(board.getLastMove().getCol());
	    	        }
	        		return v;
	        	}
	        	a=max(a,v.getEvaluation());
	        }
	        if(depth!=0) {
	        	v.setRow(board.getLastMove().getRow());
	        	v.setCol(board.getLastMove().getCol());
	        }
	       
	        return v;
	        
		}

	    //Min works similarly to max
		public Move min_value(GameBoard board, int depth,int a,int b)
		{	
			Random r = new Random();
	        if((board.IsTerminal()) || (depth == maxDepth)){
	        	Move lastmove=new Move(board.getLastMove());
	        	return lastmove;
	        }
	        Move v=new Move();
	        v.setEvaluate(Integer.MAX_VALUE);
	        ArrayList<GameBoard> succ=new ArrayList<GameBoard>(board.getChildren(GameBoard.black));
	        for(GameBoard s:succ){
	        	Move newmove=max_value(s,depth+1,a,b);
	        	if(v.getEvaluation()>=newmove.getEvaluation()){
	        		if(v.getEvaluation()==newmove.getEvaluation()){
	        			if (r.nextInt(2)==0) {
	        				
	        				v.setCol(newmove.getCol());
		        			v.setRow(newmove.getRow());
		        			v.setValue(GameBoard.black);
		        			v.setEvaluate(newmove.getEvaluation());
	        			}
	        		}else{
	        			
	        			v.setCol(newmove.getCol());
	        			v.setRow(newmove.getRow());
	        			v.setValue(GameBoard.black);
	        			v.setEvaluate(newmove.getEvaluation());
	        			
	        		}
	        	}
	        	if(v.getEvaluation()<=a) {
	        		if(depth!=0) {
	    	        	v.setRow(board.getLastMove().getRow());
	    	        	v.setCol(board.getLastMove().getCol());
	    	        }
	        		return v;
	        	}
	        	b=min(b,v.getEvaluation());
	        }
	        if(depth!=0) {
	        	v.setRow(board.getLastMove().getRow());
	        	v.setCol(board.getLastMove().getCol());
	        }
	        return v;
	    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public int getDiskType() {
		return diskType;
	}
	
	private int max(int a,int b){
		return (a>b)?a:b;
	}
	private int min(int a,int b){
		return (a<b)?a:b;
	}
}
