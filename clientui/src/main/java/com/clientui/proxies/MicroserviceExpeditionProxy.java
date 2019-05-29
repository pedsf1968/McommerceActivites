package com.clientui.proxies;

import com.clientui.beans.ExpeditionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-expedition")
public interface MicroserviceExpeditionProxy {

    @GetMapping( value = "/microservice-expedition/etat/{id}")
    String etatExpedition(@PathVariable("id") int id);

    @GetMapping(value = "/microservice-expedition/commandes/{idCommande}")
    ExpeditionBean recupererUneExpeditionByCommande(@PathVariable int idCommande);

    @GetMapping(value = "/microservice-expedition/expedition/{id}")
    ExpeditionBean recupererUneExpedition(@PathVariable("id") int id);

    @PostMapping(value = "microservice-expedition/expedition")
    ExpeditionBean ajouterExpedition(@RequestBody ExpeditionBean expedition);
}

