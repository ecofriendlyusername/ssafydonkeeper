<template>
  <div>
    <h3>{{ data.name }}</h3>
    <p>시작일: {{ data.startDate }}</p>
    <p>마감일: {{ data.endDate }}</p>
    <p>콘텐츠 내용: {{ data.content }}</p>

    <div class="baseBar">
      <div class="gaugeBar"></div>
      <div class="dot_line"></div>
    </div>

    <p v-if="data.isSuccess">성공</p>
    <p v-else>실패</p>

    <div>챌린지 기록</div>
    <div v-for="(check, idx) in checkList.filter((el, idx) => idx < showing)" :key="idx">
      <p>{{ check.year + '-' + check.month + '-' + check.day }} {{ check.log == 0 ? "실패" : "성공" }}</p>
    </div>
    <button v-if="checkList.length > showing" @click="showing += 10">더보기</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data: {
        "name": "챌린지이름임",
        "startDate": "2023-07-01",//String임
        "endDate": "2023-08-16",//지난 날짜
        "logs": "1111111111111111111111111111111111111111111111111100000000000000000000",
        "content": 111,
        "isSuccess": false,//성공여부
      },
      checkList: [],
      percent: 0,
      showing: 10,
    }
  },
  methods: {
    getChallengeDetail() {
      // 위치이동 필수
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

      this.axios({
        method: 'get',
        url: `http://localhost:8080/api/challenge/finish/${this.$route.params.id}`,
      })
        .then((res) => {
          this.data = res.data
          console.log("getChallengeDetail")
          console.log(res.data)
        })
        .then(() => {
          // 여기로 와야 함
          // @@@@@@@@@@@@@@
        })
    },
    barChart() {
      const gauge = document.querySelector(".gaugeBar");
      let tmp = (this.data.logs.match(/1/g) || []).length / this.data.logs.length * 100
      console.log(tmp);
      let limit = tmp < 100 ? tmp : 100;
      if (limit == 100) {
        gauge.style.backgroundColor = "red";
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
  width: 60%;
  position: absolute;
}
</style>