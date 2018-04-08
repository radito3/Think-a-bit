package org.elsys.netprog.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Sessions")
public class Sessions implements Serializable {

    @Column(columnDefinition = "mysql->int(11)", name = "UserId", nullable = false)
    private int UserId;

    @Id
    @Column(columnDefinition = "mysql->bigint(20)", name = "SessionId", nullable = false)
    private long SessionId;

    @Column(columnDefinition = "mysql->timestamp", name = "CreatedAt")
    private Timestamp CreatedAt;

    @Column(columnDefinition = "mysql->timestamp", name = "ExpiresAt")
    private Timestamp ExpiresAt;

    public Sessions() {}

    public Sessions(int userId, long sessionId, Timestamp createdAt, Timestamp expiresAt) {
        this.UserId = userId;
        this.SessionId = sessionId;
        this.CreatedAt = createdAt;
        this.ExpiresAt = expiresAt;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public long getSessionId() {
        return SessionId;
    }

    public void setSessionId(long sessionId) {
        SessionId = sessionId;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        CreatedAt = createdAt;
    }

    public Timestamp getExpiresAt() {
        return ExpiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        ExpiresAt = expiresAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessions sessions = (Sessions) o;
        return UserId == sessions.UserId &&
                SessionId == sessions.SessionId &&
                Objects.equals(CreatedAt, sessions.CreatedAt) &&
                Objects.equals(ExpiresAt, sessions.ExpiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserId, SessionId, CreatedAt, ExpiresAt);
    }
}
