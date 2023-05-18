<template>
  <div>
    <h3>{{ data.name }}</h3>
    <div style="border:2px solid black; border-radius: 8px; width: 80%; display: inline-block;">
      <p style="font-weight:bold; border:2px solid black; background-color:white; width: 20%; display: inline-block; margin-top: -60px;">기간</p>
      <div style="margin-top:-20px;">
        <p>시작일: {{ data.startDate }}</p>
        <p style="margin-top:-10px;">마감일: {{ data.endDate }}</p>
      </div>
    </div>
    <div style="border:2px solid black; border-radius: 8px; width: 80%; display: inline-block; margin-top:20px;">
      <p style="font-weight:bold; border:2px solid black; background-color:white; width: 20%; display: inline-block; margin-top: -60px;">내용</p>
      <div style="margin-top:-20px; padding: 5px;">
        <p>{{ data.content }}</p>
      </div>
    </div>
    <br><br>
    <div class="baseBar">
      <div class="gaugeBar"></div>
      <div class="dot_line"></div>
    </div>

    <p v-if="data.success">성공</p>
    <p v-else>실패</p>

    <headComponent :checkList="checkList"/>

  </div>
</template>

<script>
import headComponent from "@/components/calendar/headComponent.vue";
export default {
  components:{
    headComponent
  },
  data() {
    return {
      data: {},
      checkList: [],
      percent: 0,
    }
  },
  methods: {
    getChallengeDetail() {
      this.axios({
        method: 'get',
        url: process.env.VUE_APP_API_URL + `/challenge/finish/${this.$route.params.id}`,
      })
        .then((res) => {
          this.data = res.data
        })
        .then(() => {
          // 여기로 와야 함
          let idx = 0
          this.check = [];
          let currentDate = new Date(this.data.startDate.split('-'));
          while (currentDate <= new Date(this.data.endDate.split('-'))) {
            let tmp = new Date(currentDate)
            this.checkList.push({
              "year": tmp.getFullYear(),
              "month": tmp.getMonth() + 1,
              "day": tmp.getDate(),
              "log": this.data.logs[idx]
            });
            idx += 1
            currentDate.setDate(currentDate.getDate() + 1);
          }
          this.barChart()
          // @@@@@@@@@@@@@@
        })
    },
    barChart() {
      const gauge = document.querySelector(".gaugeBar");
      let tmp = (this.data.logs.match(/1/g) || []).length / this.data.logs.length * 100
      console.log(tmp);
      let limit = tmp < 100 ? tmp : 100;
      if (limit <= 60) {
        gauge.style.backgroundColor = "red";
      } else {
        gauge.style.backgroundColor = "blue";
      }

      for (let i = 0; i < limit; i++) {
        setTimeout(() => { gauge.style.width = `${i}%`; }, i * 10)
      }
    }
  },
  mounted() {
    this.getChallengeDetail()
  }
}
</script>

<style>
.baseBar {
  width: 300px;
  height: 28px;
  background-color: #E5E5E5;
  margin: 0 auto;
  padding: 2px;
  border-radius: 25px;
  display: flex;
  position: relative;
}

.gaugeBar {
  width: 0px;
  height: 28px;
  background-color: #4D82E6;
  border-radius: 25px;
  position: absolute;
}

.dot_line {
  height: 28px;
  border-right: 4px dotted black;
  z-index: 99;
  width: 80%;
  position: absolute;
}
</style>