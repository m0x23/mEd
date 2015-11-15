package ed;

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
		return returnvar;
		
		// usage:
		// if YesNoDialog.create() == 
		// YesNoDialog.NO_OPTION
		// YesNoDialog.YES_OPTION
		// YesNoDialog.CLOSED_OPTION
	}

}
