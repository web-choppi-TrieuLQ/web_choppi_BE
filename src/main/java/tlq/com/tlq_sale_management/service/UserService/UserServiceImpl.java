package tlq.com.tlq_sale_management.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tlq.com.tlq_sale_management.dto.request.UserRequest.UserCreationRequest;
import tlq.com.tlq_sale_management.dto.request.UserRequest.UserUpdateRequest;
import tlq.com.tlq_sale_management.dto.response.UserResponse;
import tlq.com.tlq_sale_management.enums.Role;
import tlq.com.tlq_sale_management.mapper.UserMapper;
import tlq.com.tlq_sale_management.model.User;
import tlq.com.tlq_sale_management.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService{

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("ErrorCode.USER_EXISTED");

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        log.info("In method get Users");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }


    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(Long id){
        log.info("In method get user by Id");
        return userMapper.toUserResponse(userRepository.findById(id).orElse(null));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new RuntimeException("ErrorCode.USER_NOT_EXISTED"));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ErrorCode.USER_NOT_EXISTED"));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }



    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean create(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean update(User user) {
        if (userRepository.existsById(user.getUserId())) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(User user) {
        userRepository.delete(user);
        return true;
    }
}
