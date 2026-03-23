●プロスポーツチーム

個人作成したポートフォリオです。

・チームの情報収集やグッズに使用するサイトです。

●アプリ内容
・チーム情報をを調べたり、チームグッズを購入する事が出来るアプリです。

●作成理由
私は、スポーツ観戦が趣味なので憧れのチームの様なアプリを作成したいと思い作成しました。



●使用技術

○開発言語

・Java

〇プロジェクト管理ツール

〇使用フレームワーク
・Java
・css/BootStrap
・MySQL

DB
・MySQL

view

・Thymeleaf,HTML
・css/Bootstrap

○サーバー
・localhost


●実装機能
〇ログアウト時
・チーム情報観覧機能
・グッズ観覧機能
・ログイン機能
・新規登録機能

〇ログイン時
・ログアウト機能
・マイページ観覧
・チーム情報観覧機能
・グッズ観覧機能
・グッズ購入機能


●SQL文
CREATE TABLE users(id INT NOT NULL AUTO_INCREMENT, name VARCHAR(50) NOT NULL, email VARCHAR(256) NOT NULL UNIQUE, password VARCHAR(256) NOT NULL UNIQUE, PRIMARY KEY(id));

CREATE TABLE user_infos(id INT NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, full_name VARCHAR(50) NOT NULL, zip_cord VARCHAR(20) NOT NULL, country VARCHAR(50) NOT NULL, pref varchar(100) NOT NULL, city varchar(100) NOT NULL, town varchar(100), tel varchar(50) NOT NULL, PRIMARY KEY(id) );

CREATE TABLE team_news(id INT  NOT NULL AUTO_INCREMENT, posting_date DATE  NOT NULL, title VARCHAR(256) NOT NULL, article TEXT NOT NULL, PRIMARY KEY(id) );

CREATE TABLE items(id INT  NOT NULL AUTO_INCREMENT, item_name VARCHAR(256) NOT NULL, price INT NOT NULL, image VARCHAR(256) NOT NULL, size_category INT NOT NULL, PRIMARY KEY(id) );

CREATE TABLE carts(id INT  NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, item_id INT NOT NULL, size VARCHAR(1) NOT NULL, num INT NOT NULL, PRIMARY KEY(id) );

CREATE TABLE orders(id INT  NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, order_date DATE NOT NULL, sum_price INT NOT NULL, num INT NOT NULL, PRIMARY KEY(id) );

CREATE TABLE order_details(id INT  NOT NULL AUTO_INCREMENT, order_id INT NOT NULL, item_id INT NOT NULL, num INT NOT NULL, PRIMARY KEY(id) );
