package br.com.zup.Credicard.user;

import br.com.zup.Credicard.card.advice.Advice;
import br.com.zup.Credicard.card.blocking.Blocking;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private String userAgent;

    @OneToOne(mappedBy = "user")
    private Blocking blocking;

    @OneToOne(mappedBy = "user")
    private Advice advice;

    @Deprecated
    private User() {}

    public User(String ip, String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
