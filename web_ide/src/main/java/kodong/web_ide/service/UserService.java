package kodong.web_ide.service;


import kodong.web_ide.model.User;
import kodong.web_ide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Object save(User user) {
        return userRepository.save(user);
    }

    public Object findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
