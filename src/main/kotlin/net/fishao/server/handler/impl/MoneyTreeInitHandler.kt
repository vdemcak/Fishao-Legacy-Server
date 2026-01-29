package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestMoneyTreeInit
import net.fishao.server.protocol.sr.ResponseMoneyTreeInit
import net.fishao.server.session.Session

class MoneyTreeInitHandler : MessageHandler<RequestMoneyTreeInit> {
    override val requestClass: Class<RequestMoneyTreeInit> = RequestMoneyTreeInit::class.java

    override suspend fun handle(session: Session, request: RequestMoneyTreeInit) {
        val response = ResponseMoneyTreeInit(
            currentLevel = 1,
            timeToNextUpgrade = 0,
            timeToNextGenerate = 0,
            fishcoinsLeft = 0,
            fishbucksLeft = 0,
            levelsData = """
                [
                  { "number": 1, "price_upgrade": 0, "fishcoins_per_day": 0, "fishbucks_per_day": 50, "time_upgrade": 0 },
                  { "number": 2, "price_upgrade": 7, "fishcoins_per_day": 1, "fishbucks_per_day": 100, "time_upgrade": 1800 },
                  { "number": 3, "price_upgrade": 18, "fishcoins_per_day": 1, "fishbucks_per_day": 150, "time_upgrade": 7200 },
                  { "number": 4, "price_upgrade": 29, "fishcoins_per_day": 1, "fishbucks_per_day": 200, "time_upgrade": 14400 },
                  { "number": 5, "price_upgrade": 40, "fishcoins_per_day": 1, "fishbucks_per_day": 250, "time_upgrade": 21600 },
                  { "number": 6, "price_upgrade": 51, "fishcoins_per_day": 2, "fishbucks_per_day": 300, "time_upgrade": 28800 },
                  { "number": 7, "price_upgrade": 62, "fishcoins_per_day": 2, "fishbucks_per_day": 350, "time_upgrade": 43200 },
                  { "number": 8, "price_upgrade": 73, "fishcoins_per_day": 2, "fishbucks_per_day": 400, "time_upgrade": 57600 },
                  { "number": 9, "price_upgrade": 84, "fishcoins_per_day": 2, "fishbucks_per_day": 450, "time_upgrade": 86400 },
                  { "number": 10, "price_upgrade": 95, "fishcoins_per_day": 3, "fishbucks_per_day": 500, "time_upgrade": 172800 },
                  { "number": 11, "price_upgrade": 106, "fishcoins_per_day": 3, "fishbucks_per_day": 550, "time_upgrade": 259200 },
                  { "number": 12, "price_upgrade": 117, "fishcoins_per_day": 3, "fishbucks_per_day": 600, "time_upgrade": 345600 },
                  { "number": 13, "price_upgrade": 128, "fishcoins_per_day": 3, "fishbucks_per_day": 650, "time_upgrade": 432000 },
                  { "number": 14, "price_upgrade": 139, "fishcoins_per_day": 4, "fishbucks_per_day": 700, "time_upgrade": 518400 },
                  { "number": 15, "price_upgrade": 150, "fishcoins_per_day": 4, "fishbucks_per_day": 750, "time_upgrade": 604800 },
                  { "number": 16, "price_upgrade": 161, "fishcoins_per_day": 4, "fishbucks_per_day": 800, "time_upgrade": 691200 },
                  { "number": 17, "price_upgrade": 172, "fishcoins_per_day": 4, "fishbucks_per_day": 850, "time_upgrade": 777600 },
                  { "number": 18, "price_upgrade": 183, "fishcoins_per_day": 5, "fishbucks_per_day": 900, "time_upgrade": 950400 },
                  { "number": 19, "price_upgrade": 194, "fishcoins_per_day": 5, "fishbucks_per_day": 950, "time_upgrade": 1123200 },
                  { "number": 20, "price_upgrade": 205, "fishcoins_per_day": 6, "fishbucks_per_day": 1000, "time_upgrade": 1296000 }
                ]
            """.trimIndent()
        ).applyCommon(request)

        session.send(response)
    }
}
