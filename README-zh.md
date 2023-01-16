Chuckx  [![](https://jitpack.io/v/Dkaishu/chuckx.svg)](https://jitpack.io/#Dkaishu/chuckx)
=====

Chuckx 是一个针对 OkHttp 的基于 Androidx 的 HTTP 拦截器（inspector）。Chuckx 会拦截并持久化在应用内的 HTTP 请求和响应，并提供一个查看界面。

如你所见，本仓库 fork 自[jgilfelt/chuck](https://github.com/jgilfelt/chuck)，它是一个非常棒的库，但是原仓库好像已经停止维护，我将其迁移到 Androidx，并继续维护。如果你的应用没有使用 Androidx，建议使用原 Chunk 库。

![Chuckx](assets/chuck.gif)

在你使用 Chuckx 时，Chunkx 默认会发送通知来显示正在进行的 HTTP 活动，点击该通知可以启动完整的 Chuckx 界面，以查看详细信息。应用也可以选择不使用通知，而是从自己界面中直接启动 Chuckx 界面。当然，你也可以通过 Intent 将内容导出。
Chuckx 的 MainActivity 在独立的进程中启动，在Android 7.x 的多窗口支持下，可与主应用程序 UI 一起显示。

![Multi-Window](assets/multiwindow.gif)

Chuckx requires Android 7+ (minSdkVersion = 24) and OkHttp 3.x.

**Warning**: 使用 Chunk 时产生和存储的数据可能包含敏感信息，如授权 token 或 Cookie，以及请求和响应体的内容。Chunk 旨在开发过程中使用，而不是在 release 版本或其他生产部署中使用。

使用
-----
在根目录下的 build.gradle 添加 jitpack 仓库:

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

在 module 内的 build.gradle 添加依赖
```gradle
dependencies {
    releaseImplementation 'com.github.Dkaishu.chuckx:library-no-op:v2.1.0'
    debugImplementation 'com.github.Dkaishu.chuckx:library:v2.1.0'
}
```

在代码中，创建一个`ChuckInterceptor`的实例，并在创建 OkHttpClient 时将其作为一个拦截器加入。

```java
OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new ChuckInterceptor(context))
        .build();
```

如此简单! 现在 Chuckx 将记录所有由你的 OkHttpClient 发起的 HTTP 交互。你也可以通过在拦截器实例上调用`showNotification(false)`来禁用通知，并通过`Chuck.getLaunchIntent()`的直接在你的应用程序中启动 Chuckx 用户界面。

FAQ
---

- 为什么我的一些请求头丢失？
- 为什么重试和重定向没有被单独捕获？
- 为什么我的编码后的（encoded）请求体（request）/响应体（response）没有显示为纯文本？

请参看 [OkHttp 关于 interceptors 的选择的章节](https://square.github.io/okhttp/features/interceptors/#choosing-between-application-and-network-interceptors)，你可以根据自己的需求选择使用 Chuckx 作为 application 拦截器还是 application 拦截器。

感谢
----------------

特别感谢：

- [chuck](https://github.com/jgilfelt/chuck) - [jgilfelt](https://github.com/jgilfelt)

Chuckx 使用了以下库:

- [OkHttp](https://github.com/square/okhttp) - Copyright Square, Inc.
- [Gson](https://github.com/google/gson) - Copyright Google Inc.
- [Cupboard](https://bitbucket.org/littlerobots/cupboard) - Copyright Little Robots.


License
-------

    Copyright (C) 2017 Dkaishu.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
