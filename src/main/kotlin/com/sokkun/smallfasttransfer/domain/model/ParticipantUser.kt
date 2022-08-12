package com.sokkun.smallfasttransfer.domain.model

import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.api.response.ParticipantUserRes
import com.sokkun.smallfasttransfer.api.response.ParticipantUserShortRes
import com.sokkun.smallfasttransfer.common.Extension.khFormat
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.repository.findByIdOrNull
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
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne
    @JoinColumn(name = "status_id")
    lateinit var status: ParticipantUserStatus

    @ManyToOne
    @JoinColumn(name = "participant_id")
    lateinit var participant: Participant

    fun toResponse(): ParticipantUserRes {
        return ParticipantUserRes(
            id = this.id,
            fullName = this.fullName,
            username = this.username,
            phone = this.phone,
            email = this.email,
            status = status,
            participant = participant.toResponse(),
            createdAt = this.createdAt.khFormat(),
            updatedAt = this.updatedAt.khFormat()
        )
    }

    fun toShortResponse(): ParticipantUserShortRes {
        return ParticipantUserShortRes(
            id = this.id,
            fullName = this.fullName,
            username = this.username
        )
    }
}
