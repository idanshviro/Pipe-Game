package state;

import solver.Action;

public class State<T> implements Comparable<State<T>>{
	
	private T state;
	private State<T> cameFrom;
	private int hash;
	private Action action;
	private double cost = 0;
	
	
	public State(T state) {
		this.state = state;
		hash = state.hashCode();
	}
	
	public State(State<T> s) {
		this.state = s.state;
		this.cameFrom = s.cameFrom;
		this.hash = s.hash;
		this.action = s.action;
		this.cost = s.cost;
	}

	public T getState() {
		return state;
	}
	public void setState(T state) {
		this.state = state;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public boolean equals(Object s) {
		if (s instanceof State) {
			return (this.hash == s.hashCode());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return hash;
	}
	@Override
	public String toString() {
		return state.toString();
	}
	
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	
	@Override
	public int compareTo(State<T> o) {
		return (Double.compare(this.cost, o.cost));
	}

	public void setAction(int x, int y, int num) {
		this.action.setX(x);
		this.action.setY(y);
		this.action.setNumOfActions(num);
	}

}