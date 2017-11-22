package ec.edu.upse.locatemev1.controladores.tabsControl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.ParametrosConexion;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.modelo.UbicacionUsuario;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class TabUbicacionFragment extends Fragment implements OnMapReadyCallback {
    ParametrosConexion conexion = new ParametrosConexion();
    List<UbicacionUsuario> lstUbicacionUsuarios = new ArrayList<UbicacionUsuario>();
    List<String> lstLatitud = new ArrayList<String>();
    List<String> lstLongitud = new ArrayList<String>();
    List<String> lstUsuario = new ArrayList<String>();
    private GoogleMap mMap;
    private MapView mapView;
    private Marker marcador;
    private View view;
    private LocationManager locationManager;
    AlertDialog alert = null;
    double lat = 0.0;
    double lon = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //hacer la peticion de las 10 ultimas ubicaciones por tutoriado
       new HttpUltimaUbicacion().execute();

       view = inflater.inflate(R.layout.fragment_tab_ubicacion, container, false);
       locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //validar si el gps esta activado o desactivado
       if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertNoGps();
       }
        for(int i=0;i<lstLatitud.size();i++){
            System.out.println("devuelve esto la lista anadida : " + lstLatitud.get(i).toString());
            // agregarMarcadorTutoriado(lstUbicacion.get(i).toString(),);
        }

       return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mMap = googleMap;

        mMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


       // miUbicacion();


       //agregarMarcadorTutoriado(-2.2325103,-80.8802052,"YO");



        //googleMap.addMarker(new MarkerOptions().position(new LatLng(-2.2325103,-80.8802052)).title("Ubicacion").snippet("usted esta aki"));
        //CameraPosition liberty = CameraPosition.builder().target(new LatLng(-2.2325103,-80.8802052)).zoom(16).bearing(0).tilt(45).build();
        //googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));



        // System.out.println("lat: "+location.getLatitude()+" long: "+location.getLongitude());

    }
  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(-2.2325103,-80.8802052)).title("Ubicacion").snippet("usted esta aki").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador1)));
        CameraPosition liberty = CameraPosition.builder().target(new LatLng(-2.2325103,-80.8802052)).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
*/
    public void registroAlerta( View view ){

       // Toast.makeText(this, "Usuario o Clave incorrecta", Toast.LENGTH_LONG).show();

    }




   /* private void agregarMarcador(double lat, double lon) {
        //ubicacion = "Longitud :" + lat + " Latitud : "+lon;
        LatLng coordenadas = new LatLng(lat, lon);
        CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Usted está aqui")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador1)));
        mMap.animateCamera(miubicacion);
        System.out.println("lat: "+lat+" long: "+lon);

    }*/

    private void agregarMarcadorTutoriado(double lat, double lon,String usuario) {
        //ubicacion = "Longitud :" + lat + " Latitud : "+lon;
        LatLng coordenadas = new LatLng(lat, lon);
        CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(usuario)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador1)));
        mMap.animateCamera(miubicacion);
        System.out.println("lat: "+lat+" long: "+lon);
    }

    /*
    private void actualizarUbicacion(Location location) {

        if (location != null) {

            lat = location.getLatitude();
            lon = location.getLongitude();
            agregarMarcador(lat, lon);
        }
    }
*/

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
/*
    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locListener);
    }*/
    private void AlertNoGps(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("El sistema GPS esta apagado, para el uso correcto de la aplicacion debe estar encendido. DESEA HABILITARLO?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();
        alert.show();
    }

    private class HttpUltimaUbicacion extends AsyncTask<Void, Void, Void > {
        GoogleMap googleMap;
        UbicacionUsuario ubicacionUsuario = new UbicacionUsuario();
        Usuario usuario = new Usuario();
        @Override
        protected Void doInBackground(Void... params) {
            try {

                for(int i = 0; i< VariablesGenerales.getListUsuario().size(); i++){

                    final String url = conexion.urlcompeta("ubicacionUsuario","ultimaDiezUbicacion/"+ VariablesGenerales.getListUsuario().get(i).getIdusuario());
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    ResponseEntity<UbicacionUsuario[]> response= restTemplate.getForEntity(url, UbicacionUsuario[].class);
                    //guardar en una lista las 10 ubicaciones
                    lstUbicacionUsuarios = Arrays.asList(response.getBody());

                    for(int j=0 ; j<lstUbicacionUsuarios.size();j++)
                    {
                        System.out.println(Double.valueOf(lstUbicacionUsuarios.get(j).getUsuUbiLatitud()) +  " "  + Double.valueOf(lstUbicacionUsuarios.get(j).getUsuUbiLongitud())+ " "+ VariablesGenerales.getListUsuario().get(i).getUsuUNombres());
                        //System.out.println(lstUbicacionUsuarios.get(j).getIdubicacion()+" "+ lstUbicacionUsuarios.get(j).getUsuUbiLongitud()+" "+lstUbicacionUsuarios.get(i).getUsuUbiLatitud());
                        //lstUbicacion.add(Double.valueOf(lstUbicacionUsuarios.get(j).getUsuUbiLatitud()) +  " "  + Double.valueOf(lstUbicacionUsuarios.get(j).getUsuUbiLongitud())+ " "+ VariablesGenerales.getListUsuario().get(i).getUsuUNombres());
                        lstLatitud.add(lstUbicacionUsuarios.get(j).getUsuUbiLatitud());
                        lstLongitud.add(lstUbicacionUsuarios.get(j).getUsuUbiLongitud());
                        lstUsuario.add(VariablesGenerales.getListUsuario().get(i).getUsuUNombres());
                        //System.out.println(Double.valueOf(lstUbicacionUsuarios.get(i).getUsuUbiLatitud()));
                        // System.out.println(Double.valueOf(lstUbicacionUsuarios.get(i).getUsuUbiLongitud()));
                        //agregarMarcadorTutoriado(googleMap,-2.2325103,-80.8802052,"YO");
                        //agregarMarcadorTutoriado(Double.valueOf(lstUbicacionUsuarios.get(j).getUsuUbiLatitud()),Double.valueOf(lstUbicacionUsuarios.get(j).getUsuUbiLongitud()),"Zuleyka");
                        //lst_Usuario.add(listaUsuarios.get(i).getUsuUNombres()+" "+listaUsuarios.get(i).getUsuUApellidos());

                    }


                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

}
