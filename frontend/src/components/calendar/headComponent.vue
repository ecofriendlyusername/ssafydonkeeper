<template>
    <div class="sec_cal">

        <!-- 캘린더 nav -->
        <div class="cal_nav">
            <a href="javascript:;" class="nav-btn go-prev" @click="goPrev">prev</a>
            <div class="year-month">{{ yearMonth }}</div>
            <a href="javascript:;" class="nav-btn go-next" @click="goNext">next</a>
        </div>

        <BodyComponent :year="year" :month="month" :checkList="checkList" @totals="emitsData" />

    </div>
</template>

<script>
import BodyComponent from '@/components/calendar/bodyComponent.vue';
export default {
    data() {
        return {
            year: new Date().getFullYear(),
            month: new Date().getMonth(),
        };
    },
    props: {
        checkList: {
            type: Object,
            required: true
        },
    },
    computed: {
        yearMonth() {
            return `${this.year}.${this.month + 1}`;
        }
    },
    components: {
        BodyComponent
    },
    methods: {
        emitsData(data) {
            this.incom = data.incom;
            this.spend = data.spend;
        },
        goPrev() {
            const day = new Date(this.year, this.month - 1, 1);
            this.year = day.getFullYear()
            this.month = day.getMonth()
        },
        goNext() {
            const day = new Date(this.year, this.month + 1, 1);
            this.year = day.getFullYear()
            this.month = day.getMonth()
        }
    },
}
</script>

<style>
.sec_cal {
    width: 360px;
    margin: 0 auto;
    font-family: "NotoSansR";
}

.sec_cal .cal_nav {
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: 700;
    font-size: 33px;
    line-height: 78px;
}

.sec_cal .cal_nav .year-month {
    width: 300px;
    text-align: center;
    line-height: 1;
}

.sec_cal .cal_nav .nav {
    display: flex;
    border: 1px solid #333333;
    border-radius: 5px;
}

.sec_cal .cal_nav .go-prev,
.sec_cal .cal_nav .go-next {
    display: block;
    width: 50px;
    height: 78px;
    font-size: 0;
    display: flex;
    justify-content: center;
    align-items: center;
}

.sec_cal .cal_nav .go-prev::before,
.sec_cal .cal_nav .go-next::before {
    content: "";
    display: block;
    width: 10px;
    height: 10px;
    border: 3px solid #000;
    border-width: 3px 3px 0 0;
    transition: border 0.1s;
}

.sec_cal .cal_nav .go-prev:hover::before,
.sec_cal .cal_nav .go-next:hover::before {
    border-color: #4285F4;
}

.sec_cal .cal_nav .go-prev::before {
    transform: rotate(-135deg);
}

.sec_cal .cal_nav .go-next::before {
    transform: rotate(45deg);
}

.sec_cal .cal_wrap {
    padding-top: 40px;
    position: relative;
    margin: 0 auto;
}

.sec_cal .cal_wrap .days {
    display: flex;
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #ddd;
}

.sec_cal .cal_wrap::after {
    top: 368px;
}

.sec_cal .cal_wrap .day {
    /* display:flex; */
    align-items: center;
    justify-content: center;
    width: calc(100% / 7);
    text-align: left;
    color: #999;
    font-size: 12px;
    text-align: center;
    border-radius: 5px;
}

.current.today {
    background: rgb(242 242 242);
}

.sec_cal .cal_wrap .dates {
    display: flex;
    flex-flow: wrap;
    height: 290px;

}

.sec_cal .cal_wrap .day:nth-child(7n -1) {
    color: #3c6ffa;
}

.sec_cal .cal_wrap .day:nth-child(7n) {
    color: #ed2a61;
}

.sec_cal .cal_wrap .day.disable {
    color: #ddd;
}

.incom {
    color: blue;
    font-size: 90%;
    margin-top: 3px;
    margin-bottom: -18px;
}

.spend {
    color: red;
    font-size: 90%;
    margin-top: 20px;
}

#calBtn {
    width: 42px;
    margin: 3px;
}

#addBtn {
    width: 57px;
    margin-top: -18px;
    margin-left: 5px;
    margin-right: 5px;
}


#subTitle {
    font-size: 110%;
    margin-bottom: 3px;
}
</style>