package s5almiakki.oauth2clientjwtpractice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String role;

    public UserEntity(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
