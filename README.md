# 目的

このプロジェクトではSpring BootでRESTful APIを実装するときにバリデーションをどのように実装するかを検討します。

# バリデーションとは

バリデーションとは、入力値が正しいかどうかをチェックすることです。バリデーションを行うことで、不正な入力値を排除することができます。  
バリデーションは、データの信頼性、整合性、セキュリティを確保し、不正確なデータや悪意のあるデータがシステムに入り込むことを防ぐために非常に重要です。

## データの整合性確認

バリデーションは、データが特定の条件に合致しているかどうかを確認し、データが整合性を持っているかどうかを検証します。  
整合性のないデータは予測不能な動作やエラーを引き起こす可能性があります。

## セキュリティ強化

バリデーションは、セキュリティを向上させるために不正な入力データを検出します。  
例えば、SQLインジェクションやクロスサイトスクリプティング（XSS）攻撃など、セキュリティ上のリスクを軽減します。

## データ品質向上

データバリデーションは、データ品質を向上させます。  
データが正確で整った状態で保存され、処理されることで、意思決定や分析の信頼性が向上します。

## エラーの早期検出

バリデーションは、データがエラーを含んでいる場合に早期に検出し、適切なエラーメッセージやエラーハンドリングを提供します。  
これにより、問題がシステムに深刻な影響を及ぼす前に対処できます。

## ビジネスルールの実装

バリデーションは、ビジネスルールをデータ層に実装するために使用されます。
たとえば、ユーザーの登録フォームにおいて、パスワードが一定の複雑さを持っている必要がある場合、その複雑さを確認するためのバリデーションルールを実装できます。

## ユーザーエクスペリエンスの向上

バリデーションは、ユーザーエクスペリエンスを向上させます。エラーメッセージやヒントを提供し、ユーザーに正しい入力を促すことができます。

# バックエンドでのバリデーションについて

# バリデーションエラー時のレスポンス

バックエンドAPIでのバリデーションエラーが発生した場合、適切なエラーレスポンスをクライアントに返すことが重要です。  
以下は、バックエンドAPIでのバリデーションエラー時に返すべき情報についての一般的なガイドラインです。

## HTTPステータスコード

バリデーションエラーが発生した場合、HTTPステータスコードを400 Bad Requestとして返します。  
これは、クライアントからのリクエストが不正確であることを示します。

## エラーメッセージ

エラーメッセージは、クライアントが理解しやすい形式で提供されるべきです。  
JSON形式やXML形式など、APIがサポートするデータフォーマットに従ってエラーメッセージを構造化します。
エラーメッセージには、どの項目がバリデーションエラーを引き起こしたか、エラーの詳細な説明、および必要な場合は対処方法やヒントを含めることができます。

下記はエラーメッセージの例です。

```json
{
  "errors": [
    {
      "field": "name",
      "message": "must not be blank"
    }
  ]
}
```

## エラーコード

エラーコードは、エラーを一意に識別するために使用されます。  
APIを呼び出した側がエラーに対処するのに役立ちます。

## 例外情報の隠蔽

セキュリティの観点から、バックエンドの例外情報を完全にクライアントに公開しないように注意してください。  
エラー情報はクライアント向けに適切にマスキングまたは非表示にする必要があります。

HTTPステータスコードは400(Bad Request)を返します。

# Spring Bootでのバリデーションについて

Spring Bootを使用してバリデーションを実装する際に、Bean ValidationとHibernate Validatorを組み合わせる方法を説明します。  
Bean Validationは、Javaの標準仕様で、Hibernate ValidatorはBean Validationの実装の1つです。  
これにより、簡単にバリデーションルールを定義し、実行できます。

> [!NOTE]
> Java Bean Validationは、Javaの標準プラットフォーム仕様の一部として導入され、当初はjavax.validationパッケージで定義されていました。
> しかし、Java EEがEclipse FoundationからJakarta EEに移行した後、Java EEから独立したプロジェクトとして、Jakarta Bean Validationとして再定義されました。
> このため、Java EE 8以降では、javax.validationパッケージではなく、jakarta.validationパッケージを使用する必要があります。
> バリデーション関連の記事ではjavax.validationパッケージを使用しているものが多いですが、Java EE 8以降を前提にしているSpring Boot
> 2.3以降では、jakarta.validationパッケージを使用する必要があります。

# バリデーションの実装

## バリデーションアノテーション

バリデーションルールを定義するために、Bean Validationではアノテーションを使用します。

### @NotNull

@NotNullアノテーションは、フィールドがnullでないことを検証します。

### @NotEmpty

@NotEmptyアノテーションは、フィールドがnullまたは空でないことを検証します。

### @NotBlank

@NotBlankアノテーションは、フィールドがnullまたは空でないことを検証します。

### @Size

@Sizeアノテーションは、フィールドのサイズが指定された範囲内であることを検証します。

### その他

その他のバリデーションアノテーションについては、以下のドキュメントを参照してください。
https://jakarta.ee/specifications/bean-validation/3.0/apidocs/
https://jakarta.ee/specifications/bean-validation/3.0/jakarta-bean-validation-spec-3.0.html#builtinconstraints
https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-defineconstraints-spec

Bean Validation 一覧 で調べるのもよいですね。

## Spring Bootでのバリデーションの実装の流れ

Spring Bootでバリデーションを実装する際の流れは、以下の通りです。

