package com.teys.aenaemma;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cgo on 26/07/16.
 */
public class QueContract {
    private static String DATE_FORMAT_COMPLETE ="dd/MM/yyyy HH:mm";

    public QueContract(){}

    /* Inner class that defines the table contents */
    public static abstract class QueEntry implements BaseColumns {
        public static final String TABLE_NAME = "questionnaire";
        public static final String COLUMN_NAME_ID = "id";

        public static final String COLUMN_NAME_IDEN = "IDEN";
        public static final String COLUMN_NAME_CDSEXO  = "CDSEXO";
        public static final String COLUMN_NAME_CDPAISNA = "CDPAISNA";
        public static final String COLUMN_NAME_CDPAISRE = "CDPAISRE";
        public static final String COLUMN_NAME_CDTRESER = "CDTRESER";
        public static final String COLUMN_NAME_CDSLAB = "CDSLAB";
        public static final String COLUMN_NAME_VIEN_RE = "VIEN_RE";
        public static final String COLUMN_NAME_CDEDAD = "CDEDAD";
        public static final String COLUMN_NAME_CDALOJIN = "CDALOJIN";
        public static final String COLUMN_NAME_CDLOCACO = "CDLOCACO";
        public static final String COLUMN_NAME_ULTIMODO = "ULTIMODO";
        public static final String COLUMN_NAME_ACOMPTES = "ACOMPTES";
        public static final String COLUMN_NAME_CDTERM = "CDTERM";
        public static final String COLUMN_NAME_CDMVIAJE = "CDMVIAJE";
        public static final String COLUMN_NAME_SITIOPARK = "SITIOPARK";
        public static final String COLUMN_NAME_CDIDAVUE = "CDIDAVUE";
        public static final String COLUMN_NAME_TAUS = "TAUS";
        public static final String COLUMN_NAME_NPERS = "NPERS";
        public static final String COLUMN_NAME_NNINOS = "NNINOS";
        public static final String COLUMN_NAME_RELACION = "RELACION";
        public static final String COLUMN_NAME_CDBILLET = "CDBILLET";
        public static final String COLUMN_NAME_NVIAJE = "NVIAJE";
        public static final String COLUMN_NAME_ACTIV05 = "ACTIV05";
        public static final String COLUMN_NAME_ESTUDIOS = "ESTUDIOS";
        public static final String COLUMN_NAME_VOL12MES = "VOL12MES";
        public static final String COLUMN_NAME_P44FACTU = "P44FACTU";
        public static final String COLUMN_NAME_BULGRUPO = "BULGRUPO";
        public static final String COLUMN_NAME_NPERBUL = "NPERBUL";
        public static final String COLUMN_NAME_CHEKINB = "CHEKINB";
        public static final String COLUMN_NAME_USOAVE = "USOAVE";
        public static final String COLUMN_NAME_MOTIVOAVION2 = "MOTIVOAVION2";
        public static final String COLUMN_NAME_CDSPROF = "CDSPROF";
        public static final String COLUMN_NAME_PREFIERE = "PREFIERE";
        public static final String COLUMN_NAME_CONSUME = "CONSUME";
        public static final String COLUMN_NAME_GAS_CONS = "GAS_CONS";
        public static final String COLUMN_NAME_COMPRART = "COMPRART";
        public static final String COLUMN_NAME_GAS_COM = "GAS_COM";
        public static final String COLUMN_NAME_PROD1 = "PROD1";
        public static final String COLUMN_NAME_PROD2 = "PROD2";
        public static final String COLUMN_NAME_PROD3 = "PROD3";
        public static final String COLUMN_NAME_PROD4 = "PROD4";
        public static final String COLUMN_NAME_PROD5 = "PROD5";
        public static final String COLUMN_NAME_CDIAPTOF = "CDIAPTOF";
        public static final String COLUMN_NAME_CDIAPTOO = "CDIAPTOO";
        public static final String COLUMN_NAME_CDOCIAAR = "CDOCIAAR";
        public static final String COLUMN_NAME_CDIAPTOD = "CDIAPTOD";
        public static final String COLUMN_NAME_CDALOJIN_LIT = "CDALOJIN_LIT";
        public static final String COLUMN_NAME_NUMVUECA = "NUMVUECA";
        public static final String COLUMN_NAME_NUMVUEPA = "NUMVUEPA";
        public static final String COLUMN_NAME_CDLOCADO = "CDLOCADO";
        public static final String COLUMN_NAME_CDLOCADO_LIT = "CDLOCADO_LIT";
        public static final String COLUMN_NAME_CDLOCACO_LIT = "CDLOCACO_LIT";
        public static final String COLUMN_NAME_CDLOCADO_REG = "CDLOCACO_REG";
        public static final String COLUMN_NAME_MOTIVOAVION2_LIT = "MOTIVOAVION2_LIT";
        public static final String COLUMN_NAME_PQFUERA = "PQFUERA";
        public static final String COLUMN_NAME_ACTIV05_LIT = "ACTIV05_LIT";
        public static final String COLUMN_NAME_NUMVUEPA_COMP = "NUMVUEPA_COMP";
        public static final String COLUMN_NAME_ULTIMODO_LIT = "ULTIMODO_LIT";
        public static final String COLUMN_NAME_IDIOMA = "IDIOMA";
        public static final String COLUMN_NAME_CDAERENC = "CDAERENC";
        public static final String COLUMN_NAME_CDENTREV = "CDENTREV";
        public static final String COLUMN_NAME_PUERTA = "PUERTA";
        public static final String COLUMN_NAME_NENCDOR = "NENCDOR";
        public static final String COLUMN_NAME_CDPAISNA_LIT = "CDPAISNA_LIT";
        public static final String COLUMN_NAME_CDPAISRE_LIT = "CDPAISRE_LIT";
        public static final String COLUMN_NAME_CDIAPTOO_LIT = "CDIAPTOO_LIT";
        public static final String COLUMN_NAME_CDIAPTOD_LIT = "CDIAPTOD_LIT";
        public static final String COLUMN_NAME_CDOCIAAR_LIT = "CDOCIAAR_LIT";
        public static final String COLUMN_NAME_CDIAPTOF_LIT = "CDIAPTOF_LIT";
        public static final String COLUMN_NAME_NUMVUEPA_LIT = "NUMVUEPA_LIT";
        public static final String COLUMN_NAME_CIAANTES = "CIAANTES";
        public static final String COLUMN_NAME_CIAANTES_LIT = "CIAANTES_LIT";
        public static final String COLUMN_NAME_PLAYA = "PLAYA";
        public static final String COLUMN_NAME_FENTREV = "FENTREV";
        public static final String COLUMN_NAME_HENTREV = "HENTREV";
        public static final String COLUMN_NAME_HLLEGA = "HLLEGA";
        public static final String COLUMN_NAME_HINI = "HINI";
        public static final String COLUMN_NAME_HFIN = "HFIN";
        public static final String COLUMN_NAME_DISTRES ="DISTRES";
        public static final String COLUMN_NAME_BARRIORES ="BARRIORES";
        public static final String COLUMN_NAME_CDCAMBIO = "CDCAMBIO";
        public static final String COLUMN_NAME_CONEXFAC = "CONEXFAC";
        public static final String COLUMN_NAME_CONEXTOUR ="CONEXTOUR";
        public static final String COLUMN_NAME_CDSINOPE = "CDSINOPE";
        public static final String COLUMN_NAME_CDALOJEN = "CDALOJEN";
        public static final String COLUMN_NAME_P14A = "P14A";
        public static final String COLUMN_NAME_BARRIOCOM ="BARRIOCOM";
        public static final String COLUMN_NAME_NMODOS = "NMODOS";
        public static final String COLUMN_NAME_MODO1 = "MODO1";
        public static final String COLUMN_NAME_EMPRESAPARK = "EMPRESAPARK";
        public static final String COLUMN_NAME_PARKINGMAD = "PARKINGMAD";
        public static final String COLUMN_NAME_RESERPAKWEB = "RESERPAKWEB";
        public static final String COLUMN_NAME_DROPOFF = "DROPOFF";
        public static final String COLUMN_NAME_MODULO = "MODULO";
        public static final String COLUMN_NAME_NVIAJE_NING = "NVIAJE_NING";
        public static final String COLUMN_NAME_NPERS_SOLO = "NPERS_SOLO";
        public static final String COLUMN_NAME_CONOCEWIFI = "CONOCE_WIFI";
        public static final String COLUMN_NAME_USADOWIFI = "USADO_WIFI";
        public static final String COLUMN_NAME_MOTIVOWIFI = "MOTIVO_WIFI";
        public static final String COLUMN_NAME_DISTRES_LIT = "DISTRES_LIT";
        public static final String COLUMN_NAME_P14A_LIT = "P14A_LIT";

