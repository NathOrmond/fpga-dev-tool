package com.nathanormond.obsrv.observable;

import java.util.Observable;
import java.util.Observer;

public class ObservableVariable<T> extends Observable{
	
	
	private String varName; 
	private T variable;
	
	public ObservableVariable(String varName) {
		this.varName = varName;
	}
	
	public T getVariable() {
		return variable;
	}
	
	public void setVariable(T newValue) {
		this.variable = newValue;
		setChanged();
		notifyObservers();
	}
	
	@Override
	public synchronized void addObserver(Observer obs) {
		System.out.println(String.format("OBSERVER ADDED : %s", obs.toString()));
		super.addObserver(obs);
	}
	
	@Override
	public void notifyObservers(Object arg) {
		super.notifyObservers();
	}
	
	@Override
	public void notifyObservers() {
		super.notifyObservers(this.variable);
	}
	
	
	@Override
	public String toString() {
		return this.varName;
	}

}
