package com.sokkun.smallfasttransfer.model

import javax.persistence.*

@Entity
@Table(name = "participant_status")
data class ParticipantStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqParticipantStatus")
    @SequenceGenerator(name = "seqParticipantStatus", sequenceName = "SEQ_PARTICIPANT_STATUS", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String
)
