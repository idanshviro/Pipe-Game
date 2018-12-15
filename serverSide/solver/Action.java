package solver;

public class Action {
	private int x;
	private int y;
	private int numOfActions;
	private int hash;

	public Action(int x , int y) {
		this.x = x;
		this.y = y;
		numOfActions = 1;
		hash = hashHelper();
	}

	public Action(int x , int y,int num) {
		this.x = x;
		this.y = y;
		this.numOfActions = num;

	}
	public void incNumOfActions(){
		numOfActions++;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getNumOfActions() {
		return numOfActions;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setNumOfActions(int numOfActions) {
		this.numOfActions = numOfActions;
	}

	@Override
	public String toString() {
		return this.y + "," + this.x + "," + numOfActions;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Action) {
			return (this.hash==obj.hashCode());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return hash;
	}

	public int hashHelper() {
		hash = 31*x+11*y;
		return hash;
	}

}
