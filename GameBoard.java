import java.util.*;



public class GameBoard 
{
	public static final int[][] posValue= initScoreBoard();
	private int WhiteScore=2;
	private int BlackScore=2;
	private int Pos_valuesB;
	private int Pos_valuesW;
	
	public static final int  white=1;
	public static final int black=2;
	private static final int empty=0;
	private int Board[][]=new int[8][8];	
	private int lastLetterPlayed;
	private Move lastMove;
	public GameBoard()//Default constructor
	{
		for(int i=0;i<8;++i)
		{
			for(int j=0;j<8;++j)
			{
				Board[i][j]=empty;
			}
		}
		lastLetterPlayed=black;
		Board[3][3]=white;
		Board[3][4]=black;
		Board[4][3]=black;
		Board[4][4]=white;
		Pos_valuesB=0;
		Pos_valuesW=0;
		
	}
	
	public int getWhiteScore() {
		return WhiteScore;
	}

	public int getBlackScore() {
		return BlackScore;
	}

	public int getLastLetterPlayed() {
		return lastLetterPlayed;
	}

	public void setLastLetterPlayed(int lastLetterPlayed) {
		this.lastLetterPlayed = lastLetterPlayed;
	}	
	
	public GameBoard(GameBoard other)//Copy Constructor
	{
		for(int i=0;i<8;++i)
		{
			for(int j=0;j<8;++j)
			{
				this.Board[i][j]=other.Board[i][j];
			}
		}
		this.WhiteScore=other.WhiteScore;
		this.BlackScore=other.BlackScore;
		this.Pos_valuesB=other.Pos_valuesB;
		this.Pos_valuesW=other.Pos_valuesW;
		this.lastLetterPlayed=other.lastLetterPlayed;
	}
	public Move getLastMove()
	{
		return lastMove;
	}
	public void setLastMove(Move lastMove)
	{
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	/* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
	public ArrayList<GameBoard> getChildren(int type)
	{
		ArrayList<GameBoard> children = new ArrayList<GameBoard>();
		GameBoard child;
		ArrayList<Move> p=PossibleMoves(type);
		
		for(int i=0;i<p.size();++i){
			child = new GameBoard(this);
			child.MakeMove(p.get(i));
			child.setLastMove(p.get(i));
			children.add(child);
			
		}
		
		return children;
	}
	
	public void PrintBoard()
	{
		String what_to_print = null;
		System.out.print("  ");
		for(int i=1;i<9;i++){
			
			System.out.print(i+" ");
		}
		System.out.println();
		for(int i=0;i<8;++i)
		{	
			System.out.print(i+1);
			for(int j=0;j<8;j++)
			{	
				if(Board[i][j]==white)
				{
					what_to_print="|W";
				}else if(Board[i][j]==black)
				{
					what_to_print="|B";
				}else if (Board[i][j]==empty)
				{
					what_to_print="| ";
				}
				System.out.print(what_to_print);
				if(j==7)System.out.print("|\n");
			}
		}
	}
	
	public void MakeMove(Move move) 
	{	
		//Omnidirectional check			
		for(int i=-1;i<2;++i) {
			for(int j=-1;j<2;++j) {
				CheckDirectional(move,i,j,move.getValue());
			}
		}
		
		lastMove = new Move(move);
		lastMove.setEvaluate(evaluate());
		setLastMove(lastMove);
	}
	public boolean isValidMove(Move move,int type) {
		
	
		if(IsOutOfBounds(move.getRow(),move.getCol())||Board[move.getRow()][move.getCol()]!=empty) {
			return false;
		}
		for(int i=-1;i<2;++i) {
			for(int j=-1;j<2;++j) {
				if(CheckMove(move,i,j,type)){ 
					return true;}
			}
			
		}
		return false;
	}

	public ArrayList<Move> PossibleMoves(int type) {
		ArrayList<Move> Pos_Moves=new ArrayList<Move>();
		Move move;
		for(int i=0;i<8;++i) {
			for(int j=0;j<8;++j) {
				move=new Move(i,j,type);
				if(isValidMove(move,type)) {
					Pos_Moves.add(move);
				}
			}
		}
		return Pos_Moves;
	}
	
	private void color(Move move, int stepi, int stepj) {
		int i=move.getRow();
		int j=move.getCol();
		int type=Board[i][j];
		while(true)
		{	
			i+=stepi;
			j+=stepj;
			if(Board[i][j]==type)break;
			Board[i][j]=type;
			if(type==black)
			{
				--WhiteScore;
				++BlackScore;
				Pos_valuesB+=posValue[i][j];
				Pos_valuesW-=posValue[i][j];
			}else{
				--BlackScore;
				++WhiteScore;
				Pos_valuesB-=posValue[i][j];
				Pos_valuesW+=posValue[i][j];
			}
		}
	}

	private void CheckDirectional(Move move,int stepi,int stepj,int type)
	{		
		int i=move.getRow();
		int j=move.getCol();		
		if(IsOutOfBounds(i+stepi, j+stepj)||Board[i+stepi][j+stepj]==empty 
				||Board[i+stepi][j+stepj]==type)return;		
		while(true)
		{
			i+=stepi;
			j+=stepj;
			if(IsOutOfBounds(i, j)||Board[i][j]==empty)	return;
			if(Board[i][j]==type){
				if(Board[move.getRow()][move.getCol()]!=type)
				{
					Board[move.getRow()][move.getCol()]=type;
					if(type==black){
						++BlackScore;
						Pos_valuesB+=posValue[move.getRow()][move.getCol()];
					}else{
						++WhiteScore;
						Pos_valuesW+=posValue[move.getRow()][move.getCol()];
					}
				}
				color(move,stepi,stepj);
				return;
			}
		}
	}

	private boolean CheckMove(Move move,int stepi,int stepj,int type)
	{		
		int i=move.getRow();
		int j=move.getCol();		
		if(IsOutOfBounds(i+stepi, j+stepj)||Board[i+stepi][j+stepj]==empty 
				||Board[i+stepi][j+stepj]==type)return false;	
		while(true)
		{
			i+=stepi;
			j+=stepj;
			if(IsOutOfBounds(i, j)||Board[i][j]==empty)	return false;
			if(Board[i][j]==type)return true;
		}
	}
	
	public void getScore(){System.out.println("White Score: "+WhiteScore+" Black Score: "+BlackScore);} 
	
	private boolean IsOutOfBounds(int i,int j) 
	{
		if(i<0||i>7||j<0||j>7)return true;
		return false;
	}
	public boolean IsTerminal() {
		if(WhiteScore==0||BlackScore==0||FullBoard()) return true;
		return false;
	}

	private boolean FullBoard() {
		for(int i=0;i<8;++i){
			for(int j=0;j<8;++j) {
				if(Board[i][j]==empty)return false;
			}
		}
		return true;
	}

	public int evaluate() {
	
		return Pos_valuesW-Pos_valuesB;
	}
	
	private static int[][] initScoreBoard(){
		int[][] v=new int[8][8];
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				v[i][j]=0;
			}
		int[] list={6,8,-8,99,-3,-4,-24,4,7};
		mirror(0,-1,list,0,v);
		mirror(1,0,list,4,v);
		mirror(2,1,list,7,v);
		return v;
		
	}
	
	private static void mirror(int j,int limiti,int[] a,int start,int[][] v)
	{
		for(int i=3;i>limiti;--i){
			v[i][j]=a[start];
			v[7-i][j]=a[start];
			v[i][7-j]=a[start];
			v[7-i][7-j]=a[start];
			v[j][i]=a[start];
			v[j][7-i]=a[start];
			v[7-j][i]=a[start];
			v[7-j][7-i]=a[start];
			++start;
		}
	}

}
