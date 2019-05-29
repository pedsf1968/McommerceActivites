package com.mexpedition.web.controller;

import com.mexpedition.dao.ExpeditionDao;
import com.mexpedition.model.Expedition;
import com.mexpedition.web.exceptions.ExpeditionNotFoundException;
import com.mexpedition.web.exceptions.ImpossibleAjouterExpeditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExpeditionController {

    @Autowired
    ExpeditionDao expeditionDao;

    @GetMapping(value = "/expedition")
    public List<Expedition> listeDesExpeditions() {
        List<Expedition> expeditions = expeditionDao.findAll();

        if(expeditions.isEmpty()) throw new ExpeditionNotFoundException("Aucune expédition n'est en cours !");

        return expeditions;
    }

    @PostMapping(value = "/expedition")
    public ResponseEntity<Expedition> ajouterExpedition(@RequestBody Expedition expedition){

        if(expedition.getEtat()< Expedition.EXPEDITION_EN_PREPARATION || expedition.getEtat()>Expedition.EXPEDITION_LIVREE)
            throw new ImpossibleAjouterExpeditionException("Etat inconnu !");

        Expedition nouvelleExpedition = expeditionDao.save(expedition);

        if(nouvelleExpedition==null) throw new ImpossibleAjouterExpeditionException("Impossible d'ajouter cette expédition !");

        return new ResponseEntity<Expedition>(nouvelleExpedition, HttpStatus.CREATED);
    }

    @GetMapping(value = "/expedition/{id}")
    public Optional<Expedition> recupererUneExpedition(@PathVariable int id){

        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent()) throw new ExpeditionNotFoundException("Cette expédition n'existe pas !");

        return expedition;
    }

    @GetMapping(value = "/etat/{id}")
    public ResponseEntity<String> etatExpedition(@PathVariable int id){
        String reponse = "Etat inconnu";
        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent()) throw new ExpeditionNotFoundException("Cette expédition n'existe pas !");

        if(expedition.get().getEtat().equals(Expedition.EXPEDITION_EN_PREPARATION))
            reponse = "En préparation";

        if(expedition.get().getEtat().equals(Expedition.EXPEDITION_EXPEDIEE))
            reponse = "Expédiée";

        if(expedition.get().getEtat().equals(Expedition.EXPEDITION_LIVREE))
            reponse = "Livrée";

        return new ResponseEntity<String>(reponse,HttpStatus.FOUND);
    }

    @GetMapping(value = "/commandes/{idCommande}")
    public Expedition recupererUneExpeditionByCommande(@PathVariable int idCommande){
        List<Expedition> expeditionList = expeditionDao.findAll();
        Expedition expedition = null;

        for(Expedition ex: expeditionList)
             if(ex.getIdCommande().equals(idCommande)) expedition = ex;

        if(expedition==null)
            throw new ExpeditionNotFoundException("Cette expédition n'existe pas !");

        return expedition;
    }


    @PutMapping(value = "/expedition")
    public ResponseEntity<Expedition> updateExpedition(@RequestBody Expedition expedition) {

        if(expedition.getEtat()< Expedition.EXPEDITION_EN_PREPARATION || expedition.getEtat()>Expedition.EXPEDITION_LIVREE)
            throw new ImpossibleAjouterExpeditionException("Etat inconnu !");

        Expedition nouvelleExpedition = expeditionDao.save(expedition);

        if(nouvelleExpedition==null) throw new ImpossibleAjouterExpeditionException("Impossible de modifier cette expédition !");

        return new ResponseEntity<Expedition>(nouvelleExpedition, HttpStatus.CREATED);
    }
}
