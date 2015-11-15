/*
 * mEd v0.5 - a lightweight text-editor written in Java
 * author - m0x23
 * 2015-11-14
 * 
 * TODOlist
 * 		DONE - contextMenu 
 * 		DONE - add undo/redo function
 * 		DONE - dark theme
 * 		DONE - switch between sans and monospaced font
 * 		TODO - configfile
 * 		TODO - set nemonic index and make runnable with Alt-Key
 * 		TODO - insert statusbar to show linenumbers and filedetails
 * 		DONE - replace GER comments to ENG to improve global usage
 * 		TODO - implement syntax highlighting mode using bobbylight/RSyntaxTextArea
 */

// Package
package mEd;

public class Main
{
	static String version = "mEd v0.5";
	public static void main(String[] args)
	{
		// print name and version
		System.out.println(version + " executed");
		//create and show the mEd
		new Window();		
	}
}
