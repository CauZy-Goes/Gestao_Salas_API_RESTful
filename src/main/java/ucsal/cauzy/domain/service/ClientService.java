//package ucsal.cauzy.domain.service;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ucsal.cauzy.domain.entity.Client;
//import ucsal.cauzy.domain.repository.ClientRepository;
//
//@Service
//@RequiredArgsConstructor
//public class ClientService {
//
//    private final ClientRepository repository;
//    private final PasswordEncoder encoder;
//
//    public Client salvar(Client client){
//        var senhaCriptografada = encoder.encode(client.getClientSecret());
//        client.setClientSecret(senhaCriptografada);
//        return repository.save(client);
//    }
//
//    public Client obterPorClientID(String clientId){
//        return repository.findByClientId(clientId);
//    }
//}
