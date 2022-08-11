package com.sokkun.smallfasttransfer.domain.model

import javax.persistence.*

@Entity
@Table(name = "participant_user_status")
data class ParticipantUserStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqParticipantUserStatus")
    @SequenceGenerator(name = "seqParticipantUserStatus", sequenceName = "SEQ_PARTICIPANT_USER_STATUS", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String
)
