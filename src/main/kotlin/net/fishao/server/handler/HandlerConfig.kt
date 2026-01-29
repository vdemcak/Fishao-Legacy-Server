package net.fishao.server.handler

import net.fishao.server.handler.impl.*
import net.fishao.server.protocol.sr.*

/**
 * Configures and creates the message registry with all handlers.
 */
object HandlerConfig {
    /**
     * Initializes and returns a [MessageRegistry] with all registered handlers and response aliases.
     */
    fun createRegistry(): MessageRegistry {
        val registry = MessageRegistry()

        registerHandlers(registry)
        registerResponseAliases(registry)

        return registry
    }

    /**
     * Registers all message handlers in the provided [MessageRegistry].
     */
    private fun registerHandlers(registry: MessageRegistry) {
        //@formatter:off
        registry.registerHandler("cr.LoginReq", LoginHandler())
        registry.registerHandler("cr.EnterWorld", EnterWorldHandler())
        registry.registerHandler("cr.GetWorldMapDataReq", WorldMapDataHandler())

        // API Handlers
        registry.registerHandler("api.announcements.init.RequestAnnouncements", AnnouncementsInitHandler())
        registry.registerHandler("api.clubs.init.RequestClubsInit",ClubsInitHandler())
        registry.registerHandler("api.monsterFishes.init.RequestMonsterFishesInit",MonsterFishesInitHandler())
        registry.registerHandler("api.furniture_factory.get_info.RequestFurnitureFactoryGetInfo",FurnitureFactoryGetInfoHandler())
        registry.registerHandler("api.worlds.init.RequestWorldsInit",WorldsInitHandler())
        registry.registerHandler("api.fishingLine.init.RequestFishingLineInit",FishingLineInitHandler())
        registry.registerHandler("api.moneyTree.init.RequestMoneyTreeInit",MoneyTreeInitHandler())
        registry.registerHandler("api.events.init.RequestEventsInit",EventsInitHandler())
        registry.registerHandler("api.features.update.RequestFeaturesUpdate",FeaturesUpdateHandler())
        registry.registerHandler("api.featuresTemporary.init.RequestFeaturesTemporaryInit",FeaturesTemporaryInitHandler())
        registry.registerHandler("api.quests.init.RequestQuestsInit",QuestsInitHandler())
        registry.registerHandler("api.collections.init.RequestCollectionsInit",CollectionsInitHandler())
        registry.registerHandler("api.sales.init.RequestSalesInit",SalesInitHandler())
        registry.registerHandler("api.periodicFeatures.init.RequestPeriodicFeaturesInit",PeriodicFeaturesInitHandler())
        registry.registerHandler("api.tutorial.init.RequestTutorialInit",TutorialInitHandler())
        registry.registerHandler("api.wheel_of_fortune.init.RequestWheelOfFortuneInit",WheelOfFortuneInitHandler())
        registry.registerHandler("api.holidays.init.RequestHolidaysInit",HolidaysInitHandler())
        registry.registerHandler("api.fishes.getRelevant.RequestFishesGetRelevant",FishesGetRelevantHandler())
        //@formatter:on
    }

    /**
     * Registers response aliases in the provided [MessageRegistry].
     */
    private fun registerResponseAliases(registry: MessageRegistry) {
        //@formatter:off
        registry.registerResponseAlias(LoginSuccessResp::class, "sr.LoginSuccessResp")
        registry.registerResponseAlias(UpdateResp::class, "sr.UpdateResp")
        registry.registerResponseAlias(WorldMapDataResp::class, "sr.WorldMapDataResp")

        // API Response Aliases
        registry.registerResponseAlias(ResponseAnnouncements::class, "api.announcements.init.ResponseAnnouncements")
        registry.registerResponseAlias(ResponseClubsInit::class, "api.clubs.init.ResponseClubsInit")
        registry.registerResponseAlias(ResponseMonsterFishesInit::class, "api.monsterFishes.init.ResponseMonsterFishesInit")
        registry.registerResponseAlias(ResponseFurnitureFactoryGetInfo::class, "api.furniture_factory.get_info.ResponseFurnitureFactoryGetInfo")
        registry.registerResponseAlias(ResponseWorldsInit::class, "api.worlds.init.ResponseWorldsInit")
        registry.registerResponseAlias(ResponseFishingLineInit::class, "api.fishingLine.init.ResponseFishingLineInit")
        registry.registerResponseAlias(ResponseMoneyTreeInit::class, "api.moneyTree.init.ResponseMoneyTreeInit")
        registry.registerResponseAlias(ResponseEventsInit::class, "api.events.init.ResponseEventsInit")
        registry.registerResponseAlias(ResponseFeaturesUpdate::class, "api.features.update.ResponseFeaturesUpdate")
        registry.registerResponseAlias(ResponseFeaturesTemporaryInit::class, "api.featuresTemporary.init.ResponseFeaturesTemporaryInit")
        registry.registerResponseAlias(ResponseQuestsInit::class, "api.quests.init.ResponseQuestsInit")
        registry.registerResponseAlias(ResponseCollectionsInit::class, "api.collections.init.ResponseCollectionsInit")
        registry.registerResponseAlias(ResponseSalesInit::class, "api.sales.init.ResponseSalesInit")
        registry.registerResponseAlias(ResponsePeriodicFeaturesInit::class, "api.periodicFeatures.init.ResponsePeriodicFeaturesInit")
        registry.registerResponseAlias(ResponseTutorialInit::class, "api.tutorial.init.ResponseTutorialInit")
        registry.registerResponseAlias(ResponseWheelOfFortuneInit::class, "api.wheel_of_fortune.init.ResponseWheelOfFortuneInit")
        registry.registerResponseAlias(ResponseHolidaysInit::class, "api.holidays.init.ResponseHolidaysInit")
        registry.registerResponseAlias(ResponseFishesGetRelevant::class, "api.fishes.getRelevant.ResponseFishesGetRelevant")
        //@formatter:on
    }
}
