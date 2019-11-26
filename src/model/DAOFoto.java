package model;

import java.awt.HeadlessException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DAOFoto {
    Connection con;
    Conexion conectar = new Conexion();
    PreparedStatement ps;
    
    public void insertarFoto(Foto f){
        try{
            String sql = "insert into fotos(id, descripcion,imagen) values(?,?,?)";
            PreparedStatement ps;
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,f.getId());
            ps.setString(2, f.getDescripcion());
            ps.setBinaryStream(3, f.getImagen(), f.getLongitud());
            ps.executeUpdate();
        }catch(SQLException | HeadlessException ex){
            System.out.println("exception: " + ex);
        }
    }
    
    public void actualizarFoto(Foto f){
        try{
            String sql = "update fotos set descripcion=? where id=?";
            PreparedStatement ps;
            con = conectar.getConnection();
            ps= con.prepareStatement(sql);
            ps.setString(1, f.getDescripcion());
            ps.setInt(2, f.getId());
            ps.executeUpdate();
        }catch(SQLException |HeadlessException ex){
            System.out.println("exception : " + ex);
        }
    }
    public void actualizarImagen(Foto f) {
        try{
            String sql = "update fotos set imagen=? where id=?";
            PreparedStatement ps;
            con = conectar.getConnection();
            ps= con.prepareStatement(sql);
            ps.setBinaryStream(1, f.getImagen(), f.getLongitud());
            ps.setInt(2, f.getId());
            ps.executeUpdate();
        }catch(SQLException | HeadlessException ex){
            System.out.println("exception : " + ex);
        }
    }
    public Object[] consultarPorld(Foto f){
        String sql = "select id, descripcion from fotos where id=?";
        Object[] filas = new Object[2];
        try{ 
            con = conectar.getConnection();
            ps= con.prepareStatement(sql);
            ps.setInt(1, f.getId());
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                filas[0]= rs.getInt(1);  //id
                filas[1] = rs.getString(2);  //descripcion
            }
        }catch(Exception ex){
            System.out.println("exception :" + ex);
        }
        return filas;
    }
    
    public InputStream consultarImagen(Foto f){
        InputStream stream = null;
        try{
            String sql = "select imagen from fotos where id=?";
            con = conectar.getConnection();
            ps= con.prepareStatement(sql);
            ps.setInt(1, f.getId());
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                stream = rs.getBinaryStream(1);
            }
            return stream;
            
        }catch(Exception ex){
            System.out.println("exception :" + ex);
            return null;
        }
        
    }
}
