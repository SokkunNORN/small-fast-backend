package com.sokkun.smallfasttransfer.domain.spec

import com.sokkun.smallfasttransfer.domain.model.*
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.JoinType

class TransactionSpec {
    companion object {
        fun genSearchSpec(value: String): Specification<Transaction> {
            return Specification{ root, _, cb ->
                val pattern = "%$value%"
                val status = root.join<Transaction, TransactionStatus>(Transaction::status.name, JoinType.INNER)
                val senderBank = root.join<Transaction, Participant>(Transaction::senderBank.name, JoinType.INNER)
                val receiverBank = root.join<Transaction, Participant>(Transaction::receiverBank.name, JoinType.INNER)
                val senderAccount = root.join<Transaction, ParticipantUser>(Transaction::senderAccount.name, JoinType.INNER)
                val receiverAccount = root.join<Transaction, ParticipantUser>(Transaction::receiverAccount.name, JoinType.INNER)
                val currency = root.join<Transaction, CurrencyType>(Transaction::currency.name, JoinType.INNER)

                cb.or(
                    cb.like(cb.lower(root.get(Transaction::transactionCode.name)), pattern),
                    cb.like(cb.lower(senderBank.get(Participant::fullName.name)), pattern),
                    cb.like(cb.lower(receiverBank.get(Participant::fullName.name)), pattern),
                    cb.like(cb.lower(senderAccount.get(ParticipantUser::fullName.name)), pattern),
                    cb.like(cb.lower(receiverAccount.get(ParticipantUser::fullName.name)), pattern),
                    cb.like(cb.lower(currency.get(CurrencyType::code.name)), pattern),
                    cb.like(cb.lower(root.get(Transaction::message.name)), pattern),
                    cb.like(cb.lower(status.get(TransactionStatus::name.name)), pattern)
                )
            }
        }

        fun genFilterBySenderBank(participantId: Long): Specification<Transaction> {
            return Specification{ root, _, cb ->
                val senderBank = root.join<Transaction, Participant>(Transaction::senderBank.name, JoinType.INNER)

                cb.equal(senderBank.get<Long>(Participant::id.name), participantId)
            }
        }

        fun genFilterByReceiverBank(participantId: Long): Specification<Transaction> {
            return Specification{ root, _, cb ->
                val receiverBank = root.join<Transaction, Participant>(Transaction::receiverBank.name, JoinType.INNER)

                cb.equal(receiverBank.get<Long>(Participant::id.name), participantId)
            }
        }

        fun genFilterByStatus(ids: List<Long>): Specification<Transaction> {
            return Specification{ root, _, _ ->
                val status = root.join<Transaction, TransactionStatus>(Transaction::status.name, JoinType.INNER)

                status.`in`(ids)
            }
        }

        fun genFilterByCurrency(currencyId: Long): Specification<Transaction> {
            return Specification{ root, _, cb ->
                val currency = root.join<Transaction, CurrencyType>(Transaction::currency.name, JoinType.INNER)

                cb.equal(currency.get<Long>(CurrencyType::id.name), currencyId)
            }
        }
    }
}