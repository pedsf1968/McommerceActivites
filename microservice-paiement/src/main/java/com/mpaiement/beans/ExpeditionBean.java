package com.mpaiement.beans;

public class ExpeditionBean {
    public static final Integer EXPEDITION_EN_PREPARATION = 0;
    public static final Integer EXPEDITION_EXPEDIEE = 1;
    public static final Integer EXPEDITION_LIVREE = 2;

    private int id;
    private Integer idCommande;
    private Integer etat;

    public ExpeditionBean() {  }

    public ExpeditionBean(int idCommande) {
        this.idCommande = idCommande;
        this.etat = EXPEDITION_EN_PREPARATION;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {

        return "ExpeditionBean{" +
                "id=" + id +
                ", idCommande=" + idCommande +
                ", etat=" + etat +
                '}';
    }
}
