package termproject;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class StudentTableModel extends AbstractTableModel {
	
	private List<Student> students;
	private String[] columnNames = {"Name", "Address", "Age"};
	
	public StudentTableModel(List<Student> students) {
		this.students = students;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return students.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Student student = students.get(rowIndex);
		String result = null;
		switch(columnIndex) {
		case 0:
			result = student.getName();
			break;
		case 1:
			result = student.getAddress();
			break;
		case 2:
			result = Integer.toString(student.getAge());
			break;
		}
return result;
	}

}
