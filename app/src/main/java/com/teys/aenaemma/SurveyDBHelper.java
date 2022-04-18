package com.teys.aenaemma;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by cgo on 27/07/16.
 */
public class SurveyDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "AenaEMMA.db";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QueContract.QueEntry.TABLE_NAME;

    public SurveyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ QueContract.QueEntry.TABLE_NAME + " ("
            + QueContract.QueEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QueContract.QueEntry.COLUMN_NAME_IDEN  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDSEXO   +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDPAISNA +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDPAISRE +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDTRESER  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDSLAB + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_VIEN_RE  +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDEDAD + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDALOJIN +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDLOCACO +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_ULTIMODO +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_ACOMPTES +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDTERM    +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDMVIAJE  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_SITIOPARK +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDIDAVUE  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_TAUS  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_NPERS   +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_NNINOS  +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_RELACION  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDBILLET  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_NVIAJE  +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_ACTIV05  +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_ESTUDIOS +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_VOL12MES +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_P44FACTU  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_BULGRUPO  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_NPERBUL   +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CHEKINB  +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_USOAVE  +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_MOTIVOAVION2 +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDSPROF +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_PREFIERE  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CONSUME  +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_GAS_CONS  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_COMPRART  +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_GAS_COM  +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_PROD1  + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_PROD2  + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_PROD3  + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_PROD4  + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_PROD5 +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDIAPTOF +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDIAPTOO +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDOCIAAR +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDIAPTOD  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDALOJIN_LIT  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_NUMVUECA  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_NUMVUEPA +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDLOCADO +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDLOCADO_LIT  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDLOCACO_LIT  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDLOCADO_REG  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_MOTIVOAVION2_LIT  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_PQFUERA  +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_ACTIV05_LIT   +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_NUMVUEPA_COMP  + "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_ULTIMODO_LIT +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_IDIOMA  +    "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDAERENC  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDENTREV  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_PUERTA  +    "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_NENCDOR +    "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDPAISNA_LIT  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDPAISRE_LIT  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDIAPTOO_LIT  +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDIAPTOD_LIT +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDOCIAAR_LIT +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CDIAPTOF_LIT +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_NUMVUEPA_LIT +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CIAANTES +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_CIAANTES_LIT +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_PLAYA +  "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_FENTREV +    "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_HENTREV +    "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_HLLEGA + "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_HINI +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_HFIN +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_DISTRES +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_BARRIORES +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDCAMBIO +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CONEXFAC +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CONEXTOUR +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDSINOPE +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CDALOJEN +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_P14A +   "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_BARRIOCOM +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_NMODOS + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_MODO1 +  "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_EMPRESAPARK +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_PARKINGMAD + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_RESERPAKWEB +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_DROPOFF +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_MODULO + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_NVIAJE_NING +    "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_NPERS_SOLO + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_CONOCEWIFI + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_USADOWIFI + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_MOTIVOWIFI + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_DISTRES_LIT +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_P14A_LIT +   "   TEXT    , "

                + QueContract.QueEntry.COLUMN_NAME_USOAEROP + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_VECESAEROP + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_MODOAEROP + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_MODOAEROP_LIT +   "   TEXT    , "
                + QueContract.QueEntry.COLUMN_NAME_MOTIVOAEROP + "   INTEGER , "
                + QueContract.QueEntry.COLUMN_NAME_MOTIVOAEROP_LIT +   "   TEXT    , "

                + QueContract.QueEntry.COLUMN_NAME_UPLOADED + "   INTEGER "
                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
