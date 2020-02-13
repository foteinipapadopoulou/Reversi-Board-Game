
public class Move 
{
	
	
	private int row,col,value,evaluation;
	
	public Move()
	{
		row=-1;
		col=-1;
		value=0;
		evaluation=0;
	}
	public Move(Move move){
		this.row=move.row;
		this.col=move.col;
		this.value=move.value;
		this.evaluation=move.evaluation;
	}
	public Move(int row,int col)
	{
		this.row=row;
		this.col=col;
		value=0;
		evaluation=0;
	}
	
	public Move(int row,int col,int value)
	{
		this.row=row;
		this.col=col;
		this.value=value;
		this.evaluation=0;
	}
	public Move(int row,int col,int value,int evaluation){
		this.row=row;
		this.col=col;
		this.value=value;
		this.evaluation=evaluation;
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	public int getEvaluation() {
		return evaluation;
	}

	public void setEvaluate(int evaluation) {
		this.evaluation = evaluation;
	}
	

	
}
