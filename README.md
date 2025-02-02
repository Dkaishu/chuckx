Chuckx  [![](https://jitpack.io/v/Dkaishu/chuckx.svg)](https://jitpack.io/#Dkaishu/chuckx)
=====
[中文文档](/README-zh.md)

Chuckx is a simple in-app HTTP inspector that depends on Androidx for Android OkHttp clients. Chuckx intercepts and persists all HTTP requests and responses inside your application, and provides a UI for inspecting their content.

As you can see, this repository is forked from [jgilfelt/chuck](https://github.com/jgilfelt/chuck), which is a great library and helped me a lot, but the original repository seems to have stopped being maintained, so I migrated it to **Androidx**, and continue to maintain it.

![Chuckx](assets/chuck.gif)

Apps using Chuckx will display a notification showing a summary of ongoing HTTP activity. Tapping on the notification launches the full Chuckx UI. Apps can optionally suppress the notification, and launch the Chuckx UI directly from within their own interface. HTTP interactions and their contents can be exported via a share intent.

The main Chuckx activity is launched in its own task, allowing it to be displayed alongside the host app UI using Android 7.x multi-window support.

![Multi-Window](assets/multiwindow.gif)

Chuckx requires Android 5+ (minSdkVersion = 21) and OkHttp 3.x.

**Warning**: The data generated and stored when using this interceptor may contain sensitive information such as Authorization or Cookie headers, and the contents of request and response bodies. It is intended for use during development, and not in release builds or other production deployments.

**If there are any problems, they can all be raised in the issue.**


Setup
-----

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency
```gradle
dependencies {
    releaseImplementation 'com.github.Dkaishu.chuckx:library-no-op:v2.1.5'
    debugImplementation 'com.github.Dkaishu.chuckx:library:v2.1.5'
}
```

In your application code, create an instance of `ChuckInterceptor` (you'll need to provide it with a `Context`, because Android) and add it as an interceptor when building your OkHttp client:

```java
OkHttpClient client = new OkHttpClient.Builder()
  .addInterceptor(new ChuckInterceptor(context))
  .build();
```

That's it! Chuckx will now record all HTTP interactions made by your OkHttp client. You can optionally disable the notification by calling `showNotification(false)` on the interceptor instance, and launch the Chuck UI directly within your app with the intent from `Chuck.getLaunchIntent()`.

FAQ
---

- Why are some of my request headers missing?
- Why are retries and redirects not being captured discretely?
- Why are my encoded request/response bodies not appearing as plain text?

Please refer to [this section of the OkHttp wiki](https://square.github.io/okhttp/features/interceptors/#choosing-between-application-and-network-interceptors). You can choose to use Chuck as either an application or network interceptor, depending on your requirements.

Acknowledgements
----------------

Chuckx uses the following open source libraries. Special thanks to chunk:
- **[chuck](https://github.com/jgilfelt/chuck) - [jgilfelt](https://github.com/jgilfelt).**
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
