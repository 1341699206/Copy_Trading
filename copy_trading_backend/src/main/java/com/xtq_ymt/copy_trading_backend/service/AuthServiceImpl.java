package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.UserRegisterDTO;
import com.xtq_ymt.copy_trading_backend.dto.UserResponseDTO;
import com.xtq_ymt.copy_trading_backend.model.User;
import com.xtq_ymt.copy_trading_backend.repository.UserRepository;
import com.xtq_ymt.copy_trading_backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户认证服务实现类。
 * 实现用户注册和登录的具体逻辑。
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 用户注册逻辑。
     * 验证用户名和邮箱的唯一性，编码用户密码，并将用户信息保存到数据库中。
     *
     * @param userRegisterDTO 包含注册信息的数据传输对象
     * @throws IllegalArgumentException 如果用户名或邮箱已存在
     */
    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // 创建用户并保存到数据库
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRole(User.Role.valueOf(userRegisterDTO.getRole()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * 用户登录逻辑。
     * 验证用户名和密码是否匹配，并生成 JWT 令牌和返回用户信息。
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 包含 JWT 令牌和用户基本信息的响应数据传输对象
     * @throws IllegalArgumentException 如果用户名或密码无效
     */
    @Override
    public UserResponseDTO login(String username, String password) {
        // 根据用户名查找用户
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // 验证密码是否匹配
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // 生成 JWT 令牌
        String token = jwtTokenProvider.generateToken(user);

        // 返回包含用户信息和 JWT 令牌的响应
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                token // 将生成的 JWT 令牌添加到返回信息中
        );
    }
}
