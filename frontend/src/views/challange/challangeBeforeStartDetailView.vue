<template>
  <div>
    <h3>{{ data.name }}</h3>
    <p>시작일: {{ data.startDate }}</p>
    <p>마감일: {{ data.endDate }}</p>
    <p>콘텐츠 내용: {{ data.content }}</p>
    <p>참여자 수: {{ data.participantCount }}</p>
    <p v-if="data.participant" v-on:click="joinCancelChallenge()">참여취소하기</p>
    <p v-else v-on:click="joinChallenge()">참여하기</p>
  </div>
</template>

<script>
export default {
  data(){
    return {
      data: []
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
      this.axios({
        method: 'get',
        url: process.env.VUE_APP_API_URL + `/challenge/join/${this.$route.params.id}`,
      })
        .then((res) => {
          console.log("joinChallenge")
          console.log(res.data)
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
        })
    },
  },
  mounted() {
    this.getChallengeDetail()
  }
}
</script>

<style>

</style>