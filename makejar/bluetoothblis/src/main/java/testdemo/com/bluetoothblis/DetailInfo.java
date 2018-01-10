package testdemo.com.bluetoothblis;

import java.util.List;

/**
 * Created by chenzhuo on 2017/3/7.
 */

public class DetailInfo {


    /**
     * result : 1
     * message : 加载成功
     * pageCount : 1
     * total : 7
     * data : {"orderNumber":"0405006","threshold":"2~8","createDate":"2017-04-05 12:02:37","signDate":"2017-04-06 02:10:43","status":1,"monitors":[{"code":"908002","alarm":1,"temperature":"19.6","collectDate":"2017-04-06 09:30:00"},{"code":"908002","alarm":1,"temperature":"19.5","collectDate":"2017-04-06 09:28:00"},{"code":"908002","alarm":1,"temperature":"19.6","collectDate":"2017-04-06 09:26:00"},{"code":"601079","alarm":1,"temperature":"19.5","collectDate":"2017-04-06 09:24:00"},{"code":"908002","alarm":1,"temperature":"19.6","collectDate":"2017-04-06 09:22:00"},{"code":"601079","alarm":1,"temperature":"19.7","collectDate":"2017-04-06 09:20:00"},{"code":"908002","alarm":1,"temperature":"19.8","collectDate":"2017-04-06 09:18:00"}]}
     */

    private String result;
    private String message;
    private int pageCount;
    private int total;
    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderNumber : 0405006
         * threshold : 2~8
         * createDate : 2017-04-05 12:02:37
         * signDate : 2017-04-06 02:10:43
         * status : 1
         * monitors : [{"code":"908002","alarm":1,"temperature":"19.6","collectDate":"2017-04-06 09:30:00"},{"code":"908002","alarm":1,"temperature":"19.5","collectDate":"2017-04-06 09:28:00"},{"code":"908002","alarm":1,"temperature":"19.6","collectDate":"2017-04-06 09:26:00"},{"code":"601079","alarm":1,"temperature":"19.5","collectDate":"2017-04-06 09:24:00"},{"code":"908002","alarm":1,"temperature":"19.6","collectDate":"2017-04-06 09:22:00"},{"code":"601079","alarm":1,"temperature":"19.7","collectDate":"2017-04-06 09:20:00"},{"code":"908002","alarm":1,"temperature":"19.8","collectDate":"2017-04-06 09:18:00"}]
         */

        private String orderNumber;
        private String threshold;
        private String createDate;
        private String signDate;
        private int status;
        private List<MonitorsBean> monitors;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getThreshold() {
            return threshold;
        }

        public void setThreshold(String threshold) {
            this.threshold = threshold;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<MonitorsBean> getMonitors() {
            return monitors;
        }

        public void setMonitors(List<MonitorsBean> monitors) {
            this.monitors = monitors;
        }

        public static class MonitorsBean {
            @Override
            public String toString() {
                return "MonitorsBean{" +
                        "code='" + code + '\'' +
                        ", alarm=" + alarm +
                        ", temperature='" + temperature + '\'' +
                        ", collectDate='" + collectDate + '\'' +
                        ", fazhi='" + fazhi + '\'' +
                        ", zuigaowen='" + zuigaowen + '\'' +
                        ", zuidiwen='" + zuidiwen + '\'' +
                        '}';
            }

            /**
             * code : 908002
             * alarm : 1
             * temperature : 19.6
             * collectDate : 2017-04-06 09:30:00
             */

            private String code;
            private int alarm;
            private String temperature;
            private String collectDate;
            private String fazhi;
            private String zuigaowen;
            private String zuidiwen;

            public String getFazhi() {
                return fazhi;
            }

            public void setFazhi(String fazhi) {
                this.fazhi = fazhi;
            }

            public String getZuigaowen() {
                return zuigaowen;
            }

            public void setZuigaowen(String zuigaowen) {
                this.zuigaowen = zuigaowen;
            }

            public String getZuidiwen() {
                return zuidiwen;
            }

            public void setZuidiwen(String zuidiwen) {
                this.zuidiwen = zuidiwen;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getAlarm() {
                return alarm;
            }

            public void setAlarm(int alarm) {
                this.alarm = alarm;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getCollectDate() {
                return collectDate;
            }

            public void setCollectDate(String collectDate) {
                this.collectDate = collectDate;
            }
        }
    }
}
