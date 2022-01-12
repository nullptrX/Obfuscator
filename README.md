# 添加插件依赖



项目根目录`build.gradle`

```groovy

buildscript {
  repositories {
    ...
    // 添加仓库地址  
    maven { url 'https://gitee.com/nullptrX/repo/raw/master/m2/' }
  }

  dependencies {
    ...
    // 添加插件地址
  	classpath 'io.github.nullptrx.obfuscator:gradle-plugin:1.0.0'
    // 添加已实现的加密字符串的库
    classpath 'io.github.nullptrx.obfuscator:xor:1.0.0'
  }
}

```



项目目录`build.gradle`，如`app/build.gradle`

```groovy

plugin {
  id 'obfuscator'
}

或者

apply plugin: 'obfuscator'

```



# 添加项目依赖（可选）

如果使用了`'io.github.nullptrx.obfuscator:xor:1.0.0'`这个库，就必须添加项目依赖，否则会使项目找不到该类

项目根目录`build.gradle`

```groovy

allprojects {
  repositories {
    google()
    mavenCentral()
    // 添加仓库地址  
    maven { url 'https://gitee.com/nullptrX/repo/raw/master/m2/' }
  }
}

```



如果上面方式不可用，项目根目录`settings.gradle`

```groovy

dependencyResolutionManagement {
  //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    jcenter() // Warning: this repository is going to shut down soon
    // 添加仓库地址  
    maven { url 'https://gitee.com/nullptrX/repo/raw/master/m2/' }
  }
}

```



# 使用混淆插件



配置插件参数

```groovy

obfuscator {
  str {
    // 默认true
    enabled = true
    // 包名
    packages = ['a.b.c']
    // 加密库实现
    implementation = 'x.y.z.XOR'
    // 模式 random(随机生成密码)/fixed(固定字符串密码)
    mode = "random"
    // 随机密码长度
    randomPassword = 1
    // 固定密码值
    fixedPassword = 'abc'
  }
}

```



# 打包测试
