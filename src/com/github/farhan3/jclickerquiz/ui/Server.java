package com.github.farhan3.jclickerquiz.ui;

import java.util.Arrays;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class Server {

	private Shell 		_mainShell;
	private Menu 		_mainMenu;
	private Label 		_horizontalSeprator;

	private Composite _mainComposite;
	private Composite _serverInfoComposite;
	private Composite _consoleComposite;
	private Composite _classListComposite;
	private Composite _questionDetailsComposite;
	private Composite _studentRepliesComposite;
	private Composite _startEndQuestionComposite;
	

	private Text _studentNumberInputText;
	private Text _consoleText;
	private Table _studentRepliesTable;
	
	private FormData fdConsoleComposite;
	private FormData fd_label;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Server window = new Server();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		_mainShell.open();
		_mainShell.layout();
		while (!_mainShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		createShell();
		createMenu();
		createMainComposite();
		createServerInfoComposite();
		createConsoleComposite();
		createHorizontalSeparator();
		createClassListComposite();
		createQuestionDetails();
		createStudentRepliesComposite();
		createStartEndQuestionComposite();
	}

	
	private void createShell() {
		//_mainShell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		_mainShell = new Shell();
		_mainShell.setMinimumSize(new Point(69, 46));
		_mainShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		_mainShell.setSize(500, 475);
		_mainShell.setText("JClickerQuiz Server");
		_mainShell.setLayout(new FillLayout(SWT.HORIZONTAL));
	}
	
	private void createMenu() {
		_mainMenu = new Menu(_mainShell, SWT.BAR);
		_mainShell.setMenuBar(_mainMenu);
		
		MenuItem jclickerquizMenuItem = new MenuItem(_mainMenu, SWT.CASCADE);
		jclickerquizMenuItem.setText("JClickerQuiz");
		
		Menu jclickerquizMenu = new Menu(jclickerquizMenuItem);
		jclickerquizMenuItem.setMenu(jclickerquizMenu);
		
		MenuItem quitMenuItem = new MenuItem(jclickerquizMenu, SWT.RADIO);
		quitMenuItem.setText("Quit");
	}
	
	private void createMainComposite() {
		_mainComposite = new Composite(_mainShell, SWT.NONE);
		_mainComposite.setLayout(new FormLayout());
	}

	private void createServerInfoComposite() {
		_serverInfoComposite = new Composite(_mainComposite, SWT.NONE);
		_serverInfoComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		_serverInfoComposite.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		_serverInfoComposite.setLayout(null);
		FormData fd = new FormData();
		fd.right = new FormAttachment(0, 490);
		fd.left = new FormAttachment(0, 10);
		fd.bottom = new FormAttachment(0, 95);
		fd.top = new FormAttachment(0, 10);
		_serverInfoComposite.setLayoutData(fd);
		
		Label serverInformationLabel = new Label(_serverInfoComposite, SWT.SHADOW_IN | SWT.CENTER);
		serverInformationLabel.setBounds(156, 10, 165, 22);
		serverInformationLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		serverInformationLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		serverInformationLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 14, SWT.NORMAL));
		serverInformationLabel.setText("Server Information");
		
		Label hostIpAddressLabel = new Label(_serverInfoComposite, SWT.NONE);
		hostIpAddressLabel.setBounds(10, 42, 94, 14);
		hostIpAddressLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		hostIpAddressLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		hostIpAddressLabel.setText("Host/IP Address");
		
		Combo hostIpAddressCombo = new Combo(_serverInfoComposite, SWT.NONE);
		hostIpAddressCombo.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
		hostIpAddressCombo.setBounds(110, 38, 195, 37);
		hostIpAddressCombo.setItems(new String[] {"LocalHost", "127.0.0.1"});
		hostIpAddressCombo.setText("LocalHost");
		
		Label portNumberLabel = new Label(_serverInfoComposite, SWT.NONE);
		portNumberLabel.setBounds(311, 41, 73, 14);
		portNumberLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		portNumberLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		portNumberLabel.setText("Port Number");
		
		Spinner portNumberSpinner = new Spinner(_serverInfoComposite, SWT.BORDER);
		portNumberSpinner.setBounds(390, 37, 78, 22);
		portNumberSpinner.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
		portNumberSpinner.setMaximum(65535);
		portNumberSpinner.setMinimum(1025);
		portNumberSpinner.setSelection(21800);
	}
	
	private void createConsoleComposite() {
		_consoleComposite = new Composite(_mainComposite, SWT.NONE);
		_consoleComposite.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		_consoleComposite.setLayout(null);
		fdConsoleComposite = new FormData();
		fdConsoleComposite.bottom = new FormAttachment(100, -10);
		fdConsoleComposite.right = new FormAttachment(_serverInfoComposite, 0, SWT.RIGHT);
		fdConsoleComposite.left = new FormAttachment(0, 10);
		_consoleComposite.setLayoutData(fdConsoleComposite);
		
		_consoleText = new Text(_consoleComposite, SWT.BORDER | SWT.V_SCROLL);
		_consoleText.setEditable(false);
		_consoleText.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		_consoleText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		_consoleText.setBounds(10, 10, 460, 30);
	}
	
	private void createHorizontalSeparator() {
		_horizontalSeprator = new Label(_mainComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_label = new FormData();
		fd_label.top = new FormAttachment(_serverInfoComposite, 6);
		fd_label.left = new FormAttachment(0);
		fd_label.right = new FormAttachment(0, 500);
		_horizontalSeprator.setLayoutData(fd_label);
	}
	
	private void createClassListComposite() {
		_classListComposite = new Composite(_mainComposite, SWT.BORDER);
		fd_label.bottom = new FormAttachment(_classListComposite, -6);
		FormData fd = new FormData();
		fd.bottom = new FormAttachment(100, -107);
		fd.left = new FormAttachment(100, -158);
		fd.right = new FormAttachment(100, -23);
		fd.top = new FormAttachment(0, 109);		
		_classListComposite.setLayoutData(fd);
		
		final List classList = new List(_classListComposite, SWT.BORDER | SWT.V_SCROLL);
		classList.setBounds(10, 37, 113, 140);
		
		Label classListLabel = new Label(_classListComposite, SWT.NONE);
		classListLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 14, SWT.NORMAL));
		classListLabel.setBounds(32, 10, 68, 21);
		classListLabel.setText("Class List");
		
		_studentNumberInputText = new Text(_classListComposite, SWT.BORDER);
		_studentNumberInputText.setBounds(10, 180, 113, 19);
		
		Button addStudentButton = new Button(_classListComposite, SWT.NONE);
		addStudentButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String textStr = _studentNumberInputText.getText();
				if (textStr != null && !textStr.isEmpty()) {
					if (!Arrays.asList(classList.getItems()).contains(textStr)) {
						classList.add(textStr);
					}
				}

			}
		});
		addStudentButton.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		addStudentButton.setBounds(19, 205, 95, 28);
		addStudentButton.setText("Add Student");
	}
	
	private void createQuestionDetails() {
		_questionDetailsComposite = new Composite(_mainComposite, SWT.BORDER);
		_questionDetailsComposite.setLayout(null);
		FormData fd = new FormData();
		fd.right = new FormAttachment(0, 158);
		fd.bottom = new FormAttachment(_horizontalSeprator, 243, SWT.BOTTOM);
		fd.left = new FormAttachment(0, 23);
		fd.top = new FormAttachment(_horizontalSeprator, 6);
		_questionDetailsComposite.setLayoutData(fd);
		
		Label questionLabel = new Label(_questionDetailsComposite, SWT.NONE);
		questionLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 14, SWT.NORMAL));
		questionLabel.setBounds(10, 11, 112, 23);
		questionLabel.setText("Question Details");
		
		Label correctAnswerLabel = new Label(_questionDetailsComposite, SWT.NONE);
		correctAnswerLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		correctAnswerLabel.setBounds(10, 88, 92, 14);
		correctAnswerLabel.setText("Correct Answer");
		
		final Button aOptionRadioButton = new Button(_questionDetailsComposite, SWT.RADIO);
		aOptionRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		aOptionRadioButton.setBounds(10, 108, 36, 18);
		aOptionRadioButton.setText("A");
		
		Button bOptionRadioButton = new Button(_questionDetailsComposite, SWT.RADIO);
		bOptionRadioButton.setBounds(10, 132, 36, 18);
		bOptionRadioButton.setText("B");
		
		Button cOptionRadioButton = new Button(_questionDetailsComposite, SWT.RADIO);
		cOptionRadioButton.setBounds(10, 156, 36, 18);
		cOptionRadioButton.setText("C");
		
		Button dOptionRadioButton = new Button(_questionDetailsComposite, SWT.RADIO);
		dOptionRadioButton.setBounds(10, 180, 36, 18);
		dOptionRadioButton.setText("D");
		
		final Button eOptionRadioButton = new Button(_questionDetailsComposite, SWT.RADIO);
		eOptionRadioButton.setBounds(10, 204, 36, 18);
		eOptionRadioButton.setText("E");
		
		
		Label numberOfChoicesLabel = new Label(_questionDetailsComposite, SWT.NONE);
		numberOfChoicesLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		numberOfChoicesLabel.setBounds(10, 40, 92, 13);
		numberOfChoicesLabel.setText("Num. of Choices");
		
		final Spinner numOfChoicesspinner = new Spinner(_questionDetailsComposite, SWT.BORDER);
		numOfChoicesspinner.setMaximum(5);
		numOfChoicesspinner.setMinimum(2);
		numOfChoicesspinner.setBounds(10, 59, 36, 23);
		numOfChoicesspinner.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				eOptionRadioButton.setEnabled(!eOptionRadioButton.getEnabled());


			}
		});
		
	}
	
	private void createStudentRepliesComposite() {
		_studentRepliesComposite = new Composite(_mainComposite, SWT.BORDER);
		_studentRepliesComposite.setLayout(null);
		FormData fd = new FormData();
		fd.bottom = new FormAttachment(_horizontalSeprator, 243, SWT.BOTTOM);
		fd.top = new FormAttachment(_horizontalSeprator, 6);
		fd.right = new FormAttachment(_classListComposite, -25);
		fd.left = new FormAttachment(_questionDetailsComposite, 24);
		_studentRepliesComposite.setLayoutData(fd);
		
		Label studentRepliesLabel = new Label(_studentRepliesComposite, SWT.NONE);
		studentRepliesLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 14, SWT.NORMAL));
		studentRepliesLabel.setBounds(12, 10, 108, 22);
		studentRepliesLabel.setText("Student Replies");
		
		_studentRepliesTable = new Table(_studentRepliesComposite, SWT.BORDER | SWT.FULL_SELECTION);
		_studentRepliesTable.setBounds(12, 38, 115, 161);
		_studentRepliesTable.setHeaderVisible(true);
		_studentRepliesTable.setLinesVisible(true);
		
		TableColumn studentNumColumn = new TableColumn(_studentRepliesTable, SWT.NONE);
		studentNumColumn.setWidth(67);
		studentNumColumn.setText("Student");
		
		TableColumn answerColumn = new TableColumn(_studentRepliesTable, SWT.NONE);
		answerColumn.setWidth(44);
		answerColumn.setText("Answer");
		
		Button listButton = new Button(_studentRepliesComposite, SWT.NONE);
		listButton.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		listButton.setBounds(34, 205, 65, 28);
		listButton.setText("List");
	}
	
	private void createStartEndQuestionComposite() {
		_startEndQuestionComposite = new Composite(_mainComposite, SWT.NONE);
		fdConsoleComposite.top = new FormAttachment(_startEndQuestionComposite, 6);
		_startEndQuestionComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd = new FormData();
		fd.right = new FormAttachment(0, 477);
		fd.bottom = new FormAttachment(0, 385);
		fd.top = new FormAttachment(0, 352);
		fd.left = new FormAttachment(0, 23);
		_startEndQuestionComposite.setLayoutData(fd);
		
		Button startQuestionButton = new Button(_startEndQuestionComposite, SWT.NONE);
		startQuestionButton.setText("Start Question");
		
		Button endQuestionButton = new Button(_startEndQuestionComposite, SWT.NONE);
		endQuestionButton.setText("End Question");
	}
	
}
