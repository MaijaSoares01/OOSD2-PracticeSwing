package termproject;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class StudentDialog extends JDialog {

	private JLabel nameLabel = new JLabel("Name");
	private JTextField nameTextField = new JTextField(20);
	private JLabel addressLabel = new JLabel("Address");
	private JTextField addressTextField = new JTextField(20);
	private JLabel ageLabel = new JLabel("Age");
	private JTextField ageTextField = new JTextField(20);
	private JLabel ageErrorLabel = new JLabel();
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private MainWindow parent;
	private Student student;
	private int index;
	
	public StudentDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		this.setLayout(new MigLayout());
		parent = (MainWindow) this.getParent();
		this.setLocationRelativeTo(parent);
		this.add(nameLabel);
		this.add(nameTextField, "wrap");
		this.add(addressLabel);
		this.add(addressTextField, "wrap");
		this.add(ageLabel);
		this.add(ageTextField, "wrap");
		Font existingFont = ageErrorLabel.getFont();
		ageErrorLabel.setFont(new Font(existingFont.getName(),Font.PLAIN, 11));
		ageErrorLabel.setForeground(Color.red);
		ageErrorLabel.setVisible(false);
		this.add(ageErrorLabel, "skip1, wrap");
		this.add(okButton, "tag ok, skip 1, split 2");
		this.add(cancelButton, "tag cancel");
		this.pack();
		System.out.println(ageErrorLabel.getFont().getName());
		System.out.println(ageErrorLabel.getFont().getSize());
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StudentDialog.this.setVisible(false);
			}
			
		});
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean allOK = true;
				String name = nameTextField.getText();
				String address = addressTextField.getText();
				String ageAsString = ageTextField.getText();
				int age = 0;
				try {
					age = Integer.parseInt(ageAsString);
				} catch (NumberFormatException e1) {
					allOK =false;
					ageErrorLabel.setVisible(true);
				}
				if (allOK) {
					Student newStudent = new Student(name, address, age);
					if (student == null) {
						parent.addStudent(newStudent);
					}else {
						parent.updateStudent(newStudent, index);
					}
					StudentDialog.this.setVisible(false);
				}
				
			}
		});
	}
	
	public StudentDialog(Frame owner, String title, boolean modal,Student student, int index) {
		this(owner, title, modal);
		this.student = student;
		this.index = index;
		nameTextField.setText(student.getName());
		addressTextField.setText(student.getAddress());
		ageTextField.setText(Integer.toString(student.getAge()));
	}
}
