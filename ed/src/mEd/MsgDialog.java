/*
 * MsgDialog class to show a information dialog
 * author - m0x23
 * 2015-11-15
 * 
 * 
 * usage: 
 * MsgDialog.create(CONTENT, TITLE)
 */

package mEd;

import javax.swing.JOptionPane;

public class MsgDialog extends JOptionPane
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
	}
	public static void create(String content, String title)
	{
		showMessageDialog(null, content, title, INFORMATION_MESSAGE);
		System.out.println("messagedialog exectuted");
	}
}
