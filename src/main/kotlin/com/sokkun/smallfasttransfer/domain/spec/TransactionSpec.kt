package com.sokkun.smallfasttransfer.domain.spec

import com.sokkun.smallfasttransfer.domain.model.*
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.JoinType

class TransactionSpec {
    companion object {
        fun genSearchWithEachStatusSpec(value: String, statusId: Long? = null): Specification<Transaction> {
            return Specification{ root, _, cd ->
                val pattern = "%$value%"
                val status = root.join<Transaction, TransactionStatus>(Transaction::status.name, JoinType.INNER)
                val senderBank = root.join<Transaction, Participant>(Transaction::senderBank.name, JoinType.INNER)
                val receiverBank = root.join<Transaction, Participant>(Transaction::receiverBank.name, JoinType.INNER)
                val senderAccount = root.join<Transaction, ParticipantUser>(Transaction::senderAccount.name, JoinType.INNER)
                val receiverAccount = root.join<Transaction, ParticipantUser>(Transaction::receiverAccount.name, JoinType.INNER)
                val currency = root.join<Transaction, CurrencyType>(Transaction::currency.name, JoinType.INNER)

                cd.and(
                    statusId?.let { cd.equal(status.get<Long>(TransactionStatus::id.name), it) },
                    cd.or(
                        cd.like(cd.lower(root.get(Transaction::transactionCode.name)), pattern),
                        cd.like(cd.lower(senderBank.get(Participant::fullName.name)), pattern),
                        cd.like(cd.lower(receiverBank.get(Participant::fullName.name)), pattern),
                        cd.like(cd.lower(senderAccount.get(ParticipantUser::fullName.name)), pattern),
                        cd.like(cd.lower(receiverAccount.get(ParticipantUser::fullName.name)), pattern),
                        cd.like(cd.lower(currency.get(CurrencyType::code.name)), pattern),
                        cd.like(cd.lower(root.get(Transaction::message.name)), pattern),
                        cd.like(cd.lower(status.get(TransactionStatus::name.name)), pattern)
                    )
                )
            }
        }

        fun genFilterBySenderBank(participantId: Long, statusId: Long? = null): Specification<Transaction> {
            return Specification{ root, _, cd ->
                val status = root.join<Transaction, TransactionStatus>(Transaction::status.name, JoinType.INNER)
                val senderBank = root.join<Transaction, Participant>(Transaction::senderBank.name, JoinType.INNER)

                cd.and(
                    statusId?.let { cd.equal(status.get<Long>(TransactionStatus::id.name), it) },
                    cd.equal(senderBank.get<Long>(Participant::id.name), participantId)
                )
            }
        }

        fun genFilterByReceiverBank(participantId: Long, statusId: Long? = null): Specification<Transaction> {
            return Specification{ root, _, cd ->
                val status = root.join<Transaction, TransactionStatus>(Transaction::status.name, JoinType.INNER)
                val receiverBank = root.join<Transaction, Participant>(Transaction::receiverBank.name, JoinType.INNER)

                cd.and(
                    statusId?.let { cd.equal(status.get<Long>(TransactionStatus::id.name), it) },
                    cd.equal(receiverBank.get<Long>(Participant::id.name), participantId)
                )
            }
        }

        fun genFilterByStatus(id: Long): Specification<Transaction> {
            return Specification{ root, _, cd ->
                val status = root.join<Transaction, TransactionStatus>(Transaction::status.name, JoinType.INNER)

                cd.equal(status.get<Long>(TransactionStatus::id.name), id)
            }
        }

        fun genFilterByCurrency(currencyId: Long, statusId: Long? = null): Specification<Transaction> {
            return Specification{ root, _, cd ->
                val status = root.join<Transaction, TransactionStatus>(Transaction::status.name, JoinType.INNER)
                val currency = root.join<Transaction, CurrencyType>(Transaction::currency.name, JoinType.INNER)

                cd.and(
                    statusId?.let { cd.equal(status.get<Long>(TransactionStatus::id.name), it) },
                    cd.equal(currency.get<Long>(CurrencyType::id.name), currencyId)
                )
            }
        }
    }
}