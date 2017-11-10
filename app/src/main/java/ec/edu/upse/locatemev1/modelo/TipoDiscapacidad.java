package ec.edu.upse.locatemev1.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gitwyn_pc on 11/10/2017.
 */

public class TipoDiscapacidad implements Parcelable {
    private Long idtipoDiscapacidad;
    private String usuTipoDescripcion;
    private String usuTipoEstado;
    private String usuTipoObservaciones;

    public TipoDiscapacidad() {
    }

    public Long getIdtipoDiscapacidad() {
        return idtipoDiscapacidad;
    }

    public void setIdtipoDiscapacidad(Long idtipoDiscapacidad) {
        this.idtipoDiscapacidad = idtipoDiscapacidad;
    }

    public String getUsuTipoDescripcion() {
        return usuTipoDescripcion;
    }

    public void setUsuTipoDescripcion(String usuTipoDescripcion) {
        this.usuTipoDescripcion = usuTipoDescripcion;
    }

    public String getUsuTipoEstado() {
        return usuTipoEstado;
    }

    public void setUsuTipoEstado(String usuTipoEstado) {
        this.usuTipoEstado = usuTipoEstado;
    }

    public String getUsuTipoObservaciones() {
        return usuTipoObservaciones;
    }

    public void setUsuTipoObservaciones(String usuTipoObservaciones) {
        this.usuTipoObservaciones = usuTipoObservaciones;
    }

    @Override
    public String toString() {
        return "{" +
                "idtipoDiscapacidad=" + idtipoDiscapacidad +
                ", usuTipoDescripcion='" + usuTipoDescripcion + '\'' +
                ", usuTipoEstado='" + usuTipoEstado + '\'' +
                ", usuTipoObservaciones='" + usuTipoObservaciones + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.idtipoDiscapacidad);
        dest.writeString(this.usuTipoDescripcion);
        dest.writeString(this.usuTipoEstado);
        dest.writeString(this.usuTipoObservaciones);
    }

    protected TipoDiscapacidad(Parcel in) {
        this.idtipoDiscapacidad = (Long) in.readValue(Long.class.getClassLoader());
        this.usuTipoDescripcion = in.readString();
        this.usuTipoEstado = in.readString();
        this.usuTipoObservaciones = in.readString();
    }

    public static final Creator<TipoDiscapacidad> CREATOR = new Creator<TipoDiscapacidad>() {
        @Override
        public TipoDiscapacidad createFromParcel(Parcel source) {
            return new TipoDiscapacidad(source);
        }

        @Override
        public TipoDiscapacidad[] newArray(int size) {
            return new TipoDiscapacidad[size];
        }
    };
}
