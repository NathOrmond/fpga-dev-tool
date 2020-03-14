package com.nathanormond.runtime;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.nathanormond.obsrv.observable.ObservableReferences;

import javax.swing.UnsupportedLookAndFeelException;

/**
 * 13/11/2018
 * 
 * @author Nathan Ormond
 */
public class Main {

	/**
	 * Main Method: Invoked on programme start
	 * @param args
	 */

	public static void main(String[] args) {

		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e2) {
					e2.printStackTrace();
				}
				break;
			}
		}

		new ObservableReferences();
		new RunTimeComposer();
	}

}
