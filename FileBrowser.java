import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FileBrowser extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2676565097047121990L;
	JLabel label = new JLabel ("File list: ");
	JButton newfile = new JButton ("New File");
	JButton open = new JButton ("Open");
	JTextField newFileTF = new JTextField(20);
	ButtonGroup bg;
	File Directory;
	public FileBrowser(String dir){
		Directory = new File(dir);
		Directory.mkdir();
		JPanel fileList = new JPanel(new GridLayout(Directory.listFiles().length+3,1));
		fileList.add(label);
		bg = new ButtonGroup();
		for(File file:Directory.listFiles()){
			JRadioButton radio = new JRadioButton(file.getName());
			radio.setActionCommand(file.getName());
			bg.add(radio);
			fileList.add(radio);
		}
		JPanel newP = new JPanel();
		newP.add(newFileTF);
		newP.add(newfile);
		newfile.addActionListener(this);
		open.addActionListener(this);
		fileList.add(open);
		fileList.add(newP);
		add(fileList);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Login login = (Login) getParent();
		if(e.getSource()== open){
			login.add(new Editor(Directory.getName()+"\\"+ bg.getSelection().getActionCommand()),"editor");
			login.cl.show(login,"editor");
		}
		if(e.getSource() == newfile){
			String file = Directory.getName() + "\\" + newFileTF.getText() + ".txt";
			if(newFileTF.getText().length() > 0 && !(new File(file).exists())){
				login.add(new Editor(file),"editor");
				login.cl.show(login,"editor");
			}
		}
	}

}
