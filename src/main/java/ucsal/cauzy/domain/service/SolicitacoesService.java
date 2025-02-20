package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.SolicitacoesRepository;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import ucsal.cauzy.domain.service.exceptions.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;
import ucsal.cauzy.rest.mapper.SolicitacoesMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitacoesService {

    @Autowired
    private SolicitacoesRepository solicitacoesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitacoesMapper solicitacoesMapper;

    public List<SolicitacoesDTO> findAll() {
        return solicitacoesRepository.findAll()
                .stream()
                .map(solicitacoesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SolicitacoesDTO findById(Integer id) {
        return solicitacoesRepository.findById(id)
                .map(solicitacoesMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<SolicitacoesDTO> findByUsuarioSolicitanteId(Integer id) {
        return solicitacoesRepository.findByUsuarioSolicitante_IdUsuario(id)
                .stream()
                .map(solicitacoesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SolicitacoesDTO save(SolicitacoesDTO solicitacoesDTO) {
        Solicitacoes solicitacoes = solicitacoesMapper.toEntity(solicitacoesDTO);
        Usuario usuarioAvaliador = solicitacoesDTO.getIdUsuarioAvaliador() == null ? null :
                usuarioRepository.findById(solicitacoesDTO.getIdUsuarioAvaliador()).orElse(null);
        solicitacoes.setUsuarioAvaliador(usuarioAvaliador);
        Solicitacoes savedSolicitacoes = solicitacoesRepository.save(solicitacoes);
        return solicitacoesMapper.toDTO(savedSolicitacoes);
    }

    public SolicitacoesDTO update(Integer id, SolicitacoesDTO solicitacoesDTO) {
        if (solicitacoesRepository.existsById(id)) {
            Solicitacoes solicitacoes = solicitacoesMapper.toEntity(solicitacoesDTO);
            solicitacoes.setIdSolicitacoes(id); // Garante que o ID não seja sobrescrito
            Solicitacoes updatedSolicitacoes = solicitacoesRepository.save(solicitacoes);
            return solicitacoesMapper.toDTO(updatedSolicitacoes);
        }
        throw new ResourceNotFoundException(id);
    }

    public void delete(Integer id) {
        if (solicitacoesRepository.existsById(id)) {
            solicitacoesRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }
}
