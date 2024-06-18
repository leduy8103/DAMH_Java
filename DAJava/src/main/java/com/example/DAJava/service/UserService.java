package com.example.DAJava.service;

import com.example.DAJava.Role;
import com.example.DAJava.model.Users;
import com.example.DAJava.repository.IRoleRepository;
import com.example.DAJava.repository.IUserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    // Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu.
    public void save(@NotNull Users user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
    // Gán vai trò mặc định cho người dùng dựa trên tên người dùng.
    public void setDefaultRole(String username) {
        userRepository.findByUsername(username).ifPresentOrElse(user -> {user.getRoles().add(roleRepository.findRoleById( Role.USER.value));
                    userRepository.save(user);
                },
                () -> { throw new UsernameNotFoundException("User not found"); }
        );
    }
    // Tải thông tin chi tiết người dùng để xác thực.
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }
    // Tìm kiếm người dùng dựa trên tên đăng nhập.
    public Optional<Users> findByUsername(String username) throws
            UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    // Chặn người dùng đăng nhập
    public void lockUser(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setAccountNonLocked(false);
            userRepository.save(user);
        });
    }

    // Mở khóa tài khoản người dùng
    public void unlockUser(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setAccountNonLocked(true);
            userRepository.save(user);
        });
    }

    // Đặt lại mật khẩu
    public void resetPassword(String username, String newPassword) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
        });
    }

    // Lấy danh sách tất cả người dùng
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users updateUser(String username, Users updatedUser) {
        Optional<Users> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());  // Bạn có thể mã hóa mật khẩu trước khi lưu
            existingUser.setRoles(updatedUser.getRoles());
            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // Cấp quyền cho người dùng
//    public void grantRole(Long userId, Long roleId) {
//        Role role = roleRepository.findById(roleId)
//                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
//        userRepository.findById(userId).ifPresent(user -> {
//            user.getRoles().add(role);
//            userRepository.save(user);
//        });
//    }
}

