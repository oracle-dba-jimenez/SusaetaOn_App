package com.susaeta.susaetaon.services

object SusaetaRepositoryProvider {
    fun searchRepository(): SusaetaRepository {
        return SusaetaRepository(SusaetaApiService.create())
    }
}