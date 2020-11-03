package com.lugopa.juegoelectiva;

import android.animation.LayoutTransition;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lugopa.juegoelectiva.Model.Puntaje;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PersonListAdapter extends ArrayAdapter<Puntaje> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;

    public PersonListAdapter(Context context, int resource, ArrayList<Puntaje> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // obtengo la informacion sobre el puntaje / persona
        String nombre = getItem(position).getNombre();
        String dificultad = getItem(position).getDificultad();
        String puntaje = getItem(position).getPuntaje();
        String ubicacion = getItem(position).getUbicacion();

        String paisP = obtenerPais(ubicacion);

        //creamos el objeto Puntaje con la informacion
        Puntaje p = new Puntaje(nombre, puntaje, dificultad, paisP);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNombre = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvDificultad = (TextView) convertView.findViewById(R.id.textView3);
        TextView tvPuntaje = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvUbicacion = (TextView) convertView.findViewById(R.id.textView4);

        tvNombre.setText(nombre);
        tvDificultad.setText(dificultad);
        tvPuntaje.setText(puntaje);
        tvUbicacion.setText(paisP);
        return convertView;
    }

    private String obtenerPais(String ubic){
        String pais = null;
        for (int i=ubic.length()-1; i!=0; i--){
            if(ubic.charAt(i)==','){
                pais=Character.toString(ubic.charAt(i+1));
                for(int j=i+2; j< ubic.length(); j++){
                    pais = pais + ubic.charAt(j);
                }
                break;
            }
        }
        return pais;
    }
}
