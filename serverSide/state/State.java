package state;

import solver.Action;

public class State<T> implements Comparable<State<T>> {
	private T state;
	private State<T> cameFrom;
	private int hash;
	private Action action;
	private double cost=0;


	public State(T s)
	{
		this.state = s;
		this.hash = s.hashCode();
	}
	
	public State(State<T> state){
	this.state = state.state;
	this.cameFrom = state.cameFrom;
	this.hash = state.hash;
	this.action = state.action;
	this.cost = state.cost;
	}

	public T  getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object s) {
		if(s instanceof State) {
			return (this.hash == s.hashCode());
		}
		return false;
	}

	// setters and getters for cameFrom
	public State<T> getCameFrom(){
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public String toString() {
		return state.toString();
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(State<T> o) {
		return Double.compare(this.cost, o.cost);
	}

}