### Spring Bootプロジェクトのセットアップ

Spring InitializrまたはMaven/Gradleプロジェクトを作成し、Spring Bootプロジェクトをセットアップします。

### 依存関係の追加

バリデーションに必要な依存関係をpom.xmlまたはbuild.gradleに追加します。  
例えば、spring-boot-starter-validationを追加して、Hibernate Validatorなどのバリデーションライブラリを利用できます。

### クラスの作成

リクエストのデータモデルを表すクラスを作成します。
このクラスにはバリデーションルールを追加します。

たとえば、下記のようなjsonリクエストを送信しユーザーの氏名を登録するAPIがあると考えます。

```json
{
  "givenName": "Taro",
  "familyName": "Yamada"
}
```

givenNameとfamilyNameは必須項目とし、nullや空文字、スペースのみを許可しないようにバリデーションルールを追加します。
つまり、下記のような登録は認めないようにします。

```json
{
  "givenName": "",
  "familyName": ""
}
```

```json
{
  "givenName": " ",
  "familyName": " "
}
```

```json
{
  "givenName": null,
  "familyName": null
}
```

jsonに対応した以下のようなクラスを作成します。  
@NotBlankアノテーションを使用して、givenNameとfamilyNameがnullまたは空でないことを検証します。

```java
public class UserRequest {
    @NotBlank
    private String givenName;
    @NotBlank
    private String familyName;

    // getter
}
```

## コントローラの作成

/usersのPOSTリクエストを受け付けるためのコントローラを作成します。
リクエストボディに@Validアノテーションを使用して、バリデーションを実行します。

```java

@RestController
public class UserController {
    @PostMapping("/users")
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody UserRequest userRequest) {
        // 登録処理は省略
        return Map.of("message", "successfully created");
    }
}
```

## 動作確認

curlコマンドでリクエストを送信します。

```shell
# 200 OKが返却される
curl -X POST -H "Content-Type: application/json" -d '{"givenName": "Taro", "familyName": "Yamada"}' http://localhost:8080/users -i
```

```shell
# 400 Bad Requestが返却される
curl -X POST -H "Content-Type: application/json" -d '{"givenName": "", "familyName": ""}' http://localhost:8080/users -i
```

Spring Bootはバリデーションエラーが発生した場合、自動で400 Bad Requestを返却します。  
また、バリデーションエラーが発生した場合、コントローラのメソッドは実行されませんので、登録処理は実行されません。

## エラーレスポンスのカスタマイズ

ここまでの手順で動作確認のためにリクエストを送ると下記のようなログが出力されているはずです。

```sh
Resolved [org.springframework.web.bind.MethodArgumentNotValidException
```

このことから、Spring Bootはバリデーションエラーが発生した場合、MethodArgumentNotValidExceptionが発生することがわかります。
エラー時のレスポンスをカスタマイズするには、このMethodArgumentNotValidExceptionをハンドリングする必要があります。

@ExceptionHandlerアノテーションと@ControllerAdviceアノテーションを使用して、MethodArgumentNotValidExceptionをハンドリングします。

```java

@RestControllerAdvice
public class UserApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Map<String, String>> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        });
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.BAD_REQUEST, "validation error", errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * エラーレスポンスのクラス
     */
    public static final class ErrorResponse {
        private final HttpStatus status;
        private final String message;
        private final List<Map<String, String>> errors;

        // constructor, getter
    }
}
```

## 動作確認

curlコマンドでリクエストを送信します。

```shell    
# 400 Bad Requestが返却される
curl -X POST -H "Content-Type: application/json" -d '{"givenName": "", "familyName": ""}' http://localhost:8080/users -i
```

レスポンスは下記のようになるはずです。

```shell
HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 17 Sep 2023 07:02:39 GMT
Connection: close

{"status":"BAD_REQUEST","message":"validation error","errors":[{"field":"givenName","message":"空白は許可されていません"},{"field":"familyName","message":"空白は許可されていません"}]}
```

jqというツールを使ってレスポンスのjsonをフォーマットすると下記のようになります。

```shell
% curl -X POST -H "Content-Type: application/json" -d '{"givenName": "", "familyName": ""}' http: //localhost:8080/users | jq
% Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
Dload  Upload   Total   Spent    Left  Speed
100   242    0   207  100    35     91     15  0: 00: 02  0: 00: 02 --: --: --   106
{
    "status": "BAD_REQUEST",
    "message": "validation error",
    "errors": [
      {
        "field": "familyName",
        "message": "空白は許可されていません"
      },
      {
        "field": "givenName",
        "message": "空白は許可されていません"
      }
    ]
}
```

エラーレスポンスをカスタマイズして、バリデーションエラーのメッセージを返却することができました。

# 参考ドキュメント

## Java Bean Validationの公式ドキュメント

https://beanvalidation.org/
Jakarta Bean Validationの公式サイトです。
こちらの記事のプレゼン資料がわかりやすい。
https://beanvalidation.org/news/2018/02/26/bean-validation-2-0-whats-in-it/

## Hibernate Validatorの公式ドキュメント

https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#preface

## SpringでのJakarta Bean Validationの実装方法紹介記事

https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html
Springの公式ドキュメントで簡単にバリデーションを実装する方法が説明されています。
サラッと重要なことが書いてあるので、読んでおくと良いと思います。
