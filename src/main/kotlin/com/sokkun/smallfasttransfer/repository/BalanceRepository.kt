package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.Balance
import com.sokkun.smallfasttransfer.domain.projection.ParticipantBalanceProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BalanceRepository: JpaRepository<Balance, Long>, JpaSpecificationExecutor<Balance> {
    @Query("SELECT * FROM balance", nativeQuery = true)
    fun getAll(): List<Balance>

    @Query(
        nativeQuery = true,
        value = "SELECT SUM(b.BALANCE) as totalBalance, " +
                "p.ID as participantId, " +
                "b.CURRENCY_ID as currencyId " +
                "FROM BALANCE b " +
                "INNER JOIN PARTICIPANT_USER pu " +
                "ON b.PARTICIPANT_USER_ID = pu.ID " +
                "INNER JOIN PARTICIPANT p " +
                "ON p.ID = pu.PARTICIPANT_ID " +
                "AND b.PARTICIPANT_USER_ID = pu.ID " +
                "AND p.ID = :participantId " +
                "GROUP BY b.CURRENCY_ID, p.ID"
    )
    fun findTotalBalanceParticipant(@Param("participantId") participantId: Long): List<ParticipantBalanceProjection>
}