<template>
  <div>
    <h3>{{ data.name }} D-{{ data.remainingDuration }}</h3>
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
      data: [],
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
  },
  mounted() {
    this.getChallengeDetail()
  }
}
</script>

<style></style>