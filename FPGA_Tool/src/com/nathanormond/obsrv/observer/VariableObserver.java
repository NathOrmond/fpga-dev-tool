package com.nathanormond.obsrv.observer;
import java.util.Observable;
import java.util.Observer;

public class VariableObserver<T> implements Observer {

	T variableInstance;
	String name; 
	
	public VariableObserver(String name) {
		this.name = name;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable obs, Object obj) {
		System.out.println(String.format("%s updated : %s", this.name, String.valueOf(obj)));
		setVariableInstance((T) obj);
	}
	
	public void setVariableInstance(T variableInstance) {
		this.variableInstance = variableInstance;
	}
	
	public T getVariableInstance() {
		return variableInstance;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
