<template>
    <div class="cal_wrap">
        <!-- 요일 -->
        <div class="days">
            <div class="day">MON</div>
            <div class="day">TUE</div>
            <div class="day">WED</div>
            <div class="day">THU</div>
            <div class="day">FRI</div>
            <div class="day">SAT</div>
            <div class="day">SUN</div>
        </div>
        <!-- 날짜들 -->
        <div class="dates">
            <div v-for="day in days" :class="day.class" :key="day.index" style="height:65px;">
                {{ day.date }}
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            today: new Date(),
            days: [],
            datas: [],
        };
    },
    props: {
        year: {
            type: Number,
            required: true
        },
        month: {
            type: Number,
            required: true
        },
        checkList: {
            type: Object,
            required: true
        },
    },
    watch: {
        checkList: {
            immediate: true,
            handler() {
                new Promise((resolve, reject) => {
                    try {
                        this.renderCalendar();
                        resolve();
                    } catch (error) {
                        reject("Error in getData: " + error);
                    }
                })
                    .then(() => {
                        try {
                            this.addData();
                        } catch (error) {
                            console.error("Error in renderCalendar: " + error);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });
            }
        },
        month: {
            handler() {
                new Promise((resolve, reject) => {
                    try {
                        this.renderCalendar();
                        resolve();
                    } catch (error) {
                        reject("Error in getData: " + error);
                    }
                })
                    .then(() => {
                        try {
                            this.addData();
                        } catch (error) {
                            console.error("Error in renderCalendar: " + error);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });
            }
        }
    },
    methods: {
        addData() {
            const dayday = document.querySelectorAll('.current');
            console.log(this.checkList.filter(el => el.month == this.month + 1));
            this.checkList.filter(el => el.month == this.month + 1)
                .forEach(el => {
                    if (el.log == 1) {
                        let TG = document.createElement("p");
                        TG.innerText = '성공!'
                        dayday[el.day - 1].appendChild(TG);
                    } else if (el.log == 0) {
                        let TG = document.createElement("p");
                        TG.innerText = '실패!'
                        dayday[el.day - 1].appendChild(TG);
                    } else {
                        let TG = document.createElement("button");
                        TG.innerText = '완료'
                        TG.addEventListener('click', () => {
                            this.setChallengeTodaySuccess()
                            event.target.setAttribute('disabled', true)
                            let TG = document.createElement("p");
                            TG.innerText = '성공!'
                            event.target.parentNode.appendChild(TG);
                        })
                        dayday[el.day - 1].appendChild(TG);

                    }
                })

        },
        renderCalendar() {
            let startDay = new Date(this.year, this.month, 0);
            let prevDate = startDay.getDate();
            let prevDay = startDay.getDay();

            let endDay = new Date(this.year, this.month + 1, 0);
            let nextDate = endDay.getDate();
            let nextDay = endDay.getDay();

            let days = [];

            for (let i = prevDate - prevDay + 1; i <= prevDate; i++) {
                days.push({ date: i, class: "day prev disable" });
            }
            for (let i = 1; i <= nextDate; i++) {
                days.push({ date: i, class: "day current" });
            }
            for (let i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
                days.push({ date: i, class: "day next disable" });
            }

            if (this.today.getMonth() === this.month + 1 && this.today.getFullYear() === this.year) {
                let todayDate = this.today.getDate();
                days[prevDay + todayDate - 1].class += "today";
            }

            this.days = days;
        },
        setChallengeTodaySuccess() {
      //오늘 성공 버튼
      this.axios({
        method: 'put',
        url: `http://localhost:8080/api/challenge/progress/success/${this.$route.params.id}`,
      })
        .then((res) => {
          console.log("setChallengeTodaySuccess")
          console.log(res.data)
        })

    },
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