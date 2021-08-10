package termproject;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame {
	//public final static int YES_NO = 0;
	//public final static int YES_NO_CANCEL = 1;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem newMenuItem = new JMenuItem("New");
	private JMenuItem openMenuItem = new JMenuItem("Open..");
	private JMenuItem saveMenuItem = new JMenuItem("Save");
	private JMenuItem copyMenuItem = new JMenuItem("Copy");
	private JMenuItem pasteMenuItem = new JMenuItem("Paste");
	private List<Student> students = new ArrayList<>();
	private StudentTableModel tableModel = new StudentTableModel(students);
	private JTable studentTable = new JTable(tableModel);
	private JScrollPane scrollPane = new JScrollPane(studentTable);
	private SubPanel subpanel;
	private JPanel alternativePanel = new JPanel();
	private JLabel wagesLabel = new JLabel("Wages");
	private JTextField wagesTextField = new JTextField(20);
	private DefaultListModel<Student> studentListModel = new DefaultListModel();////////////////////////////////
	private JScrollPane listScrollPane;
	private JList studentList;
	private JFileChooser fc = new JFileChooser();
	private JRadioButton redRadioButton = new JRadioButton("RED");/////////////
	private JRadioButton amberRadioButton = new JRadioButton("AMBER");////////////
	private JRadioButton greenRadioButton = new JRadioButton("GREEN");////////////
	private ButtonGroup trafficLightGroup = new ButtonGroup();///////////////
	
	public MainWindow(String title) {
		super(title);
		this.setLayout(new MigLayout()); 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		students.add(new Student("Janet", "Aungier St.", 20));
//		students.add(new Student("Fred", "Aungier St.", 21));
//		students.add(new Student("Tommy", "Aungier St.", 19));
		this.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);
		saveMenuItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("You selected the save menu item.");
//			}
			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(Window.this, "You selected the save menu item.");
//			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentDialog dialog = new StudentDialog(MainWindow.this, "New Student",true);
				dialog.setVisible(true);
			}
		});
		scrollPane.setPreferredSize(new Dimension(400, 200));
		this.add(scrollPane,"wrap");
		alternativePanel.setLayout(new MigLayout());
		studentTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable)e.getSource();
				if (e.getClickCount() == 2) {
					int index = table.getSelectedRow();
					Student student = students.get(index);
					StudentDialog dialog = new StudentDialog(MainWindow.this, "Edit Student",true, student, index);
					dialog.setVisible(true);
				}
			}
		});
		subpanel = new SubPanel(students);
		this.add(subpanel,"wrap");
		alternativePanel.add(wagesLabel);
		alternativePanel.add(wagesTextField,"wrap");
		redRadioButton.setActionCommand("red");///////////
		amberRadioButton.setActionCommand("amber");//////////////
		greenRadioButton.setActionCommand("green");///////////////
		trafficLightGroup.add(redRadioButton);//////////
		trafficLightGroup.add(amberRadioButton);/////////////
		trafficLightGroup.add(greenRadioButton);//////////////
		redRadioButton.setSelected(true);/////////
		alternativePanel.add(redRadioButton,"span 2,split 3");/////////////
		alternativePanel.add(amberRadioButton);///////////
		alternativePanel.add(greenRadioButton);///////////////
		
		ActionListener trafficLightListener = new ActionListener() {//////////all of this

			@Override
			public void actionPerformed(ActionEvent e) {
			if(e.getSource() != redRadioButton) {
				/////////////////add whatever here if needed
			}
			}
		};
		copyMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.this.setContentPane(alternativePanel);	
				MainWindow.this.revalidate();////////////////
			}
			
		});
		pasteMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				wagesLabel.setEnabled(!wagesLabel.isEnabled());///////////////////got rid of
//				wagesTextField.setEnabled(!wagesTextField.isEnabled());//////////////
				ButtonModel selectedButtonModel = trafficLightGroup.getSelection();/////////
				System.out.println(selectedButtonModel.getActionCommand());
			}
			
		});
		//populateListModel();////////////////////////////////
//		for(Student student : students) {                ///moved to method
//			studentListModel.addElement(student);
//		}
		studentList = new JList(studentListModel);
		studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listScrollPane = new JScrollPane(studentList);
		this.add(listScrollPane, "growx");
		openMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(MainWindow.this);
				System.out.println(returnVal);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					System.out.println("Opening....." + file.getName());
					System.out.println("From path....." + file.getAbsolutePath());
					try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
						students = (List<Student>)ois.readObject();	
						tableModel = new StudentTableModel(students);
						studentTable.setModel(tableModel);
						//subpanel.setStudents(students);
						//populateListModel();
					}catch(FileNotFoundException e1) {
							e1.printStackTrace();
						}catch(IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					
				}
			}
		});
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showSaveDialog(MainWindow.this);
				System.out.println(returnVal);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try (
							FileOutputStream fos = new FileOutputStream(file);
							ObjectOutputStream oos = new ObjectOutputStream(fos)
							
						){
							oos.writeObject(students);
						}catch(FileNotFoundException e1) {
							e1.printStackTrace();
						}catch(IOException e1) {
							e1.printStackTrace();
						}
				}
			}
			
		});
		//scrollPane.setPreferredSize(new Dimension(200, 80));
	}

	public void addStudent(Student student) {
		students.add(student);
		System.out.println("There are now " + students.size() + " students.");
		tableModel.fireTableDataChanged();
	}
	
	public void updateStudent(Student student,int index) {
		students.set(index, student);
		tableModel.fireTableRowsUpdated(index, index);
	}
//	private void populateListModel() {//////////////////////////////
//		studentListModel.clear();
//		for(Student student : students) {
//		studentListModel.addElement(student);
//	}
//	}
	
	public static void main(String[] args) {
		String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(systemLookAndFeelClassName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		MainWindow window = new MainWindow("Term project");
		//window.setSize(500, 300);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
	}
}