package com.apiTP.rottenPotatoes.services;

import com.apiTP.rottenPotatoes.entity.PasswordResetToken;
import com.apiTP.rottenPotatoes.entity.User;
import com.apiTP.rottenPotatoes.entity.VerificationToken;
import com.apiTP.rottenPotatoes.models.UserModel;
import com.apiTP.rottenPotatoes.repositories.PasswordResetTokenRepository;
import com.apiTP.rottenPotatoes.repositories.UserRepository;
import com.apiTP.rottenPotatoes.repositories.VerificatioinTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private EmailSender emailSender;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificatioinTokenRepository verificatioinTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setPrimeiroNome(userModel.getPrimeiroNome());
        user.setUltimoNome(userModel.getPrimeiroNome());
        user.setRole("USER");
        user.setSenha(passwordEncoder.encode(userModel.getSenha()));

        userRepository.save(user);
        return user;
    }

    @Override
    public void salvarTokenDeVerificacao(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);

        verificatioinTokenRepository.save(verificationToken);
    }

    @Override
    public String validandoTokenDeVerificacao(String token) {
        VerificationToken verificationToken = verificatioinTokenRepository.findByToken(token);
        if(verificationToken == null) {
            return "invalid";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getTempoParaExpirar().getTime() - cal.getTime().getTime()) <= 0) {
            verificatioinTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnable(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken gerandoNovoTokenDeVerificacao(String oldtoken) {
        VerificationToken verificationToken = verificatioinTokenRepository.findByToken(oldtoken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificatioinTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void criandoSenhaResetTokenParaUsuario(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validandoNovaSenhaToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null) {
            return "invalid";
        }

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if((passwordResetToken.getTempoParaExpirar().getTime() - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String novaSenha) {
        user.setSenha(passwordEncoder.encode(novaSenha));
        userRepository.save(user);
    }

    @Override
    public boolean verificandoSenhaAntiga(User user, String senhaAntiga) {
        return passwordEncoder.matches(senhaAntiga, user.getSenha());
    }
}
