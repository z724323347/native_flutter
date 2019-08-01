import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

// flutter 加载Android native组件，并与原生组件通信
class MixrsPage extends StatefulWidget {
  @override
  _MixrsPageState createState() => _MixrsPageState();
}

class _MixrsPageState extends State<MixrsPage> {
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
          title: Text('MixrsPage'),
          centerTitle: true,
        ),

        // listview 加载过多原生组件 明显的掉帧，该body主要是看运行效果
        body: Column(
          children: <Widget>[
            Container(
              child: Text('listview 加载过多原生组件 明显的掉帧, 仅做测试效果'),
            ),
            Expanded(
              flex: 1,
              child: ListView.builder(
                itemCount: 50,
                itemBuilder: (BuildContext context, int index) {
                  return Container(
                    width: MediaQuery.of(context).size.width,
                    height: 200,
                    child: AndroidView(
                      viewType: 'plugins.nightfarmer.top/nativeview',
                      creationParams: {
                        "myContent": "通过参数传入的静态文本内容 $index",
                      },
                      creationParamsCodec: const StandardMessageCodec(),
                      onPlatformViewCreated: onNativeViewCreated,
                    ),
                  );
                },
              ),
            )
          ],
        )
        // 该body 做测试
        // body: Center(
        //   child: Container(
        //     width: MediaQuery.of(context).size.width,
        //     height: MediaQuery.of(context).size.height,
        //     child: AndroidView(
        //       viewType: 'plugins.nightfarmer.top/nativeview',
        //       creationParams: {
        //         "myContent": "通过参数传入的静态文本内容",
        //       },
        //       creationParamsCodec: const StandardMessageCodec(),
        //       onPlatformViewCreated: onNativeViewCreated,
        //     ),
        //   ),
        // ),
        );
  }

  MethodChannel _channel;
  void onNativeViewCreated(int id) {
    _channel = new MethodChannel('plugins.nightfarmer.top/nativeview_$id');
    setNativeViewText('Flutter 传入动态{setState}文本 _ $id');
  }

  Future<void> setNativeViewText(String text) async {
    assert(text != null);
    return _channel.invokeMethod('setText', text);
  }
}
