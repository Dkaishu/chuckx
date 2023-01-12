Chuck  [![](https://jitpack.io/v/Dkaishu/chuckx.svg)](https://jitpack.io/#Dkaishu/chuckx)
=====

Chuck是一个简洁的应用内 HTTP inspector ，它用于 Android OkHttp 客户端。Chuck 会在应用程序中拦截并持久化所有的 HTTP 请求和响应，并提供一个查看界面。

如你所见，本仓库 fork 自[jgilfelt/chuck](https://github.com/jgilfelt/chuck),，它是一个非常棒的库，对我非常有用，但是原仓库好像已经停止维护，因此我将其迁移到Androidx，并继续维护。

![Chuck](assets/chuck.gif)

使用 Chuck 的应用程序将发送通知，显示正在进行的 HTTP 活动的摘要。点击该通知可以启动完整的 Chuck 用户界面。应用程序也可以选择不使用通知，而是从自己界面中直接启动 Chuck 用户界面。HTTP 交互和它们的内容可以通过 intent 导出。
Chuck 的 MainActivity 在其自身的进程中启动，Android 7.x 的多窗口支持与可与主应用程序 UI 一起显示。

![Multi-Window](assets/multiwindow.gif)

Chuck requires Android 7+ (minSdkVersion = 24 ,targetSdkVersion = 32) and OkHttp 3.x.

**Warning**: The data generated and stored when using this interceptor may contain sensitive information such as Authorization or Cookie headers, and the contents of request and response bodies. It is intended for use during development, and not in release builds or other production deployments.

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

非常简单! 现在 Chuck 将记录所有由你的 OkHttpClient 发起的 HTTP 交互。你可以通过在拦截器实例上调用`showNotification(false)`来禁用通知，并通过`Chuck.getLaunchIntent()`的直接在你的应用程序中启动 Chuck 用户界面。

FAQ
---

- Why are some of my request headers missing?
- Why are retries and redirects not being captured discretely?
- Why are my encoded request/response bodies not appearing as plain text?

Please refer to [this section of the OkHttp wiki](https://github.com/square/okhttp/wiki/Interceptors#choosing-between-application-and-network-interceptors). You can choose to use Chuck as either an application or network interceptor, depending on your requirements.

补充
----------------

Chuck 使用了以下库:

- [OkHttp](https://github.com/square/okhttp) - Copyright Square, Inc.
- [Gson](https://github.com/google/gson) - Copyright Google Inc.
- [Cupboard](https://bitbucket.org/littlerobots/cupboard) - Copyright Little Robots.

License
-------

    Copyright (C) 2017 Jeff Gilfelt.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

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
