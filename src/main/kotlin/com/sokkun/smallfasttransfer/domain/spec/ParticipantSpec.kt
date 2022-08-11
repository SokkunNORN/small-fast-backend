package com.sokkun.smallfasttransfer.domain.spec

import com.sokkun.smallfasttransfer.domain.model.Participant
import com.sokkun.smallfasttransfer.domain.model.ParticipantStatus
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.JoinType

class ParticipantSpec {
    companion object {
        fun genSearchSpec(value: String): Specification<Participant> {
            return Specification{ root, _, cd ->
                val pattern = "%$value%"
                val partStatus = root.join<Participant, ParticipantStatus>(Participant::status.name, JoinType.INNER)

                cd.or(
                    cd.like(cd.lower(root.get(Participant::fullName.name)), pattern),
                    cd.like(cd.lower(root.get(Participant::shortName.name)), pattern),
                    cd.like(cd.lower(root.get(Participant::participantCode.name)), pattern),
                    cd.like(cd.lower(root.get(Participant::bicfiCode.name)), pattern),
                    cd.like(cd.lower(root.get(Participant::bankCode.name)), pattern),
                    cd.like(cd.lower(root.get(Participant::phone.name)), pattern),
                    cd.like(cd.lower(root.get(Participant::email.name)), pattern),
                    cd.like(cd.lower(root.get(Participant::address.name)), pattern),
                    cd.like(cd.lower(partStatus.get(ParticipantStatus::description.name)), pattern)
                )
            }
        }

        fun genFilterParticipantStatusSpec(id: Long): Specification<Participant> {
            return Specification{ root, _, cd ->
                val partStatus = root.join<Participant, ParticipantStatus>(Participant::status.name, JoinType.INNER)

                cd.equal(partStatus.get<Long>(ParticipantStatus::id.name), id)
            }
        }
    }
}