package ec.edu.upse.locatemev1.controladores.tabsControl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ec.edu.upse.locatemev1.R;
import ec.edu.upse.locatemev1.configuracion.VariablesGenerales;
import ec.edu.upse.locatemev1.modelo.UbicacionUsuario;
import ec.edu.upse.locatemev1.modelo.Usuario;

public class TabUbicacionFragment extends Fragment implements OnMapReadyCallback{
    List<UbicacionUsuario> lstUbicacionUsuarios = new ArrayList<UbicacionUsuario>();
    private GoogleMap mMap;
    private MapView mapView;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Intent intent = new Intent();
       new HttpUltimaUbicacion().execute();
       view = inflater.inflate(R.layout.fragment_tab_ubicacion, container, false);
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
        googleMap.addMarker(new MarkerOptions().position(new LatLng(-2.2325103,-80.8802052)).title("Ubicacion").snippet("usted esta aki").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador1)));
        CameraPosition liberty = CameraPosition.builder().target(new LatLng(-2.2325103,-80.8802052)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    public void registroAlerta( View view ){

       // Toast.makeText(this, "Usuario o Clave incorrecta", Toast.LENGTH_LONG).show();

    }


    private class HttpUltimaUbicacion extends AsyncTask<Void, Void, Void > {
        UbicacionUsuario ubicacionUsuario = new UbicacionUsuario();
        Usuario usuario = new Usuario();
        @Override
        protected Void doInBackground(Void... params) {
            try {

                for(int i=0 ; i< VariablesGenerales.getListUsuario().size();i++){

                    System.out.println("=== Imprime ===");
                    final String url = VariablesGenerales.strRuta+"ubicacionusuario/ubicacionUsuario/"+VariablesGenerales.getListUsuario().get(i).getIdusuario();
                    RestTemplate restTemplate = new RestTemplate();

                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                    ResponseEntity<UbicacionUsuario[]> response= restTemplate.getForEntity(url, UbicacionUsuario[].class);

                    lstUbicacionUsuarios = Arrays.asList(response.getBody());
                    for(int j=0 ; i<lstUbicacionUsuarios.size();i++)
                    {
                        System.out.println(lstUbicacionUsuarios.get(i).getIdubicacion()+" "+ lstUbicacionUsuarios.get(i).getUsuUbiLongitud()+" "+lstUbicacionUsuarios.get(i).getUsuUbiLatitud());
                        //lst_Usuario.add(listaUsuarios.get(i).getUsuUNombres()+" "+listaUsuarios.get(i).getUsuUApellidos());

                    }
                }


                //listaUsuarios = Arrays.asList(response.getBody());


                //listaUsuarios = Arrays.asList(response.getBody());
                // for(Usuario usuario: listaUsuarios){
                //listaUsuarios.add(usuario);
                // System.out.println(String.valueOf(usuario.getIdusuario()));
                // System.out.println(String.valueOf(usuario.getUsuUApellidos()));
                // }

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
