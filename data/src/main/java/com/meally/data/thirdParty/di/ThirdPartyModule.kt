package com.meally.data.thirdParty.di

import com.meally.data.thirdParty.ThirdPartyRepositoryImpl
import com.meally.domain.thirdParty.ThirdPartyRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val thirdPartyModule = module {
    singleOf(::ThirdPartyRepositoryImpl) bind ThirdPartyRepository::class
}