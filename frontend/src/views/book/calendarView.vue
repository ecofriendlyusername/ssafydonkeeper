<template>
  <div class="DivH">
    <div class="sec_cal">
      <!-- 캘린더 nav -->
      <div class="cal_nav">
        <a href="javascript:;" class="nav-btn go-prev" @click="goPrev">prev</a>
        <div class="year-month">{{ yearMonth }}</div>
        <a href="javascript:;" class="nav-btn go-next" @click="goNext">next</a>
      </div>

      <div style="display:flex; justify-content:center;">
        <div style="display:flex; justify-content:space-between; width: 70%;">
          <div>
            <div id="subTitle">
              수입
            </div>
            <div style="color:#4285F4;">
              {{ incom }}
            </div>
          </div>
    
          <div>
            <div id="subTitle">
              지출
            </div>
            <div style="color:#EA4335">
              {{ spend }}
            </div>
          </div>
    
          <div>
            <div id="subTitle">
              잔액
            </div>
            <div style="color:black">
              {{ incom - spend }}
            </div>
          </div>
      </div>  
      </div>
      

      <!-- <div>수입 <p id="income">{{ incom }}</p> | 지출 {{ spend }} | 잔액 {{ incom - spend }}</div> -->
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

      <div style="display:flex; justify-content: end; margin-top: 38px;">
        <p v-on:click="this.$router.push('/book/list')">
          <img src="@/assets/list.png" id="calBtn">
        </p>

        <p v-on:click="null">
        <img src="@/assets/copy.png" id="calBtn">
        </p>

        <p v-on:click="this.$router.push('/book/add')">
        <img src="@/assets/add.png" id="addBtn">
        </p>
      </div>

      <button v-on:click="addData()">dataSet(임시버튼)</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      today: new Date(),
      thisMonth: new Date(),
      days: [],
      // 임시데이터
      incom: 0,
      spend: 0,
      datas:[
      {
          'category':'incom',
          'day':6,
          'money':100000,
        },
        {
          'category':'spend',
          'day':6,
          'money':10000,
        },
        {
          'category':'incom',
          'day':2,
          'money':2000,
        },
        {
          'category':'spend',
          'day':3,
          'money':10000,
        },
        {
          'category':'spend',
          'day':5,
          'money':10000,
        },
      ]
    };
  },
  computed: {
    yearMonth() {
      return `${this.thisMonth.getFullYear()}.${this.thisMonth.getMonth() + 1}`;
    }
  },
  methods: {
    // 데이터 추가하는 함수, mount 걸어야 함, async로 renderCalendar다음에 시작되도록 해야 함
    addData() {
      const dayday = document.querySelectorAll('.current');
      this.datas.forEach(data=>{
        let p = document.createElement("p");
        p.setAttribute('class', data.category)
        p.innerText = data.money;
        dayday[data.day-1].appendChild(p);

        if (data.category == 'incom') {
          this.incom += data.money
        } else {
          this.spend += data.money
        }
      })
      /*
      대충 axios.get(url)
      .then(res => {
        res.data.'리스트이름'.forEach(data=>{
          데이터집어넣기
        })
      })
      */
    },
    renderCalendar() {
      let currentYear = this.thisMonth.getFullYear();
      let currentMonth = this.thisMonth.getMonth();

      let startDay = new Date(currentYear, currentMonth, 0);
      let prevDate = startDay.getDate();
      let prevDay = startDay.getDay();

      let endDay = new Date(currentYear, currentMonth + 1, 0);
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

      if (this.today.getMonth() === currentMonth && this.today.getFullYear() === currentYear) {
        let todayDate = this.today.getDate();
        days[prevDay + todayDate - 1].class += " today";
      }

      this.days = days;
    },
    goPrev() {
      let currentYear = this.thisMonth.getFullYear();
      let currentMonth = this.thisMonth.getMonth();
      this.thisMonth = new Date(currentYear, currentMonth - 1, 1);
      this.renderCalendar();
    },
    goNext() {
      let currentYear = this.thisMonth.getFullYear();
      let currentMonth = this.thisMonth.getMonth();
      this.thisMonth = new Date(currentYear, currentMonth + 1, 1);
      this.renderCalendar();
    }
  },
  async mounted() {
    this.renderCalendar();
    // this.addData();
  }
};
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
    border-radius:5px;
}

.current.today {background: rgb(242 242 242);}

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

.incom{
  color: blue;
  font-size: 90%;
  margin-top: 3px;
  margin-bottom: -18px;
}

.spend{
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