# アーキテクチャ

## 処理方式

- [Quarkiverse Hub](https://github.com/quarkiverse)
- [Quarkus Extensions](https://quarkus.io/extensions/)
- [Quarkus Guides](https://quarkus.io/guides/)

## ライブラリ

- [quarkus-arc](https://quarkus.io/extensions/io.quarkus/quarkus-arc/)
  - Bean定義
  - [quarkus-arc](https://github.com/quarkusio/quarkus/tree/main/extensions/arc)
  - [Contexts and Dependency Injection](https://quarkus.io/guides/cdi-reference)
  - [CDI 統合ガイド](https://ja.quarkus.io/guides/cdi-integration)
  - [QuarkusのCDIでBeanが見つからなかった問題の解決備忘録](https://qiita.com/fujithuro/items/74c5e14b5615b54f667d)

- [quarkus-junit5](https://github.com/quarkusio/quarkus/tree/main/test-framework/junit5)
  - [アプリケーションのテスト](https://quarkus.io/guides/getting-started-testing)
    - mockは、quarkus-junit5-mockito を利用
  - [Testing components](https://quarkus.io/guides/testing-components)
  - [Continuous Testing](https://quarkus.io/guides/continuous-testing)
    - テストツールの使い方
  - [Testing Quarkus Applications](https://www.baeldung.com/java-quarkus-testing)

- [io.quarkus:quarkus-test-h2](https://github.com/quarkusio/quarkus/tree/main/test-framework/h2)
  - [Quarkusでデータソースを設定](https://ja.quarkus.io/guides/datasource)
    - アプリケーションをネイティブ実行可能ファイルにコンパイルしても、データベースはJVMプロセスとして実行されます。

- [io.rest-assured](https://github.com/rest-assured/rest-assured)
  - RESTサービステスト
  - [rest-assured](https://rest-assured.io/)

- [quarkus-core](https://github.com/quarkusio/quarkus/tree/main/core/runtime)
  - quarkus-resteas
  - 

Jakarta JSON Binding

## リアクティブ

- [リアクティブ入門](https://ja.quarkus.io/guides/getting-started-reactive)
- [Quarkus リアクティブアーキテクチャ](https://ja.quarkus.io/guides/quarkus-reactive-architecture)


## REST API lambdaアーキテクチャ

- [quarkus-amazon-lambda-rest](https://quarkus.io/extensions/io.quarkus/quarkus-amazon-lambda-rest/)
  - [Reactive Routesの使用](https://ja.quarkus.io/guides/reactive-routes)
    - quarkus-resteasy
      - WORKER THREADを使うため、オーバーヘッドあり
      - [RESTEasy Classic](https://ja.quarkus.io/guides/resteasy)
        - Quarkus 2.8 までデフォルトの Jakarta REST (旧称 JAX-RS) 実装であった RESTEasy Classic
        - 従来のブロッキングワークロードとリアクティブワークロードの両方を同様にサポートする Quarkus REST (旧称 RESTEasy Reactive) の使用が推奨されています。
    - quarkus-undertow
      - WORKER THREADを使うため、オーバーヘッドあり
      - [HTTPリファレンス](https://ja.quarkus.io/guides/http-reference)
        - Undertowがある場合、RESTEasyはServletフィルタとして機能します。 
        - Undertowがない場合、RESTEasyはServletを介さずにVert.x上で直接動作します。
        - Eclipse Vert.xは、基本的なHTTPレイヤーを提供します。 
        - Servletのサポートのために、QuarkusはVert.xの上で動作するカスタマイズされたUndertowバージョンを使用し、Jakarta RESTのサポートはRESTEasyを通じて提供されます。
    - quarkus-reactive-routes
      - リアクティブな、＠Route利用
      - WORKER THREADを使うないため、オーバーヘッドなし
        - 本当は、これが最適。
  - [quarkus-funqy-http]
    - `@Funq`を使って、HTTPサービスを構築する。
      - [Funqy](https://quarkus.io/guides/funqy)
        - AWS API Gateway経由で利用するときには、利用しない。

- [Quarkus REST (旧 RESTEasy Reactive) への移行](https://ja.quarkus.io/guides/rest-migration)
  - `quarkus-resteasy`より、`quarkus-rest`を使う。
  - [Quarkus REST（旧RESTEasy Reactive）によるRESTサービスの作成](https://ja.quarkus.io/guides/rest)
  - `quarkus-rest`は、`quarkus-vertx-http`を使っているので、`quarkus-amazon-lambda-rest`を使える？

- [REST API と HTTP API のどちらかを選択する](https://docs.aws.amazon.com/ja_jp/apigateway/latest/developerguide/http-api-vs-rest.html)
  - API Gatewayは、基本に、REST APIを利用する

- rest-jsonのバインド方法
  - quarkus-rest-jackson
  - quarkus-rest-jsonb
  - quarkus-jsonp

- 結論
  - 以下を組合せる。
    - runtime
      - quarkus-amazon-lambda-rest
      - quarkus-rest
    - test
      - quarkus-junit5
      - rest-assured
    - 実装方法ガイドライン
      - [Quarkus REST（旧RESTEasy Reactive）によるRESTサービスの作成](https://ja.quarkus.io/guides/rest)

## REST API 以外のlambdaアーキテクチャ

- [AWS Lambda](https://ja.quarkus.io/guides/aws-lambda)

- [Java の Lambda 関数ハンドラーの定義](https://docs.aws.amazon.com/ja_jp/lambda/latest/dg/java-handler.html)
  - 他の AWS サービスによって送信されるイベントの場合、aws-lambda-java-events ライブラリのタイプを使用します。
    - [aws-lambda-java-events](https://github.com/aws/aws-lambda-java-libs/tree/main/aws-lambda-java-events)

## 処理方式

- 使い分け
  - シンプルな実装：Quarkus＋Lambda
    - Lambdaによるイベント駆動、または、API Gateway経由の REST API
    - 基本的に、こちらを採用するが、複雑なものまで、これで実装しない。
  - 複雑な実装：Spring Boot＋Container
    - ECS＋FargateのWeb AP、または、ECS Task

- API Gateway経由でのRest API
- Lambadのイベント駆動
  - S3：ファイル保存での処理起動
  - DynamoDB：CQRSによるインデックス作成
  - SQS：
  - Step Functions：バッチ処理
  - EventBridge
    - [Amazon EventBridge イベントバスのコンセプト](https://docs.aws.amazon.com/eventbridge/latest/userguide/eb-what-is-how-it-works-concepts.html)
    - [Amazon EventBridge Pipes のコンセプト](https://docs.aws.amazon.com/eventbridge/latest/userguide/pipes-concepts.html)
    - [Amazon EventBridge のイベントパターンのベストプラクティス](https://docs.aws.amazon.com/eventbridge/latest/userguide/eb-patterns-best-practices.html)

## 処理方式の実装

- [AWS Lambda](https://quarkus.io/guides/aws-lambda)
  - 標準的なイベント駆動実装

- [Invoking Lambda with events from other AWS services](https://docs.aws.amazon.com/lambda/latest/dg/lambda-services.html)
  - Amazon Managed Streaming for Apache Kafka : Event source mapping
  - Self-managed Apache Kafka : Event source mapping
  - Amazon API Gateway : Event-driven; synchronous invocation
  - AWS CloudFormation : Event-driven; asynchronous invocation
  - Amazon CloudWatch Logs : Event-driven; asynchronous invocation
  - AWS CodeCommit : Event-driven; asynchronous invocation
  - AWS CodePipeline : Event-driven; asynchronous invocation
  - Amazon Cognito : Event-driven; synchronous invocation
  - AWS Config : Event-driven; asynchronous invocation
  - Amazon Connect : Event-driven; synchronous invocation
  - Amazon DocumentDB : Event source mapping
  - Amazon DynamoDB : Event source mapping
  - Elastic Load Balancing (Application Load Balancer) : Event-driven; synchronous invocation
  - Amazon EventBridge (CloudWatch Events) : Event-driven; asynchronous invocation (event buses), synchronous or asynchronous invocation (pipes and schedules)
  - AWS IoT : Event-driven; asynchronous invocation
  - Amazon Kinesis : Event source mapping
  - Amazon Data Firehose : Event-driven; synchronous invocation
  - Amazon Lex : Event-driven; synchronous invocation
  - Amazon MQ : Event source mapping
  - Amazon Simple Email Service : Event-driven; asynchronous invocation
  - Amazon Simple Notification Service : Event-driven; asynchronous invocation
  - Amazon Simple Queue Service : Event source mapping
  - Amazon Simple Storage Service (Amazon S3) : Event-driven; asynchronous invocation
  - Amazon Simple Storage Service Batch : Event-driven; synchronous invocation
  - Secrets Manager : Secret rotation
  - AWS Step Functions : Event-driven; synchronous or asynchronous invocation
  - Amazon VPC Lattice : Event-driven; synchronous invocation

- [REST API と HTTP API のどちらかを選択する](https://docs.aws.amazon.com/ja_jp/apigateway/latest/developerguide/http-api-vs-rest.html)
  - API Gatewayは、基本に、REST APIを利用する

- [AWS Lambda with Quarkus REST, Undertow, or Reactive Routes](https://ja.quarkus.io/guides/aws-lambda-http)
  - 標準的なREST API実装
  - [quarkus-amazon-lambda-rest](https://quarkus.io/extensions/io.quarkus/quarkus-amazon-lambda-rest/)

```cmd
mvn archetype:generate -DarchetypeGroupId=io.quarkus -DarchetypeArtifactId=quarkus-amazon-lambda-rest-archetype -DarchetypeVersion=3.22.1
```

- RESTEasy
  - JAX-RS (Java API for RESTful Web Services) で、RESTful Web サービスを構築するためのフレームワーク。
  - Quarkus 2.8 までデフォルトの Jakarta REST (旧称 JAX-RS) 実装
  - [RESTEasy リファレンスガイド](https://docs.redhat.com/ja/documentation/red_hat_jboss_enterprise_application_platform/5/html-single/resteasy_reference_guide/index)
  - [RESTEasy](https://resteasy.dev/)
  - [RESTEasy Classic](https://quarkus.io/guides/resteasy)
- Undertow
  - 軽量で高性能な Java ベースの Web サーバー 
  - [Undertow](https://docs.redhat.com/ja/documentation/red_hat_jboss_enterprise_application_platform/7.1/html/development_guide/undertow)
- Vert.x-Web
  - Webアプリケーションを構築するためのフレームワーク
  - [Vert.x-Web](https://vertx.io/docs/vertx-web/java/)
- Funqy
  - クロスプロバイダで利用可能な関数を実装するときに利用する。
    - AWS API Gateway経由で利用するときには、利用しない。
  - [Funqy](https://quarkus.io/guides/funqy)

- RESTの実装方法
  - [Writing REST Services with Quarkus REST (formerly RESTEasy Reactive)](https://quarkus.io/guides/rest)

- サンプル
  - quarkus-resteasy ： remove if not using jaxrs
  - quarkus-undertow ： remove if not using servlets
    - 
  - quarkus-reactive-routes ： remove if not using vertx web
    - vertxの実装を使って、Reactive実装する。
    - [Reactive Routesの使用](https://ja.quarkus.io/guides/reactive-routes)
  - quarkus-funqy-http ： remove if not using funqy
    - [Funqy](https://quarkus.io/guides/funqy)
      - AWS API Gateway経由で利用するときには、利用しない。
vertx

## 永続化アクセス

- DBアクセス
  - mybatis
    - [MyBatis SQL Mapper](https://quarkus.io/extensions/io.quarkiverse.mybatis/quarkus-mybatis/)
    - [Quarkus - Using MyBatis](https://docs.quarkiverse.io/quarkus-mybatis/dev/index.html)
- AWS SDK v2
  - [Using the AWS Java SDK v2](https://quarkus.io/guides/aws-lambda#aws-sdk-v2)

## ライブラリ化

- [独自のエクステンションの作成](https://ja.quarkus.io/guides/writing-extensions)
- [Creating a Quarkus extension for AWS CloudWatch](https://quarkus.io/blog/quarkus-aws-cloudwatch_extension/)
- [Solving problems with custom Quarkus extensions](https://quarkus.io/blog/solving-problems-with-custom-extensions/)

  - 以下のコマンドで、ひな形を作成する。

```cmd
mvn io.quarkus:quarkus-maven-plugin:create-extension -DwithoutTests
```

## 設定値管理

- [設定リファレンスガイド](https://ja.quarkus.io/guides/config-reference)

## Uber-jarとFast-jar

- [QuarkusのFast-JARパッケージ](https://blog.worldline.tech/2023/09/05/quarkus-fast-jar.html)

## Mavenのexecutionタグ

- [Guide to Configuring Plug-ins](https://maven.apache.org/guides/mini/guide-configuring-plugins.html)

## Mavenプラグイン

- [QuarkusとMaven](https://quarkus.io/guides/maven-tooling.html)
- [Quarkus Mavenプラグイン](https://ja.quarkus.io/guides/quarkus-maven-plugin)

- [マルチモジュールプロジェクトでの作業](https://quarkus.io/guides/maven-tooling.html#multi-module-maven)

## IDE統合

- [IDE サポートのヒント](https://ja.quarkus.io/guides/writing-extensions#ide-support-tips)
  - pom.xmlのプロパティに、`<m2e.apt.activation>jdt_apt</m2e.apt.activation>` を設定
