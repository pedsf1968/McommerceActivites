package com.clientui.beans;

public class ExpeditionBean {
    private Integer id;
    private Integer idCommande;
    private Integer etat;

    public static final Integer EXPEDITION_EN_PREPARATION = 0;
    public static final Integer EXPEDITION_EXPEDIEE = 1;
    public static final Integer EXPEDITION_LIVREE = 2;

    public ExpeditionBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return "Expedition{" +
                "id=" + id +
                ", idCommande=" + idCommande +
                ", etat=" + etat +
                '}';
    }
}

