package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.repository.SolicitacoesRepository;
import ucsal.cauzy.domain.repository.StatusRepository;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;
import ucsal.cauzy.rest.mapper.SolicitacoesMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitacoesService {

    @Autowired
    private SolicitacoesRepository solicitacoesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitacoesMapper solicitacoesMapper;

    @Autowired
    private EspacoFisicoRepository espacoFisicoRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private EmailService emailService;

    public List<SolicitacoesDTO> findAll() {
        return null;
    }

    public Solicitacoes findById(Integer id) {
        return solicitacoesRepository.findById(id).get();
    }

    public List<SolicitacoesDTO> findByUsuarioSolicitanteId(Integer id) {
        return null;
    }

    public SolicitacoesDTO save(SolicitacoesDTO solicitacoesDTO) {
        Solicitacoes solicitacoes = tratarSolicitacoes(solicitacoesDTO);
        Solicitacoes savedSolicitacoes = solicitacoesRepository.save(solicitacoes);
        return null;
    }

    private Solicitacoes tratarSolicitacoes(SolicitacoesDTO solicitacoesDTO) {
        Solicitacoes solicitacoes = solicitacoesMapper.toEntity(solicitacoesDTO);

        Usuario usuarioAvaliador = solicitacoesDTO.idUsuarioAvaliador() == null ? null :
                usuarioRepository.findById(solicitacoesDTO.idUsuarioAvaliador()).orElse(null);

        Status status = solicitacoesDTO.idStatus() == null ? null :
                statusRepository.findById(solicitacoesDTO.idStatus()).orElse(null);

        solicitacoes.setUsuarioAvaliador(usuarioAvaliador);
        solicitacoes.setStatus(status);
        return solicitacoes;
    }

    public SolicitacoesDTO update(Integer id, SolicitacoesDTO solicitacoesDTO) {
        if (!solicitacoesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Solicitação", id);
        }
        Solicitacoes solicitacoes = tratarSolicitacoes(solicitacoesDTO);
        solicitacoes.setIdSolicitacoes(id);
        Solicitacoes updatedSolicitacoes = solicitacoesRepository.save(solicitacoes);

        emailService.enviarNotificacaoAlteracao(updatedSolicitacoes);

        return  null;
    }

    public void delete(Integer id) {
        if (!solicitacoesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Solicitação", id);
        }
        solicitacoesRepository.deleteById(id);
    }
}