        public static final String COLUMN_NAME_USOAEROP = "USOAEROP";
        public static final String COLUMN_NAME_VECESAEROP = "VECESAEROP";
        public static final String COLUMN_NAME_MODOAEROP = "MODOAEROP";
        public static final String COLUMN_NAME_MODOAEROP_LIT = "MODOAEROP_LIT";
        public static final String COLUMN_NAME_MOTIVOAEROP = "MOTIVOAEROP";
        public static final String COLUMN_NAME_MOTIVOAEROP_LIT = "MOTIVOAEROP_LIT";

        public static final String COLUMN_NAME_UPLOADED = "UPLOADED";
    }

    public static ContentValues toContentValues(Questionnaire que){
        ContentValues values = new ContentValues();

        values.put(QueEntry.COLUMN_NAME_IDEN, que.getIden());
        values.put(QueEntry.COLUMN_NAME_CDSEXO, que.getCdsexo());
        values.put(QueEntry.COLUMN_NAME_CDPAISNA, que.getCdpaisna());
        values.put(QueEntry.COLUMN_NAME_CDPAISRE, que.getCdpaisre());
        values.put(QueEntry.COLUMN_NAME_CDTRESER, que.getCdtreser());
        values.put(QueEntry.COLUMN_NAME_CDSLAB,	que.getCdslab());
        values.put(QueEntry.COLUMN_NAME_VIEN_RE, que.getVien_re());
        values.put(QueEntry.COLUMN_NAME_CDEDAD,	que.getCdedad());
        values.put(QueEntry.COLUMN_NAME_CDALOJIN, que.getCdalojin());
        values.put(QueEntry.COLUMN_NAME_CDLOCACO, que.getCdlocaco());
        values.put(QueEntry.COLUMN_NAME_ULTIMODO, que.getUltimodo());
        values.put(QueEntry.COLUMN_NAME_ACOMPTES, que.getAcomptes());
        values.put(QueEntry.COLUMN_NAME_CDTERM,	que.getCdterm());
        values.put(QueEntry.COLUMN_NAME_CDMVIAJE, que.getCdmviaje());
        values.put(QueEntry.COLUMN_NAME_SITIOPARK, que.getSitiopark());
        values.put(QueEntry.COLUMN_NAME_CDIDAVUE, que.getCdidavue());
        values.put(QueEntry.COLUMN_NAME_TAUS, que.getTaus());
        values.put(QueEntry.COLUMN_NAME_NPERS, que.getNpers());
        values.put(QueEntry.COLUMN_NAME_NNINOS,	que.getNninos());
        values.put(QueEntry.COLUMN_NAME_RELACION, que.getRelacion());
        values.put(QueEntry.COLUMN_NAME_CDBILLET, que.getCdbillet());
        values.put(QueEntry.COLUMN_NAME_NVIAJE,	que.getNviaje());
        values.put(QueEntry.COLUMN_NAME_ACTIV05, que.getActiv05());
        values.put(QueEntry.COLUMN_NAME_ESTUDIOS, que.getEstudios());
        values.put(QueEntry.COLUMN_NAME_VOL12MES, que.getVol12mes());
        values.put(QueEntry.COLUMN_NAME_P44FACTU, que.getP44factu());
        values.put(QueEntry.COLUMN_NAME_BULGRUPO, que.getBulgrupo());
        values.put(QueEntry.COLUMN_NAME_NPERBUL, que.getNperbul());
        values.put(QueEntry.COLUMN_NAME_CHEKINB, que.getChekinb());
        values.put(QueEntry.COLUMN_NAME_USOAVE , que.getUsoave());
        values.put(QueEntry.COLUMN_NAME_MOTIVOAVION2, que.getMotivoavion2());
        values.put(QueEntry.COLUMN_NAME_CDSPROF, que.getCdsprof());
        values.put(QueEntry.COLUMN_NAME_PREFIERE, que.getPrefiere());
        values.put(QueEntry.COLUMN_NAME_CONSUME, que.getConsume());
        values.put(QueEntry.COLUMN_NAME_GAS_CONS, que.getGas_cons());
        values.put(QueEntry.COLUMN_NAME_COMPRART, que.getComprart());
        values.put(QueEntry.COLUMN_NAME_GAS_COM, que.getGas_com());
        values.put(QueEntry.COLUMN_NAME_PROD1, que.getProd1());
        values.put(QueEntry.COLUMN_NAME_PROD2, que.getProd2());
        values.put(QueEntry.COLUMN_NAME_PROD3, que.getProd3());
        values.put(QueEntry.COLUMN_NAME_PROD4, que.getProd4());
        values.put(QueEntry.COLUMN_NAME_PROD5, que.getProd5());
        values.put(QueEntry.COLUMN_NAME_CDIAPTOF, que.getCdiaptof());
        values.put(QueEntry.COLUMN_NAME_CDIAPTOO, que.getCdiaptoo());
        values.put(QueEntry.COLUMN_NAME_CDOCIAAR, que.getCdociaar());
        values.put(QueEntry.COLUMN_NAME_CDIAPTOD, que.getCdiaptod());
        values.put(QueEntry.COLUMN_NAME_CDALOJIN_LIT, que.getCdalojin_lit());
        values.put(QueEntry.COLUMN_NAME_NUMVUECA, que.getNumvueca());
        values.put(QueEntry.COLUMN_NAME_NUMVUEPA, que.getNumvuepa());
        values.put(QueEntry.COLUMN_NAME_CDLOCADO, que.getCdlocado());
        values.put(QueEntry.COLUMN_NAME_CDLOCADO_LIT, que.getCdlocado_lit());
        values.put(QueEntry.COLUMN_NAME_CDLOCACO_LIT, que.getCdlocaco_lit());
        values.put(QueEntry.COLUMN_NAME_CDLOCADO_REG, que.getCdlocado_reg());
        values.put(QueEntry.COLUMN_NAME_MOTIVOAVION2_LIT, que.getMotivoavion2_lit());
        values.put(QueEntry.COLUMN_NAME_PQFUERA, que.getPqfuera());
        values.put(QueEntry.COLUMN_NAME_ACTIV05_LIT, que.getActiv05_lit());
        values.put(QueEntry.COLUMN_NAME_NUMVUEPA_COMP, que.getNumvuepa_comp());
        values.put(QueEntry.COLUMN_NAME_ULTIMODO_LIT, que.getUltimodo_lit());
        values.put(QueEntry.COLUMN_NAME_IDIOMA, que.getIdioma());
        values.put(QueEntry.COLUMN_NAME_CDAERENC, que.getCdaerenc());
        values.put(QueEntry.COLUMN_NAME_CDENTREV, que.getCdentrev());
        values.put(QueEntry.COLUMN_NAME_PUERTA,	que.getPuerta());
        values.put(QueEntry.COLUMN_NAME_NENCDOR, que.getNencdor());
        values.put(QueEntry.COLUMN_NAME_CDPAISNA_LIT, que.getCdpaisna_lit());
        values.put(QueEntry.COLUMN_NAME_CDPAISRE_LIT, que.getCdpaisre_lit());
        values.put(QueEntry.COLUMN_NAME_CDIAPTOO_LIT, que.getCdiaptoo_lit());
        values.put(QueEntry.COLUMN_NAME_CDIAPTOD_LIT, que.getCdiaptod_lit());
        values.put(QueEntry.COLUMN_NAME_CDOCIAAR_LIT, que.getCdociaar_lit());
        values.put(QueEntry.COLUMN_NAME_CDIAPTOF_LIT, que.getCdiaptof_lit());
        values.put(QueEntry.COLUMN_NAME_NUMVUEPA_LIT, que.getNumvuepa_lit());
        values.put(QueEntry.COLUMN_NAME_CIAANTES, que.getCiaantes());
        values.put(QueEntry.COLUMN_NAME_CIAANTES_LIT, que.getCiaantes_lit());
        values.put(QueEntry.COLUMN_NAME_PLAYA, que.getPlaya());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_COMPLETE);
        values.put(QueEntry.COLUMN_NAME_FENTREV, sdf.format(que.getFentrev()));
        values.put(QueEntry.COLUMN_NAME_HENTREV, sdf.format(que.getHentrev()));
        values.put(QueEntry.COLUMN_NAME_HLLEGA,	sdf.format(que.getHllega()));
        values.put(QueEntry.COLUMN_NAME_HINI, sdf.format(que.getHini()));
        values.put(QueEntry.COLUMN_NAME_HFIN, sdf.format(que.getHfin()));
        values.put(QueEntry.COLUMN_NAME_DISTRES, que.getDistres());
        values.put(QueEntry.COLUMN_NAME_BARRIORES, que.getBarriores());
        values.put(QueEntry.COLUMN_NAME_CDCAMBIO, que.getCdcambio());
        values.put(QueEntry.COLUMN_NAME_CONEXFAC, que.getConexfac());
        values.put(QueEntry.COLUMN_NAME_CONEXTOUR, que.getConextour());
        values.put(QueEntry.COLUMN_NAME_CDSINOPE, que.getCdsinope());
        values.put(QueEntry.COLUMN_NAME_CDALOJEN, que.getCdalojen());
        values.put(QueEntry.COLUMN_NAME_P14A, que.getP14a());
        values.put(QueEntry.COLUMN_NAME_BARRIOCOM, que.getBarriocom());
        values.put(QueEntry.COLUMN_NAME_NMODOS,	que.getNmodos());
        values.put(QueEntry.COLUMN_NAME_MODO1, que.getModo1());
        values.put(QueEntry.COLUMN_NAME_EMPRESAPARK, que.getEmpresapark());
        values.put(QueEntry.COLUMN_NAME_PARKINGMAD,	que.getParkingmad());
        values.put(QueEntry.COLUMN_NAME_RESERPAKWEB, que.getReserpakweb());
        values.put(QueEntry.COLUMN_NAME_DROPOFF, que.getDropoff());
        values.put(QueEntry.COLUMN_NAME_MODULO,	que.getModulo());
        values.put(QueEntry.COLUMN_NAME_NVIAJE_NING, que.getNviaje_ning());
        values.put(QueEntry.COLUMN_NAME_NPERS_SOLO,	que.getNpers_solo());

