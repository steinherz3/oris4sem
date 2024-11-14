package ru.itis.secondsemwork.security.details;

import ru.itis.secondsemwork.model.Player;
import ru.itis.secondsemwork.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public void setRepository(PlayerRepository repository) {
        this.repository = repository;
    }

    private PlayerRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Player> userOptional = repository.findByUsername(username);
        if (userOptional.isPresent()) {
            return new UserDetailsImpl(userOptional.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}