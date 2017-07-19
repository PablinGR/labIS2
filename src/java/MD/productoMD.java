package MD;

import DP.productoDP;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class productoMD {

    productoDP productoDP;
    Connection con;
    Statement stm;
    String cadena;
    
    public productoMD(productoDP productoDP) {
        this.productoDP = productoDP;
    }
    
    private DataSource getLabJDBC() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/LabJDBC");
    }
    
    public void grabar() {
        DataSource dataSource;
        try {
            dataSource = this.getLabJDBC();
            con = dataSource.getConnection();
            stm = con.createStatement();
            java.sql.Date sqlDate = new java.sql.Date(productoDP.getFecha().getTime());
            
            cadena = "insert into APP.productoxx values(" + productoDP.getCodigo()
                    + ",'" + productoDP.getNombre() + "','" + productoDP.getDescripcion()
                    + "'," + productoDP.getValor() + ",'" + sqlDate + "')";
            System.out.println("Cadena:" + cadena);
            stm.execute(cadena);
            
        } catch (NamingException ex) {
            Logger.getLogger(productoMD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(productoMD.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
