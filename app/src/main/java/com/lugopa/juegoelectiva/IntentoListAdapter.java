package com.lugopa.juegoelectiva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lugopa.juegoelectiva.Model.Intento;
import com.lugopa.juegoelectiva.Model.Puntaje;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IntentoListAdapter extends ArrayAdapter<Intento> {
    private static final String TAG = "IntentoListAdapter";

    private Context mContext;
    private int mResource;

    public IntentoListAdapter(Context context, int resource, ArrayList<Intento> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // obtengo la informacion sobre el Intento
        String numero = getItem(position).getNumero();
        String descripcion = getItem(position).getDescripcion();
        String posicion = getItem(position).getPosicion();

        //creamos el objeto Intento con la informacion
        Intento intento = new Intento(numero, descripcion,posicion);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvPosicion = (TextView) convertView.findViewById(R.id.textView1_posicion);
        TextView tvNumero = (TextView) convertView.findViewById(R.id.textView2_numero);
        TextView tvDescripcion= (TextView) convertView.findViewById(R.id.textView3_descripcion);

        tvPosicion.setText(posicion);
        tvNumero.setText(numero);
        tvDescripcion.setText(descripcion);

        return convertView;
    }
}
