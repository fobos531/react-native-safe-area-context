package com.th3rdwave.safeareacontext

import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

// Fool autolinking for older versions that do not support BaseReactPackage.
// public class SafeAreaContextPackage implements ReactPackage {
class SafeAreaContextPackage : BaseReactPackage() {
  override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
    return when (name) {
      SafeAreaContextModule.NAME -> SafeAreaContextModule(reactContext)
      else -> null
    }
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider {
    val moduleList: Array<Class<out NativeModule?>> = arrayOf(SafeAreaContextModule::class.java)
    val reactModuleInfoMap: MutableMap<String, ReactModuleInfo> = HashMap()
    for (moduleClass in moduleList) {
      val reactModule = moduleClass.getAnnotation(ReactModule::class.java) ?: continue
      reactModuleInfoMap[reactModule.name] =
          ReactModuleInfo(
              reactModule.name,
              moduleClass.name,
              true,
              reactModule.needsEagerInit,
              reactModule.isCxxModule,
              BuildConfig.IS_NEW_ARCHITECTURE_ENABLED)
    }
    return ReactModuleInfoProvider { reactModuleInfoMap }
  }

  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf<ViewManager<*, *>>(SafeAreaProviderManager(), SafeAreaViewManager())
  }
}
