//se creo con request 
package DP;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "loginDP")//alias
@RequestScoped
public class loginDP {
    String usuario;
    String clave;
    
    //tienen un constructor vacio como carateristica
    public loginDP() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String verificar()
    {
        System.out.println("Usuario: "+ this.usuario);
        System.out.println("Clave: "+ this.clave);
        String retorno = "";
        if(usuario.equals(clave)){
            return "paginaProducto.xhtml";
        }
        else{
            return "paginaError.xhtml";
        }       
    }
}
