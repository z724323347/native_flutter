package flutter;

import io.flutter.plugin.common.PluginRegistry;

public class NativeLayoutFlutterPlugin {
    public static void registerWith(PluginRegistry registry) {
        final String key = NativeLayoutFlutterPlugin.class.getCanonicalName();

        if (registry.hasPlugin(key)) return;

        PluginRegistry.Registrar registrar = registry.registrarFor(key);
        registrar.platformViewRegistry().registerViewFactory("plugins.nightfarmer.top/nativelayout", new NativeLayoutFactory(registrar));
    }
}
