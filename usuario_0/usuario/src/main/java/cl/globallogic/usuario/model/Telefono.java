package cl.globallogic.usuario.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "TELEFONO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Telefono implements Serializable {
    @Id
    @Column(name="id_telefono")
    String id_telefono = UUID.randomUUID().toString();;

    @Column
    String id_usuario ;

    @Column
    String number ;

    @Column
    String citycode ;

    @Column
    String contrycode ;

    public String getId_telefono() {
        return id_telefono;
    }

    public void setId_telefono(String id_telefono) {
        this.id_telefono = id_telefono;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="id_usuario", nullable=false, insertable = false, updatable = false)
    //private Usuario usuario;



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getContrycode() {
        return contrycode;
    }

    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }

}
