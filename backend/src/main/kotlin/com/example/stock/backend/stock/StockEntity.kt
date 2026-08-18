package com.example.stock.backend.stock

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class StockEntity(
    val symbol: String,
    val name: String,
    val price: Long,
    val changePercent: Double,
    val logoUrl: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
