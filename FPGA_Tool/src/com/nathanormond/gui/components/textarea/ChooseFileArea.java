package com.nathanormond.gui.components.textarea;

public class ChooseFileArea extends AbstractTextArea {

	private static final long serialVersionUID = 1L;
	public static String placeholder;

	public ChooseFileArea(String placeholder) {
		super(placeholder, false);
		ChooseFileArea.placeholder = placeholder;
	}

	@Override
	public boolean isCorrectFormat() {
		return !getText().equals(AbstractTextArea.defaultText);
	}
	
	@Override
	public void mouseClickBehaviours() {
		// not used as area not editable	
	}

	@Override
	public String toolTipText() {
		return "<html>Select a file in file system <br>file path displayed in this text area.</html>";
	}

	
}