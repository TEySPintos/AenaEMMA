package com.teys.aenaemma;

import java.util.Date;

/**
 * Created by cgo on 21/06/16.
 */
public class Questionnaire {


    private int iden, cdsexo,  cdpaisna, cdpaisre , cdtreser, cdslab, vien_re, cdedad;
    private int cdalojin, cdlocaco, ultimodo, acomptes, cdterm, cdmviaje, sitiopark;
    private int cdidavue, taus, npers, nninos, relacion, cdbillet, nviaje, activ05, estudios;
    private int vol12mes, p44factu, bulgrupo, nperbul, chekinb, usoave, motivoavion2, cdsprof;
    private int prefiere, consume, gas_cons, comprart, gas_com, prod1, prod2, prod3, prod4, prod5;

    private int usoAerop, vecesAerop, modoAerop, motivoAerop;

    private String cdiaptof, cdiaptoo, cdociaar, cdiaptod, cdalojin_lit, numvueca, numvuepa;
    private String cdlocado, cdlocado_lit, cdlocaco_lit, cdlocado_reg, motivoavion2_lit, pqfuera, activ05_lit, numvuepa_comp, ultimodo_lit;
    private String idioma, cdaerenc, cdentrev, puerta, nencdor;
    private String cdpaisna_lit, cdpaisre_lit, cdiaptoo_lit, cdiaptod_lit, cdociaar_lit, cdiaptof_lit, numvuepa_lit;

    private String ciaantes, ciaantes_lit, playa, distres_lit, p14a_lit;

    private String modoAerop_lit, motivoAerop_lit;

    private Date  fentrev, hentrev, hllega, hini, hfin;

    //En principio estos campos todavía no se utilizan
    private int  distres, barriores, cdcambio , conexfac, conextour, cdsinope,cdalojen;
    private int  p14a, barriocom,nmodos, modo1,  empresapark, parkingmad, reserpakweb;
    private int  dropoff, modulo, nviaje_ning,  npers_solo;
    private int conoceWifi, usadoWifi, motivoWifi;





    public Questionnaire(String idQuest, String codEncuestador, String idioma, String airport) {
        this.cdentrev = idQuest;
        this.nencdor = codEncuestador;
        this.cdaerenc = airport;
        this.idioma = idioma;
    }

    /*
    * Iden del questionario
    */
    public String getCdentrev() {
        return cdentrev;
    }
    /*
    * Iden del questionario
     */
    public void setCdentrev(String cdentrev) {
        this.cdentrev = cdentrev;
    }

    /*
    * Cod del entrevistador
     */
    public String getNencdor() {
        return nencdor;
    }
    /*
    * Cod del entrevistador
    */
    public void setNencdor(String nencdor) {
        this.nencdor = nencdor;
    }

    /*
        * Cod del aeropuerto de la entrevista
        */
    public String getCdaerenc() {
        return cdaerenc;
    }
    /*
    * Cod del aeropuerto de la entrevista
     */
    public void setCdaerenc(String cdaerenc) {
        this.cdaerenc = cdaerenc;
    }

    /*
    * Cod del idioma en que se ha hecho la entrevista
     */
    public String getIdioma() {
        return idioma;
    }
    /*
     * Cod del idioma en que se ha hecho la entrevista
    */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }




    /*
                * Fecha de la entrevista
                 */
    public Date getFentrev() {
        return fentrev;
    }
    /*
        * Fecha de la entrevista
         */
    public void setFentrev(Date fentrev) {
        this.fentrev = fentrev;
    }
/*
* Hora de la entrevista
 */
    public Date getHentrev() {
        return hentrev;
    }
    /*
    * Hora de la entrevista
     */
    public void setHentrev(Date hentrev) {
        this.hentrev = hentrev;
    }



    /*
            * Hora a la que se ha iniciado la encuesta
             */
    public Date getHini() {
        return hini;
    }
    /*
    * Hora a la que se ha iniciado la encuesta
     */
    public void setHini(Date hini) {
        this.hini = hini;
    }
    /*
    * Hora a la que se ha finaliza la encuesta
     */
    public Date getHfin() {
        return hfin;
    }
    /*
    * Hora a la que se ha finaliza la encuesta
     */
    public void setHfin(Date hfin) {
        this.hfin = hfin;
    }
