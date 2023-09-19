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

## テストコードの実装

MockMvcを使用したテストコードを実装します。

```java

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void ユーザー登録時にgivenNameとfamilyNameがnullの場合は400エラーとなること() throws Exception {
        UserPostRequest userRequest = new UserPostRequest(null, null);
        ResultActions actual = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userRequest)));
        actual.andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "status": "BAD_REQUEST",
                          "message": "validation error",
                          "errors": [
                            {
                              "field": "familyName",
                              "message": "must not be blank"
                            },
                            {
                              "field": "givenName",
                              "message": "must not be blank"
                            }
                          ]
                        }
                        """));
    }

}
```

このようにテストコードを実装することで、バリデーションエラー時のレスポンスが正しいことを確認することができます。  
ただ、すべてのパターンの入力値をテストしてしまうとUserControllerTestクラスが肥大化してしまいます。  
そこで、パラメータのバリデーションはFormのクラスの単体試験としてテストすることにします。

```java
class UserPostRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenNameとfamilyNameがnullのときにバリデーションエラーとなること() {
        UserPostRequest userPostRequest = new UserPostRequest(null, null);
        Set<ConstraintViolation<UserPostRequest>> violations = validator.validate(userPostRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenName", "空白は許可されていません"), tuple("familyName", "空白は許可されていません"));
    }

    @Test
    public void givenNameとfamilyNameが空文字のときにバリデーションエラーとなること() {
        UserPostRequest userPostRequest = new UserPostRequest("", "");
        Set<ConstraintViolation<UserPostRequest>> violations = validator.validate(userPostRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("givenName", "空白は許可されていません"), tuple("familyName", "空白は許可されていません"));
    }
}
```

## その他のバリデーションアノテーションを利用した実装

@NotBlank以外にも様々なバリデーションアノテーションを利用した実装とテストのサンプルコードを用意しました。

### @AssertTrueを使った相関項目チェック

@NotBlankではフィールド単項目のチェックしかできませんが、@AssertTrueを使うことで相関項目のチェックを実装することができます。

たとえば、givenNameとfamilyNameを更新するときに、どちらも空文字の場合はエラーとするような場合です。

こういった相関項目チェックは、@AssertTrueを使うことで実装することができます。

// sample

他にも相関項目チェックは下記のようなユースケースがあります。

- パスワードの確認
  パスワードとパスワードの確認フィールドが一致していない場合にエラーを表示する。
- 年齢による購入制限
  未成年の場合に購入できない商品の場合にエラーを表示する。
- 日付範囲の妥当性チェック
  開始日と終了日の範囲が、終了日 < 開始日となっている場合にエラーを表示する。

### カスタムバリデーションアノテーションを使った実装

すでに定義されているバリデーションアノテーションでは実装できないバリデーションを実装する場合は、カスタムバリデーションアノテーションを定義することで実装することができます。
カスタムバリデーションアノテーションを定義するには、下記の2つのクラスを作成する必要があります。

- バリデーションアノテーションの定義クラス
- バリデーションアノテーションのバリデータクラス

#### バリデーションアノテーションの定義クラス

バリデーションアノテーションの定義クラスは、@Constraintアノテーションを付与して定義します。

// sample

```java

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailValidator.class})
public @interface Email {

    String message() default "メールアドレスの形式が不正です";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

@Targetアノテーションで、バリデーションアノテーションを付与できる対象を指定します。
@Retentionアノテーションで、バリデーションアノテーションの有効期間を指定します。
@Constraintアノテーションで、バリデーションアノテーションのバリデータクラスを指定します。

#### バリデーションアノテーションのバリデータクラス

バリデーションアノテーションのバリデータクラスは、ConstraintValidatorインターフェースを実装して定義します。

```java
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
}
```

ConstraintValidatorインターフェースのジェネリクスには、バリデーションアノテーションの定義クラスとバリデート対象の値の型を指定します。

```java
public class カスタムバリデータのクラス名 implements ConstraintValidator<バリデーションアノテーションの定義クラス名, バリデート対象の値の型>
```

バリデーションアノテーションのバリデータクラスでは、isValidメソッドを実装します。

```java
boolean isValid(バリデート対象の値の型 value,ConstraintValidatorContext context);
```

isValidメソッドの第1引数には、バリデーション対象のフィールドの値が渡されます。
isValidメソッドの第2引数には、バリデーションのコンテキストが渡されます。
バリデーションのコンテキストでは、バリデーションの結果を設定したり、バリデーションのメッセージを設定したりすることができます。

### カスタムバリデーションアノテーションの利用例

バリデーションアノテーションをカスタマイズしてできることとしては下記のようなものがあります。

- 登録したいTODOのステータスがTODOかIN_PROGRESSのいずれかであることをチェックする
- そのサービス独自のパスワードのルールを満たしているかチェックする
- 宿の予約人数が、部屋の定員を超えていないかチェックする

また、@AssertTrueアノテーションを使ったチェックの事例を、バリデーションアノテーションをカスタマイズして実装することもできます。

## まとめ

Spring Bootでは、Jakarta Bean Validationの実装としてHibernate Validatorが利用されています。  
Hibernate Validatorは、Bean Validationの実装の1つであり、Bean Validationの仕様に準拠しています。

Jakarta Bean ValidationとHibernate Validatorの仕様を理解し、それらがどのようにSpring Bootで利用されているかを理解することで、Spring
Bootでバリデーションを実装することができます。

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
