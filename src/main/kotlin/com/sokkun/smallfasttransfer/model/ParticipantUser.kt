package com.sokkun.smallfasttransfer.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "participant_user")
data class ParticipantUser(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqParticipantUser")
    @SequenceGenerator(name = "seqParticipantUser", sequenceName = "SEQ_PARTICIPANT_USER", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "full_name")
    val fullName: String,

    @Column(name = "username")
    val username: String,

    @Column(name = "phone")
    val phone: String? = null,

    @Column(name = "email")
    val email: String? = null,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "status_id")
    val statusId: Long,

    @Column(name = "participant_id")
    val participantId: Long,
)
