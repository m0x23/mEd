/*
 * mEd v0.4 - a lightweight text-editor written in Java
 * author - m0x23
 * m0x23
 * 2015-11-14
 * 
 * TODOlist
 * 		DONE - contextMenu 
 * 		DONE - add undo/redo function
 * 		DONE - dark theme
 * 		DONE - switch between sans and monospaced font
 * 		TODO - configfile
 * 		TODO - set nemonic index and make runnable with Alt-Key
 */


// Package
package ed;

public class Main
{
	public static void main(String[] args)
	{
		// print LaunchINFO
		System.out.println("mEd v0.4 executed");
		//create and show the mEd
		new Window();		
	}
}
