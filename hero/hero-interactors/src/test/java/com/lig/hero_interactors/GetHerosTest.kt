package com.lig.hero_interactors

import DataState
import com.lig.coreunit.ProgressBarState
import com.lig.coreunit.UIComponent
import com.lig.hero_datasource_test.cache.HeroCacheFake
import com.lig.hero_datasource_test.cache.HeroDatabaseFake
import com.lig.hero_datasource_test.network.HeroServiceFake
import com.lig.hero_datasource_test.network.HeroServiceResponseType
import com.lig.hero_datasource_test.network.data.HeroDataValid
import com.lig.hero_datasource_test.network.data.HeroDataValid.NUM_HEROS
import com.lig.hero_datasource_test.network.deserializeHeroData
import com.lig.hero_domain.Hero
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetHerosTest {

    private lateinit var getHeros: GetHero

    @Test
    fun `get hero success`() = runBlocking {
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.GoodData
        )

        getHeros = GetHero(
            cache = heroCache,
            service = heroService
        )

        // confirme the cache is empty before
        var cachedHeros = heroCache.selectAll()
        assert(cachedHeros.isEmpty())

        //Execute the use case
        val emissions = getHeros.execute().toList()

        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        assert(emissions[1] is DataState.Data)
        System.out.println("${(emissions[1] as DataState.Data).data?.size ?: 0}");
        assert((emissions[1] as DataState.Data).data?.size ?: 0 == NUM_HEROS)

        cachedHeros = heroCache.selectAll()
        assert(cachedHeros.size == NUM_HEROS)

        //confirme loading state is over
        assert(emissions[2] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))

    }


    /**
     * 1. Insert some data into the cache by executing a successful use-case.
     * 2. Configure the network operation to return malformed data.
     * 3. Execute use-case for a second time and confirm it still emits the cached data.
     */
    @Test
    fun getHeros_malformedData_successFromCache() =  runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.MalformedData // Malformed Data
        )

        getHeros = GetHero(
            cache = heroCache,
            service = heroService
        )

        // Confirm the cache is empty before any use-cases have been executed
        var cachedHeros = heroCache.selectAll()
        assert(cachedHeros.isEmpty())

        // Add some data to the cache by executing a successful request
        val heroData = deserializeHeroData(HeroDataValid.data)
        heroCache.insert(heroData)

        // Confirm the cache is not empty anymore
        cachedHeros = heroCache.selectAll()
        assert(cachedHeros.size == 121)

        // Execute the use-case
        val emissions = getHeros.execute().toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // Confirm second emission is error response
        assert(emissions[1] is DataState.Response)
        val errorMsg = ((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).title
        System.out.println("${errorMsg}");

        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).title == "Network Data Error")
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description.contains("Unexpected JSON token at offset"))

        // Confirm third emission is data from the cache
        assert(emissions[2] is DataState.Data)
        assert((emissions[2] as DataState.Data).data?.size == 121)

        // Confirm the cache is still not empty
        cachedHeros = heroCache.selectAll()
        assert(cachedHeros.size == 121)

        // Confirm loading state is IDLE
        assert(emissions[3] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))
    }

}