# ZK+Spring+Hibernate+Maven整合

***
##說明
***

ZK 是一個基於 Java EE 與 AJAX 的使用者介面框架 (UI framework)，它提供上百個元件讓你打造快速反應的網頁應用程式 (web application)的介面。

ZK的實現完全用java實現，開發過程中不會有任何的JavaScript代碼，但是通過將zul文件（ZK的源文件，類似於jsp文件）通過ZK引擎編譯後查看頁面原始碼，結果還是可以看到，ZK實現的本質還是通過JavaScript+CSS來處理異步請求的。

ZK通常會與SpringMVC做配合，讓Spring管理Model並且傳給ZK顯示，
嚴格來說ZK就是一個支援Spring的前端框架，但是它並不適合用來做一般網頁，而是用來寫back office(後台)，
ZK可以單獨使用，不過這裡要說明與hibernate，spring等結合構建一個完整的WEB應用。
<br><br>

***
###配置
***

根據文件，ZK有自有的注入方式，這裡說明以Spring為主的注入方式。

1.Maven配置pom.xml<br>
範例專案：<br>
zk          8.5.0-Eval<br>
Spring      5.0.6.RELEASE<br>
Hibernate   5.4.1.Final<br>
MySql       5.1.45<br>
c3p0        0.9.5.2<br>

2.在Web.xml中加入需要的listener與Zk所需的servlet<br>
Web.xml是用來載入主要的Web初始化，所以將ZK與Spring載入，詳情可以參照官網或者文件<br>
http://books.zkoss.org/zkessentials-book/master/spring_integration/configuration.html

3.配置Spring引入Hibernate<br>
設定applicationContext.xml<br>
這邊要載入bean，並且讓Spring可以使用Annotation的方式注入及接管Hibernate。

<br><br>

***
###設計模式MVC與MVVM
***

MVC 其實是個通用的軟體模式詞彙，泛指一般把系統分成模型（Model）, 視圖（View）, 控制器（Controller）三部分的架構。ZK 借用這個詞來指稱「直接透過元件 API 來控制元件」的設計模式。

--------------------------------------------------------------------
View<--component API--Controller components event listeners---->Model
--------------------------------------------------------------------

MVVM 的 Model 跟 View 所代表的角色跟 MVC 相同， VM 代表的是 ViewModel，其實是另一種形式的控制器角色，只是這個模式下，你不需呼叫元件的 API 來控制畫面，而是「透過資料繫結 (data binding) 來控制元件」。

--------------------------------------------------------------------
View<--data binding-->ViewModel states commands---->Model
--------------------------------------------------------------------

<br><br>

***
###ZK的方式獲取Spring的Bean
***

1.ZK本身支援MVC或者MVVM，但是在注入上只限制Composer或者ViewModel才能注入Bean。
若為Controller必須在後面繼承Composer，像是`extends SelectorComposer<Component>`，
如此一來才能夠使用ZK既有的注入方式注入Bean。

2.使用@wirevariable取代Spring的@Autowire
<br><br>

***
###ViewModel data binding 語法
***

1.載入資料語法<br>
@init : 一次性載入<br>
@load : 載入並同步(ViewModel帶入至zul顯示)<br>
@save : 元件屬性綁定到ViewModel<br>
@bind : 雙向繫結(@load+@save)

2.@Command : 事件傾聽器<br>
將元件的事件屬性（如 onClick）綁定到 ViewModel 中的方法 (method)，之後在該事件發生時（如按下按鈕），ZK 就呼叫綁定的方法

3.@NotifyChange : 更新畫面<br>
必須要主動透過 @NotifyChange 告知ViewModel根據改變的屬性個數不同

4.@validator : 輸入驗證<br>
驗證器綁定到元件上。
<br><br>

***
###範例專案
***

以MVVM架構實現"當日郊山活動"之 新增、查詢、修改、刪除。
<br><br>

***
###參考資料
***

1.zk<br>
https://www.zkoss.org/ <br>
2.Introduction of ZK (含Spring配置)<br>
http://books.zkoss.org/zkessentials-book/master/index.html <br>
3.Introduction of MVVM <br>
http://books.zkoss.org/zk-mvvm-book/8.0/introduction_of_mvvm.html <br>
4.ZK 教學中文書 - ZK 教練 <br>
https://hawk.gitbook.io/zk-coach/ <br>
    


