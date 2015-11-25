/*
 * MsgDialog class to show a information dialog
 * author - m0x23
 * 2015-11-15
 * 
 * 
 * usage: 
 * MsgDialog.createDialog(String CONTENT, String TITLE, String <"INFO" OR "ERROR" OR "WARNING">)
 */

package mEd;

import javax.swing.JOptionPane;

public class MsgDialog extends JOptionPane
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
	}
	
	public static void createDialog(String content, String title, String type)
	{
		// check if allowed type paramameter was given
		if(type == "INFO" || type == "ERROR" || type == "WARNING") System.out.println("messagedialog exectuted");
		
		
		if(type == "INFO")
		{
			showMessageDialog(null, content, title, INFORMATION_MESSAGE);
		}
		else if(type == "ERROR")
		{
			showMessageDialog(null, content, title, ERROR_MESSAGE);
		}
		else if(type == "WARNING")
		{
			showMessageDialog(null, content, title, WARNING_MESSAGE);
		}
	}
}
