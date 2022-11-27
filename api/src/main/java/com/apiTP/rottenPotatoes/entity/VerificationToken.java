package com.apiTP.rottenPotatoes.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_verification")
public class VerificationToken implements Serializable {
    private static final long serialVersionUID = 1L;

    // Tempo para a expiração do Token
    private static final int TEMPO_EXPIRACAO = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date tempoParaExpirar;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))

    private User user;

    public VerificationToken() {
    }

    public VerificationToken(User user, String token) {
        super();
        this.user = user;
        this.token = token;
        this.tempoParaExpirar = calculoDataExpiracao(TEMPO_EXPIRACAO);
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.tempoParaExpirar = calculoDataExpiracao(TEMPO_EXPIRACAO);
    }

    private Date calculoDataExpiracao(int tempoExpiracao) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, tempoExpiracao);
        return new Date(calendar.getTime().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTempoParaExpirar() {
        return tempoParaExpirar;
    }

    public void setTempoParaExpirar(Date tempoParaExpirar) {
        this.tempoParaExpirar = tempoParaExpirar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(tempoParaExpirar, that.tempoParaExpirar) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, tempoParaExpirar, user);
    }
}
