<template>
  <div>
    <h3># {{ data.name }}</h3>
    <p style="margin-top:-15px; margin-bottom: 20px; color:#4D82E6; font-weight: bold;">참여자 수: {{ data.participantCount }}</p>

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


    <p v-if="data.participant" v-on:click="joinCancelChallenge()" id="exitBtn">참여 취소하기</p>
    <p v-else v-on:click="joinChallenge()" id="joinBtn">참여하기</p>
  </div>
</template>

<script>
export default {
  data(){
    return {
      data: [],
    }
  },
  methods:{
    getChallengeDetail() {
      this.axios({
        method: 'get',
        url: process.env.VUE_APP_API_URL + `/challenge/${this.$route.params.id}`,
      })
        .then((res) => {
          this.data = res.data
          console.log("getChallengeDetail")
          console.log(res.data)
        })
    },
    joinChallenge() {
      console.log(this.data.participant);
      this.axios({
        method: 'get',
        url: process.env.VUE_APP_API_URL + `/challenge/join/${this.$route.params.id}`,
      })
        .then((res) => {
          console.log("joinChallenge")
          console.log(res.data)
          this.data.participant = true
          this.data.participantCount += 1
        })
    },
    joinCancelChallenge() {
      this.axios({
        method: 'delete',
        url: process.env.VUE_APP_API_URL + `/challenge/cancel/${this.$route.params.id}`,
      })
        .then((res) => {
          console.log("joinCancelChallenge")
          console.log(res.data)
          this.data.participant = false
          this.data.participantCount -= 1
        })
    },
  },
  mounted() {
    this.getChallengeDetail()
  }
}
</script>

<style>
#joinBtn {
  display: inline-block;
  width: 32%;
  border-radius: 8px;
  background-color: #4D82E6;
  padding:5px;
  color:white;
  font-weight: bold;
}

#exitBtn {
  display: inline-block;
  width: 32%;
  border-radius: 8px;
  background-color: rgb(102, 102, 102);
  padding:5px;
  color:white;
  font-weight: bold;
}

</style>