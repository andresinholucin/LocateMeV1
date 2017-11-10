package ec.edu.upse.locatemev1.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gitwyn_pc on 05/10/2017.
 */

public class TiempoSensado implements Parcelable {
    private Long idtiempoSensado;
    private int usuTiempoDescripcion;
    private String usuTiempoEstado;

    public Long getIdtiempoSensado() {
        return idtiempoSensado;
    }

    public void setIdtiempoSensado(Long idtiempoSensado) {
        this.idtiempoSensado = idtiempoSensado;
    }

    public int getUsuTiempoDescripcion() {
        return usuTiempoDescripcion;
    }

    public void setUsuTiempoDescripcion(int usuTiempoDescripcion) {
        this.usuTiempoDescripcion = usuTiempoDescripcion;
    }

    public String getUsuTiempoEstado() {
        return usuTiempoEstado;
    }

    public void setUsuTiempoEstado(String usuTiempoEstado) {
        this.usuTiempoEstado = usuTiempoEstado;
    }

    @Override
    public String toString() {
        return "{" +
                "idtiempoSensado=" + idtiempoSensado +
                ", usuTiempoDescripcion=" + usuTiempoDescripcion +
                ", usuTiempoEstado='" + usuTiempoEstado + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.idtiempoSensado);
        dest.writeInt(this.usuTiempoDescripcion);
        dest.writeString(this.usuTiempoEstado);
    }

    public TiempoSensado() {
    }

    protected TiempoSensado(Parcel in) {
        this.idtiempoSensado = (Long) in.readValue(Long.class.getClassLoader());
        this.usuTiempoDescripcion = in.readInt();
        this.usuTiempoEstado = in.readString();
    }

    public static final Creator<TiempoSensado> CREATOR = new Creator<TiempoSensado>() {
        @Override
        public TiempoSensado createFromParcel(Parcel source) {
            return new TiempoSensado(source);
        }

        @Override
        public TiempoSensado[] newArray(int size) {
            return new TiempoSensado[size];
        }
    };
}
