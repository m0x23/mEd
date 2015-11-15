package ed;

import javax.swing.JOptionPane;

public class YesNoDialog
{

	public static void main(String[] args)
	{
	}

	public static int create(String content, String title)
	{
		int returnvar;
		// open the dialog
		returnvar = JOptionPane.showConfirmDialog(null, content, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return returnvar;
		
		// usage:
		// if YesNoDialog.create() == 
		// JOptionPane.NO_OPTION
		// JOptionPane.YES_OPTION
		// JOptionPane.CLOSED_OPTION
	}

}
