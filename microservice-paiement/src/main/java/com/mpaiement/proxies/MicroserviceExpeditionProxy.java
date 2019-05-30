package com.mpaiement.proxies;

import com.mpaiement.beans.ExpeditionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-expedition")
public interface MicroserviceExpeditionProxy {

    @PostMapping(value = "/microservice-expedition/expedition")
    ResponseEntity<ExpeditionBean> ajouterExpedition(@RequestBody ExpeditionBean expeditionBean);
}
