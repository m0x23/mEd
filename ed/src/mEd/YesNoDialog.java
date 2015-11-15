/*
 * YesNoDialog class to show a confirm dialog
 * author - m0x23
 * 2015-11-15
 * 
 * 
 * usage: 
 * int var = YesNoDialog.create(CONTENT, TITLE)
 * 
 * 
 * if (var == YesNoDialog.NO_OPTION) do something;
 * if (var == YesNoDialog.YES_OPTION) do something;
 * if (var == YesNoDialog.CLOSED_OPTION) do something
 */

package mEd;

import javax.swing.JOptionPane;

public class YesNoDialog extends JOptionPane
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
	}

	public static int create(String content, String title)
	{
		int returnvar;
		// open the dialog
		returnvar = showConfirmDialog(null, content, title, YES_NO_OPTION, QUESTION_MESSAGE);
		System.out.println("yesnodialog executed");

		// print the result to terminal
		if(returnvar == 0)
		{
			System.out.println("selection: yes");
		}
		else
		{
			System.out.println("selection: no");
		}
		return returnvar;
	}

}
