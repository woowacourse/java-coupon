package coupon.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
