<template>
  <div>
    <div class="sec_cal">
      <!-- 캘린더 nav -->
      <div class="cal_nav">
        <a href="javascript:;" class="nav-btn go-prev" @click="goPrev">prev</a>
        <div class="year-month">{{ yearMonth }}</div>
        <a href="javascript:;" class="nav-btn go-next" @click="goNext">next</a>
      </div>
      <div>수입 {{ '1,000,000' }} | 지출 {{ '350,000' }} | 잔액 {{ '650,000' }}</div>
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
          <div v-for="day in days" :class="day.class" :key="day.index">{{ day.date }}</div>
        </div>
      </div>
      <router-link to="/book/add">aa</router-link>
      <button>copy</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      today: new Date(),
      thisMonth: new Date(),
      days: []
    };
  },
  computed: {
    yearMonth() {
      return `${this.thisMonth.getFullYear()}.${this.thisMonth.getMonth() + 1}`;
    }
  },
  methods: {
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
  mounted() {
    this.renderCalendar();
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
    font-size: 48px;
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
    width: 20px;
    height: 20px;
    border: 3px solid #000;
    border-width: 3px 3px 0 0;
    transition: border 0.1s;
}

.sec_cal .cal_nav .go-prev:hover::before,
.sec_cal .cal_nav .go-next:hover::before {
    border-color: #ed2a61;
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
    display:flex;
    align-items: center;
    justify-content: center;
    width: calc(100% / 7);
    text-align: left;
    color: #999;
    font-size: 12px;
    text-align: center;
    border-radius:5px
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
</style>