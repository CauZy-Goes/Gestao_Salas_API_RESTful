package ucsal.cauzy.rest.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface GenericController {

//    Uma URI (Uniform Resource Identifier)
    default URI gerarHeaderLocation(Integer id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