/*
* Puerta de embarque
 */
    public String getPuerta() {
        return puerta;
    }
    /*
    * Puerta de embarque
     */
    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    /*
    * Numero de vuelo principal
     */
    public String getNumvueca() {
        return numvueca;
    }
    /*
    * Numero de vuelo principal
    */
    public void setNumvueca(String numvueca) {
        this.numvueca = numvueca;
    }


    public int getIden() {
        return iden;
    }

    public void setIden(int iden) {
        this.iden = iden;
    }

/*
* Cod del pais de naciiento (P1)
 */
    public int getCdpaisna() {
        return cdpaisna;
    }
    /*
    * Cod del pais de naciiento (P1)
     */
    public void setCdpaisna(int cdpaisna) {
        this.cdpaisna = cdpaisna;
    }
    /*
    * Cod pais de residencia (P2)
     */
    public int getCdpaisre() {
        return cdpaisre;
    }
    /*
        * Cod pais de residencia (P2)
         */
    public void setCdpaisre(int cdpaisre) {
        this.cdpaisre = cdpaisre;
    }
    /*
    * Codigo de la localidad o area de residencia (P2a)
     */
    public String getCdlocado() {
        return cdlocado;
    }
    /*
        * Codigo de la localidad o area de residencia (P2a)
         */
    public void setCdlocado(String cdlocado) {
        this.cdlocado = cdlocado;
    }


    /*
    * Cod. tipo procedencia (P3)
     */
    public int getVien_re() {
        return vien_re;
    }
    /*
        * Cod. tipo procedencia (P3)
         */
    public void setVien_re(int vien_re) {
        this.vien_re = vien_re;
    }

    /*
        * Cod localidad acceso aeropuerto (P3a)
         */
    public void setCdlocaco(int cdlocaco) {
        this.cdlocaco = cdlocaco;
    }
    /*
            * Cod localidad acceso aeropuerto (P3a)
             */
    public int getCdlocaco() {
        return cdlocaco;
    }
    /*
    * Cod Aeropuerto procedencia (P3b)
    */
    public String getCdiaptoo() {
        return cdiaptoo;
    }
    /*
    * Cod Aeropuerto procedencia (P3b)
     */
    public void setCdiaptoo(String cdiaptoo) {
        this.cdiaptoo = cdiaptoo;
    }

    /*
    * Cod tipo alojamiento en localidad (P4)
     */
    public int getCdalojin() {
        return cdalojin;
    }
    /*
       * Cod tipo alojamiento en localidad (P4)
        */
    public void setCdalojin(int cdalojin) {
        this.cdalojin = cdalojin;
    }
/*
* Literal otros tipos de alojamiento (P4_lit)
 */
    public String getCdalojin_lit() {
        return cdalojin_lit;
    }
    /*
    * Literal otros tipos de alojamiento (P4_lit)
     */
    public void setCdalojin_lit(String cdalojin_lit) {
        this.cdalojin_lit = cdalojin_lit;
    }

    /*
    * Cod del ultimo modo de transporte hasta el aeropuerto (P5)
     */
    public int getUltimodo() {
        return ultimodo;
    }
    /*
        * Cod del ultimo modo de transporte hasta el aeropuerto (P5)
    */
    public void setUltimodo(int ultimodo) {
        this.ultimodo = ultimodo;
    }
