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

    /**
     * listeDesExpeditions : retourne la liste de toutes les expéditions
     *
     * @return List<Expedition>
     */
    @GetMapping(value = "/expedition")
    public ResponseEntity<List<Expedition>> listeDesExpeditions() {
        List<Expedition> listExpeditions = expeditionDao.findAll();

        if(listExpeditions.isEmpty()) throw new ExpeditionNotFoundException("Aucune expédition n'est en cours !");

        return new ResponseEntity<List<Expedition>>(listExpeditions,HttpStatus.FOUND);
    }

    /**
     * ajouterExpedition : ajoute une nouvelle expédition
     *
     * @param expedition à ajouter
     * @return expédition ajoutée avec l'identifiant de l'expédition
     */
    @PostMapping(value = "/expedition")
    public ResponseEntity<Expedition> ajouterExpedition(@RequestBody Expedition expedition){

        // vérifie que l'état est cohérent
        if(expedition.getEtat()< Expedition.EXPEDITION_EN_PREPARATION || expedition.getEtat()>Expedition.EXPEDITION_LIVREE)
            throw new ImpossibleAjouterExpeditionException("Etat inconnu !");

        // on ne peut pas écraser une autre expédition
        if(expedition.getId()!=null)
            if (!expeditionDao.findById(expedition.getId()).equals(Optional.empty()))
                throw new ImpossibleAjouterExpeditionException("Expédition déjà existante !");

        // faut-il tester aussi les numéros de commandes?
        // peut-on avoir des expéditions différentes pour la même commandes?
        Expedition nouvelleExpedition = expeditionDao.save(expedition);

        if(nouvelleExpedition==null) throw new ImpossibleAjouterExpeditionException("Impossible d'ajouter cette expédition !");

        return new ResponseEntity<Expedition>(nouvelleExpedition, HttpStatus.CREATED);
    }

    /**
     * recupererUneExpedition : récupère une expédition par son identifiant
     *
     * @param id identifiant de l'expédition
     * @return
     */
    @GetMapping(value = "/expedition/{id}")
    public Optional<Expedition> recupererUneExpedition(@PathVariable int id){

        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent()) throw new ExpeditionNotFoundException("Cette expédition n'existe pas !");

        return expedition;
    }

    /**
     * etatExpedition : retourne l'état d'une expédition sous forme de chaîne de caractères
     *
     * @param id identifiant de l'expédition
     * @return état sous forme de String
     */
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

    /**
     * recupererUneExpeditionByCommande : récupère une expédition par le numéro de commande
     *
     * @param idCommande identifiant de la commande
     * @return expédition trouvée
     */
    @GetMapping(value = "/commandes/{idCommande}")
    public Expedition recupererUneExpeditionByCommande(@PathVariable int idCommande){

    	Optional<Expedition> expedition = expeditionDao.findByIdCommande(idCommande);
    	
         if (!expedition.isPresent()) {
            throw new ExpeditionNotFoundException("Cette expedition n'existe pas");
        } else {
            return expedition.get();
        }
    }


    /**
     * updateExpedition : mise à jour d'une expédition
     *
     * @param expedition nouvelle expédition
     * @return expédition modifiée
     */
    @PutMapping(value = "/expedition")
    public ResponseEntity<Expedition> updateExpedition(@RequestBody Expedition expedition) {

        if(expedition.getEtat()< Expedition.EXPEDITION_EN_PREPARATION || expedition.getEtat()>Expedition.EXPEDITION_LIVREE)
            throw new ImpossibleAjouterExpeditionException("Etat inconnu !");

        // on ne peut pas mettre à jour une expédition inconnue
        if(expeditionDao.findById(expedition.getId())==null)
            throw new ExpeditionNotFoundException("Cette expédition n'existe pas !");

        Expedition nouvelleExpedition = expeditionDao.save(expedition);

        if(nouvelleExpedition==null) throw new ImpossibleAjouterExpeditionException("Impossible de modifier cette expédition !");

        return new ResponseEntity<Expedition>(nouvelleExpedition, HttpStatus.CREATED);
    }
}
