package com.jysd.cartoonapp.bean;

/**
 * @类名: ChapterBean
 * @功能描述: 章节bean
 * @作者: $zuozhe$
 * @时间: $date$
 * @最后修改者:
 * @最后修改内容:
 */


public class ChapterBean {

    /**
     * href url
     */
    private  String chapterUrl;
    /**
     * 页数
     */
    private  String chapterPage;



    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public String getChapterPage() {
        return chapterPage;
    }

    public void setChapterPage(String chapterPage) {
        this.chapterPage = chapterPage;
    }
}