/*
* Literal para otros modos de transporte (P5_lit)
 */
    public String getUltimodo_lit() {
        return ultimodo_lit;
    }
    /*
    * Literal para otros modos de transporte (P5_lit)
     */
    public void setUltimodo_lit(String ultimodo_lit) {
        this.ultimodo_lit = ultimodo_lit;
    }

    /*
                * Cod Sitio aparcamiento (P6)
                 */
    public int getSitiopark() {
        return sitiopark;
    }
    /*
    * Cod Sitio aparcamiento (P6)
     */

    public void setSitiopark( int sitiopark){
        this.sitiopark = sitiopark;
    }

    /*
    * Literal motivo parking pago exterior (P6b)
     */
    public String getPqfuera() {
        return pqfuera;
    }
    /*
        * Literal motivo parking pago exterior (P6b)
         */
    public void setPqfuera(String pqfuera) {
        this.pqfuera = pqfuera;
    }

    /*
    * Numero de acompañantes (P7)
     */
    public int getAcomptes() {
        return acomptes;
    }

    /*
    * Numero de acompañantes (P7)
     */
    public void setAcomptes(int acomptes) {
        this.acomptes = acomptes;
    }

    /*
            * Hora de llegada al aeropuerto (P8)
             */
    public Date getHllega() {
        return hllega;
    }
    /*
    * Hora de llegada al aeropuerto (P8)
    */
    public void setHllega(Date hllega) {
        this.hllega = hllega;
    }

    /*
        * Cod el aeropuerto de destino (P9)
         */
    public String getCdiaptod() {
        return cdiaptod;
    }

    /*
    * Cod el aeropuerto de destino (P9)
     */
    public void setCdiaptod(String cdiaptod) {
        this.cdiaptod = cdiaptod;
    }

    /*
    *   Número de vuelo real (P10)
     */
    public String getNumvuepa() {
        return numvuepa;
    }

    /*
    *   Número de vuelo real (P10)
     */
    public void setNumvuepa(String numvuepa) {
        this.numvuepa = numvuepa;
    }

    /*
    * Cod con el identificador de la compañia
     */
    public String getNumvuepa_comp() {
        return numvuepa_comp;
    }
    /*
        * Cod con el identificador de la compañia
         */
    public void setNumvuepa_comp(String numvuepa_comp) {
        this.numvuepa_comp = numvuepa_comp;
    }

    /*
        * Cod de transbordo o finalizacion de viaje (P11)
        */
    public int getCdterm() {
        return cdterm;
    }
    /*
    * Cod de transbordo o finalizacion de viaje (P11)
    */
    public void setCdterm(int cdterm) {
        this.cdterm = cdterm;
    }

    /*
    * Compañia aerea con la que continua el viaje (P12)
     */
    public String getCdociaar() {
        return cdociaar;
    }
    /*
    * Compañia aerea con la que continua el viaje (P12)
    */
    public void setCdociaar(String cdociaar) {
        this.cdociaar = cdociaar;
    }

    /*
    * Codigo del aeropuerto en el que finaliza el viaje (P13)
     */
    public String getCdiaptof() {
        return cdiaptof;
    }
    /*
    * Codigo del aeropuerto en el que finaliza el viaje (P13)
    */
    public void setCdiaptof(String cdiaptof) {
        this.cdiaptof = cdiaptof;
    }

    /*
    *  Código del motivo principal del viaje (P14)
    */
    public int getCdmviaje() {
        return cdmviaje;
    }
    /*
    *  Código del motivo principal del viaje (P14)
    */
    public void setCdmviaje(int cdmviaje) {
        this.cdmviaje = cdmviaje;
    }

    /*
    * Código de viaje ida o vuelta
    */
    public int getCdidavue() {
        return cdidavue;
    }
    /*
    * Código de viaje ida o vuelta
    */
    public void setCdidavue(int cdidavue) {
        this.cdidavue = cdidavue;
    }

    /*
    * Numero de dias del viaje
     */
    public int getTaus() {
        return taus;
    }
    /*
     * Numero de dias del viaje
    */
    public void setTaus(int taus) {
        this.taus = taus;
    }

    /*
    * Num de personas en el grupo
     */
    public int getNpers() {
        return npers;
    }

    /*
    * Num de personas en el grupo
     */
    public void setNpers(int npers) {
        this.npers = npers;
    }

    /*
        * Numero de niños entre los acompañantes (P17)
         */
    public int getNninos() {
        return nninos;
    }
    /*
    * Numero de niños entre los acompañantes (P17)
    */
    public void setNninos(int nninos) {
        this.nninos = nninos;
    }

    /*
    * Cod.de relación entre los acompañantes (P18)
     */
    public int getRelacion() {
        return relacion;
    }
    /*
       * Cod.de relación entre los acompañantes (P18)
        */
    public void setRelacion(int relacion) {
        this.relacion = relacion;
    }
/*
* Numero de dias con los que se reservo el billete (P19)
 */
    public int getCdtreser() {
        return cdtreser;
    }
    /*
    * Numero de dias con los que se reservo el billete (P19)
     */
    public void setCdtreser(int cdtreser) {
        this.cdtreser = cdtreser;
    }

    /*
    * Cod. con el tipo de billete (P20)
     */
    public int getCdbillet() {
        return cdbillet;
    }
    /*
        * Cod. con el tipo de billete (P20)
         */
    public void setCdbillet(int cdbillet) {
        this.cdbillet = cdbillet;
    }


    /*
    * Num de viajes en los ultimos 12 meses (P21)
     */
    public int getNviaje() {
        return nviaje;
    }
    /*
        * Num de viajes en los ultimos 12 meses (P21)
         */
    public void setNviaje(int nviaje) {
        this.nviaje = nviaje;
    }

    /*
    * Num veces misma ruta (P22)
     */
    public int getVol12mes() {
        return vol12mes;
    }
    /*
    * Num veces misma ruta (P22)
    */
    public void setVol12mes(int vol12mes) {
        this.vol12mes = vol12mes;
    }

    /*
    * Se ha facturado equipaje (P23)
     */
    public int getP44factu() {
        return p44factu;
    }
    /*
        * Se ha facturado equipaje (P23)
         */
    public void setP44factu(int p44factu) {
        this.p44factu = p44factu;
    }
