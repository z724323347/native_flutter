import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String time = '';
  Timer timer;

  static const nativeChannel = const MethodChannel('io/native.channel.method');

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  void _getTime() {
    setState(() {
      time = DateTime.now().toString();
    });
  }

  @override
  void initState() {
    super.initState();
    timer = Timer.periodic(new Duration(seconds: 1), (timer) {
      _getTime();
    });
  }

  @override
  void dispose() {
    super.dispose();
    timer.cancel();
  }

  Future<Null> _finish() async {
    try {
      Map<String, dynamic> map = {
        'url': 'https://github.com/z724323347/native_flutter',
        'msg': 'github'
      };
      // 在通道上调用此方法 与native通信
      final String result = await nativeChannel.invokeMethod('finish', map);
    } on PlatformException catch (e) {}
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: (){
        //flutter module 参数统一返回到native端
        return _finish();
      },
      child: Scaffold(
        appBar: AppBar(
          leading: GestureDetector(
            onTap: () {
              // Navigator.of(context).pop();
              _finish();
            },
            child: Icon(Icons.arrow_back),
          ),
          title: Text('Flutter Page'),
          centerTitle: true,
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(
                'Flutter page event:',
              ),
              Text(
                '$_counter',
                style: Theme.of(context).textTheme.display1,
              ),
              SizedBox(height: 20),
              Text('$time'),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: _incrementCounter,
          tooltip: 'Increment',
          child: Icon(Icons.add),
        ), // This trailing comma makes auto-formatting nicer for build methods.
      ),
    );
  }
}
