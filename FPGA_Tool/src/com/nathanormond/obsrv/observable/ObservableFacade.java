package com.nathanormond.obsrv.observable;

import java.util.Observer;

public class ObservableFacade extends ObservableReferences {
	
	/**
	 * adds an observer to a variable
	 * @param varName
	 * @param obs
	 */
	public static void addObserver(String varName, Observer obs) {
		for (ObservableVariable<?> observable : observables) {
			if (observable.toString().equals(varName)) {
				observable.addObserver(obs);
				break;
			}
		}
	}

	/**
	 * sets a variable value
	 * @param varName
	 * @param newValue
	 */
	public static <T> void setValue(String varName, T newValue) {
		for (ObservableVariable observable : observables) {
			if (observable.toString().equals(varName)) {
				observable.setVariable(newValue);
				break;
			}
		}
	}
	
	/**
	 * variable value for name
	 * @param varName
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String varName) {
		for (ObservableVariable<?> observable : observables) {
			if (observable.toString().equals(varName)) {
				return (T) observable.getVariable();
			}
		}
		return null;
	}

	/**
	 * number of observers
	 * @return observersCount
	 */
	public static int getObserversCount() {
		int observerCount = 0;
		for (ObservableVariable<?> observable : observables) {
			observerCount = observerCount + observable.countObservers();
		}
		return observerCount;
	}

}
