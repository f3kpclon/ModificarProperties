package clases;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class FrameModificar extends JFrame {
	
	ArrayList<String> propiedades;
	JTextField txtclave,txtvalor;
	int indice=0;
	boolean sw=false;
	Properties p;
	File archivo;


	
	public FrameModificar(ArrayList<String> prop,Properties proper,File f){
		propiedades=prop;
		p=proper;
		archivo=f;
		JPanel ptitulo=new JPanel();
		ptitulo.add(new JLabel("Modificar Valores"));
		JPanel pdatos=new JPanel();
		pdatos.setLayout(new GridLayout(2,2,4,4));
		
		pdatos.add(new JLabel("Clave: "));
		txtclave=new JTextField();
		pdatos.add(txtclave);
		pdatos.add(new JLabel("Valor: "));
		txtvalor=new JTextField();
		pdatos.add(txtvalor);
		
		txtclave.setText(""+propiedades.get(indice));
		indice++;
		txtvalor.setText(""+propiedades.get(indice));
		
		JPanel pbotones=new JPanel();
		JButton btnsiguiente=new JButton("Siguiente");
		btnsiguiente.addActionListener(arg0 -> {
			if(indice<propiedades.size()-1){
				if(sw){
					indice+=2;
					sw=false;
				}else{
					indice++;
				}
				txtclave.setText(""+propiedades.get(indice));
				indice++;
				txtvalor.setText(""+propiedades.get(indice));
			}
		});
		JButton btnanterior=new JButton("Anterior");
		btnanterior.addActionListener(arg0 -> {
			if(indice>0){
				if(sw==false){
					indice-=2;
					sw=true;
				}else{
					indice--;
				}
				txtvalor.setText(""+propiedades.get(indice));
				indice--;
				txtclave.setText(""+propiedades.get(indice));
			}
		});
		JButton btnguardar=new JButton("Guardar");
		btnguardar.addActionListener(arg0 -> {
			final String keyPass ="Socius2020";
			AES.setKey(keyPass);
			AES.encrypt(txtvalor.getText());

			//if(p.containsKey(txtclave.getText())){
				if(p!=null){
					p.setProperty(txtclave.getText(),AES.getEncryptedString() );
					propiedades.set(propiedades.indexOf(txtclave.getText())+1,txtvalor.getText());
				}
			//}else{
				/*p.put(txtclave.getText(), txtvalor.getText());
				propiedades.add(txtclave.getText());
				propiedades.add(txtvalor.getText());*/
			//}
			FileOutputStream salida;
			try {
				salida = new FileOutputStream(archivo);
				p.store(salida, "Cambios realizados");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		pbotones.add(btnanterior);
		pbotones.add(btnguardar);
		pbotones.add(btnsiguiente);
		
		add(ptitulo,BorderLayout.NORTH);
		add(pdatos,BorderLayout.CENTER);
		add(pbotones,BorderLayout.SOUTH);
		
	}

}
