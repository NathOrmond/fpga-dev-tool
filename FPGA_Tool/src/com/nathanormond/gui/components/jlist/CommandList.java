package com.nathanormond.gui.components.jlist;

import java.awt.Font;
import java.util.List;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

public abstract class CommandList extends JList<Object> implements ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	private int visibleRowCount = 4;
	public CommandList(List<String> commands) {
		super(commands.toArray());
		setListGraphicsAndStyling();
	}
	
	private void setListGraphicsAndStyling(){ 
		Font f = new Font("Arial", Font.PLAIN, 20);
		setFont(f);
		setVisibleRowCount(visibleRowCount);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addListSelectionListener(this);
	}
	
	public JList<Object> getJList(){ 
		return this;
	}
	
}