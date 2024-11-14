package ru.itis.secondsemwork.util.password;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordMapper {

    final PasswordEncoder passwordEncoder;

    @PasswordMapping
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }
}