package control;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Funciones {
    public static ImageIcon extraerImagen(InputStream stream) {
        ImageIcon icon;
        try{
            BufferedImage buffer= ImageIO.read(stream);
            icon = new ImageIcon(buffer);
            ImageIcon foto = icon;
            
            Image img = foto.getImage();
            
            Image newimg = img.getScaledInstance(140,170, java.awt.Image.SCALE_SMOOTH);
            
            ImageIcon newIcon = new ImageIcon(newimg);
            return newIcon;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Extrear imagen: " + e);
            return null;
        }
    }
    
}
