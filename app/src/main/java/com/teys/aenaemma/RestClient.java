package com.teys.aenaemma;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cgo on 20/08/16.
 */
public class RestClient {
//    private static final String NEWQUE_URL = "http://192.168.1.131:8080/AenaEMMA/rest/queemma/new";
    private static final String NEWQUE_URL = "http://213.99.51.19:8081/AenaEMMA/rest/queemma/new";
    private static final String TAG = RestClient.class.toString();

    private static RestClient instance = null;

    private Context mCtx;
    private int sendedQuesNumber, failedQueNumber, totalQueNumber=0;
    private DBManager dbManager;
    private RequestQueue mRequestQueue;


    private RestClient(Context ctx){
        mCtx = ctx;
        dbManager = new DBManager(ctx);
        mRequestQueue = Volley.newRequestQueue(ctx);
    }

    public static RestClient getInstance(Context ctx){
        if(instance==null){
            instance = new RestClient(ctx);
        }
        return instance;
    }


    public int sendQues(ArrayList<Questionnaire> quesList){
        if(instance ==null || totalQueNumber!=0){
            return 0;
        }

        sendedQuesNumber = 0;
        failedQueNumber = 0;


        if(quesList== null || quesList.isEmpty()){
            return sendedQuesNumber;
        }
        totalQueNumber=quesList.size();

        Gson gson = new Gson();

        for(Questionnaire que: quesList){
            final int queIden = Integer.parseInt(que.getCdentrev());
            String jsonStringBody = gson.toJson(que);
            JSONObject jsonBody = null;
            try {
                jsonBody = new JSONObject(jsonStringBody);
            } catch (Exception e){
                Log.e(TAG, "Unable to cast form gson to JSONObject");
                continue;
            }

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, NEWQUE_URL, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            addSendedQue(queIden);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            addFailedQue(queIden);
                            Log.i(TAG, "Failure when sending questionnaire with iden: " + queIden);
                            error.printStackTrace();
                        }
                    });

            mRequestQueue.add(jsObjRequest);

        }

        return sendedQuesNumber;
    }

    private void addSendedQue(int queIden){
        sendedQuesNumber++;
        dbManager.updateAsSended(queIden);
        checkAllQuesSended();
    }

    private void addFailedQue(int queIden){
        failedQueNumber++;
        checkAllQuesSended();
    }

    private void checkAllQuesSended() {
        if(failedQueNumber+sendedQuesNumber==totalQueNumber){
            if(sendedQuesNumber==totalQueNumber) {
                Toast.makeText(mCtx, "Se han enviado correctamente todos los cuestionarios", Toast.LENGTH_LONG).show();
            }  else if(failedQueNumber == totalQueNumber){
                Toast.makeText(mCtx, "No se ha podido enviar los cuestionarios, por favor intentelo más tarde", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mCtx, "Se han enviado correctamente "+sendedQuesNumber+", por favor vuelva intentar enviar los restantes", Toast.LENGTH_LONG).show();
            }
            totalQueNumber = 0;
        }
    }



}
