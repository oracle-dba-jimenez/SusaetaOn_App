package services

object SusaetaRepositoryProvider {
    fun provideSearchRepository(): SusaetaRepository {
        return SusaetaRepository(SusaetaApiService.Factory.create())
    }
}