/*
* Numero de bultos facturados (P23a)
 */
    public int getBulgrupo() {
        return bulgrupo;
    }
    /*
    * Numero de bultos facturados (P23a)
     */
    public void setBulgrupo(int bulgrupo) {
        this.bulgrupo = bulgrupo;
    }
/*
* Numero de personas con bultos (P24)
 */
    public int getNperbul() {
        return nperbul;
    }
    /*
    * Numero de personas con bultos (P24)
     */
    public void setNperbul(int nperbul) {
        this.nperbul = nperbul;
    }
/*
* Cod de obtención de tarjeta de embarque (P25)
 */
    public int getChekinb() {
        return chekinb;
    }

    /*
* Cod de obtención de tarjeta de embarque (P25)
 */
    public void setChekinb(int chekinb) {
        this.chekinb = chekinb;
    }


//-----P26B
    public int getUsoAerop() {
        return usoAerop;
    }

    public void setUsoAerop(int usoAerop) {
        this.usoAerop = usoAerop;
    }


    public int getVecesAerop() {
        return vecesAerop;
    }

    public void setVecesAerop(int vecesAerop) {
        this.vecesAerop = vecesAerop;
    }


    public int getModoAerop() {
        return modoAerop;
    }

    public void setModoAerop(int modoAerop) {
        this.modoAerop = modoAerop;
    }


    public String getModoAerop_lit() {
        return modoAerop_lit;
    }

    public void setModoAerop_lit(String modoAerop_lit) {
        this.modoAerop_lit = modoAerop_lit;
    }

//-----P27B

    public int getMotivoAerop() {
        return motivoAerop;
    }

    public void setMotivoAerop(int motivoAerop) {
        this.motivoAerop = motivoAerop;
    }


    public String getMotivoAerop_lit() {
        return motivoAerop_lit;
    }

    public void setMotivoAerop_lit(String motivoAerop_lit) {
        this.motivoAerop_lit = motivoAerop_lit;
    }

//-----

    public int getUsoave() {
        return usoave;
    }
    /*
        * Ha usado AVE (P26)
         */
    public void setUsoave(int usoave) {
        this.usoave = usoave;
    }
/*
* Cod motivo uso avion (P27)
 */
    public int getMotivoavion2() {
        return motivoavion2;
    }
    /*
    * Cod motivo uso avion (P27)
     */
    public void setMotivoavion2(int motivoavion2) {
        this.motivoavion2 = motivoavion2;
    }
/*
* Literal con otros motivos (P27_otros)
 */
    public String getMotivoavion2_lit() {
        return motivoavion2_lit;
    }
    /*
    * Literal con otros motivos (P27_otros)
     */
    public void setMotivoavion2_lit(String motivoavion2_lit) {
        this.motivoavion2_lit = motivoavion2_lit;
    }

    /*
        * Cod Preferencia medio transporte (P28)
         */
    public int getPrefiere() {
        return prefiere;
    }
    /*
        * Cod Preferencia medio transporte (P28)
         */
    public void setPrefiere(int prefiere) {
        this.prefiere = prefiere;
    }
/*
* Ha consumido producto restauración (P29)
 */
    public int getConsume() {
        return consume;
    }
    /*
    * Ha consumido producto restauración (P29)
     */
    public void setConsume(int consume) {
        this.consume = consume;
    }

    /*
    * Numero del gasto en restauracion (P29a)
     */
    public int getGas_cons() {
        return gas_cons;
    }
    /*
        * Numero del gasto en restauracion (P29a)
         */
    public void setGas_cons(int gas_cons) {
        this.gas_cons = gas_cons;
    }
/*
* Ha comprado algun producto (P30)
 */
    public int getComprart() {
        return comprart;
    }
    /*
    * Ha comprado algun producto (P30)
     */
    public void setComprart(int comprart) {
        this.comprart = comprart;
    }

    /*
    * Numero de gasto en productos (P30b)
     */
    public int getGas_com() {
        return gas_com;
    }

    /*
    * Numero de gasto en productos (P30b)
     */
    public void setGas_com(int gas_com) {
        this.gas_com = gas_com;
    }
