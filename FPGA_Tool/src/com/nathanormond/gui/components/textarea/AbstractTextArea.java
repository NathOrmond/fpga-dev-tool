package com.nathanormond.gui.components.textarea;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

public abstract class AbstractTextArea extends JTextArea {

	private static final long serialVersionUID = 1L;
	private static int width = 10;
	private static int height = 1;
	private boolean fieldCompleted = false;
	public static String defaultText;
	private boolean firstPass = true;
	Color c;
	
	public AbstractTextArea(String defaultText, boolean editable) {
		super(height,width);
		AbstractTextArea.defaultText = defaultText;
		commandAreaFunctionality();
		setEditable(editable);
		setVisible(true);
	}
	
	public void commandAreaFunctionality() { 
		placeHolder();
		mouseClickBehaviours();
		validityCheck();
		styleArea();
	}
	
	
	private void styleArea(){ 
		Font f = new Font("Arial", Font.PLAIN, 15);
		setFont(f);
		setToolTipText(toolTipText());
	}
	
	public abstract String toolTipText();
	
	public void validityCheck() { 
		setBackGroundColour(isCorrectFormat());
		setBackground(c);
	}
	
	public abstract boolean isCorrectFormat();
	
	/**
	 * adds placeholder into text field if required
	 */
	private void placeHolder() { 
		if(firstPass) { 
			setText(defaultText);
			firstPass = false;
		}
	}
	
	/**
	 * removes text in field upon click
	 */
	public abstract void mouseClickBehaviours();
	
	
	/**
	 * Background Colour Check red for invalid user input green for valid.
	 */
	private void setBackGroundColour(boolean condition) {
		if (condition) {
			c = new Color(40, 180, 10); /* GREEN */
			setFieldCompleted(true);
		} else {
			c = new Color(255, 60, 0); /* RED */
			setFieldCompleted(false);
		}
	}

	/*******************************************************
	 * GETTERS / SETTERS
	 *******************************************************/

	public boolean isFieldCompleted() {
		return fieldCompleted;
	}

	public void setFieldCompleted(boolean fieldCompleted) {
		this.fieldCompleted = fieldCompleted;
	}

	public JTextArea getArea() {
		return this;
	}
	
}