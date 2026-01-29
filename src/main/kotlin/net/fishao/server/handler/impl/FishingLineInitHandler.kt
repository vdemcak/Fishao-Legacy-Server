package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestFishingLineInit
import net.fishao.server.protocol.sr.ResponseFishingLineInit
import net.fishao.server.session.Session

class FishingLineInitHandler : MessageHandler<RequestFishingLineInit> {
    override val requestClass: Class<RequestFishingLineInit> = RequestFishingLineInit::class.java

    override suspend fun handle(session: Session, request: RequestFishingLineInit) {
        val response = ResponseFishingLineInit(
            currentLevel = 1,
            timeToNextUpgrade = 0,
            levelsData = """
                [
                  { "number": 1, "price_upgrade": 0,   "line_length": 40,  "time_upgrade": 0,     "available_colors": ["white"] },
                  { "number": 2, "price_upgrade": 3,   "line_length": 50,  "time_upgrade": 300,   "available_colors": ["white"] },
                  { "number": 3, "price_upgrade": 8,   "line_length": 60,  "time_upgrade": 900,   "available_colors": ["white"] },
                  { "number": 4, "price_upgrade": 15,  "line_length": 70,  "time_upgrade": 1800,  "available_colors": ["white"] },
                  { "number": 5, "price_upgrade": 24,  "line_length": 80,  "time_upgrade": 3600,  "available_colors": ["white","blue_light"] },
                  { "number": 6, "price_upgrade": 35,  "line_length": 90,  "time_upgrade": 7200,  "available_colors": ["white","blue_light"] },
                  { "number": 7, "price_upgrade": 48,  "line_length": 100, "time_upgrade": 14400, "available_colors": ["white","blue_light"] },
                  { "number": 8, "price_upgrade": 63,  "line_length": 125, "time_upgrade": 21600, "available_colors": ["white","blue_light"] },
                  { "number": 9, "price_upgrade": 80,  "line_length": 150, "time_upgrade": 28800, "available_colors": ["white","blue_light","green_light"] },
                  { "number": 10, "price_upgrade": 99, "line_length": 175, "time_upgrade": 43200, "available_colors": ["white","blue_light","green_light"] },
                  { "number": 11, "price_upgrade": 120,"line_length": 200, "time_upgrade": 57600, "available_colors": ["white","blue_light","green_light"] },
                  { "number": 12, "price_upgrade": 143,"line_length": 250, "time_upgrade": 86400, "available_colors": ["white","blue_light","green_light"] },
                  { "number": 13, "price_upgrade": 168,"line_length": 300, "time_upgrade": 172800, "available_colors": ["white","blue_light","green_light","silver"] },
                  { "number": 14, "price_upgrade": 195,"line_length": 400, "time_upgrade": 259200, "available_colors": ["white","blue_light","green_light","silver"] },
                  { "number": 15, "price_upgrade": 224,"line_length": 500, "time_upgrade": 345600, "available_colors": ["white","blue_light","green_light","silver"] },
                  { "number": 16, "price_upgrade": 255,"line_length": 750, "time_upgrade": 432000, "available_colors": ["white","blue_light","green_light","silver"] },
                  { "number": 17, "price_upgrade": 288,"line_length": 1000,"time_upgrade": 518400, "available_colors": ["white","blue_light","green_light","silver","gold"] },
                  { "number": 18, "price_upgrade": 323,"line_length": 1250,"time_upgrade": 604800, "available_colors": ["white","blue_light","green_light","silver","gold"] },
                  { "number": 19, "price_upgrade": 360,"line_length": 1500,"time_upgrade": 691200, "available_colors": ["white","blue_light","green_light","silver","gold"] },
                  { "number": 20, "price_upgrade": 399,"line_length": 1750,"time_upgrade": 777600, "available_colors": ["white","blue_light","green_light","silver","gold"] }
                ]
            """.trimIndent(),
            color = "default"
        ).applyCommon(request)

        session.send(response)
    }
}
