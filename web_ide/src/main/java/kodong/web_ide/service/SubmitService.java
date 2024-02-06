package kodong.web_ide.service;

import kodong.web_ide.model.Submit;
import kodong.web_ide.repository.SubmitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmitService {

    private final SubmitRepository submitRepository;

    @Transactional
    public Object save(Submit submit) {
        return submitRepository.save(submit);
    }

    public Object findById(Long submitId) {
        return submitRepository.findById(submitId).orElse(null);
    }

}
