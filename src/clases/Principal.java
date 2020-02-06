package clases;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Principal extends JFrame {

	public JTextArea txtarchivo;
	public ArrayList<String> propiedades=new ArrayList<String>();
	public Properties prop;
	public File f;
	
	public Principal(){
		JPanel pbuscar=new JPanel();
		JButton btnexaminar=new JButton("Examinar");
		btnexaminar.addActionListener(e -> {
			JFileChooser selector=new JFileChooser();
			int r=selector.showOpenDialog(null);
			if(r==JFileChooser.APPROVE_OPTION){
				prop=new Properties();
				try {
					f=selector.getSelectedFile();
					prop.load(new FileInputStream(selector.getSelectedFile()));
					for (Enumeration i = prop.keys(); i.hasMoreElements(); ) {
						  Object obj = i.nextElement();
						  propiedades.add(""+obj);
						  propiedades.add(prop.getProperty(obj.toString()));
						  txtarchivo.append(obj + ": "+ prop.getProperty(obj.toString())+"\n");
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		pbuscar.add(new JLabel("Buscar Archivo: "));
		pbuscar.add(btnexaminar);
		
		txtarchivo=new JTextArea();
		add(txtarchivo);
		
		JPanel pabajo=new JPanel();
		JButton btnmodificar=new JButton("Modificar Valores");
		btnmodificar.addActionListener(e -> {
			FrameModificar fm=new FrameModificar(propiedades,prop,f);
			fm.setVisible(true);
			fm.setBounds(0,0,400,170);
			fm.setLocationRelativeTo(null);
		});
		pabajo.add(btnmodificar);
		
		add(pbuscar,BorderLayout.NORTH);
		add(new JScrollPane(txtarchivo),BorderLayout.CENTER);
		add(pabajo,BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		Principal p=new Principal();
		p.setVisible(true);
		p.setBounds(0, 0, 400, 200);
		p.setLocationRelativeTo(null);
		p.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
