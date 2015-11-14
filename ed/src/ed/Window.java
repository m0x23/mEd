/*
 * mEd v0.4 - a lightweight text-editor written in Java
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
package ed;

// Imports
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class Window extends JFrame implements ActionListener
{
	// autocreated JFrame serialVersionUID
	private static final long serialVersionUID = 1L;

	// scrollable textarea
	private JTextArea area;
	private JScrollPane scroll;

	// undomanager
	UndoManager undoManager;

	// top menu bar
	private JMenuBar menuBar = new JMenuBar();

	// menus to add to bar
	JMenu menuFile = new JMenu("File");
	JMenu menuEdit = new JMenu("Edit");
	JMenu menuHelp = new JMenu("Help");

	// items to add to menus
	JMenuItem menuItemFileOpen2 = new JMenuItem("Open");
	JMenuItem menuItemFileNew = new JMenuItem("New");
	JMenuItem menuItemFileSave = new JMenuItem("Save");
	JMenuItem menuItemFileSaveAs = new JMenuItem("Save as");
	JMenuItem menuItemFileExit = new JMenuItem("Quit");

	JMenuItem menuItemEditCopy = new JMenuItem("Copy");
	JMenuItem menuItemEditCut = new JMenuItem("Cut");
	JMenuItem menuItemEditPaste = new JMenuItem("Paste");
	JMenuItem menuItemEditDelete = new JMenuItem("Delete");
	JMenuItem menuItemEditUndo = new JMenuItem("Undo");
	JMenuItem menuItemEditRedo = new JMenuItem("Redo");
	JMenuItem menuItemEditDark = new JMenuItem("Toggle Dark");
	JMenuItem menuItemEditFont = new JMenuItem("Toggle Font");

	JMenuItem menuItemHelpHelp = new JMenuItem("Help Dialog");
	JMenuItem menuItemHelpAbout = new JMenuItem("About");

	JMenuItem contextItemCopy = new JMenuItem("Copy");
	JMenuItem contextItemCut = new JMenuItem("Cut");
	JMenuItem contextItemPaste = new JMenuItem("Paste");
	JMenuItem contextItemDelete = new JMenuItem("Delete");

	// global vars
	String openFilePath = "";
	String copiedText = "";

	// global toggle vars
	boolean isDark = false;
	boolean isMono = true;

	// contextmenu
	JPopupMenu contextMenu = new JPopupMenu("Context");

	public Window()
	{
		// set window title using superclass constructor
		super(Main.version);

		area = new JTextArea();

		// set options for textarea
		area.setWrapStyleWord(true);
		area.setLineWrap(true);
		area.setFont(new Font("monospaced", Font.PLAIN, 14));
		area.setTabSize(2);
		area.setComponentPopupMenu(contextMenu);
		area.setBackground(Color.white);
		area.setForeground(Color.black);
		area.setCaretColor(Color.black);

		scroll = new JScrollPane(area);

		// add listener for undomanager
		undoManager = new UndoManager();
		area.getDocument().addUndoableEditListener(undoManager);

		// size and layout of the window
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = (int) tk.getScreenSize().getWidth();
		int height = (int) tk.getScreenSize().getHeight();
		this.setSize(width / 2, height / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		// add menus to menubar
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuHelp);

		// add file menu items
		menuFile.add(menuItemFileNew);
		menuFile.add(menuItemFileOpen2);
		menuFile.add(menuItemFileSave);
		menuFile.add(menuItemFileSaveAs);
		menuFile.addSeparator();
		menuFile.add(menuItemFileExit);

		// set file menu keyboard shortcuts
		menuItemFileNew.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		menuItemFileOpen2.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		menuItemFileSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		menuItemFileSaveAs.setAccelerator(KeyStroke.getKeyStroke("ctrl shift S"));
		menuItemFileExit.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));

		// add edit menu items
		menuEdit.add(menuItemEditCopy);
		menuEdit.add(menuItemEditCut);
		menuEdit.add(menuItemEditPaste);
		menuEdit.add(menuItemEditDelete);
		menuEdit.addSeparator();
		menuEdit.add(menuItemEditUndo);
		menuEdit.add(menuItemEditRedo);
		menuEdit.addSeparator();
		menuEdit.add(menuItemEditDark);
		menuEdit.add(menuItemEditFont);

		// set edit menu shortcuts
		menuItemEditCopy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		menuItemEditCut.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		menuItemEditPaste.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
		menuItemEditDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		menuItemEditUndo.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		menuItemEditRedo.setAccelerator(KeyStroke.getKeyStroke("ctrl shift Z"));
		menuItemEditRedo.setEnabled(false);
		menuItemEditDark.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
		menuItemEditFont.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));

		// add help menu items
		menuHelp.add(menuItemHelpHelp);
		menuHelp.add(menuItemHelpAbout);

		// set help menu shortcuts
		menuItemHelpHelp.setAccelerator(KeyStroke.getKeyStroke("ctrl F1"));
		menuItemHelpAbout.setAccelerator(KeyStroke.getKeyStroke("ctrl F2"));

		// add context menu items
		contextMenu.add(contextItemPaste);
		contextMenu.add(contextItemDelete);
		contextMenu.add(contextItemCut);
		contextMenu.add(contextItemCopy);

		// set context menu shortcuts
		contextItemCopy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		contextItemCut.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		contextItemPaste.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));

		// add ActionListener to the items
		menuItemFileNew.addActionListener(this);
		menuItemFileOpen2.addActionListener(this);
		menuItemFileSave.addActionListener(this);
		menuItemFileSaveAs.addActionListener(this);
		menuItemFileExit.addActionListener(this);

		menuItemEditCopy.addActionListener(this);
		menuItemEditCut.addActionListener(this);
		menuItemEditPaste.addActionListener(this);
		menuItemEditDelete.addActionListener(this);
		menuItemEditUndo.addActionListener(this);
		menuItemEditRedo.addActionListener(this);
		menuItemEditDark.addActionListener(this);
		menuItemEditFont.addActionListener(this);

		menuItemHelpAbout.addActionListener(this);
		menuItemHelpHelp.addActionListener(this);

		contextItemCopy.addActionListener(this);
		contextItemCut.addActionListener(this);
		contextItemPaste.addActionListener(this);
		contextItemDelete.addActionListener(this);

		// set init color of the bars and the items
		menuBar.setBackground(Color.lightGray);
		menuFile.setForeground(Color.black);
		menuEdit.setForeground(Color.black);
		menuHelp.setForeground(Color.black);
		menuItemFileNew.setBackground(Color.lightGray);
		menuItemFileOpen2.setBackground(Color.lightGray);
		menuItemFileSave.setBackground(Color.lightGray);
		menuItemFileSaveAs.setBackground(Color.lightGray);
		menuItemFileExit.setBackground(Color.lightGray);
		menuItemEditCopy.setBackground(Color.lightGray);
		menuItemEditPaste.setBackground(Color.lightGray);
		menuItemEditCut.setBackground(Color.lightGray);
		menuItemEditDelete.setBackground(Color.lightGray);
		menuItemEditUndo.setBackground(Color.lightGray);
		menuItemEditRedo.setBackground(Color.lightGray);
		menuItemEditDark.setBackground(Color.lightGray);
		menuItemEditFont.setBackground(Color.lightGray);
		menuItemHelpHelp.setBackground(Color.lightGray);
		menuItemHelpAbout.setBackground(Color.lightGray);

		menuItemFileNew.setForeground(Color.black);
		menuItemFileOpen2.setForeground(Color.black);
		menuItemFileSave.setForeground(Color.black);
		menuItemFileSaveAs.setForeground(Color.black);
		menuItemFileExit.setForeground(Color.black);
		menuItemEditCopy.setForeground(Color.black);
		menuItemEditPaste.setForeground(Color.black);
		menuItemEditCut.setForeground(Color.black);
		menuItemEditDelete.setForeground(Color.black);
		menuItemEditUndo.setForeground(Color.black);
		menuItemEditRedo.setForeground(Color.black);
		menuItemEditDark.setForeground(Color.black);
		menuItemEditFont.setForeground(Color.black);
		menuItemHelpHelp.setForeground(Color.black);
		menuItemHelpAbout.setForeground(Color.black);

		// set menu items mnemonic button
		// menuFile.setDisplayedMnemonicIndex(0);
		// menuItemFileNew.setDisplayedMnemonicIndex(0);

		// add textarea and menubar to jframe
		this.add(scroll);
		this.setJMenuBar(menuBar);

		// make visible
		this.setVisible(true);
	}

	// saveAs function
	void saveAs()
	{
		int saveAsIsSelected;
		String saveAsFilePath = null; // path
		FileWriter fw; // filewriter object

		// select file dialog
		JFileChooser saveAsFileChoose;
		saveAsFileChoose = new JFileChooser();
		saveAsFileChoose.setDialogTitle("Save as...");
		System.out.println("saveAs executed");

		// show the dialog
		saveAsIsSelected = saveAsFileChoose.showSaveDialog(this);
		System.out.println("return:" + saveAsIsSelected);

		// if a file was selected then
		if(saveAsIsSelected == 0)
		{
			// save the path
			saveAsFilePath = saveAsFileChoose.getSelectedFile().getAbsolutePath();
			System.out.println("saveAs file to: " + saveAsFilePath);
		}
		try
		{
			// construct the fw with the selected path
			fw = new FileWriter(saveAsFilePath);
			// write to target path
			area.write(fw);
			fw.close(); // close fw
			System.out.println("file successfully saved as " + saveAsFilePath); // success
																																					// message
		}
		catch(IOException e)
		{
			System.out.println("ERROR: file not saved: " + saveAsFilePath);
			System.out.println(e.toString());
		}
		// >Aktueller< Dateipfad wird aktualisiert
		openFilePath = saveAsFilePath;
		// Fenstertitel wird neu geschrieben
		this.setTitle(Main.version + " - " + openFilePath);
	}

	// what to when the actionlistener gets a source
	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();

		// action for help - about
		if(source == menuItemHelpAbout)
		{
			System.out.println("about executed");
			System.out.println(Main.version + " - by m0x23");
			// GUI-MessageDialog anzeigen
			JOptionPane.showMessageDialog(null, Main.version + "\na lightweight text-editor written in Java\n\nby m0x23", "About mEd", JOptionPane.INFORMATION_MESSAGE);
		}

		// action for help - help dialog
		if(source == menuItemHelpHelp)
		{
			System.out.println("help executed");
			// show popup dialog
			JOptionPane.showMessageDialog(null, "mEd text-editor\n\n" + "all keyboard shortcuts activated\nlook for tooltips in menu\n\nversion information: " + Main.version, "mEd Manual",
					JOptionPane.INFORMATION_MESSAGE);
		}

		// action for file - close
		if(source == menuItemFileExit)
		{
			// show Yes No selection
			int returnConfirm = JOptionPane.showConfirmDialog(null, "Do you want to save before exit?", "Save File?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			// selected no
			if(returnConfirm == JOptionPane.NO_OPTION)
			{
				System.out.println("selection: no");
				System.exit(0);
			}

			// selected yes
			else if(returnConfirm == JOptionPane.YES_OPTION)
			{
				if(openFilePath.equals(""))
				{
					saveAs();
					System.out.println("savepath: " + openFilePath);
					System.out.println(Main.version + " closed");
					System.exit(0);
				}
				else
				{
					System.out.println("savepath: " + openFilePath);
					FileWriter fw;
					try
					{
						fw = new FileWriter(openFilePath);
						area.write(fw);
						fw.close();
						System.out.println("file successfully saved");
					}
					catch(IOException e)
					{
						System.out.println("ERROR: file not saved: " + openFilePath);
						System.out.println(e.toString());
					}
				}

				System.out.println(Main.version + " closed");
				System.exit(0);
			}

			// confirmation unexcepted exited
			else if(returnConfirm == JOptionPane.CLOSED_OPTION)
			{
				System.out.println("confirmation closed");
			}

		}

		// action for file - new
		if(source == menuItemFileNew)
		{
			System.out.println("create new file?");
			// show Yes No selection
			int returnConfirm = JOptionPane.showConfirmDialog(null, "Do you want to create a new file?", "New File?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			// selected no
			if(returnConfirm == JOptionPane.NO_OPTION)
			{
				System.out.println("selection: no");
			}

			// selected yes
			else if(returnConfirm == JOptionPane.YES_OPTION)
			{
				// clear textarea - reset windowtitle
				area.setText("");
				this.setTitle(Main.version);

				// clear current working path
				openFilePath = "";

				System.out.println("selection: yes");
				System.out.println("new file created");
			}

			// confirmation unexcepted exited
			else if(returnConfirm == JOptionPane.CLOSED_OPTION)
			{
				System.out.println("confirmation closed");
			}
		}

		// action for file - open
		if(source == menuItemFileOpen2)
		{
			int openIsSelected;
			// select file dialog
			JFileChooser openFileChoose;
			openFileChoose = new JFileChooser();
			System.out.println("open executed");
			openFileChoose.setDialogTitle("Open...");

			// show the dialog
			openIsSelected = openFileChoose.showOpenDialog(this);
			System.out.println("return:" + openIsSelected);

			// if a file was selected:
			if(openIsSelected == 0)
			{
				openFilePath = openFileChoose.getSelectedFile().getAbsolutePath();
				System.out.println("selected file: " + openFilePath);
			}
			// add file content to the textarea
			String line;
			String linebuilder;
			FileReader fr; // file reader object
			// init bufferedreader to get stringlines
			BufferedReader br = null;
			try
			{
				fr = new FileReader(openFileChoose.getSelectedFile().getAbsolutePath());
				br = new BufferedReader(fr);
				line = "";
				linebuilder = br.readLine(); // get first line of file
				
				// loop to build the finalstring for the textarea
				while(linebuilder != null)
				{
					line = line + linebuilder + "\n"; // finalstring(the temp)
					linebuilder = br.readLine(); // get next line
				}
				// add finalstring
				area.setText(line);
				fr.close(); // close fr

				// reset caret to start of file
				area.setCaretPosition(0);
				// success message and refresh the windowtitle
				System.out.println("file successfully opened");
				this.setTitle(Main.version + " - " + openFilePath);
			}
			catch(FileNotFoundException e)
			{
				System.out.println("ERROR: File not found");
				e.printStackTrace();
			}
			catch(IOException e)
			{
				System.out.println("IO ERROR");
				e.printStackTrace();
			}
		}

		// action for file - save
		if(source == menuItemFileSave)
		{
			// if a file is already opened it will be overwritten
			if(!openFilePath.equals(""))
			{
				System.out.println("save executed");
				System.out.println("savepath: " + openFilePath);
				FileWriter fw;
				try
				{
					fw = new FileWriter(openFilePath);
					area.write(fw);
					fw.close();
					System.out.println("file successfully saved");
				}
				catch(IOException e)
				{
					System.out.println("ERROR: file not saved: " + openFilePath);
					System.out.println(e.toString());
				}
			}
			// if not: please select a path to write the file
			else
			{
				saveAs();
			}
		}

		// action for file - saveas
		if(source == menuItemFileSaveAs)
		{
			saveAs();
		}

		// action for context menu copy / edit - copy
		if(source == menuItemEditCopy || source == contextItemCopy)
		{
			System.out.println("selection cutted to clipboard");
			System.out.println("copied text: " + area.getSelectedText());
			// copy to clipboard
			area.copy(); 
		}

		// action for context menu paste / edit - paste
		if(source == menuItemEditPaste || source == contextItemPaste)
		{
			System.out.println("clipboard pasted");
			// paste from clipboard to textarea
			area.paste(); 
		}

		// action for context menu cut / edit - cut
		if(source == menuItemEditCut || source == contextItemCut)
		{
			System.out.println("selection cutted to clipboard");
			// cut to clipboard
			area.cut(); 
		}

		// action for context menu delete / edit - delete
		if(source == menuItemEditDelete || source == contextItemDelete)
		{
			System.out.println("delete executed");
			System.out.println("deleted selection: " + area.getSelectedText());
			try
			{
				Robot emuPress;
				// create object for filepress emulation
				emuPress = new Robot();
				//press and release the delete button
				emuPress.keyPress(KeyEvent.VK_DELETE);
				emuPress.keyRelease(KeyEvent.VK_DELETE);
			}
			catch(AWTException ex)
			{
				ex.printStackTrace();
			}
		}

		// action for edit - undo
		if(source == menuItemEditUndo)
		{
			System.out.println("undo executed");
			try
			{
				// undo last operation
				undoManager.undo();
				menuItemEditRedo.setEnabled(true);
				// disable redo if theres nothing to redo
				if(!undoManager.canUndo())
				{
					menuItemEditRedo.setEnabled(false);
				}
			}
			catch(CannotUndoException ex)
			{
				System.out.println("nothing to undo");
			}
		}

		// action for edit - redo
		if(source == menuItemEditRedo)
		{
			System.out.println("redo executed");
			try
			{
				// redo last operation
				undoManager.redo();
				// disable if theres nothing to do
				if(!undoManager.canRedo())
				{
					menuItemEditRedo.setEnabled(false);
				}
			}
			catch(CannotRedoException ex)
			{
				System.out.println("nothing to redo");
			}
		}

		// action for edit - toggle darkmode
		if(source == menuItemEditDark)
		{
			System.out.println("dark executed");
			if(isDark)
			{
				//set colors of interface
				menuBar.setBackground(Color.lightGray);
				menuFile.setForeground(Color.black);
				menuEdit.setForeground(Color.black);
				menuHelp.setForeground(Color.black);
				menuItemFileNew.setBackground(Color.lightGray);
				menuItemFileOpen2.setBackground(Color.lightGray);
				menuItemFileSave.setBackground(Color.lightGray);
				menuItemFileSaveAs.setBackground(Color.lightGray);
				menuItemFileExit.setBackground(Color.lightGray);
				menuItemEditCopy.setBackground(Color.lightGray);
				menuItemEditPaste.setBackground(Color.lightGray);
				menuItemEditCut.setBackground(Color.lightGray);
				menuItemEditDelete.setBackground(Color.lightGray);
				menuItemEditUndo.setBackground(Color.lightGray);
				menuItemEditRedo.setBackground(Color.lightGray);
				menuItemEditDark.setBackground(Color.lightGray);
				menuItemEditFont.setBackground(Color.lightGray);
				menuItemHelpHelp.setBackground(Color.lightGray);
				menuItemHelpAbout.setBackground(Color.lightGray);

				menuItemFileNew.setForeground(Color.black);
				menuItemFileOpen2.setForeground(Color.black);
				menuItemFileSave.setForeground(Color.black);
				menuItemFileSaveAs.setForeground(Color.black);
				menuItemFileExit.setForeground(Color.black);
				menuItemEditCopy.setForeground(Color.black);
				menuItemEditPaste.setForeground(Color.black);
				menuItemEditCut.setForeground(Color.black);
				menuItemEditDelete.setForeground(Color.black);
				menuItemEditUndo.setForeground(Color.black);
				menuItemEditRedo.setForeground(Color.black);
				menuItemEditDark.setForeground(Color.black);
				menuItemEditFont.setForeground(Color.black);
				menuItemHelpHelp.setForeground(Color.black);
				menuItemHelpAbout.setForeground(Color.black);
				
				//set colors of textarea
				area.setBackground(Color.white);
				area.setForeground(Color.black);
				area.setCaretColor(Color.black);

			}
			else
			{
				//set colors of interface
				menuBar.setBackground(Color.darkGray);
				menuFile.setForeground(Color.white);
				menuEdit.setForeground(Color.white);
				menuHelp.setForeground(Color.white);
				menuItemFileNew.setBackground(Color.darkGray);
				menuItemFileOpen2.setBackground(Color.darkGray);
				menuItemFileSave.setBackground(Color.darkGray);
				menuItemFileSaveAs.setBackground(Color.darkGray);
				menuItemFileExit.setBackground(Color.darkGray);
				menuItemEditCopy.setBackground(Color.darkGray);
				menuItemEditPaste.setBackground(Color.darkGray);
				menuItemEditCut.setBackground(Color.darkGray);
				menuItemEditDelete.setBackground(Color.darkGray);
				menuItemEditUndo.setBackground(Color.darkGray);
				menuItemEditRedo.setBackground(Color.darkGray);
				menuItemEditDark.setBackground(Color.darkGray);
				menuItemEditFont.setBackground(Color.darkGray);
				menuItemHelpHelp.setBackground(Color.darkGray);
				menuItemHelpAbout.setBackground(Color.darkGray);

				menuItemFileNew.setForeground(Color.white);
				menuItemFileOpen2.setForeground(Color.white);
				menuItemFileSave.setForeground(Color.white);
				menuItemFileSaveAs.setForeground(Color.white);
				menuItemFileExit.setForeground(Color.white);
				menuItemEditCopy.setForeground(Color.white);
				menuItemEditPaste.setForeground(Color.white);
				menuItemEditCut.setForeground(Color.white);
				menuItemEditDelete.setForeground(Color.white);
				menuItemEditUndo.setForeground(Color.white);
				menuItemEditRedo.setForeground(Color.white);
				menuItemEditDark.setForeground(Color.white);
				menuItemEditFont.setForeground(Color.white);
				menuItemHelpHelp.setForeground(Color.white);
				menuItemHelpAbout.setForeground(Color.white);

				// set colors of textarea and caret
				area.setBackground(Color.darkGray);
				area.setForeground(Color.white);
				area.setCaretColor(Color.white);

			}
			// toggle isDark
			isDark = !isDark;
			System.out.println("darkmode: " + isDark);
		}

		// action for edit - toggle font
		if(source == menuItemEditFont)
		{
			System.out.println("font executed");
			if(isMono)
			{
				//set font to sans
				area.setFont(new Font("sans", Font.PLAIN, 14));
				System.out.println("font: sans");
			}
			else
			{
				// set font to monospaced
				area.setFont(new Font("monospaced", Font.PLAIN, 14));
				System.out.println("font: monospaced");
			}
			// toggle isMono
			isMono = !isMono;
		}
	}
}
