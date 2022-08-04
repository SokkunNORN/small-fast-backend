package com.sokkun.smallfasttransfer.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "participant")
data class Participant(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqParticipant")
    @SequenceGenerator(name = "seqParticipant", sequenceName = "SEQ_PARTICIPANT", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "full_name")
    val fullName: String,

    @Column(name = "short_name")
    val shortName: String,

    @Column(name = "participant_code")
    val participantCode: String,

    @Column(name = "bicfi_code")
    val bicfiCode: String,

    @Column(name = "bank_code")
    val bankCode: String,

    @Column(name = "phone")
    val phone: String?,

    @Column(name = "email")
    val email: String?,

    @Column(name = "address")
    val address: String?,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "status_id")
    val statusId: Long,
)
