package com.nathanormond.gui.components.textarea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;

public class TrueFalseArea extends JTextArea {
	
	private static final long serialVersionUID = 1L;
	private int prefWidth = 50;
	private int prefHeight = 30;
	private Font f;

	public TrueFalseArea() {
		super("NONE");
		this.setPreferredSize(new Dimension(prefWidth, prefHeight));
		f = new Font("Arial", Font.BOLD, 12);
		this.setFont(f);
		this.setEditable(false);
		neutralStyling();
	}

	@Override
	public void setText(String t) {
		if(t.toLowerCase().equals("true")) { 
			thingsAreBad();
			super.setText("FAIL");
		} else if(t.toLowerCase().equals("false")) { 
			thingsAreOK();
			super.setText("OK");
		} else { 
			neutralStyling();
			super.setText("none");
		}
	}
	
	private void thingsAreOK() { 
		this.setBackground(Color.GREEN);
	}
	
	private void thingsAreBad() { 
		this.setBackground(Color.RED);
	}

	private void neutralStyling() { 
		this.setBackground(Color.DARK_GRAY);
	}
	
}
