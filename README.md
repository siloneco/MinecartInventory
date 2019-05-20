# Minecart Inventory Plugin

## 概要
トロッコに乗った時にプレイヤーのインベントリを切り替えることができるPluginです。  
インベントリはトロッコの名前と同じIDで登録されたインベントリに切り替わります。  
登録されていない場合は何も変わりません。  
名前の付いていないトロッコの場合は **default** で登録されたインベントリに切り替わります  

## コマンド (短縮: /mi )
* /minecartinventory set [ID] - 指定したIDでインベントリを保存します。
* /minecartinventory reset [ID] - 登録されているインベントリデータを削除します
* /minecartinventory list - 登録されているインベントリデータを表示します  

## その他の機能
### 1. トロッコの名前を設定
金床で名前を付けた名札を スニーク + 右クリック でトロッコに名前を付けることができます
### 2. トロッコの名前を表示
手に何も持たずトロッコを スニーク + 右クリック でトロッコの名前を表示できます

## 権限
| 権限 | 内容 | デフォルト値 |
|-----------|------------|------------|
| minecartinventory.switchinventory | この権限を持っているプレイヤーのみトロッコに乗った時にインベントリを切り替えます | 全員 |
| minecartinventory.command.minecartinventory | /minecartinventoryコマンドの権限 | OPのみ |
| minecartinventory.changecartname | 名札でトロッコの名前を変更できる権限 | OPのみ |
| minecartinventory.checkname | トロッコの名前を確認できる権限 | OPのみ |

## License
[GNU General Public License v3.0](LICENSE)

## 製作者

* [siloneco](https://github.com/siloneco)