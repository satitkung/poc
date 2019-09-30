package com.example.poc.notification

object Test {

    data class Base(val configure: Client)
    data class Client(val appConfig: String, val miniAppConfig: String)
    private val Real: Base by lazy {
        setInit()
    }

    init {
        setInit()
    }

    private fun setInit(): Base {
        return Base(Client("App Config", "mini App Config"))
    }

    fun getAppConfig(): String {
        return Real.configure.appConfig
    }

    fun getMiniAppConfig(miniApp: MiniApp): String {
        when (miniApp) {
//            MiniApp.Base -> EncryptedSharedPreferences.instance.getString(MiniApp.Base.name,"")

        }
        return Real.configure.miniAppConfig
    }
}

enum class MiniApp {
    Base,
    Deposit,
    Lending
}