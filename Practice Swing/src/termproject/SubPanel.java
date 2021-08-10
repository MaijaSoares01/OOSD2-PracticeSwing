package termproject;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SubPanel extends JPanel {
	//List<Student> students;//////got rid of
	private JComboBox<Student> studentComboBox;//////////////////// 
	
	public SubPanel(List<Student> students) {
		//this.students = students;/////////got rid of
		studentComboBox = new JComboBox<Student>((Student[])students.toArray(new Student[0]));////////////////
		this.add(studentComboBox);
		studentComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Student student = (Student)studentComboBox.getSelectedItem();
				System.out.println("You selected someone from " + student.getAddress());
				
			}
			
		});
	}
	
	public void setStudents(List<Student> students) {
		DefaultComboBoxModel<Student> model = new DefaultComboBoxModel<>((Student[])students.toArray(new Student[0]));
		studentComboBox.setModel(model);
	}
}
