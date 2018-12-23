package ru.otus.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.otus.models.DataSet;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.otus.shared.Constants.DB.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = TBL_USERS_ATTEMPTS)
public class UserAttemptEntity implements DataSet, Serializable
{
    static final long serialVersionUID = 50L;

    private static final int DEFAULT_MAX_ATTEMPTS_COUNT = 10;

    public static final int DEFAULT_LOCK_TIME_MINUTES = 1;

    @Id
    @SequenceGenerator(name = SEQ_GENERATOR_USER_ATTEMPT, sequenceName = SEQ_NAME_USER_ATTEMPT, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = SEQ_GENERATOR_USER_ATTEMPT)
    @Column(name = F_ID, nullable = false, unique = true)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = F_USER_ID, referencedColumnName = F_ID)
    private UserEntity user;

    @Column(name = F_ATTEMPTS_LEFT)
    private int attemptsLeft;

    @Column(name = F_LAST_ATTEMPT_TIME)
    private LocalDateTime lastAttemptTime;

    private void clearAttempts()
    {
        attemptsLeft = DEFAULT_MAX_ATTEMPTS_COUNT;
        lastAttemptTime = LocalDateTime.now();
    }

    private boolean isUnlockPossible()
    {
        return LocalDateTime.now().minusMinutes(DEFAULT_LOCK_TIME_MINUTES).isAfter(lastAttemptTime);
    }

    public boolean isLocked()
    {
        return (attemptsLeft == 0 && !isUnlockPossible());
    }

    public void unLockIfPossible()
    {
        if (attemptsLeft == 0 && isUnlockPossible()) {
            clearAttempts();
        }
    }

    public void decAttemptCount()
    {
        if (attemptsLeft > 0) {
            attemptsLeft--;
            lastAttemptTime = LocalDateTime.now();
        }
    }

    @Override
    public String nameGet()
    {
        return Long.toString(id);
    }

    @Override
    public void letName(String name)
    {
    }
}
