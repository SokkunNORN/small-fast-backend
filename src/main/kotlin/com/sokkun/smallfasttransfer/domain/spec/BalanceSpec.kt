package com.sokkun.smallfasttransfer.domain.spec

import com.sokkun.smallfasttransfer.domain.model.Balance
import com.sokkun.smallfasttransfer.domain.model.CurrencyType
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

        fun genFindByUserIdAndCurrencyId(userId: Long, currencyId: Long): Specification<Balance> {
            return Specification { root, _, cd ->
                val user = root.join<Balance, ParticipantUser>(Balance::participantUser.name, JoinType.INNER)
                val currency = root.join<Balance, CurrencyType>(Balance::currencyType.name, JoinType.INNER)

                cd.and(
                    cd.equal(user.get<Long>(ParticipantUser::id.name), userId),
                    cd.equal(currency.get<Long>(CurrencyType::id.name), currencyId)
                )
            }
        }
    }
}