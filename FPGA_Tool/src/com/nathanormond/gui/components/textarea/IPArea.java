package com.nathanormond.gui.components.textarea;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import com.nathanormond.obsrv.observable.ObservableFacade;


public class IPArea extends AbstractTextArea{

	private static final long serialVersionUID = 1L;
	private static String defaultText = "IP address";

	public IPArea() {
		super(defaultText, true);
	}

	/**
	 * 
	 */
	@Override
	public boolean isCorrectFormat() {
		if(validate(getText())) { 
			ObservableFacade.setValue("ipAddr", getText());
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	/**
	 * 
	 * @param ip
	 * @return
	 */
	private static boolean validate(final String ip) {
	    return PATTERN.matcher(ip).matches();
	}

	/**
	 * 
	 */
	@Override
	public void mouseClickBehaviours() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (getText().equals(defaultText)) {
					setText("");
				}
			}
		});

	}

	/**
	 * 
	 */
	@Override
	public String toolTipText() {
		return "<html>Ensure IP address is correct <br>"
			 + "then press \"SET IP\" button to change. </html>";
	}
}