/*
* Cod tipo producto (P30b)
 */
    public int getProd1() {
        return prod1;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public void setProd1(int prod1) {
        this.prod1 = prod1;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public int getProd2() {
        return prod2;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public void setProd2(int prod2) {
        this.prod2 = prod2;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public int getProd3() {
        return prod3;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public void setProd3(int prod3) {
        this.prod3 = prod3;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public int getProd4() {
        return prod4;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public void setProd4(int prod4) {
        this.prod4 = prod4;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public int getProd5() {
        return prod5;
    }
    /*
    * Cod tipo producto (P30b)
     */
    public void setProd5(int prod5) {
        this.prod5 = prod5;
    }
/*
* Cod de situación laboral (P31)
 */
    public int getCdslab() {
        return cdslab;
    }
    /*
    * Cod de situación laboral (P31)
     */
    public void setCdslab(int cdslab) {
        this.cdslab = cdslab;
    }

    /*
    * Cod Profesion (P32)
     */
    public int getCdsprof() {
        return cdsprof;
    }
    /*
    * Cod Profesion (P32)
     */
    public void setCdsprof(int cdsprof) {
        this.cdsprof = cdsprof;
    }

    /*
        * Cod de tipo de actividad de la empresa (P33)
         */
    public int getActiv05() {
        return activ05;
    }
    /*
        * Cod de tipo de actividad de la empresa (P33)
         */
    public void setActiv05(int activ05) {
        this.activ05 = activ05;
    }
    /*
    * Literal para otro tipo de actividad de la empresa (P33)
             */
    public void setActiv05_lit(String activ05_lit) {
        this.activ05_lit = activ05_lit;
    }
    /*
    * Literal para otro tipo de actividad de la empresa (P33)
             */
    public String getActiv05_lit(){
        return activ05_lit;
    }

    /*
    * Cod Estudios (P34)
     */
    public int getEstudios() {
        return estudios;
    }
    /*
        * Cod Estudios (P34)
         */
    public void setEstudios(int estudios) {
        this.estudios = estudios;
    }

    /*
    * Cod edad (P35)
     */
    public int getCdedad() {
        return cdedad;
    }
    /*
        * Cod edad (P35)
         */
    public void setCdedad(int cdedad) {
        this.cdedad = cdedad;
    }

    /*
    * Cod sexo (P36)
     */
    public int getCdsexo() {
        return cdsexo;
    }
    /*
        * Cod sexo (P36)
         */
    public void setCdsexo(int cdsexo) {
        this.cdsexo = cdsexo;
    }


    public String getPlaya() {
        return playa;
    }

    public void setPlaya(String playa) {
        this.playa = playa;
    }

    public String getCiaantes() {
        return ciaantes;
    }

    public void setCiaantes(String ciaantes) {
        this.ciaantes = ciaantes;
    }

    public String getCiaantes_lit() {
        return ciaantes_lit;
    }

    public void setCiaantes_lit(String ciaantes_lit) {
        this.ciaantes_lit = ciaantes_lit;
    }



    //CAMPOS LITERALES PARA CUMPLIMENTAR DICCIONARIOS CON OTROS

    public String getCdpaisna_lit() {
        return cdpaisna_lit;
    }

    public void setCdpaisna_lit(String cdpaisna_lit) {
        this.cdpaisna_lit = cdpaisna_lit;
    }

    public String getCdpaisre_lit() {
        return cdpaisre_lit;
    }

    public void setCdpaisre_lit(String cdpaisre_lit) {
        this.cdpaisre_lit = cdpaisre_lit;
    }

    public String getCdiaptoo_lit() {
        return cdiaptoo_lit;
    }

    public void setCdiaptoo_lit(String cdiaptoo_lit) {
        this.cdiaptoo_lit = cdiaptoo_lit;
    }

    public String getCdiaptod_lit() {
        return cdiaptod_lit;
    }

    public void setCdiaptod_lit(String cdiaptod_lit) {
        this.cdiaptod_lit = cdiaptod_lit;
    }

    public String getCdociaar_lit() {
        return cdociaar_lit;
    }

    public void setCdociaar_lit(String cdociaar_lit) {
        this.cdociaar_lit = cdociaar_lit;
    }

    public String getCdiaptof_lit() {
        return cdiaptof_lit;
    }

    public void setCdiaptof_lit(String cdiaptof_lit) {
        this.cdiaptof_lit = cdiaptof_lit;
    }

    public String getNumvuepa_lit() {
        return numvuepa_lit;
    }

    public void setNumvuepa_lit(String numvuepa_lit) {
        this.numvuepa_lit = numvuepa_lit;
    }

    public String getCdlocado_lit() {
        return cdlocado_lit;
    }

    public void setCdlocado_lit(String cdlocado_lit) {
        this.cdlocado_lit = cdlocado_lit;
    }

    public String getCdlocaco_lit() {
        return cdlocaco_lit;
    }

    public void setCdlocaco_lit(String cdlocaco_lit) {
        this.cdlocaco_lit = cdlocaco_lit;
    }

    public String getCdlocado_reg() {
        return cdlocado_reg;
    }

    public void setCdlocado_reg(String cdlocado_reg) {
        this.cdlocado_reg = cdlocado_reg;
    }

    //CAMPOS SIN USAR SOLO PARA GENERAR JSON


    public int getDistres() {
        return distres;
    }

    public void setDistres(int distres) {
        this.distres = distres;
    }

    public int getBarriores() {
        return barriores;
    }

    public void setBarriores(int barriores) {
        this.barriores = barriores;
    }

    public int getCdcambio() {
        return cdcambio;
    }

    public void setCdcambio(int cdcambio) {
        this.cdcambio = cdcambio;
    }



    public int getConexfac() {
        return conexfac;
    }

    public void setConexfac(int conexfac) {
        this.conexfac = conexfac;
    }

    public int getConextour() {
        return conextour;
    }

    public void setConextour(int conextour) {
        this.conextour = conextour;
    }

    public int getCdsinope() {
        return cdsinope;
    }

    public void setCdsinope(int cdsinope) {
        this.cdsinope = cdsinope;
    }

    public int getCdalojen() {
        return cdalojen;
    }

    public void setCdalojen(int cdalojen) {
        this.cdalojen = cdalojen;
    }

    public int getP14a() {
        return p14a;
    }

    public void setP14a(int p14a) {
        this.p14a = p14a;
    }

    public int getBarriocom() {
        return barriocom;
    }

    public void setBarriocom(int barriocom) {
        this.barriocom = barriocom;
    }



    public int getNmodos() {
        return nmodos;
    }

    public void setNmodos(int nmodos) {
        this.nmodos = nmodos;
    }

    public int getModo1() {
        return modo1;
    }

    public void setModo1(int modo1) {
        this.modo1 = modo1;
    }

    public int getEmpresapark() {
        return empresapark;
    }

    public void setEmpresapark(int empresapark) {
        this.empresapark = empresapark;
    }

    public int getParkingmad() {
        return parkingmad;
    }

    public void setParkingmad(int parkingmad) {
        this.parkingmad = parkingmad;
    }

    public int getReserpakweb() {
        return reserpakweb;
    }

    public void setReserpakweb(int reserpakweb) {
        this.reserpakweb = reserpakweb;
    }

    public int getDropoff() {
        return dropoff;
    }

    public void setDropoff(int dropoff) {
        this.dropoff = dropoff;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public int getNviaje_ning() {
        return nviaje_ning;
    }

    public void setNviaje_ning(int nviaje_ning) {
        this.nviaje_ning = nviaje_ning;
    }

    public int getNpers_solo() {
        return npers_solo;
    }

    public void setNpers_solo(int npers_solo) {
        this.npers_solo = npers_solo;
    }

    public String getDistres_lit() {
        return distres_lit;
    }

    public void setDistres_lit(String distres_lit) {
        this.distres_lit = distres_lit;
    }

    public String getP14a_lit() {
        return p14a_lit;
    }

    public void setP14a_lit(String p14a_lit) {
        this.p14a_lit = p14a_lit;
    }

    public int getConoceWifi() {
        return conoceWifi;
    }

    public void setConoceWifi(int conoceWifi) {
        this.conoceWifi = conoceWifi;
    }

    public int getUsadoWifi() {
        return usadoWifi;
    }

    public void setUsadoWifi(int usadoWifi) {
        this.usadoWifi = usadoWifi;
    }

    public int getMotivoWifi() {
        return motivoWifi;
    }

    public void setMotivoWifi(int motivoWifi) {
        this.motivoWifi = motivoWifi;
    }
}
