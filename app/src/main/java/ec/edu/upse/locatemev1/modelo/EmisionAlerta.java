package ec.edu.upse.locatemev1.modelo;

import java.sql.Date;

/**
 * Created by Mishell on 26/07/2017.
 */

public class EmisionAlerta {
    private Long idemisionAlerta;
    private String usuAlerCiudad;
    private String usuAlerCodigopais;
    private String usuAlerEstado;
    private Date usuAlerFecha;
    private String usuAlerLatitud;
    private String usuAlerLongitud;
    private String usuAlerPais;
    private String usuAlerProvincia;
    private String usuAlerViapublica;
    private TipoAlerta tipoAlerta;
    private Usuario usuario;

    public EmisionAlerta() {
    }

    public Long getIdemisionAlerta() {
        return idemisionAlerta;
    }

    public void setIdemisionAlerta(Long idemisionAlerta) {
        this.idemisionAlerta = idemisionAlerta;
    }

    public String getUsuAlerCiudad() {
        return usuAlerCiudad;
    }

    public void setUsuAlerCiudad(String usuAlerCiudad) {
        this.usuAlerCiudad = usuAlerCiudad;
    }

    public String getUsuAlerCodigopais() {
        return usuAlerCodigopais;
    }

    public void setUsuAlerCodigopais(String usuAlerCodigopais) {
        this.usuAlerCodigopais = usuAlerCodigopais;
    }

    public String getUsuAlerEstado() {
        return usuAlerEstado;
    }

    public void setUsuAlerEstado(String usuAlerEstado) {
        this.usuAlerEstado = usuAlerEstado;
    }

    public Date getUsuAlerFecha() {
        return usuAlerFecha;
    }

    public void setUsuAlerFecha(Date usuAlerFecha) {
        this.usuAlerFecha = usuAlerFecha;
    }

    public String getUsuAlerLatitud() {
        return usuAlerLatitud;
    }

    public void setUsuAlerLatitud(String usuAlerLatitud) {
        this.usuAlerLatitud = usuAlerLatitud;
    }

    public String getUsuAlerLongitud() {
        return usuAlerLongitud;
    }

    public void setUsuAlerLongitud(String usuAlerLongitud) {
        this.usuAlerLongitud = usuAlerLongitud;
    }

    public String getUsuAlerPais() {
        return usuAlerPais;
    }

    public void setUsuAlerPais(String usuAlerPais) {
        this.usuAlerPais = usuAlerPais;
    }

    public String getUsuAlerProvincia() {
        return usuAlerProvincia;
    }

    public void setUsuAlerProvincia(String usuAlerProvincia) {
        this.usuAlerProvincia = usuAlerProvincia;
    }

    public String getUsuAlerViapublica() {
        return usuAlerViapublica;
    }

    public void setUsuAlerViapublica(String usuAlerViapublica) {
        this.usuAlerViapublica = usuAlerViapublica;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
