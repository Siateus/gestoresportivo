package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.service.AtletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;





}
