package com.nathanormond.gui.components.textarea;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

public class NumberTextArea extends JTextArea {


	private static final long serialVersionUID = 1L;

	
	public NumberTextArea(String placeholder) {
		setColumns(placeholder.length() + 2);
		setText(placeholder);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (getText().equals(placeholder)) {
					setText("");
				}
			}
		});
	}

	/**
	 * sorry
	 * @return
	 */
	public boolean isContentInteger(){ 
		
		setRedBackGround();
		
		try{ 
			Integer.parseInt(getText());
		} catch(NumberFormatException e) { 
			return false;
		}catch(NullPointerException e) { 
			return false;
		}
		
		setGreenBackground();
		
		return true;
	}
	
	public void setRedBackGround(){ 
		Color c = new Color(255, 60, 0);
		setBackground(c);
	}
	
	public void setGreenBackground(){ 
		Color c = new Color(40, 180, 10);
		setBackground(c);
	}
	
}