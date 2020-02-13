
import java.util.*;
import java.io.*;
public class TestMain 
{	
	static PrintStream out=new PrintStream(System.out);
	static Scanner in=new Scanner(System.in);
	public static void main(String[] args)
	{	
		GameBoard start=new GameBoard(); //Initialization of our board
		start.PrintBoard();//prints our board
		out.println("Would you like to play first?\ny->yes!\nn->No!"); //Ask the player
		String answer=in.next(); //read answer
		while(!answer.equals("n") && !answer.equals("y"))//valid input check
		{
			out.println("Invalid input, please try again ");
			answer=in.next();
		}
		int depth=0;
		while(true) {
			out.println("Give depth ");
			depth=read();
			if(depth>0) break;			
		}
		out.println("Player 1 enter your name");
		//Create 2 players
		Player player1=new Player(in.next(),(GameBoard.black),1);//human player,always black
		Player player2=new Player("CPU",(GameBoard.white),depth);//computer player,always white
		//Available moves
		ArrayList<Move> pos_moves;
		Move move;
		/*Initialization of the first player
		 * if human player chooses to play first ,then 
		 * we set last letter played to white
		 */
		if(answer.equals("y")) start.setLastLetterPlayed(GameBoard.white);
		//while the game hasn't reached its end,then players must make their move
		while(!start.IsTerminal()) {
			switch (start.getLastLetterPlayed())
			{
                //If white played last, then black plays now
				case GameBoard.white:
                    out.println("Black moves");
                    //Store the possible moves of black 
                    pos_moves=start.PossibleMoves(GameBoard.black);
                    if(!pos_moves.isEmpty()) {
                    	//Shows possible moves
                    	out.println("Possible moves:");
                    	for(Move m:pos_moves){
   						 out.println("Row: "+(m.getRow()+1)+",Column: "+(m.getCol()+1));
   					 	}
                    	//Asks for a valid move
                    	while(true) {
		                    out.println("Row?:");
		            		int x=read();
		            		out.println("Column?:");
		            		int y=read();
		            		/*
		            		 * We number our rows and columns from 0 to 7 
		            		 * but we represent to the player from 1 to 8
		            		 * so we subtract them by one to pass them in our methods
		            		 */
		            		move=new Move(--x,--y,player1.getDiskType());
		            		//break when we find a valid move
		            		if(start.isValidMove(move,GameBoard.black)) {
		            			break;
		            		}
		            		out.println("Invalid input.Please try again!");
	            		}
                    	//make the move into the board 
                    	start.MakeMove(move);
                    	start.PrintBoard();
	            		start.getScore();
	            		start.setLastLetterPlayed(GameBoard.black);
                    }else{
                    	out.println("No possible moves for black");
                    	start.setLastLetterPlayed(GameBoard.black);
                    }
	            		
					break;
                //If black played last, then white plays now
				case GameBoard.black:
					 pos_moves=start.PossibleMoves(GameBoard.white);
					
	                    if(!pos_moves.isEmpty()){
	                    	out.println("White moves");
		                    start.setLastLetterPlayed(GameBoard.white);
		                    Move move1=player2.MiniMax(start);
		                    //System.out.println("Col= " +move1.getCol()+" Row= "+move1.getRow()+" Value = "+move1.getValue());
		                    start.MakeMove(move1);
		                    start.PrintBoard();
		            		start.getScore();
		            		start.setLastLetterPlayed(GameBoard.white);
	                    }else{
	                    	out.println("No possible moves for white");
	                    	start.setLastLetterPlayed(GameBoard.white);
	                    }
	                    
						break;
				default:
					break;
			}
		}
		String winner=(start.getBlackScore()>start.getWhiteScore())?player1.getName():"Computer";
		if(start.getBlackScore()==start.getWhiteScore()) {
			out.println("Draw!");
		}else out.println(winner+" wins!!!!");
		out.close();
		in.close();

	}
	
	private static int read() {
		String s;
		while(true)
		{
			s=in.next();
			if(isStringInt(s))return Integer.parseInt(s);
			out.println("Please enter an integer");
		}
	}

	private static boolean isStringInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
}
