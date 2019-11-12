import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

// flutter 加载Android native组件，并与原生组件通信
class MixrsLayoutPage extends StatefulWidget {
  @override
  _MixrsLayoutPageState createState() => _MixrsLayoutPageState();
}

class _MixrsLayoutPageState extends State<MixrsLayoutPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          leading: GestureDetector(
            child: Icon(Icons.arrow_back),
            onTap: () {
              Navigator.of(context).pop();
            },
          ),
          title: Text('MixrsLayoutPage'),
          centerTitle: true,
        ),

        // 该body 做测试
        body: Center(
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            child: AndroidView(
              viewType: 'plugins.nightfarmer.top/nativelayout',
              creationParams: {
                "layout": "通过参数传入的静态文本内容",
              },
              creationParamsCodec: const StandardMessageCodec(),
              onPlatformViewCreated: onNativeViewCreated,
            ),
          ),
        ),
        );
  }

  MethodChannel _channel;
  void onNativeViewCreated(int id) {
    _channel = new MethodChannel('plugins.nightfarmer.top/nativelayout_$id');
    setNativeViewText('Flutter 传入动态{setState}文本 _ $id');
  }

  Future<void> setNativeViewText(String text) async {
    assert(text != null);
    Map _k = {'text': text, 'img': 'assets/image/ic.png'};
    return _channel.invokeMethod('setLayoutText', _k);
  }
}
