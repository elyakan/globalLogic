package cl.globallogic.usuario.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @Column(name="id_usuario")
    String id_usuario = UUID.randomUUID().toString();;

    @Column
    String name ;

    @Column
    //@Pattern(regexp = "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",message="The mail is badly formed!")
    String email ;

    @Column
    String password ;

    @Column
    LocalDate fcreate;

    @Column
    LocalDate fmodified;

    @Column
    LocalDate last_login;

    @Column(name = "isactive", columnDefinition="BIT")
    Boolean isactive ;

    @OneToMany(mappedBy="id_usuario")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Telefono> telefono;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<Telefono> getTelefono() {
        return telefono;
    }

    public void setTelefono(List<Telefono> telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFcreate() {
        return fcreate;
    }

    public void setFcreate(LocalDate fcreate) {
        this.fcreate = fcreate;
    }

    public LocalDate getFmodified() {
        return fmodified;
    }

    public void setFmodified(LocalDate fmodified) {
        this.fmodified = fmodified;
    }

    public LocalDate getLast_login() {
        return last_login;
    }

    public void setLast_login(LocalDate last_login) {
        this.last_login = last_login;
    }
}
