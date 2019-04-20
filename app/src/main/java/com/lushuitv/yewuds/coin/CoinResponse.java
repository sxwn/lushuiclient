package com.lushuitv.yewuds.coin;

import com.lushuitv.yewuds.module.entity.HttpBean;

import java.util.List;

/**
 * 我的金币
 * Created by weip on 2017\12\21 0021.
 */

public class CoinResponse extends HttpBean{


    /**
     * OBJECT : {"list":[{"gold":10,"id":18,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":60,"id":19,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":180,"id":20,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":680,"id":21,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":1080,"id":22,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":1880,"id":23,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":4880,"id":24,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":7980,"id":25,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":9980,"id":26,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0}],"text":"温馨提示:\nIOS用户兑换比例为：1元人民币=7 金币；\nAndroid用户兑换比例为：1元人民币=10 金币；"}
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
         * list : [{"gold":10,"id":18,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":60,"id":19,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":180,"id":20,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":680,"id":21,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":1080,"id":22,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":1880,"id":23,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":4880,"id":24,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":7980,"id":25,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0},{"gold":9980,"id":26,"isActive":0,"msg":"充值金币","name":"充值金币","price":1,"type":0}]
         * text : 温馨提示:
         IOS用户兑换比例为：1元人民币=7 金币；
         Android用户兑换比例为：1元人民币=10 金币；
         */

        private String text;
        private List<ListBean> list;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * gold : 10
             * id : 18
             * isActive : 0
             * msg : 充值金币
             * name : 充值金币
             * price : 1
             * type : 0
             */

            private int gold;
            private int id;
            private int isActive;
            private String msg;
            private String name;
            private int price;
            private int type;

            public int getGold() {
                return gold;
            }

            public void setGold(int gold) {
                this.gold = gold;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsActive() {
                return isActive;
            }

            public void setIsActive(int isActive) {
                this.isActive = isActive;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
