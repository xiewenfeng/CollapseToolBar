##一、沉浸式状态栏的实现：
###1､ 在代码中设置statusBar的color值，并设置android:fitsSystemWindows="true"
android:fitsSystemWindows设置成true确保应用内容不会和系统窗口（状态栏和底下的虚拟导般栏）重叠。通过在 View 上设置和系统窗口一样高度的边框（padding ）来确保应用内容不会出现到系统窗口下面。

优点：系统会计算好系统窗口高度
缺点：若有背景图需要延伸到到系统窗口时无法实现
如下图所示
![这里写图片描述](http://img.blog.csdn.net/20170312205613101?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvc21pbGVpYW0=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

###2､为不同android版本标题栏设置不同的高度
若要背景图也延伸到系统窗口，一般通过将状态栏设成透明色，这样就可以实现背景图延伸到状态栏。

```
 /**
     * 设置状态栏颜色为透明色，这样能保证状态栏为沉浸式状态
     * 根据SDK >= 21时，标题栏高度设为statusBarHeight(25dp)+titlBarHeight(48dp)
     * 若SDK < 21,标题栏高度直接为titlBarHeight,关于不同的高度设置可以用两个values，values-19两个分别设置不同的dimens
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
```
优点：背景图可以延伸到状态栏
缺点：不同的android版本需要计算titlebar高度，稍微复杂一些
实现效果如下：
![这里写图片描述](http://img.blog.csdn.net/20170312205750037?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvc21pbGVpYW0=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

##二、标题栏折叠实现
若要使上图2中的页面，向上滑动时，头图慢慢消失，当要显示现在的列表时，标题栏显示在最顶上，如下图效果，就需要结合CoordinatorLayout与CollapsingToolbarLayout结合使用。
![这里写图片描述](http://upload-images.jianshu.io/upload_images/2109077-69d4c51948dc6414.gif?imageMogr2/auto-orient/strip)

具体使用方法：CoordinatorLayout作为最外层容器，AppBarLayout作为第一个子view，还包含一个NestedScrollView作为一起滑动的控件，CollapsingToolbarLayout嵌套在AppBarLayout里，AppBarLayout里面又嵌套ImageView和Toolbar。
###1､CoordinatorLayout 
CoordinatorLayout是用来协调其子view们之间动作的一个父view。
类似于FrameLayout，根布局，能实现的效果：
1) 让浮动操作按钮上下滑动，为Snackbar留出空间。
2) 扩展或者缩小Toolbar或者头部，让主内容区域有更多的空间。
3) 控制哪个view应该扩展还是收缩，以及其显示大小比例，包括视差滚动效果动画。
我们在NestedScrollView中设置了behavior属性，
它主要用来给CoordinatorLayout的子view们实现交互的。


###2､AppBarLayout
AppBarLayout是一种支持响应滚动手势的app bar布局（比如工具栏滚出或滚入屏幕）；与AppBarLayout组合的滚动布局有（Recyclerview、NestedScrollView等），这需要相应的控件设置app:layout_behavior="@string/appbar_scrolling_view_behavior"（上面代码中NestedScrollView控件所设置的）。没有设置的话，AppBarLayout将不会响应滚动布局的滚动事件。

###3､CollapsingToolbarLayout
CollapsingToolbarLayout是专门用来实现子布局内不同元素响应滚动细节的布局。

在CollapsingToolbarLayout中，设置了以下以个参数：
1) //当完全CollapsingToolbarLayout折叠(收缩)后的背景颜色。
app:contentScrim="?attr/colorPrimary"
2) //设置扩张时候(还没有收缩时)title距离左边的距离
app:expandedTitleMarginStart="48dp"
3) //设置扩张时候(还没有收缩时)title距离右边的距离
app:expandedTitleMarginEnd="64dp"
4)//scroll:设置该控件能滚动，   exitUntilCollapsed: 向上滚动时收缩View，Toolbar一直固定在上面
app:layout_scrollFlags="scroll|exitUntilCollapsed"

具体layout_scrollFlags还有其他三个设置：
1) enterAlways - 实现quick return效果, 当向下移动时，立即显示View（比如Toolbar)。
2) enterAlwaysCollapsed - 当你的View已经设置minHeight属性又使用此标志时，那么view将在到达这个最小高度的时候开始显示，并且从这个时候开始慢慢展开，当滚动到顶部的时候展开完。
3) snap:当一个滚动事件结束，如果视图是部分可见的，那么它将被滚动到收缩或展开。例如，如果视图只有底部25%显示，它将折叠。相反，如果它的底部75%可见，那么它将完全展开。

CollapsingToolbarLayout还可以进行一些其他设置：
1) app:contentScrim：设置折叠时标题栏布局的颜色，默认colorPrimary的色值
2) app:statusBarScrim：设置折叠时状态栏的颜色。默认colorPrimaryDark的色值。

###4､Toolbar
这里设置了app:layout_collapseMode="pin"
pp:layout_collapseMode有三种设置：
1) off：这个是默认属性，布局将正常显示，没有折叠的行为。
2) pin：CollapsingToolbarLayout折叠后，此布局将固定在顶部。
3) parallax：CollapsingToolbarLayout折叠时，此布局也会有视差折叠效果。
若设置了parallax模式时，还可以通过app:layout_collapseParallaxMultiplier设置视差滚动因子，值为：0~1。

###5､NestedScrollView
这里设置了app:layout_behavior属性，
app:layout_behavior="@string/appbar_scrolling_view_behavior"
behavior属性主要是设置了ScrollView与AppBarLayout的相互依赖关系，以非侵入的为View添加动态的依赖布局，和处理父布局(CoordinatorLayout)滑动手势。
