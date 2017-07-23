import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2900918031239405046L;
	JLabel userL = new JLabel("Username: ");
	JTextField userTF = new JTextField();
	JLabel passl = new JLabel("Password: ");
	JPasswordField passTF = new JPasswordField();
	JPanel loginp = new JPanel(new GridLayout(3,2));
	JPanel panel = new JPanel();
	JButton login = new JButton  ("Login");
	JButton register = new JButton("Register");
	CardLayout cl;
	Login(){
		setLayout(new CardLayout());
		loginp.add(userL);
		loginp.add(userTF);
		loginp.add(passl);
		loginp.add(passTF);
		login.addActionListener(this);
		register.addActionListener(this);
		loginp.add(login);
		loginp.add(register);
		panel.add(loginp);
		
		cl = (CardLayout) getLayout();
		add(panel,"login");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login){
			try {
				BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
				String pass = null;
				String line = input.readLine();
				while(line!= null){
					StringTokenizer st = new StringTokenizer(line);
					if(userTF.getText().equals(st.nextToken())){
						pass = st.nextToken();
					}
					line = input.readLine();
				}
				input.close();
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(new String(passTF.getPassword()).getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				
				for( int i =0;i<byteData.length;i++){
					sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100,16).substring(1));
				}
				
				if(pass.equals(sb.toString())){
					add(new FileBrowser(userTF.getText()),"fb");
					cl.show(this,"fb");
				}
			} catch (FileNotFoundException e1) {
				System.out.println("File not Found");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource()== register){
			add(new Register(), "register");
			cl.show(this, "register");
		}
	}
	
	public static void main (String[] args){
		JFrame frame = new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		Login login = new Login();
		frame.add(login);
		frame.setVisible(true);
	}

}
