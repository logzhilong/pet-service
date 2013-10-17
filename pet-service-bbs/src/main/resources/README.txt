访问路径---------
http://[ip]:[port]/pet-service-bbs/request?body=[参数]

参数样例-------------

1:发帖:
{"method":"sendNote","token":"","params":{"userId":"33333","forumId":"DBEF25E53AA5401384CFC60603CC3FC7","name":"子集4的帖子","content":"子集4的帖子"}}

2:回帖:
{"method":"replyNote","token":"","params":{"userId":"6","noteId":"3","content":"第三方的萨菲","area":"北京","pid":"3"}}

3:关注圈子:
{"method":"attentionForum","token":"","params":{"userId":"6","forumid":"2"}}

4:退出圈子:
{"method":"quitForum","token":"","params":{"forumid":"56D13924B32C4DD5BC00CCE3F2963A1D"}}

5:搜索(如果Forumid为0则全站搜索,否则圈子内搜索):
{"method":"searchNote","token":"","params":{"forumid":"0","notename":"我","pageNo":"1","pageSize":"10"}}

6:根据id查看帖子详情:
{"method":"detailNote","token":"","params":{"userId":"6","noteid":"C5564A82DBC749C2A78DE5B0215B41DA"}}

7:删除帖子;
{"method":"delNote","token":"","params":{"userId":"6","noteid":"D2A849F4095942FE8FC6C8C9FB4BDC03"}}

8:举报帖子
{"method":"reportNote","token":"","params":{"noteid":"D2A849F4095942FE8FC6C8C9FB4BDC03"}}

9:根据帖子id获取所有回复
{"method":"getAllReplyNoteByNoteid","token":"","params":{"noteId":"816B9BA15E8B48E5ADF282BCB7FD640E","pageNo":"1","pageSize":"3"}}

10:(全站)我发表过的帖子列表
{"method":"getMyNotedListByuserid","token":"","params":{"userid":"1","pageNo":"1","pageSize":"3"}}

11:最新帖子(forumid为0则表示全站搜索否则圈子内部搜索)
{"method":"newNoteByFid","token":"","params":{"forumPid":"92DE9E82807142A293107DFFC4368177","pageNo":"1","pageSize":"10"}}

12:今日新增帖子列表(forumPid为0则全站搜索,否则为圈子内部搜索)
{"method":"getTodayNewNoteListByFid","token":"","params":{"forumPid":"92DE9E82807142A293107DFFC4368177","pageNo":"1","pageSize":"3"}}

13:获取精华(forumPid为0则全站搜索,否则为圈子内部搜索)
{"method":"getEuteNoteList","token":"","params":{"forumPid":"92DE9E82807142A293107DFFC4368177","pageNo":"1","pageSize":"3"}}

14(全站)最新回复(根据回复时间将帖子显示{不显示置顶帖子})
{"method":"getNewReplysByReplyct","token":"","params":{"pageNo":"1","pageSize":"3"}}

15查看圈子列表
{"method":"getAllForumAsTree","token":"","params":{"userId":"6"}}

16回复过我的帖子

17我回复过的