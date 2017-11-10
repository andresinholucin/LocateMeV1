package ec.edu.upse.locatemev1.modelo;

import java.util.Date;

/**
 * Created by Gitwym on 25/07/2017.
 */

public class UbicacionUsuario{

    private Long idubicacion;
    private String usuUbiCiudad;
    private String usuUbiCodigopais;
    private String usuUbiEstado;
    private Date usuUbiHoraFin;
    private Date usuUbiHoraInicio;
    private String usuUbiLatitud;
    private String usuUbiLongitud;
    private String usuUbiPais;
    private String usuUbiProvincia;
    private String usuUbiViapublica;
    private Usuario usuario;

    public UbicacionUsuario() {
    }

    public UbicacionUsuario(Long idubicacion, String usuUbiCiudad, String usuUbiCodigopais, String usuUbiEstado, Date usuUbiHoraFin, Date usuUbiHoraInicio, String usuUbiLatitud, String usuUbiLongitud, String usuUbiPais, String usuUbiProvincia, String usuUbiViapublica, Usuario usuario) {
        this.idubicacion = idubicacion;
        this.usuUbiCiudad = usuUbiCiudad;
        this.usuUbiCodigopais = usuUbiCodigopais;
        this.usuUbiEstado = usuUbiEstado;
        this.usuUbiHoraFin = usuUbiHoraFin;
        this.usuUbiHoraInicio = usuUbiHoraInicio;
        this.usuUbiLatitud = usuUbiLatitud;
        this.usuUbiLongitud = usuUbiLongitud;
        this.usuUbiPais = usuUbiPais;
        this.usuUbiProvincia = usuUbiProvincia;
        this.usuUbiViapublica = usuUbiViapublica;
        this.usuario = usuario;
    }

    public Long getIdubicacion() {
        return idubicacion;
    }

    public void setIdubicacion(Long idubicacion) {
        this.idubicacion = idubicacion;
    }

    public String getUsuUbiCiudad() {
        return usuUbiCiudad;
    }

    public void setUsuUbiCiudad(String usuUbiCiudad) {
        this.usuUbiCiudad = usuUbiCiudad;
    }

    public String getUsuUbiEstado() {
        return usuUbiEstado;
    }

    public void setUsuUbiEstado(String usuUbiEstado) {
        this.usuUbiEstado = usuUbiEstado;
    }

    public String getUsuUbiCodigopais() {
        return usuUbiCodigopais;
    }

    public void setUsuUbiCodigopais(String usuUbiCodigopais) {
        this.usuUbiCodigopais = usuUbiCodigopais;
    }

    public Date getUsuUbiHoraFin() {
        return usuUbiHoraFin;
    }

    public void setUsuUbiHoraFin(Date usuUbiHoraFin) {
        this.usuUbiHoraFin = usuUbiHoraFin;
    }

    public Date getUsuUbiHoraInicio() {
        return usuUbiHoraInicio;
    }

    public void setUsuUbiHoraInicio(Date usuUbiHoraInicio) {
        this.usuUbiHoraInicio = usuUbiHoraInicio;
    }

    public String getUsuUbiLatitud() {
        return usuUbiLatitud;
    }

    public void setUsuUbiLatitud(String usuUbiLatitud) {
        this.usuUbiLatitud = usuUbiLatitud;
    }

    public String getUsuUbiLongitud() {
        return usuUbiLongitud;
    }

    public void setUsuUbiLongitud(String usuUbiLongitud) {
        this.usuUbiLongitud = usuUbiLongitud;
    }

    public String getUsuUbiPais() {
        return usuUbiPais;
    }

    public void setUsuUbiPais(String usuUbiPais) {
        this.usuUbiPais = usuUbiPais;
    }

    public String getUsuUbiProvincia() {
        return usuUbiProvincia;
    }

    public void setUsuUbiProvincia(String usuUbiProvincia) {
        this.usuUbiProvincia = usuUbiProvincia;
    }

    public String getUsuUbiViapublica() {
        return usuUbiViapublica;
    }

    public void setUsuUbiViapublica(String usuUbiViapublica) {
        this.usuUbiViapublica = usuUbiViapublica;
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }




}
