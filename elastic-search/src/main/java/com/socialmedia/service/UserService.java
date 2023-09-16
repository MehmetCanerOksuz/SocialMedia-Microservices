package com.socialmedia.service;
import com.socialmedia.repository.IUserRepository;
import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceManager<UserProfile, String> {

    //Dependency Injec -->> constructor injection, setter injection, field injection
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;

    }
}
