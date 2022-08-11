package com.sokkun.smallfasttransfer.domain.model

import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.common.Extension.khFormat
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
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne
    @JoinColumn(name = "status_id")
    lateinit var status: ParticipantStatus

    fun toResponse(): ParticipantRes {
        return ParticipantRes(
            id = this.id,
            fullName = this.fullName,
            shortName = this.shortName,
            participantCode = this.participantCode,
            bicfiCode = this.bicfiCode,
            bankCode = this.bankCode,
            phone = this.phone,
            email = this.email,
            address = this.address,
            status = status,
            createdAt = this.createdAt.khFormat(),
            updatedAt = this.updatedAt.khFormat()
        )
    }
}
