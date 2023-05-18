<template>
  <div>
    <h3>{{ data.name }} D-{{ data.remainingDuration }}</h3>
    <p>시작일: {{ data.startDate }}</p>
    <p>마감일: {{ data.endDate }}</p>
    <p>콘텐츠 내용: {{ data.content }}</p>

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
      data: [],
      showing: 10,
      checkList: []
    }
  },
  methods: {
    getChallengeDetail() {
      this.axios({
        method: 'get',
        url: process.env.VUE_APP_API_URL + `/challenge/progress/${this.$route.params.id}`,
      })
        .then((res) => {
          this.data = res.data
          console.log("getChallengeDetail")
          console.log(res.data)
        })
        .then(() => {
          let idx = 0
          this.check = [];
          let currentDate = new Date(this.data.startDate.split('-'));
          while (currentDate <= new Date()) {
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
        })
    },
    joinChallenge() {
      //챌린지 참여 잘됨.
      this.axios({
        method: 'get',
        url: process.env.VUE_APP_API_URL + `/challenge/join/${this.$route.params.id}`,
      })
        .then((res) => {
          console.log("joinChallenge")
          console.log(res.data)
        })

    },
  },
  mounted() {
    this.getChallengeDetail()
  }
}
</script>

<style></style>