        values.put(QueEntry.COLUMN_NAME_CONOCEWIFI,	que.getConoceWifi());
        values.put(QueEntry.COLUMN_NAME_USADOWIFI, que.getUsadoWifi());
        values.put(QueEntry.COLUMN_NAME_MOTIVOWIFI,	que.getMotivoWifi());

        values.put(QueEntry.COLUMN_NAME_DISTRES_LIT, que.getDistres_lit());
        values.put(QueEntry.COLUMN_NAME_P14A_LIT, que.getP14a_lit());

        values.put(QueEntry.COLUMN_NAME_USOAEROP, que.getUsoAerop());
        values.put(QueEntry.COLUMN_NAME_VECESAEROP, que.getVecesAerop());
        values.put(QueEntry.COLUMN_NAME_MODOAEROP, que.getModoAerop());
        values.put(QueEntry.COLUMN_NAME_MODOAEROP_LIT, que.getModoAerop_lit());
        values.put(QueEntry.COLUMN_NAME_MOTIVOAEROP, que.getMotivoAerop());
        values.put(QueEntry.COLUMN_NAME_MOTIVOAEROP_LIT, que.getMotivoAerop_lit());


        values.put(QueEntry.COLUMN_NAME_UPLOADED, false);


        return values;
    }

    public static Questionnaire toQuestionnaire(Cursor c){
        String idQuest = c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_IDEN));
        String codEncuestador = c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_NENCDOR));
        String idioma = c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_IDIOMA));
        String airport = c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDAERENC));

        Questionnaire quest = new Questionnaire(idQuest, codEncuestador, idioma, airport);

        quest.setIden (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_IDEN )));
        quest.setCdsexo (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDSEXO  )));
        quest.setCdpaisna (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDPAISNA)));
        quest.setCdpaisre (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDPAISRE)));
        quest.setCdtreser (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDTRESER )));
        quest.setCdslab (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDSLAB)));
        quest.setVien_re (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_VIEN_RE )));
        quest.setCdedad (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDEDAD)));
        quest.setCdalojin (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDALOJIN)));
        quest.setCdlocaco (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDLOCACO)));
        quest.setUltimodo (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_ULTIMODO)));
        quest.setAcomptes (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_ACOMPTES)));
        quest.setCdterm (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDTERM   )));
        quest.setCdmviaje (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDMVIAJE )));
        quest.setSitiopark (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_SITIOPARK)));
        quest.setCdidavue (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDIDAVUE )));
        quest.setTaus (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_TAUS )));
        quest.setNpers (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_NPERS  )));
        quest.setNninos (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_NNINOS )));
        quest.setRelacion (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_RELACION )));
        quest.setCdbillet (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDBILLET )));
        quest.setNviaje (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_NVIAJE )));
        quest.setActiv05 (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_ACTIV05 )));
        quest.setEstudios (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_ESTUDIOS)));
        quest.setVol12mes (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_VOL12MES)));
        quest.setP44factu (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_P44FACTU )));
        quest.setBulgrupo (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_BULGRUPO )));
        quest.setNperbul (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_NPERBUL  )));
        quest.setChekinb (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CHEKINB )));
        quest.setUsoave (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_USOAVE )));
        quest.setMotivoavion2 (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_MOTIVOAVION2)));
        quest.setCdsprof (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDSPROF)));
        quest.setPrefiere (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_PREFIERE )));
        quest.setConsume (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CONSUME )));
        quest.setGas_cons (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_GAS_CONS )));
        quest.setComprart (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_COMPRART )));
        quest.setGas_com (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_GAS_COM )));
        quest.setProd1 (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_PROD1 )));
        quest.setProd2 (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_PROD2 )));
        quest.setProd3 (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_PROD3 )));
        quest.setProd4 (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_PROD4 )));
        quest.setProd5 (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_PROD5)));
        quest.setCdiaptof (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDIAPTOF)));
        quest.setCdiaptoo (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDIAPTOO)));
        quest.setCdociaar (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDOCIAAR)));
        quest.setCdiaptod (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDIAPTOD )));
        quest.setCdalojin_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDALOJIN_LIT )));
        quest.setNumvueca (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_NUMVUECA )));
        quest.setNumvuepa (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_NUMVUEPA)));
        quest.setCdlocado (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDLOCADO)));
        quest.setCdlocado_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDLOCADO_LIT )));
        quest.setCdlocaco_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDLOCACO_LIT )));
        quest.setCdlocado_reg (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDLOCADO_REG )));
        quest.setMotivoavion2_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_MOTIVOAVION2_LIT )));
        quest.setPqfuera (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_PQFUERA )));
        quest.setActiv05_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_ACTIV05_LIT  )));
        quest.setNumvuepa_comp (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_NUMVUEPA_COMP )));
        quest.setUltimodo_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_ULTIMODO_LIT)));
        quest.setIdioma (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_IDIOMA )));
        quest.setCdaerenc (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDAERENC )));
        quest.setCdentrev (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDENTREV )));
        quest.setPuerta (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_PUERTA )));
        quest.setNencdor (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_NENCDOR)));
        quest.setCdpaisna_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDPAISNA_LIT )));
        quest.setCdpaisre_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDPAISRE_LIT )));
        quest.setCdiaptoo_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDIAPTOO_LIT )));
        quest.setCdiaptod_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDIAPTOD_LIT)));
        quest.setCdociaar_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDOCIAAR_LIT)));
        quest.setCdiaptof_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CDIAPTOF_LIT)));
        quest.setNumvuepa_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_NUMVUEPA_LIT)));
        quest.setCiaantes (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CIAANTES)));
        quest.setCiaantes_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_CIAANTES_LIT)));
        quest.setPlaya (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_PLAYA)));

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_COMPLETE);
        try {
            quest.setFentrev((Date) sdf.parseObject(c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_FENTREV))));
            quest.setHentrev((Date) sdf.parseObject(c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_HENTREV))));
            quest.setHllega((Date) sdf.parseObject(c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_HLLEGA))));
            quest.setHini((Date) sdf.parseObject(c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_HINI))));
            quest.setHfin((Date) sdf.parseObject(c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_HFIN))));
        }catch (Exception e){
            e.printStackTrace();
        }
        quest.setDistres (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_DISTRES)));
        quest.setBarriores (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_BARRIORES)));
        quest.setCdcambio (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDCAMBIO)));
        quest.setConexfac (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CONEXFAC)));
        quest.setConextour (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CONEXTOUR)));
        quest.setCdsinope (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDSINOPE)));
        quest.setCdalojen (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CDALOJEN)));
        quest.setP14a (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_P14A)));
        quest.setBarriocom (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_BARRIOCOM)));
        quest.setNmodos (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_NMODOS)));
        quest.setModo1 (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_MODO1)));
        quest.setEmpresapark (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_EMPRESAPARK)));
        quest.setParkingmad (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_PARKINGMAD)));
        quest.setReserpakweb (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_RESERPAKWEB)));
        quest.setDropoff (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_DROPOFF)));
        quest.setModulo (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_MODULO)));
        quest.setNviaje_ning (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_NVIAJE_NING)));
        quest.setNpers_solo (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_NPERS_SOLO)));


        quest.setConoceWifi (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_CONOCEWIFI)));
        quest.setUsadoWifi (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_USADOWIFI)));
        quest.setMotivoWifi (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_MOTIVOWIFI)));
        quest.setDistres_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_DISTRES_LIT)));
        quest.setP14a_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_P14A_LIT)));

        quest.setUsoAerop (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_USOAEROP)));
        quest.setVecesAerop (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_VECESAEROP)));
        quest.setModoAerop (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_MODOAEROP)));
        quest.setModoAerop_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_MODOAEROP_LIT)));
        quest.setMotivoAerop (c.getInt(c.getColumnIndex(QueEntry.COLUMN_NAME_MOTIVOAEROP)));
        quest.setMotivoAerop_lit (c.getString(c.getColumnIndex(QueEntry.COLUMN_NAME_MOTIVOAEROP_LIT)));

        return quest;
    };

}
