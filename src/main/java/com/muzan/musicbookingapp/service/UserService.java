package com.muzan.musicbookingapp.service;

import com.muzan.musicbookingapp.dto.ArtistDto;
import com.muzan.musicbookingapp.dto.UserDto;
import com.muzan.musicbookingapp.model.User;
import com.muzan.musicbookingapp.model.UserRole;
import com.muzan.musicbookingapp.repository.IArtistRepository;
import com.muzan.musicbookingapp.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final IArtistRepository artistRepository;
    private final ModelMapper modelMapper;

    public UserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        var userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto getCurrentUserDto() {
        var user = getAuthenticatedUser();
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();

        if (user.getRole() == UserRole.ARTIST) {
            var artistEntity = artistRepository.findByUserId(user.getId())
                    .orElse(null);
            ArtistDto artistDto = ArtistDto.builder()
                    .id(artistEntity.getId())
                    .user(artistEntity.getUser())
                    .stageName(artistEntity.getStageName())
                    .bio(artistEntity.getBio())
                    .build();

            userDto.setArtistProfile(artistDto);
        }

        return userDto;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
