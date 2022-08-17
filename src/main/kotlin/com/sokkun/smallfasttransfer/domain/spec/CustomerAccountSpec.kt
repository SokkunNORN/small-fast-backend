package com.sokkun.smallfasttransfer.domain.spec

import com.sokkun.smallfasttransfer.domain.model.CustomerAccount
import com.sokkun.smallfasttransfer.domain.model.Participant
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.JoinType

class CustomerAccountSpec {
    companion object {
        fun getByParticipant(id: Long): Specification<CustomerAccount> {
            return Specification { root, _, cd ->
                val participant = root.join<CustomerAccount, Participant>(CustomerAccount::participant.name, JoinType.INNER)

                cd.equal(participant.get<Long>(Participant::id.name), id)
            }
        }
    }
}