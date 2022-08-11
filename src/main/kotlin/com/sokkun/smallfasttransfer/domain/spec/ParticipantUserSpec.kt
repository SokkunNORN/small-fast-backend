package com.sokkun.smallfasttransfer.domain.spec

import com.sokkun.smallfasttransfer.domain.model.Participant
import com.sokkun.smallfasttransfer.domain.model.ParticipantStatus
import com.sokkun.smallfasttransfer.domain.model.ParticipantUser
import com.sokkun.smallfasttransfer.domain.model.ParticipantUserStatus
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.JoinType

class ParticipantUserSpec {
    companion object {
        fun genSearchSpec(value: String): Specification<ParticipantUser> {
            return Specification{ root, _, cd ->
                val pattern = "%$value%"
                val partStatus = root.join<ParticipantUser, ParticipantUserStatus>(ParticipantUser::status.name, JoinType.INNER)
                val partParticipant = root.join<ParticipantUser, Participant>(ParticipantUser::participant.name, JoinType.INNER)

                cd.or(
                    cd.like(cd.lower(root.get(ParticipantUser::fullName.name)), pattern),
                    cd.like(cd.lower(root.get(ParticipantUser::username.name)), pattern),
                    cd.like(cd.lower(root.get(ParticipantUser::phone.name)), pattern),
                    cd.like(cd.lower(root.get(ParticipantUser::email.name)), pattern),
                    cd.like(cd.lower(partStatus.get(ParticipantUserStatus::name.name)), pattern),
                    cd.like(cd.lower(partParticipant.get(Participant::shortName.name)), pattern),
                    cd.like(cd.lower(partParticipant.get(Participant::fullName.name)), pattern)
                )
            }
        }

        fun genFilterByStatusSpec(id: Long): Specification<ParticipantUser> {
            return Specification{ root, _, cd ->
                val partStatus = root.join<ParticipantUser, ParticipantUserStatus>(ParticipantUser::status.name, JoinType.INNER)

                cd.equal(partStatus.get<Long>(ParticipantUserStatus::id.name), id)
            }
        }

        fun genFilterByParticipantSpec(id: Long): Specification<ParticipantUser> {
            return Specification{ root, _, cd ->
                val partParticipant = root.join<ParticipantUser, Participant>(ParticipantUser::participant.name, JoinType.INNER)

                cd.equal(partParticipant.get<Long>(Participant::id.name), id)
            }
        }
    }
}