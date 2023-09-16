package com.socialmedia.service;

import com.socialmedia.dto.request.UserProfileUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.UserProfileFindAllResponseDto;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.exception.UserManagerException;
import com.socialmedia.manager.IAuthManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.rabbitmq.producer.RegisterElasticProducer;
import com.socialmedia.repository.IUserRepository;
import com.socialmedia.repository.entity.Post;
import com.socialmedia.repository.enums.EStatus;
import com.socialmedia.utility.JwtTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService extends ServiceManager<Post, String> {

    //Dependency Injec -->> constructor injection, setter injection, field injection
    private final IUserRepository userRepository;
    private final JwtTokenManager jwtTokenManager; //singleton üretilen JwtTokenManager sınıfının bu AuthService'e çağırılıp kullanıma açılması işlemidir..
    private final IAuthManager authManager;
    private final CacheManager cacheManager;
    private final RegisterElasticProducer registerElasticProducer;
    public UserService(IUserRepository userRepository, JwtTokenManager jwtTokenManager, IAuthManager authManager, CacheManager cacheManager, RegisterElasticProducer registerElasticProducer) {
        super(userRepository);
        this.userRepository = userRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
        this.cacheManager = cacheManager;
        this.registerElasticProducer = registerElasticProducer;
    }

    public Boolean createNewUser(UserSaveRequestDto dto) {
        try {
            Post post = IUserMapper.INSTANCE.toUserProfile(dto);

            save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public String activateStatus(String token) {

        Optional<Long> authId = jwtTokenManager.getAuthIdFromToken(token);

        if (authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }

        Optional<Post> userProfile = userRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return "Hesabınız aktive olmustur.";
    }

    @Transactional
    public String updateUserProfile(UserProfileUpdateRequestDto dto) {
        Optional<Long> authId=jwtTokenManager.getAuthIdFromToken(dto.getToken());
        if (authId.isEmpty()){
            throw  new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<Post> userProfile=userRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw  new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        if (!userProfile.get().getEmail().equals(dto.getEmail())||
                !userProfile.get().getUsername().equals(dto.getUsername())){
            userProfile.get().setEmail(dto.getEmail());
            userProfile.get().setUsername(dto.getUsername());

            authManager.updateAuth(IUserMapper.INSTANCE.toAuthUpdateRequestDto(userProfile.get()),"Bearer "+dto.getToken());

        }

//        userProfile = Optional.of(IUserMapper.INSTANCE.toUserProfile(dto));  // id ve authId null geliyor..

        userProfile.get().setAbout(dto.getAbout());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setName(dto.getName());
        userProfile.get().setSurName(dto.getSurName());
        userProfile.get().setSurName(dto.getSurName());
        userProfile.get().setAvatar(dto.getAvatar());

        update(userProfile.get());

        //cacheManager.getCache("find_by_username").evict(userProfile.get().getUsername());
        cacheManager.getCache("find_by_username").put(userProfile.get().getUsername(),userProfile.get());
        return "Update Başarılı";

    }

    public Boolean createNewUserWithRabbitmq(RegisterModel model) {
        try {
            Post post = IUserMapper.INSTANCE.toUserProfile(model);

            save(post);
            registerElasticProducer.sendNewUser(IUserMapper.INSTANCE.toRegeRegisterElasticModel(post));

            return true;
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    @Cacheable(value = "find_by_username")
    public Post findByUsername(String username) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Optional<Post> userProfile=userRepository.findByUsername(username);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return userProfile.get();
    }

    @Cacheable(value = "find_by_status")
    public List<Post> findByStatus(EStatus status){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Post> list=userRepository.findUserProfileByStatus(status);
        if(list.isEmpty()){
            throw new RuntimeException("Herhangi bir veri bulunamadı");
        }
        return list;
    }

    @Cacheable(value = "find_by_status2", key = "#status.toUpperCase(T(java.util.Locale).ENGLISH)")
    public List<Post> findByStatus2(String status) {

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        List<Post> list= new ArrayList<>();

        EStatus myStatus;
        try {
            myStatus = EStatus.valueOf(status.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            throw new UserManagerException(ErrorType.STATUS_NOT_FOUND);
        }
        return userRepository.findUserProfileByStatus(myStatus);
    }


    public String deleteUserProfile(String token) {
        Optional<Long> authId = jwtTokenManager.getAuthIdFromToken(token.substring(7));

        Optional<Post> userProfile = userRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.DELETED);
        update(userProfile.get());

        cacheManager.getCache("find_by_username").evict(userProfile.get().getUsername());

        return userProfile.get().getId()+ "id li kullanıcı silinmiştir.";
    }

    public List<UserProfileFindAllResponseDto> findAllUserProfile() {

        List<Post> postList = findAll();
        return postList.stream().map(x-> IUserMapper.INSTANCE.toUserProfileFindAllResponseDto(x)).collect(Collectors.toList());
    }

    public Page<Post> findAllByPageable(int pageSize, int pageNumber, String direction, String sortParameter) {
        Sort sort= Sort.by(Sort.Direction.fromString(direction),sortParameter);
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return userRepository.findAll(pageable);
    }

    public Slice<Post> findAllBySlice(int pageSize, int pageNumber, String direction, String sortParameter) {
        Sort sort= Sort.by(Sort.Direction.fromString(direction),sortParameter);
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return userRepository.findAll(pageable);
    }
}
