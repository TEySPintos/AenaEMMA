package com.teys.aenaemma;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cgo on 12/08/16.
 */
public class SurveyItemAdapter extends ArrayAdapter<Questionnaire> {


    public SurveyItemAdapter(Context context, List<Questionnaire> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Questionnaire quest = getItem(position);
        ViewHolder holder;


        //Inflate view
        if(convertView==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.survey_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvIden = ((TextView) convertView.findViewById(R.id.surveylist_item_text_iden));
            holder.tvCodEncu = ((TextView) convertView.findViewById(R.id.surveylist_item_text_codEnc));
            holder.tvFecha = ((TextView) convertView.findViewById(R.id.surveylist_item_text_fecha));
            holder.tvCodAerop = ((TextView) convertView.findViewById(R.id.surveylist_item_text_codAerop));
            holder.tvCodIdioma = ((TextView) convertView.findViewById(R.id.surveylist_item_text_codIdioma));
            holder.tvPuerta = ((TextView) convertView.findViewById(R.id.surveylist_item_text_terminal));

            holder.btnDelete = ((ImageButton) convertView.findViewById(R.id.surveylist_item_button_delete));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Data population
        holder.tvIden.setText(String.valueOf(quest.getCdentrev()));
        holder.tvCodEncu.setText(String.valueOf(quest.getNencdor()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        holder.tvFecha.setText(sdf.format(quest.getFentrev()));
        holder.tvCodAerop.setText(quest.getCdaerenc());
        holder.tvCodIdioma.setText(quest.getIdioma());
        holder.tvPuerta.setText(quest.getPuerta());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                        View convertView =(View) v.getParent().getParent();
                        final ViewHolder holder = (ViewHolder) convertView.getTag();

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                        alertDialogBuilder.setMessage("¿Está seguro de que quiere eliminar la encuesta con identificador "+ holder.tvIden.getText() +"?" );
                        alertDialogBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            int iden = Integer.parseInt(holder.tvIden.getText().toString());

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                DBManager dbManager = new DBManager(getContext());
                                dbManager.deleteQuest(iden);
                                remove(quest);
                                return;
                            }
                        });
                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                return;
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });


        return convertView;
    }


    static class ViewHolder {
        public TextView tvIden;
        public TextView tvCodEncu;
        public TextView tvFecha;
        public TextView tvCodAerop;
        public TextView tvCodIdioma;
        public TextView tvPuerta;
        public ImageButton btnDelete;
    }
}
