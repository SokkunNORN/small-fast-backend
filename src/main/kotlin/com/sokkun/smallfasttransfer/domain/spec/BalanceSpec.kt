package com.sokkun.smallfasttransfer.domain.spec

import com.sokkun.smallfasttransfer.domain.model.Balance
import com.sokkun.smallfasttransfer.domain.model.ParticipantUser
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.JoinType

class BalanceSpec {
    companion object {
        fun genFilterUserId(id: Long): Specification<Balance> {
            return Specification { root, _, cd ->
                val user = root.join<Balance, ParticipantUser>(Balance::participantUser.name, JoinType.INNER)

                cd.equal(user.get<Long>(ParticipantUser::id.name), id)
            }
        }
    }
}