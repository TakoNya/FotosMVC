package control;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.DAOFoto;
import model.Foto;
import view.Vista;
        
public class Controlador implements ActionListener{
    private Vista vista;
    private Foto foto = new Foto();
    DAOFoto modelo = new DAOFoto();
    FileInputStream stream;
    int longitudBytes;
    boolean hayNuevaImagen;
    
    public enum Acciones{
        agregar_imagen,
        consultar_imagen,
        guardar_imagen,
        nueva_imagen
    }
    public Controlador(Vista vista){
        this.vista = vista;
    }
    
    public void abrirFormulario(){
        vista.setTitle("Agregar imagen");
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        this.vista.btnExaminar.setActionCommand("agregar_imagen");
        this.vista.btnExaminar.addActionListener(this);
        this.vista.btnConsultar.setActionCommand("consultar_imagen");
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnGuardar.setActionCommand("guardar_imagen");
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnNuevo.setActionCommand("nueva_imagen");
        this.vista.btnNuevo.addActionListener(this);
        nuevaImagen();
    }
    @Override
    public void actionPerformed(ActionEvent evento){
        switch(Acciones.valueOf(evento.getActionCommand())){
            case agregar_imagen:
                agregarImagen();
                break;
            case consultar_imagen:
                consultarImagen();
                break;
            case guardar_imagen:
                guardarImagen();
                break;
            case nueva_imagen:
                nuevaImagen();
                break;
        }
    }
    public void agregarImagen(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = fileChooser.showOpenDialog(null);
        if (estado == JFileChooser.APPROVE_OPTION){
            try{
                vista.lblImagen.setIcon(null);
                stream = new FileInputStream(fileChooser.getSelectedFile());
                this.longitudBytes = (int) fileChooser.getSelectedFile().length();
                try{
                    Image icono = ImageIO.read(fileChooser.getSelectedFile()).getScaledInstance(vista.lblImagen.getWidth(),
                            vista.lblImagen.getHeight(),
                            Image.SCALE_DEFAULT);
                    vista.lblImagen.setIcon(new ImageIcon(icono));
                    hayNuevaImagen = true;
                    vista.lblImagen.updateUI();
                }catch(IOException ex) {
                    JOptionPane.showMessageDialog(null, "imagen: " + ex);
                }
                
            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
        }
        
    }
    public void guardarImagen(){
        if (validarImagen()){
            foto.setId(Integer.parseInt(vista.txtId.getText()));
            Object[] datos = modelo.consultarPorld(foto);
            if (datos[0] == null){
                insertarImagen();
            }else {
                actualizarImagen();
            }
            }else{
            JOptionPane.showMessageDialog(null, "Complete todos los datos antes de guardar", "Error", JOptionPane.ERROR_MESSAGE);
            
            
        }
    }
    public void insertarImagen(){
        int id = Integer.parseInt(vista.txtId.getText());
        String descripcion = vista.txtDescripcion.getText();
        foto.setId(id);
        foto.setDescripcion(descripcion);
        foto.setImagen(stream);
        foto.setLongitud(longitudBytes);
        modelo.insertarFoto(foto);
        JOptionPane.showMessageDialog(null, "Se agregó correctamente", "Exito!",JOptionPane.INFORMATION_MESSAGE);
        nuevaImagen();
    }      
    public void actualizarImagen(){
        int id= Integer.parseInt(vista.txtId.getText());
        String descripcion = vista.txtDescripcion.getText();
        foto.setId(id);
        foto.setDescripcion(descripcion);
        modelo.actualizarFoto(foto);
        if (hayNuevaImagen){
            foto.setImagen(stream);
            foto.setLongitud(longitudBytes);
            modelo.actualizarImagen(foto);
        }
        JOptionPane.showMessageDialog(null,"Se actualizó correctamnte", "Exito!", JOptionPane.INFORMATION_MESSAGE);
        nuevaImagen();
    }
    
    public void consultarImagen(){
        Object[] datos;
        int id = Integer.parseInt(vista.txtId.getText());
        foto.setId(id);
        datos = modelo.consultarPorld(foto);
        if (datos[0] != null){
            vista.txtId.setText(datos[0].toString());
            vista.txtDescripcion.setText(datos[1].toString());
            InputStream input = modelo.consultarImagen(foto);
            ImageIcon imagen = Funciones.extraerImagen(input);
            vista.lblImagen.setIcon(imagen);
        }else{
            JOptionPane.showMessageDialog(null, "La imagen no exista(" + vista.txtId.getText() + ")");
            
        }
        hayNuevaImagen = false;
    }
    public void nuevaImagen(){
        
            int id = Integer.parseInt(vista.txtId.getText());
            vista.txtDescripcion.setText(null);
            vista.lblImagen.setIcon(null);
            hayNuevaImagen = false;
        }
    
        public boolean validarImagen() {
            try{
                int id = Integer.parseInt(vista.txtId.getText());
        }catch(Exception ex){
            return false;
        }
            if (vista.txtDescripcion.getText().trim().isEmpty()){
                return false;
            }
            if (vista.lblImagen.getIcon() == null) {
                return false;
            }
            return true;
    }
    
}
