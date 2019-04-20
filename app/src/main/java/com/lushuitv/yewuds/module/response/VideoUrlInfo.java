package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

/**
 * Description 视频信息
 * Created by weip
 * Date on 2017/9/11.
 */

public class VideoUrlInfo  extends HttpBean {


    /**
     * OBJECT : {"workContent":{"contentAdvanceUrl":"http://1254218478.vod2.myqcloud.com/2a49cdb1vodtransgzp1254218478/85e115999031868223179867389/","contentId":1,"contentUrl":"http://1254218478.vod2.myqcloud.com/2a49cdb1vodtransgzp1254218478/85e11d769031868223179867539/"},"buyRecord":{"recordId":2,"recordMoney":0,"recordStatus":0}}
     */

    private OBJECTBean OBJECT;

    public OBJECTBean getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(OBJECTBean OBJECT) {
        this.OBJECT = OBJECT;
    }

    public static class OBJECTBean {
        /**
         * workContent : {"contentAdvanceUrl":"http://1254218478.vod2.myqcloud.com/2a49cdb1vodtransgzp1254218478/85e115999031868223179867389/","contentId":1,"contentUrl":"http://1254218478.vod2.myqcloud.com/2a49cdb1vodtransgzp1254218478/85e11d769031868223179867539/"}
         * buyRecord : {"recordId":2,"recordMoney":0,"recordStatus":0}
         */

        private WorkContentBean workContent;
        private BuyRecordBean buyRecord;
        private String var;
        private String var_advance;

        public String getVar() {
            return var;
        }

        public void setVar(String var) {
            this.var = var;
        }

        public String getVar_advance() {
            return var_advance;
        }

        public void setVar_advance(String var_advance) {
            this.var_advance = var_advance;
        }

        public WorkContentBean getWorkContent() {
            return workContent;
        }

        public void setWorkContent(WorkContentBean workContent) {
            this.workContent = workContent;
        }

        public BuyRecordBean getBuyRecord() {
            return buyRecord;
        }

        public void setBuyRecord(BuyRecordBean buyRecord) {
            this.buyRecord = buyRecord;
        }

        public static class WorkContentBean {
            /**
             * contentAdvanceUrl : http://1254218478.vod2.myqcloud.com/2a49cdb1vodtransgzp1254218478/85e115999031868223179867389/
             * contentId : 1
             * contentUrl : http://1254218478.vod2.myqcloud.com/2a49cdb1vodtransgzp1254218478/85e11d769031868223179867539/
             */

            private String contentAdvanceUrl;
            private int contentId;
            private String contentUrl;

            public String getContentAdvanceUrl() {
                return contentAdvanceUrl;
            }

            public void setContentAdvanceUrl(String contentAdvanceUrl) {
                this.contentAdvanceUrl = contentAdvanceUrl;
            }

            public int getContentId() {
                return contentId;
            }

            public void setContentId(int contentId) {
                this.contentId = contentId;
            }

            public String getContentUrl() {
                return contentUrl;
            }

            public void setContentUrl(String contentUrl) {
                this.contentUrl = contentUrl;
            }

            @Override
            public String toString() {
                return "{" +
                        "contentAdvanceUrl='" + contentAdvanceUrl + '\'' +
                        ", contentId=" + contentId +
                        ", contentUrl='" + contentUrl + '\'' +
                        '}';
            }
        }

        public static class BuyRecordBean {
            /**
             * recordId : 2
             * recordMoney : 0
             * recordStatus : 0
             */

            private int recordId;
            private int recordMoney;
            private int recordStatus;

            public int getRecordId() {
                return recordId;
            }

            public void setRecordId(int recordId) {
                this.recordId = recordId;
            }

            public int getRecordMoney() {
                return recordMoney;
            }

            public void setRecordMoney(int recordMoney) {
                this.recordMoney = recordMoney;
            }

            public int getRecordStatus() {
                return recordStatus;
            }

            public void setRecordStatus(int recordStatus) {
                this.recordStatus = recordStatus;
            }


            @Override
            public String toString() {
                return "{" +
                        "recordId=" + recordId +
                        ", recordMoney=" + recordMoney +
                        ", recordStatus=" + recordStatus +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "{" +
                    "workContent=" + workContent +
                    ", buyRecord=" + buyRecord +
                    ", var='" + var + '\'' +
                    ", var_advance='" + var_advance + '\'' +
                    '}';
        }
    }


}
