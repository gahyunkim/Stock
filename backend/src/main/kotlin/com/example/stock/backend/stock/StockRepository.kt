package com.example.stock.backend.stock

import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<StockEntity, Long>
