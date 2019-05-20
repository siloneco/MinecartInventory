# Minecart Inventory Plugin

## 概要
Entityに乗った時にプレイヤーのインベントリを切り替えることができるPluginです。  
インベントリはEntityの名前と同じIDで登録されたインベントリに切り替わります。  
登録されていない場合は何も変わりません。  
名前の付いていないEntityの場合は **default** で登録されたインベントリに切り替わります  

## コマンド (短縮: /mi )
* /minecartinventory set [ID] - 指定したIDでインベントリを保存します。
* /minecartinventory reset [ID] - 登録されているインベントリデータを削除します
* /minecartinventory list - 登録されているインベントリデータを表示します  

## その他の機能
### 1. Entityの名前を設定
金床で名前を付けた名札を スニーク + 右クリック でEntityに名前を付けることができます
### 2. Entityの名前を表示
手に何も持たずEntityを スニーク + 右クリック でEntityの名前を表示できます

## 権限
| 権限 | 内容 | デフォルト値 |
|-----------|------------|------------|
| minecartinventory.switchinventory | この権限を持っているプレイヤーのみEntityに乗った時にインベントリを切り替えます | 全員 |
| minecartinventory.command.minecartinventory | /minecartinventoryコマンドの権限 | OPのみ |
| minecartinventory.changecartname | 名札でEntityの名前を変更できる権限 | OPのみ |
| minecartinventory.checkname | Entityの名前を確認できる権限 | OPのみ |

## License
[GNU General Public License v3.0](LICENSE)

## 製作者

* [siloneco](https://github.com/siloneco)