# 簡易仕様書サンプル

### 作者
内海匠
### アプリ名
nearNearグルメ

#### コンセプト
近くのお店を調べることができる。
「近い」の「near」が猫の鳴き声に似ていると感じたため全体的に猫をイメージして作成

#### こだわったポイント
検索範囲を指定するときに数字を入力するのではなく猫を左右に動かして検索範囲を変えれるようにすることで直感的に操作できるようにした。

### 公開したアプリの URL（Store にリリースしている場合）

### 該当プロジェクトのリポジトリ URL（GitHub,GitLab など Git ホスティングサービスを利用されている場合）
[https://github.com/xxxx](https://github.com/otomototomoni/nearNear)

## 開発環境
### 開発環境
Android Studio Ladybug | 2024.2.1 Patch 2

### 開発言語
Kotlin 2.0.0

### テーブル定義(ER図)などの設計ドキュメント（ウェブアプリ）


### 開発環境構築手順(ウェブアプリ)


## 動作対象端末・OS
### 動作対象OS
Android 13.2.3

## 開発期間
14日間

## アプリケーション機能

### 機能一覧
- レストラン検索：ホットペッパーグルメサーチAPIを使用して、現在地周辺の飲食店を検索する。
- レストラン情報取得：ホットペッパーグルメサーチAPIを使用して、飲食店の詳細情報を取得する。
- 電話アプリ連携：飲食店の電話番号を電話アプリに連携する。
- 地図アプリ連携：飲食店の所在地を地図アプリに連携する。

### 画面一覧
- 検索画面 ：条件を指定してレストランを検索する。
- 一覧画面 ：検索結果の飲食店を一覧表示する。

### 使用しているAPI,SDK,ライブラリなど
- ホットペッパーグルメサーチAPI
- LocationManager

### 技術面でアドバイスして欲しいポイント
〇LocationManagerやFused Location Provider APIなどいろいろな方法を用いて現在地を取得しようと試みたのですが、どうしても現在地が取得できませんでした。どのようにしたら現在地を取得できるのかアドバイスしていただきたいです。
〇メソッドをどれほどの大きさの処理で分けるのか手探りで行っていたので、実際にコードを書いていてどのように分けられていたら読みやすいのかアドバイスしていただきたいです。また、ファイル名や処理をファイルで分割することを何となくで行っていたためどのようにすればいいのか教えていただきたいです。
〇現在の私は、変数名などを決める際に何をしたいのかを単純に英語で記述したりキャメルケースで記述したりしているのですが、命名規則をする際にどのようなことを意識しているか教えていただきたいです。
