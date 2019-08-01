package flutter;

import io.flutter.plugin.common.PluginRegistry;

public class NativeViewFlutterPlugin {
    public static void registerWith(PluginRegistry registry) {
        final String key = NativeViewFlutterPlugin.class.getCanonicalName();

        if (registry.hasPlugin(key)) return;

        PluginRegistry.Registrar registrar = registry.registrarFor(key);
        registrar.platformViewRegistry().registerViewFactory("plugins.nightfarmer.top/nativeview", new NativeViewFactory(registrar.messenger()));
    }
}
