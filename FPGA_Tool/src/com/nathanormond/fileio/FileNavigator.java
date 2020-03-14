package com.nathanormond.fileio;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class FileNavigator {

	private URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
	private String initalFilePath;

	public FileNavigator() {
		try {
			initalFilePath = URLDecoder.decode(url.getFile(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}

	public String selectPath() {
		JFileChooser jfc = new JFileChooser(initalFilePath);
		int returnValue = jfc.showOpenDialog(null);
		return (returnValue == JFileChooser.APPROVE_OPTION) ? jfc.getSelectedFile().getAbsolutePath() : "choose file";
	}

	public String selectFolder() {
		JFileChooser jfc = new JFileChooser(initalFilePath);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		int returnValue = jfc.showOpenDialog(null);
		File selectedFile = FileSystemView.getFileSystemView().getHomeDirectory();

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = jfc.getSelectedFile();
		}
		return selectedFile.getAbsolutePath();
	}
	
	public File selectFile() { 
		return new File(selectPath());
	}

	
}
