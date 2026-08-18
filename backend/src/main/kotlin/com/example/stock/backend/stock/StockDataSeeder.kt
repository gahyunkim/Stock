package com.example.stock.backend.stock

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/** 앱 시작 시 DB가 비어있으면 샘플 종목 데이터를 채워 넣는다. */
@Component
class StockDataSeeder(
    private val stockRepository: StockRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (stockRepository.count() > 0L) return

        stockRepository.saveAll(
            listOf(
                StockEntity(
                    symbol = "005930",
                    name = "삼성전자",
                    price = 71_200,
                    changePercent = 1.2,
                    logoUrl = "https://placehold.co/64x64/1976D2/FFFFFF.png?text=SS",
                ),
                StockEntity(
                    symbol = "035720",
                    name = "카카오",
                    price = 42_300,
                    changePercent = -0.8,
                    logoUrl = "https://placehold.co/64x64/FFCC00/000000.png?text=K",
                ),
                StockEntity(
                    symbol = "035420",
                    name = "NAVER",
                    price = 210_500,
                    changePercent = 2.1,
                    logoUrl = "https://placehold.co/64x64/03C75A/FFFFFF.png?text=N",
                ),
            ),
        )
    }
}
