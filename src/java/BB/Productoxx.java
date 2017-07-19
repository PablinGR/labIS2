/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BB;

import Controller.ProductoxxJpaController;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pablingr
 */
@Named(value = "producto")
@RequestScoped
@Entity
@Table(name = "PRODUCTOXX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productoxx.findAll", query = "SELECT p FROM Productoxx p")
    , @NamedQuery(name = "Productoxx.findByCodigo", query = "SELECT p FROM Productoxx p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Productoxx.findByNombre", query = "SELECT p FROM Productoxx p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Productoxx.findByDescripcion", query = "SELECT p FROM Productoxx p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Productoxx.findByValor", query = "SELECT p FROM Productoxx p WHERE p.valor = :valor")
    , @NamedQuery(name = "Productoxx.findByFecha", query = "SELECT p FROM Productoxx p WHERE p.fecha = :fecha")})
public class Productoxx implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Integer codigo;
    @Size(max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Productoxx() {
    }

    public Productoxx(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productoxx)) {
            return false;
        }
        Productoxx other = (Productoxx) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BB.Productoxx[ codigo=" + codigo + " ]";
    }
    
    public void grabar()
    {
        ProductoxxJpaController productoJpaController = new ProductoxxJpaController();
        try {
            productoJpaController.create(this);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(Productoxx.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Productoxx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
