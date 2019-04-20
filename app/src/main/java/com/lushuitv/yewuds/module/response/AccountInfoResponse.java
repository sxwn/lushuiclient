package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/24.
 */

public class AccountInfoResponse extends HttpBean {


    /**
     * OBJECT : {"banks":[{"bankBoundDate":"2017-09-08","bankCard":"ali","bankCardType":"0","bankId":4775,"bankIsDefault":0,"bankOwner":"dfsaf","bankStatus":0},{"bankBoundDate":"2017-09-11","bankCard":"15652351341","bankCardType":"8","bankId":4777,"bankIsDefault":1,"bankOwner":"陆凯","bankStatus":0}],"withDrawMoney":"1501.00"}
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
         * banks : [{"bankBoundDate":"2017-09-08","bankCard":"ali","bankCardType":"0","bankId":4775,"bankIsDefault":0,"bankOwner":"dfsaf","bankStatus":0},{"bankBoundDate":"2017-09-11","bankCard":"15652351341","bankCardType":"8","bankId":4777,"bankIsDefault":1,"bankOwner":"陆凯","bankStatus":0}]
         * withDrawMoney : 1501.00
         */

        private String withDrawMoney;
        private List<BanksBean> banks;

        public String getWithDrawMoney() {
            return withDrawMoney;
        }

        public void setWithDrawMoney(String withDrawMoney) {
            this.withDrawMoney = withDrawMoney;
        }

        public List<BanksBean> getBanks() {
            return banks;
        }

        public void setBanks(List<BanksBean> banks) {
            this.banks = banks;
        }

        public static class BanksBean {
            /**
             * bankBoundDate : 2017-09-08
             * bankCard : ali
             * bankCardType : 0
             * bankId : 4775
             * bankIsDefault : 0
             * bankOwner : dfsaf
             * bankStatus : 0
             */

            private String bankBoundDate;
            private String bankCard;
            private String bankCardType;
            private int bankId;
            private int bankIsDefault;
            private String bankOwner;
            private int bankStatus;

            public String getBankBoundDate() {
                return bankBoundDate;
            }

            public void setBankBoundDate(String bankBoundDate) {
                this.bankBoundDate = bankBoundDate;
            }

            public String getBankCard() {
                return bankCard;
            }

            public void setBankCard(String bankCard) {
                this.bankCard = bankCard;
            }

            public String getBankCardType() {
                return bankCardType;
            }

            public void setBankCardType(String bankCardType) {
                this.bankCardType = bankCardType;
            }

            public int getBankId() {
                return bankId;
            }

            public void setBankId(int bankId) {
                this.bankId = bankId;
            }

            public int getBankIsDefault() {
                return bankIsDefault;
            }

            public void setBankIsDefault(int bankIsDefault) {
                this.bankIsDefault = bankIsDefault;
            }

            public String getBankOwner() {
                return bankOwner;
            }

            public void setBankOwner(String bankOwner) {
                this.bankOwner = bankOwner;
            }

            public int getBankStatus() {
                return bankStatus;
            }

            public void setBankStatus(int bankStatus) {
                this.bankStatus = bankStatus;
            }
        }
    }
}
