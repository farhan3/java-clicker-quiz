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
import org.eclipse.swt.custom.StyledText;

public class Server {

	private Shell _mainShell;
	private Menu _mainMenu;
	private Composite _mainComposite;
	private Composite serverInfoComposite;
	
	private Text text;
	private Text text_1;
	private Table table;

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

	
	private void createShell() {
		_mainShell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		_mainShell.setMinimumSize(new Point(69, 46));
		_mainShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		_mainShell.setSize(500, 500);
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
		serverInfoComposite = new Composite(_mainComposite, SWT.NONE);
		serverInfoComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		serverInfoComposite.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		serverInfoComposite.setLayout(null);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.top = new FormAttachment(0, 10);
		fd_composite_1.left = new FormAttachment(0, 10);
		fd_composite_1.bottom = new FormAttachment(0, 95);
		fd_composite_1.right = new FormAttachment(0, 490);
		serverInfoComposite.setLayoutData(fd_composite_1);
		
		Label lblServerInformation = new Label(serverInfoComposite, SWT.SHADOW_IN | SWT.CENTER);
		lblServerInformation.setBounds(156, 10, 165, 22);
		lblServerInformation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblServerInformation.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		lblServerInformation.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 14, SWT.NORMAL));
		lblServerInformation.setText("Server Information");
		
		Label lblHostipAddress = new Label(serverInfoComposite, SWT.NONE);
		lblHostipAddress.setBounds(10, 42, 94, 14);
		lblHostipAddress.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		lblHostipAddress.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		lblHostipAddress.setText("Host/IP Address");
		
		Combo combo = new Combo(serverInfoComposite, SWT.NONE);
		combo.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
		combo.setBounds(110, 38, 195, 37);
		combo.setItems(new String[] {"LocalHost", "127.0.0.1"});
		combo.setText("LocalHost");
		
		Spinner spinner = new Spinner(serverInfoComposite, SWT.BORDER);
		spinner.setBounds(390, 37, 78, 22);
		spinner.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
		spinner.setMaximum(65535);
		spinner.setMinimum(1025);
		spinner.setSelection(21800);
		
		Label lblNewLabel = new Label(serverInfoComposite, SWT.NONE);
		lblNewLabel.setBounds(311, 41, 73, 14);
		lblNewLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_FOREGROUND));
		lblNewLabel.setText("Port Number");
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		createShell();
		createMenu();
		createMainComposite();
		createServerInfoComposite();
	
		
		
		Composite composite_2 = new Composite(_mainComposite, SWT.NONE);
		composite_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_2.setLayout(null);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(100, -10);
		fd_composite_2.right = new FormAttachment(serverInfoComposite, 0, SWT.RIGHT);
		fd_composite_2.left = new FormAttachment(serverInfoComposite, 0, SWT.LEFT);
		composite_2.setLayoutData(fd_composite_2);
		
		Label label = new Label(_mainComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(serverInfoComposite, 6);
		fd_label.bottom = new FormAttachment(serverInfoComposite, 8, SWT.BOTTOM);
		fd_label.left = new FormAttachment(0);
		fd_label.right = new FormAttachment(0, 500);
		label.setLayoutData(fd_label);
		
		Composite composite_3 = new Composite(_mainComposite, SWT.BORDER);
		fd_composite_2.top = new FormAttachment(composite_3, 50);
		FormData fd_composite_3 = new FormData();
		fd_composite_3.top = new FormAttachment(label, 6);
		fd_composite_3.bottom = new FormAttachment(100, -124);
		fd_composite_3.right = new FormAttachment(100, -23);
		
		text_1 = new Text(composite_2, SWT.BORDER | SWT.V_SCROLL);
		text_1.setEditable(false);
		text_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_1.setBounds(10, 10, 460, 44);
		composite_3.setLayoutData(fd_composite_3);
		
		final List list = new List(composite_3, SWT.BORDER | SWT.V_SCROLL);
		list.setBounds(10, 37, 113, 140);
		
		Label lblStudents = new Label(composite_3, SWT.NONE);
		lblStudents.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 14, SWT.NORMAL));
		lblStudents.setBounds(32, 10, 68, 21);
		lblStudents.setText("Class List");
		
		text = new Text(composite_3, SWT.BORDER);
		text.setBounds(10, 180, 113, 19);
		
		Button btnAddStudent = new Button(composite_3, SWT.NONE);
		btnAddStudent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String textStr = text.getText();
				if (textStr != null && !textStr.isEmpty()) {
					if (!Arrays.asList(list.getItems()).contains(textStr)) {
						list.add(textStr);
					}
				}

			}
		});
		btnAddStudent.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		btnAddStudent.setBounds(19, 205, 95, 28);
		btnAddStudent.setText("Add Student");
		
		Composite composite_4 = new Composite(_mainComposite, SWT.BORDER);
		composite_4.setLayout(null);
		FormData fd_composite_4 = new FormData();
		fd_composite_4.top = new FormAttachment(label, 6);
		fd_composite_4.bottom = new FormAttachment(composite_2, -50);
		fd_composite_4.left = new FormAttachment(0, 23);
		composite_4.setLayoutData(fd_composite_4);
		
		Label lblQuestion = new Label(composite_4, SWT.NONE);
		lblQuestion.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 14, SWT.NORMAL));
		lblQuestion.setBounds(10, 11, 112, 23);
		lblQuestion.setText("Question Details");
		
		final Button btnRadioButton = new Button(composite_4, SWT.RADIO);
		btnRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnRadioButton.setBounds(10, 108, 36, 18);
		btnRadioButton.setText("A");
		
		Button btnRadioButton_1 = new Button(composite_4, SWT.RADIO);
		btnRadioButton_1.setBounds(10, 132, 36, 18);
		btnRadioButton_1.setText("B");
		
		Button btnRadioButton_2 = new Button(composite_4, SWT.RADIO);
		btnRadioButton_2.setBounds(10, 156, 36, 18);
		btnRadioButton_2.setText("C");
		
		Button btnRadioButton_3 = new Button(composite_4, SWT.RADIO);
		btnRadioButton_3.setBounds(10, 180, 36, 18);
		btnRadioButton_3.setText("D");
		
		final Button btnRadioButton_4 = new Button(composite_4, SWT.RADIO);
		btnRadioButton_4.setBounds(10, 204, 36, 18);
		btnRadioButton_4.setText("E");
		
		final Spinner spinner_1 = new Spinner(composite_4, SWT.BORDER);
		spinner_1.setMaximum(5);
		spinner_1.setMinimum(2);
		spinner_1.setBounds(10, 59, 36, 23);
		spinner_1.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				btnRadioButton_4.setEnabled(!btnRadioButton_4.getEnabled());


			}
		});
		
		text_1.append(spinner_1.getText() + "\n");
		
		Label lblNumberOfOptions = new Label(composite_4, SWT.NONE);
		lblNumberOfOptions.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		lblNumberOfOptions.setBounds(10, 40, 92, 13);
		lblNumberOfOptions.setText("Num. of Options");
		
		Label lblCorrectAnswer = new Label(composite_4, SWT.NONE);
		lblCorrectAnswer.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		lblCorrectAnswer.setBounds(10, 88, 92, 14);
		lblCorrectAnswer.setText("Correct Answer");
		fd_composite_4.right = new FormAttachment(100, -341);
		fd_composite_3.left = new FormAttachment(0, 342);
		
		Composite composite_6 = new Composite(_mainComposite, SWT.BORDER);
		composite_6.setLayout(null);
		FormData fd_composite_6 = new FormData();
		fd_composite_6.bottom = new FormAttachment(composite_3, 0, SWT.BOTTOM);
		fd_composite_6.top = new FormAttachment(label, 6);
		fd_composite_6.right = new FormAttachment(composite_3, -25);
		fd_composite_6.left = new FormAttachment(composite_4, 23);
		composite_6.setLayoutData(fd_composite_6);
		
		table = new Table(composite_6, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(12, 38, 115, 161);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnStudent = new TableColumn(table, SWT.NONE);
		tblclmnStudent.setWidth(67);
		tblclmnStudent.setText("Student");
		
		TableColumn tblclmnAnswer = new TableColumn(table, SWT.NONE);
		tblclmnAnswer.setWidth(44);
		tblclmnAnswer.setText("Answer");
		
		Button btnList = new Button(composite_6, SWT.NONE);
		btnList.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		btnList.setBounds(34, 205, 65, 28);
		btnList.setText("List");
		
		Label lblStudentReplies = new Label(composite_6, SWT.NONE);
		lblStudentReplies.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 14, SWT.NORMAL));
		lblStudentReplies.setBounds(12, 10, 108, 22);
		lblStudentReplies.setText("Student Replies");
		
		Composite composite_5 = new Composite(_mainComposite, SWT.BORDER);
		composite_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_composite_5 = new FormData();
		fd_composite_5.top = new FormAttachment(composite_3, 11);
		fd_composite_5.bottom = new FormAttachment(composite_2, -6);
		fd_composite_5.left = new FormAttachment(composite_4, 0, SWT.LEFT);
		fd_composite_5.right = new FormAttachment(composite_3, 0, SWT.RIGHT);
		composite_5.setLayoutData(fd_composite_5);
		
		Button button = new Button(composite_5, SWT.NONE);
		button.setText("Start Question");
		
		Button button_1 = new Button(composite_5, SWT.NONE);
		button_1.setText("End Question");

	}
}
