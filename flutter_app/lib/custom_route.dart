import 'package:flutter/material.dart';

/**
 * 自定义 Route
 */
class CustomRoute extends PageRouteBuilder{

  final Widget widget;

  CustomRoute(this.widget)
    :super(
      transitionDuration:Duration(milliseconds: 400),
      pageBuilder:(
        BuildContext context,
        Animation<double> animation1,
        Animation<double> animation2,
      ){
        return widget;
      },

      transitionsBuilder:(
        BuildContext context,
        Animation<double> animation1,
        Animation<double> animation2,
        Widget child,
      ){
        //左右滑动
        return SlideTransition(
          position: Tween<Offset>(
            begin: Offset(-1.0, 0.0),
            end: Offset(0.0, 0.0)
          ).animate(CurvedAnimation(
            parent: animation1,
            curve: Curves.decelerate
          )),
          child: child,
        );
      },
    );


}